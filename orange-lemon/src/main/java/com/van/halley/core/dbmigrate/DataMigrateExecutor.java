package com.van.halley.core.dbmigrate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 用于对数据的迁移处理
 * @author anxin
 *
 */
public class DataMigrateExecutor {
	private static Logger LOG = LoggerFactory.getLogger(DataMigrateExecutor.class);
	
	private DataMigrateInfo dataMigrateInfo;
	
	private DataSource sourceDataSource;
	
	private DataSource targetDataSource;
	
	public DataMigrateExecutor(DataMigrateInfo dataMigrateInfo, DataSource sourceDataSource, DataSource targetDataSource){
		this.dataMigrateInfo = dataMigrateInfo;
		this.sourceDataSource = sourceDataSource;
		this.targetDataSource = targetDataSource;
	}
	
	public void migrate(){
		boolean flag = false;
		if(dataMigrateInfo.getDefaultTable() != null && !dataMigrateInfo.getDefaultTable().isEmpty()){
			migrateDefault();
			flag = true;
		}
		if(dataMigrateInfo.getStrictTable() != null && !dataMigrateInfo.getStrictTable().isEmpty()){
			migrateStrict();
			flag = true;
		}
		
		saveVersion(sourceDataSource, dataMigrateInfo.getDataVersion(), flag);
		saveVersion(targetDataSource, dataMigrateInfo.getDataVersion(), flag);
	}
	
	//TODO
    private void migrateDefault(){
    	Connection sourceConn = null;
		Connection targetConn = null;
		
		PreparedStatement sourceSelectPstmt = null;
		PreparedStatement targetSelectPstmt = null;
		
		PreparedStatement batchInsertPstmtTarget = null;
		PreparedStatement batchUpdatePstmtTarget = null;
		
		ResultSet sourceRs = null;
		ResultSet targetRs = null;
		
		List<String> allTables = new ArrayList<String>();
		allTables.addAll(dataMigrateInfo.getDefaultTable());
		allTables.addAll(dataMigrateInfo.getStrictTable());
		
		try{
			sourceConn = sourceDataSource.getConnection();
			targetConn = targetDataSource.getConnection();
			
			targetConn.setAutoCommit(false);
			
			for(String tableName : allTables){
				List<String> columns = dataMigrateInfo.getDefaultColumns().get(tableName);
				List<String> primaries = dataMigrateInfo.getPrimaryColumns().get(tableName);
				
				String selectSource = DataMigrateUtil.buildSelect(tableName, columns);
				String countTarget = DataMigrateUtil.buildCount(tableName, primaries);
				String insertTarget = DataMigrateUtil.buildInsert(tableName, columns);
				String updateTarget = DataMigrateUtil.buildUpdate(tableName, primaries, columns);

				batchInsertPstmtTarget = targetConn.prepareStatement(insertTarget);
				batchUpdatePstmtTarget = targetConn.prepareStatement(updateTarget);
						
				sourceSelectPstmt = sourceConn.prepareStatement(selectSource);
				sourceRs = sourceSelectPstmt.executeQuery();
				while(sourceRs.next()){
					targetSelectPstmt = targetConn.prepareStatement(countTarget);
					for(int i=0, len=primaries.size(); i<len; i++){
						targetSelectPstmt.setObject(i+1, sourceRs.getObject(primaries.get(i)));
					}
					targetRs = targetSelectPstmt.executeQuery();
					if(targetRs.next()){
						if(targetRs.getInt(1) == 0){//could not find any record
							//~ insert record to target DB
							for(int j=0, len=columns.size(); j<len; j++){
								batchInsertPstmtTarget.setObject(j + 1, sourceRs.getObject(columns.get(j)));
							}
							
							batchInsertPstmtTarget.addBatch();
						}else{
							//~ update record to target DB
							for(int j=0, len=columns.size(); j<len; j++){
								batchUpdatePstmtTarget.setObject(j + 1, sourceRs.getObject(columns.get(j)));
							}
							
							for(int j=0, len=primaries.size(); j<len; j++){
								batchUpdatePstmtTarget.setObject(columns.size() + j + 1, sourceRs.getObject(primaries.get(j)));
							}
							
							batchUpdatePstmtTarget.addBatch();
						}
					}else{
						LOG.error("Count record occurs error, table name : {}", tableName);
					}
				}
				
				int[] counts = batchInsertPstmtTarget.executeBatch();
				int result = 0;
				for(int i : counts){
					result += i;
				}
				if(result > 0){
					LOG.info("Insert {} records of table {} to target DB", tableName, result);
				}
				
				counts = batchUpdatePstmtTarget.executeBatch();
				result = 0;
				for(int i : counts){
					result += i;
				}
				
				if(result > 0){
					LOG.info("Update {} records of table {} to target DB", tableName, result);
				}
			}
			
			targetConn.commit();
			targetConn.setAutoCommit(true);
		}catch(SQLException e){
			
			LOG.error("init Db catched error", e);
			
			try{
				targetConn.rollback();
			}catch(SQLException e1){
				LOG.info("data rollback faild, error {} ", e1);
			}
			
			ruinResource(null, batchInsertPstmtTarget, null);
			ruinResource(null, batchUpdatePstmtTarget, null);
			ruinResource(sourceRs, sourceSelectPstmt, sourceConn);
			ruinResource(targetRs, targetSelectPstmt, targetConn);
		}finally{
			ruinResource(null, batchInsertPstmtTarget, null);
			ruinResource(null, batchUpdatePstmtTarget, null);
			ruinResource(sourceRs, sourceSelectPstmt, sourceConn);
			ruinResource(targetRs, targetSelectPstmt, targetConn);
		}
    }
    
    //TODO
    private void migrateStrict(){
    	Connection sourceConn = null;
		Connection targetConn = null;
		
		PreparedStatement sourceSelectPstmt = null;
		PreparedStatement targetSelectPstmt = null;
		
		PreparedStatement batchDeletePstmtTarget = null;
		
		ResultSet sourceRs = null;
		ResultSet targetRs = null;
		
		try{
			sourceConn = sourceDataSource.getConnection();
			targetConn = targetDataSource.getConnection();
			
			targetConn.setAutoCommit(false);
			
			for(String tableName : dataMigrateInfo.getStrictTable()){
				List<String> columns = dataMigrateInfo.getDefaultColumns().get(tableName);
				List<String> primaries = dataMigrateInfo.getPrimaryColumns().get(tableName);
				
				String selectTarget = DataMigrateUtil.buildSelect(tableName, columns);
				String countSource = DataMigrateUtil.buildCount(tableName, primaries);
				String deleteTarget = DataMigrateUtil.buildDelete(tableName, primaries);

				batchDeletePstmtTarget = targetConn.prepareStatement(deleteTarget);
				
				targetSelectPstmt = targetConn.prepareStatement(selectTarget);
				targetRs = targetSelectPstmt.executeQuery();
				while(targetRs.next()){
					sourceSelectPstmt = sourceConn.prepareStatement(countSource);
					for(int i=0, len=primaries.size(); i<len; i++){
						sourceSelectPstmt.setObject(i+1, targetRs.getObject(primaries.get(i)));
					}
					sourceRs = sourceSelectPstmt.executeQuery();
					if(sourceRs.next()){
						if(sourceRs.getInt(1) == 0){//could not find any record
							//~ insert record to target DB
							for(int j=0, len=primaries.size(); j<len; j++){
								batchDeletePstmtTarget.setObject(j + 1, targetRs.getObject(primaries.get(j)));
							}
							
							batchDeletePstmtTarget.addBatch();
						}
					}else{
						LOG.error("Count record occurs error, table name : {}", tableName);
					}
				}
				
				int[] counts = batchDeletePstmtTarget.executeBatch();
				int result = 0;
				for(int i : counts){
					result += i;
				}
				
				if(result > 0){
					LOG.info("Delete {} records of table {} from target DB", tableName, result);
				}
			}
			
			targetConn.commit();
			targetConn.setAutoCommit(true);
		}catch(SQLException e){
			
			LOG.error("init Db catched error", e);
			
			try{
				targetConn.rollback();
			}catch(SQLException e1){
				LOG.info("data rollback faild, error {} ", e1);
			}
			
			ruinResource(null, batchDeletePstmtTarget, null);
			ruinResource(sourceRs, sourceSelectPstmt, sourceConn);
			ruinResource(targetRs, targetSelectPstmt, targetConn);
		}finally{
			ruinResource(null, batchDeletePstmtTarget, null);
			ruinResource(sourceRs, sourceSelectPstmt, sourceConn);
			ruinResource(targetRs, targetSelectPstmt, targetConn);
		}
    }
    
    public boolean saveVersion(DataSource dataSource, String version, boolean isSuccess){
		String sql = "UPDATE V_VERSION SET IS_SUCCESS = ? , EXECUTE_TIME=SYSDATE() WHERE VERSION=?";
		Connection conn = null;
    	PreparedStatement pst = null;
    	try{
    		conn = dataSource.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, isSuccess ? "T" : "F");
			pst.setString(2, version);
			int count = pst.executeUpdate();
			if(count > 0){
				LOG.info("Updated version success, new version: {}", version);
				return true;
			}
    	} catch (SQLException e) {
    		ruinResource(null, pst, conn);
    		LOG.error("dataMigrate Db catched error", e);
		} finally{
			ruinResource(null, pst, conn);
		}
    	return false;
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
    
    
    //~ getter and setter 

	public DataMigrateInfo getDataMigrateInfo() {
		return dataMigrateInfo;
	}

	public void setDataMigrateInfo(DataMigrateInfo dataMigrateInfo) {
		this.dataMigrateInfo = dataMigrateInfo;
	}

	public DataSource getSourceDataSource() {
		return sourceDataSource;
	}

	public void setSourceDataSource(DataSource sourceDataSource) {
		this.sourceDataSource = sourceDataSource;
	}

	public DataSource getTargetDataSource() {
		return targetDataSource;
	}

	public void setTargetDataSource(DataSource targetDataSource) {
		this.targetDataSource = targetDataSource;
	}
    
    
    
}
