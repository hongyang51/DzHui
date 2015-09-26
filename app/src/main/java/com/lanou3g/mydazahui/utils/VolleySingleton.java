package com.lanou3g.mydazahui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
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
    private static StringRequest stringRequest;


    // 构造方法私有,防止其他类中通过new 创建对象
    private VolleySingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10 * 1024 * 1024);

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

    public StringRequest getStringRequest(String Url){

        stringRequest = new StringRequest(Url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        addQueue(stringRequest,"asd");

        return stringRequest;
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
