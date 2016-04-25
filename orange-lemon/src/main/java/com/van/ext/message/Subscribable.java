package com.van.ext.message;

public interface Subscribable<T> {
    void handleMessage(T message);

    String getTopic();
}
