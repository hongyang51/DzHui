package com.xyb513951.mydazahui.base;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by dllo on 15/9/30.
 */
public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        if (mContext == null) {
            mContext = this;
        }
        super.onCreate();
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=561b70cb");
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }


    public static Context getContext() {
        return mContext;
    }
}
