package com.van.halley.core.cache;

public interface CacheStrategy {
    Cache getCache(String cacheName);
    
    void retrieveCache(String cacheName);
    
    void initialize();
}
