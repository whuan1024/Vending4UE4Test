package com.cloudminds.vending.utils;


import android.util.Log;

public class LogUtil {

    private static final String TAG = "CloudVendingApp";

    public void configure() {
        ConfigureLog4j.configure();
    }

    //==========================================================
    // 使用默认的LOGTAG
    //==========================================================

    public static void v(String msg) {
//        Logger LOGGER = Logger.getLogger(TAG);
//        LOGGER.info(msg);
        Log.v(TAG, msg);
    }

    public static void d(String msg) {
//        Logger LOGGER = Logger.getLogger(TAG);
//        LOGGER.debug(msg);
        Log.d(TAG, msg);
    }

    public static void i(String msg) {
//        Logger LOGGER = Logger.getLogger(TAG);
//        LOGGER.info(msg);
        Log.i(TAG, msg);
    }

    public static void w(String msg) {
//        Logger LOGGER = Logger.getLogger(TAG);
//        LOGGER.warn(msg);
        Log.w(TAG, msg);
    }

    public static void e(String msg) {
//        Logger LOGGER = Logger.getLogger(TAG);
//        LOGGER.error(msg);
        Log.e(TAG, msg);
    }

    public static void e(String msg, Throwable tr) {
//        Logger LOGGER = Logger.getLogger(TAG);
//        LOGGER.error(msg, tr);
        Log.e(TAG, msg, tr);
    }

    //==========================================================
    // 使用自定义的LOGTAG
    //==========================================================

    public static void v(String tag, String msg) {
//        Logger LOGGER = Logger.getLogger(tag);
//        LOGGER.info(msg);
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
//        Logger LOGGER = Logger.getLogger(tag);
//        LOGGER.debug(msg);
        Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
//        Logger LOGGER = Logger.getLogger(tag);
//        LOGGER.info(msg);
        Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
//        Logger LOGGER = Logger.getLogger(tag);
//        LOGGER.warn(msg);
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
//        Logger LOGGER = Logger.getLogger(tag);
//        LOGGER.error(msg);
        Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
//        Logger LOGGER = Logger.getLogger(tag);
//        LOGGER.error(msg, tr);
        Log.e(tag, msg, tr);
    }
}
