package com.van.core.spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderSupport;

import com.van.halley.util.file.IoUtil;

public class ApplicationPropertiesFactoryBean extends PropertiesLoaderSupport
        implements FactoryBean<Properties>, InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(ApplicationPropertiesFactoryBean.class);
    //加载路径为"classpath:/properties.lst等的properties文件
    private ResourceLoader resourceLoader = new DefaultResourceLoader();
    private String propertiesListLocation = "classpath:/properties.lst";
    private Properties properties;
    
    public boolean isSingleton() {
        return true;
    }
    
    
    public void afterPropertiesSet() throws IOException {
        this.readProperties();// 判断加载哪些properties
        this.setIgnoreResourceNotFound(true);// 默认设置找不到资源也不会报错
        this.properties = mergeProperties();// 加载properties
    }
    
    public Properties getObject() throws IOException {
        return properties;
    }
    
    public Properties getProperties(){
        return properties;
    }

    public Class<Properties> getObjectType() {
        return Properties.class;
    }

    protected void readProperties() throws IOException {
        Resource propertiesListResource = resourceLoader
                .getResource(propertiesListLocation);
        List<Resource> resources = new ArrayList<Resource>();

        if (!propertiesListResource.exists()) {
            logger.info("use default properties");
            resources.add(resourceLoader
                    .getResource("classpath:/properties/application.properties"));
         
            //resources.add(resourceLoader
                   // .getResource("classpath:/properties/log4j.properties"));
            
        } else {
            String text = IoUtil.readString(propertiesListResource);

            for (String str : text.split("\n")) {
                str = str.trim();

                if (str.length() == 0) {
                    continue;
                }
                resources.add(resourceLoader.getResource(str));
            }
        }
        //设置资源以加载
        setLocations(resources.toArray(new Resource[0]));
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<String, Object>();

        for (String key : properties.stringPropertyNames()) {
            map.put(key, properties.get(key));
        }
        return map;
    }

    public String getPropertiesListLocation() {
        return propertiesListLocation;
    }

    public void setPropertiesListLocation(String propertiesListLocation) {
        this.propertiesListLocation = propertiesListLocation;
    }
    
}
