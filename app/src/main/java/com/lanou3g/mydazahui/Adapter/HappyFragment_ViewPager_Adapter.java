package com.lanou3g.mydazahui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
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

/**
 * Created by dllo on 15/9/28.
 */
public class HappyFragment_ViewPager_Adapter extends PagerAdapter implements SwipeRefreshLoadingLayout.OnRefreshListener, SwipeRefreshLoadingLayout.OnLoadListener {
    private Context context;
    private ListView listView;
    private HappyFragment_ListView_Adapter adapter;
    private String[] Url = {Final_Base.HAPPY_URL_TOP + "popular" + Final_Base.HAPPY_URL_CENTER
            + 0 + Final_Base.HAPPY_URL_BOTTOM, Final_Base.HAPPY_URL_TOP + "new" + Final_Base.HAPPY_URL_CENTER
            + 0 + Final_Base.HAPPY_URL_BOTTOM};
    private ArrayList<Happy.jokes> jokes;
    private VolleySingleton singleton;
    private SwipeRefreshLoadingLayout loadingLayout;
    private SwipeRefreshLoadingLayout.OnRefreshListener listener;
    private int i = 0;
    public HappyFragment_ViewPager_Adapter(Context context) {
        this.context = context;
        singleton = VolleySingleton.getVolleySingleton(context);

    }

    @Override
    public int getCount() {
        return Url.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
         i = position;
        final View view = View.inflate(context, R.layout.happy_viewpager_item, null);
        loadingLayout = (SwipeRefreshLoadingLayout) view.findViewById(R.id.loadingLayout);

        jokes = new ArrayList<>();
//        Log.e("sss", "-------" + Url[position]);
        StringRequest request = new StringRequest(Url[position], new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.e("s", "--------" + response);
                Gson gson = new Gson();
                Happy happy = gson.fromJson(response, Happy.class);
                jokes = (ArrayList<Happy.jokes>) happy.getJokes();
                Log.e("sss","当前网址为"+Url[position]);
                adapter = new HappyFragment_ListView_Adapter(context, jokes,Url[position],loadingLayout);
                adapter.notifyDataSetChanged();
                notifyDataSetChanged();
                listView = (ListView) view.findViewById(R.id.happy_listview);
                listView.setAdapter(adapter);
//                loadingLayout.setOnRefreshListener(this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        singleton.addQueue(request, "Happy");
        notifyDataSetChanged();
        loadingLayout.setOnRefreshListener(this);
        loadingLayout.setOnLoadListener(this);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    // 上拉加载
    @Override
    public void onLoad() {
//        adapter.Loading(jokes);
//        loadingLayout.setLoading(false);
        adapter.Loading(loadingLayout);


    }

    

//    @Override
//    public void onRefresh() {
//
//    }

    // 下拉刷新
    @Override
    public void onRefresh() {
        adapter.Refreshing(loadingLayout);

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
}
