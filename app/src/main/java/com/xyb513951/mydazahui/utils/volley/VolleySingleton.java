package com.xyb513951.mydazahui.utils.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by xyb on 15/9/17.
 */
public class VolleySingleton {
    private static final String TAG = "VolleySingleton";
    private static VolleySingleton volleySingleton;
    private static RequestQueue requestQueue;
    private static Context context;
    private static ImageLoader imageLoader;


    // 构造方法私有,防止其他类中通过new 创建对象
    private VolleySingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            LruCache<String, Bitmap> cache = new LruCache<>(10 * 1024 * 1024);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }


    //提供一个静态方法,获取本类的实例
    public static VolleySingleton getVolleySingleton(Context context) {
        if (volleySingleton == null) {
            volleySingleton = new VolleySingleton(context);
        }

        return volleySingleton;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    //提供一个方法,将Request添加到队列
    public <T> void addQueue(Request<T> request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }

    // 将request移除队列
    public void removeRequest(Object object) {
        getRequestQueue().cancelAll(object);
    }


}
