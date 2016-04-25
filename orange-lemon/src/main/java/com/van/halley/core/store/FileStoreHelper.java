package com.van.halley.core.store;

import java.io.File;
import java.io.FileOutputStream;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.UUID;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class FileStoreHelper implements StoreHelper {
    private static Logger LOG = LoggerFactory.getLogger(FileStoreHelper.class);
    
    @Value("${store.dir}")
    private String storeDir;

    @Override
    public StoreResult getStore(String model, String key) throws Exception {
        if (key == null) {
        	LOG.info("key cannot be null");
            return null;
        }

        if (key.indexOf("../") != -1) {
            StoreResult storeResult = new StoreResult();
            storeResult.setModel(model);
            storeResult.setKey(key);

            return storeResult;
        }

        File file = new File(storeDir + "/" + model + "/" + key);

        if (!file.exists()) {
        	LOG.info("cannot find : {}", file);

            return null;
        }

        StoreResult storeResult = new StoreResult();
        storeResult.setModel(model);
        storeResult.setKey(key);
        storeResult.setDataSource(new FileDataSource(file));

        return storeResult;
    }
    
    @Override
	public String getDir(String model, String key) throws Exception {
    	if (key == null) {
        	LOG.info("key cannot be null");
            return null;
        }

        if (key.indexOf("../") != -1) {
            return null;
        }

        File file = new File(storeDir + "/" + model + "/" + key);

        if (!file.exists() || !file.isDirectory()) {
        	LOG.info("cannot find : {}", file);
            return null;
        }else{
        	return file.getAbsolutePath();
        }
	}

    @Override
    public void removeStore(String model, String key) throws Exception {
        if (key.indexOf("../") != -1) {
            return;
        }

        File file = new File(storeDir + "/" + model + "/" + key);
        file.delete();
    }

    @Override
    public StoreResult addStore(String model, DataSource dataSource)
            throws Exception {
        String prefix = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String suffix = getSuffix(dataSource.getName());
        String path = prefix + "/" + UUID.randomUUID() + "." + suffix;
        File dir = new File(storeDir + "/" + model + "/" + prefix);
        dir.mkdirs();

        File targetFile = new File(storeDir + "/" + model + "/" + path);
        FileOutputStream fos = new FileOutputStream(targetFile);

        try {
            FileCopyUtils.copy(dataSource.getInputStream(), fos);
            fos.flush();
        } finally {
            fos.close();
        }

        StoreResult storeResult = new StoreResult();
        storeResult.setModel(model);
        storeResult.setKey(path);
        storeResult.setDataSource(new FileDataSource(targetFile));

        return storeResult;
    }

    @Override
    public StoreResult addStore(String model, String key, DataSource dataSource)
            throws Exception {
        File dir = new File(storeDir + "/" + model);
        dir.mkdirs();

        File targetFile = new File(storeDir + "/" + model + "/" + key);
        FileOutputStream fos = new FileOutputStream(targetFile);

        try {
            FileCopyUtils.copy(dataSource.getInputStream(), fos);
            fos.flush();
        } finally {
            fos.close();
        }

        StoreResult storeResult = new StoreResult();
        storeResult.setModel(model);
        storeResult.setKey(key);
        storeResult.setDataSource(new FileDataSource(targetFile));

        return storeResult;
    }
    
    @Override
	public StoreResult budgeStore(String model, String key, String nModel)throws Exception {
		StoreResult origin = getStore(model, key);
		StoreResult immigration = addStore(nModel, origin.getDataSource());
		FileUtils.deleteQuietly(((FileDataSource)origin.getDataSource()).getFile());
		return immigration;
	}

    public static String getSuffix(String text) {
        int lastIndex = text.lastIndexOf(".");

        if (lastIndex != -1) {
            return text.substring(lastIndex + 1);
        } else {
            return "";
        }
    }

	public String getStoreDir() {
		return storeDir;
	}

	public void setStoreDir(String storeDir) {
		this.storeDir = storeDir;
	}
	
	public static void main(String[] args) {
		System.out.println(getSuffix("dfsdf.fsf.xl"));
	}
	
}
