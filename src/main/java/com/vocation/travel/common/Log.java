package com.vocation.travel.common;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Log {
    public static void startLog(String method) {
        log.info("METHOD: {}", method);
    }

    public static void startLog(String service, String method) {
        log.info("START SERVICE: {}", service);
        log.info("START METHOD: {}", method);
    }

    public static void inputLog(Object... data) {
        log.info("INPUT: {}", data);
    }

    public static void debugLog(Object... data) {
        log.debug("DATA: {}", data);
    }

    public static void outputLog(Object data) {
        log.debug(  "Result: {}", data);
    }

    public static void endLog(String service, String method) {
        log.info("END METHOD: {}", method);
        log.info("END SERVICE: {}", service);
    }

    public static void errorLog(String message, Exception exception) {
        log.error("ERROR: {}", message, exception);
    }

    public static void errorLog(Exception exception) {
        log.error("ERROR: ", exception);
    }

    public static void errorLog(String message) {
        log.error("ERROR: {}", message);
    }
}
