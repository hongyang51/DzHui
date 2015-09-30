package com.lanou3g.mydazahui.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by dllo on 15/9/30.
 */
public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mContext == null) {
            mContext = this;
        }
//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);     		// 初始化 JPush
    }


    public static Context getContext() {
        return mContext;
    }
}
