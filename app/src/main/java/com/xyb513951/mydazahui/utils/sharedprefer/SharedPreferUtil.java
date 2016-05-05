package com.xyb513951.mydazahui.utils.sharedprefer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences 工具封装类
 * Created by dllo on 15/9/22.
 */
public class SharedPreferUtil {
    private static final String PREF_NAME = "config";

    public static boolean getBoolean(Context context ,String key,
                                     boolean defaulfValue) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,defaulfValue);
    }
    public static void setBoolean(Context context ,String key,
                                     boolean value) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key,value).commit();
    }
}
