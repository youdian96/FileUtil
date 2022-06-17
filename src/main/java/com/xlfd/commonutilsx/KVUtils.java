package com.xlfd.commonutilsx;

import android.content.Context;
import android.util.Log;

import com.tencent.mmkv.MMKV;

/**
 * 使用前现在application中进行初始化
 */
public class KVUtils {

    private static final String TAG = "KVUtils";

    private static MMKV mmkv;

    public static void init(Context context) {
        if (mmkv != null) {
            Log.w(TAG, "已经初始化过了无需再初始化");
            return;
        }
        MMKV.initialize(context);
        mmkv = MMKV.defaultMMKV();
    }

    public static void save(String key, String value) {
        mmkv.encode(key, value);
    }

    public static void save(String key, int value) {
        mmkv.encode(key, value);
    }

    public static void save(String key, Long value) {
        mmkv.encode(key, value);
    }

    public static void save(String key, boolean value) {
        mmkv.encode(key, value);
    }

    public static void save(String key, float value) {
        mmkv.encode(key, value);
    }

    public static String getString(String key, String defaultValue) {
        return mmkv.getString(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return mmkv.getInt(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return mmkv.getLong(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return mmkv.getBoolean(key, defaultValue);
    }

    public static double getFloat(String key, float defaultValue) {
        return mmkv.getFloat(key, defaultValue);
    }


}
