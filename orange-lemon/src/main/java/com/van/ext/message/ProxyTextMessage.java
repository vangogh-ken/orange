package com.van.ext.message;

import javax.jms.TextMessage;

public class ProxyTextMessage extends ProxyMessage implements TextMessage {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
