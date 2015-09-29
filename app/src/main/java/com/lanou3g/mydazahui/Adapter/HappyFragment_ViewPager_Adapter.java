package com.lanou3g.mydazahui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.bean.Happy;
import com.lanou3g.mydazahui.listview.SwipeRefreshLoadingLayout;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 15/9/28.
 */
public class HappyFragment_ViewPager_Adapter extends PagerAdapter {
    private Context context;
//    private ListView listView;
    private HappyFragment_ListView_Adapter ada,ada1;
    private String[] Url = {Final_Base.HAPPY_URL_TOP + "popular" + Final_Base.HAPPY_URL_CENTER
            + 0 + Final_Base.HAPPY_URL_BOTTOM, Final_Base.HAPPY_URL_TOP + "new" + Final_Base.HAPPY_URL_CENTER
            + 0 + Final_Base.HAPPY_URL_BOTTOM};
    private ArrayList<Happy.jokes> jokes;
    private VolleySingleton singleton;
    private SwipeRefreshLoadingLayout loadingLayoutOne;
    private SwipeRefreshLoadingLayout loadingLayoutTwo;
    //    private Handler handler;
    private LayoutInflater inflater;
    private int  PId = 0;
    private int  NId = 0;

    private View view;
    private Map<Integer, View> views;


    public HappyFragment_ViewPager_Adapter(Context context) {
        this.context = context;
        singleton = VolleySingleton.getVolleySingleton(context);
        inflater = LayoutInflater.from(context);
        views = new HashMap<>();
//        handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                if (msg != null && msg.what == 207) {
//                    i = (int) msg.obj;
//                    Log.e("Handler", i + " ");
//                }
//                return false;
//            }
//        });

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private View getView(final int position) {
        view = views.get(position);
        if (view == null) {
            view = inflater.inflate(R.layout.happy_viewpager_item, null);

            final ListView listView = (ListView) view.findViewById(R.id.happy_listview);

            StringRequest request = new StringRequest(Url[position], new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    Happy happy = gson.fromJson(response, Happy.class);
                    ArrayList<Happy.jokes> jokes = (ArrayList<Happy.jokes>) happy.getJokes();
                    Log.e("当前获取URl", "当前网址为" + Url[position]);
                    HappyFragment_ListView_Adapter  adapter = new HappyFragment_ListView_Adapter(context, jokes);
                    if (position == 0){
                    ada = adapter;

                    }else{
                        ada1 = adapter;
                    }

                    listView.setAdapter(adapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            if(position == 0){
                loadingLayoutOne = (SwipeRefreshLoadingLayout) view.findViewById(R.id.loadingLayout);

            }else{
                loadingLayoutTwo = (SwipeRefreshLoadingLayout) view.findViewById(R.id.loadingLayout);
            }

            singleton.addQueue(request, "Happy");
            views.put(position, view);
        }

        return view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(position);


        if (position == 0) {

            loadingLayoutOne.setOnLoadListener(new ZooLoadLick());
            loadingLayoutOne.setOnRefreshListener(new ZooClick());
            Log.e("当前点击的是", 0 + "");
        } else {
            loadingLayoutTwo.setOnLoadListener(new OneLoadClick());
            loadingLayoutTwo.setOnRefreshListener(new OneClick());
            Log.e("当前点击的是", 1 + "");
        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private class OneLoadClick implements SwipeRefreshLoadingLayout.OnLoadListener {
        @Override
        public void onLoad() {
            NId = NId + 15;
            String Url = Final_Base.HAPPY_URL_TOP + "new" + Final_Base.HAPPY_URL_CENTER
                    + NId + Final_Base.HAPPY_URL_BOTTOM;
            StringRequest request = new StringRequest(Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    Happy happy = gson.fromJson(response, Happy.class);
                    jokes = (ArrayList<Happy.jokes>) happy.getJokes();
                    ada1.Loading(jokes);
                    loadingLayoutTwo.setLoading(false);
                    Log.e("当前加载的是", 1 + "");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            singleton.addQueue(request, "request");


        }
    }

    private void initOnLoad(String Url, final SwipeRefreshLoadingLayout loadingLayout) {


    }


    private class OneClick implements SwipeRefreshLoadingLayout.OnRefreshListener {
        @Override
        public void onRefresh() {

            String Url = Final_Base.HAPPY_URL_TOP + "new" + Final_Base.HAPPY_URL_CENTER
                    + 0 + Final_Base.HAPPY_URL_BOTTOM;
            StringRequest request = new StringRequest(Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Gson gson = new Gson();
                    Happy happy = gson.fromJson(response, Happy.class);
                    jokes = (ArrayList<Happy.jokes>) happy.getJokes();
                    ada1.Refreshing(jokes);
                    loadingLayoutTwo.setRefreshing(false);
                    Log.e("当前刷新的是", 1 + "");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            singleton.addQueue(request, "request");
            NId = 0;
        }
    }

    private class ZooClick implements SwipeRefreshLoadingLayout.OnRefreshListener {

        @Override
        public void onRefresh() {

            String Url = Final_Base.HAPPY_URL_TOP + "popular" + Final_Base.HAPPY_URL_CENTER
                    + 0 + Final_Base.HAPPY_URL_BOTTOM;
            StringRequest request = new StringRequest(Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Gson gson = new Gson();
                    Happy happy = gson.fromJson(response, Happy.class);
                    jokes = (ArrayList<Happy.jokes>) happy.getJokes();
                    ada.Refreshing(jokes);
                    loadingLayoutOne.setRefreshing(false);
                    Log.e("当前刷新的是", 0 + "");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            singleton.addQueue(request, "request");
            PId = 0;
        }
    }

    private class ZooLoadLick implements SwipeRefreshLoadingLayout.OnLoadListener {
        @Override
        public void onLoad() {
            PId = PId + 15;
            String Url = Final_Base.HAPPY_URL_TOP + "popular" + Final_Base.HAPPY_URL_CENTER
                    + PId + Final_Base.HAPPY_URL_BOTTOM;
            StringRequest request = new StringRequest(Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    Happy happy = gson.fromJson(response, Happy.class);
                    jokes = (ArrayList<Happy.jokes>) happy.getJokes();
                    ada.Loading(jokes);
                    loadingLayoutOne.setLoading(false);
                    Log.e("当前加载的是", 0 + "");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            singleton.addQueue(request, "request");

        }
    }
//        i = i + 15;
//        String[] Url = {Final_Base.HAPPY_URL_TOP + "popular" + Final_Base.HAPPY_URL_CENTER
//                + 0 + Final_Base.HAPPY_URL_BOTTOM, Final_Base.HAPPY_URL_TOP + "new" + Final_Base.HAPPY_URL_CENTER
//                + 0 + Final_Base.HAPPY_URL_BOTTOM};
//        StringRequest request = new StringRequest(Url[i], new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("sss","------------>"+response);
//                Gson gson = new Gson();
//                Happy happy = gson.fromJson(response, Happy.class);
//                jokes = (ArrayList<Happy.jokes>) happy.getJokes();
//                adapter.Refreshing(jokes);
//                loadingLayout.setRefreshing(false);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        singleton.addQueue(request, "request");

}
