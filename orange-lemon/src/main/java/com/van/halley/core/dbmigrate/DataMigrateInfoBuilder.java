package com.van.halley.core.dbmigrate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.van.halley.util.StringUtil;

/**
 * 
 * 数据版本迁移严格数据库表名、字段必须一致。
 * 
 * @author anxinxx
 *
 */
public class DataMigrateInfoBuilder {
	private static Logger LOG = LoggerFactory.getLogger(DataMigrateInfoBuilder.class);
	
	/**
	 * default version table name, strict
	 */
	private static final String VERSION_TABLE = "V_VERSION";
	private static final String TEST_TABLE = "TEST";
	
	public void build(DataMigrateInfo dataMigrateInfo, Properties properties, DataSource sourceDataSource,
			DataSource targetDataSource) {
		boolean enable = "true".equals(properties.getProperty("dataMigrate.enable")) ? true : false;
		
		String configXmlPath = properties.getProperty("dataMigrate.configXml");
		List<String> skipTables = DataMigrateUtil.getSkipTables(configXmlPath);
		List<String> strictTables = DataMigrateUtil.getStrictTables(configXmlPath);
		Map<String, List<String>> skipColumns = DataMigrateUtil.getSkipColumns(configXmlPath);
		
		dataMigrateInfo.setEnable(enable);
		dataMigrateInfo.setSourceDbName(properties.getProperty("dataMigrate.sourceDbName"));
		dataMigrateInfo.setTargetDbName(properties.getProperty("dataMigrate.targetDbName"));
		
		setVersion(dataMigrateInfo, sourceDataSource, targetDataSource);
		setVariables(dataMigrateInfo, sourceDataSource, skipTables, strictTables, skipColumns);
	}
	
	public void setVariables(DataMigrateInfo dataMigrateInfo, DataSource dataSource, 
			List<String> skips, List<String> stricts, Map<String, List<String>> skipColumns){
		//~ tables =====================================
		List<String> defaultTable = new ArrayList<String>();
		List<String> strictTable = new ArrayList<String>();
		
		//~ columns =====================================
		Map<String, List<String>> primaryColumns = new HashMap<String, List<String>>();
		Map<String, List<String>> defaultColumns = new HashMap<String, List<String>>();
		Connection conn = null;
		ResultSet rsTable = null;
		ResultSet rsPrimary = null;
		ResultSet rsColumn = null;
		try{
			conn = dataSource.getConnection();
			DatabaseMetaData metaData = conn.getMetaData();
			
			rsTable = metaData.getTables(null, dataMigrateInfo.getSourceDbName(), null, null);
			while(rsTable.next()){
				String tableName = rsTable.getString("TABLE_NAME").toUpperCase();
				////skip table
				if(skips.contains(tableName) || VERSION_TABLE.equalsIgnoreCase(tableName) || TEST_TABLE.equalsIgnoreCase(tableName)){
					continue;
				}
				
				rsPrimary = metaData.getPrimaryKeys(null, metaData.getSchemaTerm(), tableName);
				List<String> primary = new ArrayList<String>();
				while(rsPrimary.next()){
					primary.add(rsPrimary.getString("COLUMN_NAME").toUpperCase());
				}
				List<String> column = new ArrayList<String>();
				
				rsColumn = metaData.getColumns(null, metaData.getSchemaTerm(), tableName, null);
				while(rsColumn.next()){
					String columnName = rsColumn.getString("COLUMN_NAME").toUpperCase();
					if(skipColumns.containsKey(tableName)){
						if(!skipColumns.get(tableName).contains(columnName)){
							column.add(columnName);
						}
					}else{
						column.add(columnName);
					}
				}
				
				primaryColumns.put(tableName, primary);
				defaultColumns.put(tableName, column);
				if(stricts.contains(tableName)){
					strictTable.add(tableName);
				}else{
					defaultTable.add(tableName);
				}
			}
			
			dataMigrateInfo.setDefaultTable(defaultTable);
			dataMigrateInfo.setStrictTable(strictTable);
			dataMigrateInfo.setPrimaryColumns(primaryColumns);
			dataMigrateInfo.setDefaultColumns(defaultColumns);
		}catch(SQLException e){
			ruinResource(rsColumn, null, null);
			ruinResource(rsPrimary, null, null);
			ruinResource(rsTable, null, conn);
		}finally{
			ruinResource(rsColumn, null, null);
			ruinResource(rsPrimary, null, null);
			ruinResource(rsTable, null, conn);
		}
	}
	
	public void setVersion(DataMigrateInfo dataMigrateInfo, DataSource sourceDataSource, DataSource targetDataSource){
		if(!existsVersion(sourceDataSource)){
			createVersion(sourceDataSource);
			
			dataMigrateInfo.setDataVersion(StringUtil.customFormat(new Date(), "yyyyMMddHH"));
			initVersion(dataMigrateInfo, sourceDataSource);
		}else{
			dataMigrateInfo.setDataVersion(selectVersion(sourceDataSource));
		}
		
		if(!existsVersion(targetDataSource)){
			createVersion(targetDataSource);
			initVersion(dataMigrateInfo, targetDataSource);
		}else{
			//if get the same version, so skip the data migrate
			if(dataMigrateInfo.getDataVersion().equals(selectVersion(targetDataSource))){
				dataMigrateInfo.setEnable(false);
			}else{
				initVersion(dataMigrateInfo, targetDataSource);
			}
		}
	}
	
	public void initVersion(DataMigrateInfo dataMigrateInfo, DataSource dataSource){
		String sql = "INSERT INTO V_VERSION VALUES(UUID(), '" + dataMigrateInfo.getDataVersion() +"', NULL, NULL, NULL, NULL, NULL, NULL, NULL)";
		Connection conn = null;
    	PreparedStatement pst = null;
    	try{
    		conn = dataSource.getConnection();
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
    	} catch (SQLException e) {
    		ruinResource(null, pst, conn);
    		LOG.error("dataMigrate Db catched error", e);
		} finally{
			ruinResource(null, pst, conn);
		}
	}
	
	public boolean existsVersion(DataSource dataSource){
		Connection conn = null;
		ResultSet rs = null;
		boolean flag = false;
		try{
			conn = dataSource.getConnection();
			DatabaseMetaData metaData = conn.getMetaData();
			rs = metaData.getTables(null, metaData.getSchemaTerm(), VERSION_TABLE, null);
			if(rs.next()){
				flag = true;
			}
		}catch (SQLException e){
			ruinResource(rs, null, conn);
			LOG.error("dataMigrate Db catched error", e);
		}finally{
			ruinResource(rs, null, conn);
		}
		
		return flag;
	}
	
	public void createVersion(DataSource dataSource){
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE V_VERSION(");
    	sql.append("ID VARCHAR(64) PRIMARY KEY NOT NULL,");
    	sql.append("VERSION VARCHAR(64) UNIQUE,");
    	sql.append("IS_SUCCESS VARCHAR(64),");
    	sql.append("EXECUTE_TIME TIMESTAMP NULL,");
    	sql.append("DESCN VARCHAR(256),");
    	sql.append("STATUS VARCHAR(64),");
    	sql.append("CREATE_TIME TIMESTAMP NOT NULL DEFAULT NOW(),");
    	sql.append("MODIFY_TIME TIMESTAMP,");
    	sql.append("DISP_INX INT");
    	sql.append(");");
    	Connection conn = null;
    	PreparedStatement pst = null;
    	try{
    		conn = dataSource.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.executeUpdate();
			
			LOG.info("create version table success. sql: {}", sql.toString());
    	} catch (SQLException e) {
    		ruinResource(null, pst, conn);
    		LOG.error("dataMigrate Db catched error", e);
		} finally{
			ruinResource(null, pst, conn);
		}
	}
	
	public String selectVersion(DataSource dataSource){
		String sql = "SELECT VERSION FROM V_VERSION ORDER BY CREATE_TIME DESC LIMIT 1";
		Connection conn = null;
    	PreparedStatement pst = null;
    	ResultSet rs = null;
    	try{
    		conn = dataSource.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery(sql);
			if(rs.next()){
				return rs.getString("VERSION");
			}
    	} catch (SQLException e) {
    		ruinResource(rs, pst, conn);
    		LOG.error("dataMigrate Db catched error", e);
		} finally{
			ruinResource(rs, pst, conn);
		}
    	
    	return null;
	}
	
	/**
     * ruin resources
     */
    public void ruinResource(ResultSet rs, Statement stmt, Connection conn){
    	try {
	    	if(rs != null){
	    		rs.close();
	    	}
	    	if(stmt != null){
	    		stmt.close();
	    	}
	    	if(conn != null){
	    		conn.close();
	    	}
    	}catch (SQLException e) {
    		LOG.error("ruinResouce Db catched error, {}", e);
		}
    }
}
