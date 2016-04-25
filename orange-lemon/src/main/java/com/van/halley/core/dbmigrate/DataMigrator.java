package com.van.halley.core.dbmigrate;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义只针对数据的迁移
 * @author Think
 *
 */
public class DataMigrator {
	private static Logger LOG = LoggerFactory.getLogger(DataMigrator.class);
    private DataSource sourceDataSource;
    /**
     * 目标数据源
     */
    private DataSource targetDataSource;
    /**
     * 系统配置属性
     */
    private Properties applicationProperties;
    
    private DataMigrateInfo dataMigrateInfo = new DataMigrateInfo();
    
    private DataMigrateInfoBuilder dataMigrateInfoBuilder = new DataMigrateInfoBuilder();
    
    @PostConstruct
    public void execute() {
    	dataMigrateInfoBuilder.build(dataMigrateInfo, applicationProperties, sourceDataSource, targetDataSource);
    	if(dataMigrateInfo.isEnable()){
    		LOG.info("Doing dataMigrate...");
    		DataMigrateExecutor dataMigrateExecutor = new DataMigrateExecutor(dataMigrateInfo, sourceDataSource, targetDataSource);
    		dataMigrateExecutor.migrate();
    	}else{
    		LOG.info("Skip dataMigrate...");
    	}
    }
    
    public void setApplicationProperties(Properties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

	public void setSourceDataSource(DataSource sourceDataSource) {
		this.sourceDataSource = sourceDataSource;
	}

	public void setTargetDataSource(DataSource targetDataSource) {
		this.targetDataSource = targetDataSource;
	}

	public DataSource getSourceDataSource() {
		return sourceDataSource;
	}

	public DataSource getTargetDataSource() {
		return targetDataSource;
	}

	public Properties getApplicationProperties() {
		return applicationProperties;
	}
	
}
