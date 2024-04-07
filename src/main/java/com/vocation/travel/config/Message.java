package com.vocation.travel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Message {
    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key, Object[] object) {
        return messageSource.getMessage(key, object, Locale.ENGLISH);
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.ENGLISH);
    }

}