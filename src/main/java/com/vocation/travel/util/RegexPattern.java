package com.vocation.travel.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex Pattern.
 *
 * @author MQViet
 * */
public class RegexPattern {

    private static final String PHONE_PATTERN = "^(\\+84|0)\\d{9,10}$";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public boolean regexPhone(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean regexEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
