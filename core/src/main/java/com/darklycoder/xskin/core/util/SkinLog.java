package com.darklycoder.xskin.core.util;

import android.util.Log;

import com.darklycoder.xskin.core.config.SkinConfig;

public class SkinLog {

    private static final String TAG = "XSkin";

    private SkinLog() {
        throw new AssertionError();
    }

    public static void i(String msg) {
        if (SkinConfig.DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (SkinConfig.DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (SkinConfig.DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (SkinConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (SkinConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (SkinConfig.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

}
