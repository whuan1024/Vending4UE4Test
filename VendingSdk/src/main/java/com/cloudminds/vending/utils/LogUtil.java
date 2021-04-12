package com.cloudminds.vending.utils;

import org.apache.log4j.Logger;

public class LogUtil {

    private static final String TAG = "CloudVendingApp";

    public void configure() {
        ConfigureLog4j.configure();
    }

    //==========================================================
    // 使用默认的LOGTAG
    //==========================================================

    public static void v(String msg) {
        Logger LOGGER = Logger.getLogger(TAG);
        LOGGER.info(msg);
    }

    public static void d(String msg) {
        Logger LOGGER = Logger.getLogger(TAG);
        LOGGER.debug(msg);
    }

    public static void i(String msg) {
        Logger LOGGER = Logger.getLogger(TAG);
        LOGGER.info(msg);
    }

    public static void w(String msg) {
        Logger LOGGER = Logger.getLogger(TAG);
        LOGGER.warn(msg);
    }

    public static void e(String msg) {
        Logger LOGGER = Logger.getLogger(TAG);
        LOGGER.error(msg);
    }

    public static void e(String msg, Throwable tr) {
        Logger LOGGER = Logger.getLogger(TAG);
        LOGGER.error(msg, tr);
    }

    //==========================================================
    // 使用自定义的LOGTAG
    //==========================================================

    public static void v(String tag, String msg) {
        Logger LOGGER = Logger.getLogger(tag);
        LOGGER.info(msg);
    }

    public static void d(String tag, String msg) {
        Logger LOGGER = Logger.getLogger(tag);
        LOGGER.debug(msg);
    }

    public static void i(String tag, String msg) {
        Logger LOGGER = Logger.getLogger(tag);
        LOGGER.info(msg);
    }

    public static void w(String tag, String msg) {
        Logger LOGGER = Logger.getLogger(tag);
        LOGGER.warn(msg);
    }

    public static void e(String tag, String msg) {
        Logger LOGGER = Logger.getLogger(tag);
        LOGGER.error(msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        Logger LOGGER = Logger.getLogger(tag);
        LOGGER.error(msg, tr);
    }
}
