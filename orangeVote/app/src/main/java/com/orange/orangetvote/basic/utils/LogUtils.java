package com.orange.orangetvote.basic.utils;

import android.util.Log;

import com.orange.orangetvote.BuildConfig;

/**
 * Log日誌
 *
 * */
public class LogUtils {

    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final int DEBUG_LEVEL = 0;
    private static final int WRITE_LEVEL = 1;
    private static final int ERROR_LEVEL = 2;
    private static final int INFORMATION_LEVEL = 3;

    /**
     * 獲取當前Class名稱
     *
     * @return  String
     * */
    private static String getClassName(){
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        String result = thisMethodStack.getClassName();
        result = result.substring(result.lastIndexOf("."), result.length());
        return result;
    }

    /**
     * System.out.println輸出
     *
     * @param tag       tag名稱
     * @param message   輸出內容
     * */
    public static void print(String tag, String message){
        if(DEBUG){
            System.out.println(getClassName() + " : " + message);
        }
    }

    /**
     * System.out.println輸出
     *
     * @param message 輸出內容
     * */
    public static void print(String message){
        print(getClassName(), message);
    }

    /**
     * Log輸出
     *
     * @param level     log的等級
     * @param tag       tag名稱
     * @param message   log輸出內容
     * */
    private static void log(int level, String tag, String message){
        if(DEBUG){
            switch (level){
                case DEBUG_LEVEL:
                    Log.d(tag, message);
                    break;
                case WRITE_LEVEL:
                    Log.w(tag, message);
                    break;
                case ERROR_LEVEL:
                    Log.e(tag, message);
                    break;
                case INFORMATION_LEVEL:
                    Log.i(tag, message);
                    break;
            }
        }
    }

    /**
     * Log.d輸出
     *
     * @param message   log輸出內容
     * */
    public static void d(String message){
        log(DEBUG_LEVEL, getClassName(), message);
    }

    /**
     * Log.d輸出
     *
     * @param tag       tag名稱
     * @param message   log輸出內容
     * */
    public static void d(String tag, String message){
        log(DEBUG_LEVEL, tag, message);
    }

    /**
     * Log.w輸出
     *
     * @param message   log輸出內容
     * */
    public static void w(String message){
        log(WRITE_LEVEL, getClassName(), message);
    }

    /**
     * Log.w輸出
     *
     * @param tag       tag名稱
     * @param message   log輸出內容
     * */
    public static void w(String tag, String message){
        log(WRITE_LEVEL, tag, message);
    }

    /**
     * Log.e輸出
     *
     * @param message   log輸出內容
     * */
    public static void e(String message){
        log(ERROR_LEVEL, getClassName(), message);
    }

    /**
     * Log.e輸出
     *
     * @param tag       tag名稱
     * @param message   log輸出內容
     * */
    public static void e(String tag, String message){
        log(ERROR_LEVEL, tag, message);
    }

    /**
     * Log.i輸出
     *
     * @param message   log輸出內容
     * */
    public static void i(String message){
        log(INFORMATION_LEVEL, getClassName(), message);
    }

    /**
     * Log.i輸出
     *
     * @param tag       tag名稱
     * @param message   log輸出內容
     * */
    public static void i(String tag, String message){
        log(INFORMATION_LEVEL, tag, message);
    }
}
