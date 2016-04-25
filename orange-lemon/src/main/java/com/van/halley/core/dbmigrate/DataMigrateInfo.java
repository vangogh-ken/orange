package com.van.halley.core.dbmigrate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataMigrateInfo {
	/**
	 * 是要可用
	 */
	private boolean enable;
	/**
	 * 当前同步数据的版本
	 */
	private String dataVersion;
	/**
	 * 数据名
	 */
	private String sourceDbName;
	/**
	 * 数据名
	 */
	private String targetDbName;

	// ~ tables =====================================
	List<String> defaultTable = new ArrayList<String>();
	List<String> strictTable = new ArrayList<String>();

	// ~ columns =====================================
	Map<String, List<String>> primaryColumns = new HashMap<String, List<String>>();
	Map<String, List<String>> defaultColumns = new HashMap<String, List<String>>();

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(String dataVersion) {
		this.dataVersion = dataVersion;
	}


	public String getSourceDbName() {
		return sourceDbName;
	}

	public void setSourceDbName(String sourceDbName) {
		this.sourceDbName = sourceDbName;
	}

	public String getTargetDbName() {
		return targetDbName;
	}

	public void setTargetDbName(String targetDbName) {
		this.targetDbName = targetDbName;
	}

	public List<String> getDefaultTable() {
		return defaultTable;
	}

	public void setDefaultTable(List<String> defaultTable) {
		this.defaultTable = defaultTable;
	}

	public List<String> getStrictTable() {
		return strictTable;
	}

	public void setStrictTable(List<String> strictTable) {
		this.strictTable = strictTable;
	}

	public Map<String, List<String>> getPrimaryColumns() {
		return primaryColumns;
	}

	public void setPrimaryColumns(Map<String, List<String>> primaryColumns) {
		this.primaryColumns = primaryColumns;
	}

	public Map<String, List<String>> getDefaultColumns() {
		return defaultColumns;
	}

	public void setDefaultColumns(Map<String, List<String>> defaultColumns) {
		this.defaultColumns = defaultColumns;
	}


	/**
	 * 需要跳过的表名
	 */
	/*
	 * private List<String> skipTables = new ArrayList<String>();
	 *//**
		 * 严格迁移表名，目标数据与版本数据一致(可除跳过字段)。
		 */
	/*
	 * private List<String> strictTables = new ArrayList<String>();
	 *//**
		 * 需要跳过的字段
		 */
	/*
	 * private Map<String, List<String>> skipColumns = new HashMap<String,
	 * List<String>>();
	 *//**
		 * 源数据库名
		 */
	/*
	 * private String sourceDbName;
	 *//**
		 * 目标数据库名
		 */
	/*
	 * private String targetDbName;
	 *//**
		 * 源数据源
		 */
	/*
	 * private DataSource sourceDataSource;
	 *//**
		 * 目标数据源
		 */
	/*
	 * private DataSource targetDataSource;
	 *//**
		 * 源数据信息
		 */
	/*
	 * private DatabaseMetaData sourceDbMetaData;
	 *//**
		 * 目标数据信息
		 *//*
		 * private DatabaseMetaData targetDbMetaData;
		 * 
		 * public boolean isEnable() { return enable; }
		 * 
		 * public void setEnable(boolean enable) { this.enable = enable; }
		 * 
		 * public String getDataVersion() { return dataVersion; }
		 * 
		 * public void setDataVersion(String dataVersion) { this.dataVersion =
		 * dataVersion; }
		 * 
		 * public List<String> getSkipTables() { return skipTables; }
		 * 
		 * public void setSkipTables(List<String> skipTables) { this.skipTables
		 * = skipTables; }
		 * 
		 * public List<String> getStrictTables() { return strictTables; }
		 * 
		 * public void setStrictTables(List<String> strictTables) {
		 * this.strictTables = strictTables; }
		 * 
		 * public Map<String, List<String>> getSkipColumns() { return
		 * skipColumns; }
		 * 
		 * public void setSkipColumns(Map<String, List<String>> skipColumns) {
		 * this.skipColumns = skipColumns; }
		 * 
		 * public DataSource getSourceDataSource() { return sourceDataSource; }
		 * 
		 * public void setSourceDataSource(DataSource sourceDataSource) {
		 * this.sourceDataSource = sourceDataSource; }
		 * 
		 * public DataSource getTargetDataSource() { return targetDataSource; }
		 * 
		 * public void setTargetDataSource(DataSource targetDataSource) {
		 * this.targetDataSource = targetDataSource; }
		 * 
		 * public DatabaseMetaData getSourceDbMetaData() { return
		 * sourceDbMetaData; }
		 * 
		 * public void setSourceDbMetaData(DatabaseMetaData sourceDbMetaData) {
		 * this.sourceDbMetaData = sourceDbMetaData; }
		 * 
		 * public DatabaseMetaData getTargetDbMetaData() { return
		 * targetDbMetaData; }
		 * 
		 * public void setTargetDbMetaData(DatabaseMetaData targetDbMetaData) {
		 * this.targetDbMetaData = targetDbMetaData; }
		 * 
		 * public String getSourceDbName() { return sourceDbName; }
		 * 
		 * public void setSourceDbName(String sourceDbName) { this.sourceDbName
		 * = sourceDbName; }
		 * 
		 * public String getTargetDbName() { return targetDbName; }
		 * 
		 * public void setTargetDbName(String targetDbName) { this.targetDbName
		 * = targetDbName; }
		 */
}
