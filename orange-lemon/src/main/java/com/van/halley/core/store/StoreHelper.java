package com.van.halley.core.store;

import javax.activation.DataSource;

public interface StoreHelper {
    StoreResult getStore(String model, String key) throws Exception;

    String getDir(String model, String key) throws Exception;
    
    void removeStore(String model, String key) throws Exception;

    StoreResult addStore(String model, DataSource dataSource) throws Exception;

    StoreResult addStore(String model, String key, DataSource dataSource)throws Exception;
    
    /**
     * 移动文件
     * @param model
     * @param key
     * @param nModel
     * @param nKey
     */
    StoreResult budgeStore(String model, String key, String nModel)throws Exception;
}
