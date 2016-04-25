package com.van.halley.core.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

public class LocalCacheStrategy implements CacheStrategy {
    private Map<String, Cache> cacheMap = new HashMap<String, Cache>();
    @Override
    public Cache getCache(String cacheName) {
        Cache cache = cacheMap.get(cacheName);

        if (cache != null) {
            return cache;
        }

        cache = new MapCache();
        cacheMap.put(cacheName, cache);

        return cache;
    }

	@Override
	@PostConstruct
	public void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	public void retrieveCache(String cacheName) {
		cacheMap.put(cacheName, null);
	}
}
