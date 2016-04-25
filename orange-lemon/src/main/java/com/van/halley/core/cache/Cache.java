package com.van.halley.core.cache;

public interface Cache {
    <T> T get(String key);

    void put(String key, Object value);

    void remove(String key);
}
