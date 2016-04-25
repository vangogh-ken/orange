package com.van.halley.core.cache;

public class RemoteCacheStrategy implements CacheStrategy {
    private Cache cache = new MapCache();
    @Override
    public Cache getCache(String cacheName) {
        return cache;
    }

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void retrieveCache(String cacheName) {
		// TODO Auto-generated method stub
		
	}
}
