package com.lanou3g.mydazahui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou3g.mydazahui.adapter.News_ViewPager_FragmentAda;
import com.lanou3g.mydazahui.base.BaseFragment;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.bean.Theme;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * 新闻页面
 * Created by dllo on 15/9/22.
 */
public class NewsFragment extends BaseFragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private VolleySingleton singleton;
    private StringRequest request;
    private ArrayList<Theme.OthersEntity> othersEntities;
    public ViewPager getmViewPager() {
        return mViewPager;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_others_tablayout_item, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        singleton = VolleySingleton.getVolleySingleton(mActivity);
        mTabLayout = (TabLayout) view.findViewById(R.id.TabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

    }

    @Override
    public void initData() {
        othersEntities = new ArrayList<>();
        request = new StringRequest(Final_Base.THEME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Theme theme = gson.fromJson(response,Theme.class);
                othersEntities = (ArrayList<Theme.OthersEntity>) theme.getOthers();
                News_ViewPager_FragmentAda fragmentAda = new News_ViewPager_FragmentAda(getChildFragmentManager(),othersEntities);
                mViewPager.setAdapter(fragmentAda);
                mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                mTabLayout.setupWithViewPager(mViewPager);
                mTabLayout.setSelectedTabIndicatorColor(0xff0088ff);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("解析失败", "网络拉取失败"+"NewsFragment");
            }
        });
        request.setShouldCache(false);
        singleton.addQueue(request,Final_Base.THEME_URL_REQUEUE_TAG);
    }
}
