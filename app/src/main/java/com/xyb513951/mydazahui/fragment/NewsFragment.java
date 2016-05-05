package com.xyb513951.mydazahui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou3g.mydazahui.R;
import com.xyb513951.mydazahui.adapter.News_ViewPager_FragmentAda;
import com.xyb513951.mydazahui.base.BaseFragment;
import com.xyb513951.mydazahui.base.Final_Base;
import com.xyb513951.mydazahui.bean.Theme;
import com.xyb513951.mydazahui.greendaobean.OthersEntity;
import com.xyb513951.mydazahui.greendaobean.OthersEntityDao;
import com.xyb513951.mydazahui.utils.daosingleton.DaoSingleton;
import com.xyb513951.mydazahui.utils.volley.VolleySingleton;

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
    private ArrayList<OthersEntity> othersEntities;
    private OthersEntityDao othersEntityDao;
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
        othersEntityDao = DaoSingleton.getInstance().getOthersEntityDao();
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
                othersEntities = (ArrayList<OthersEntity>) theme.getOthers();
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
                othersEntities = (ArrayList<OthersEntity>) othersEntityDao.loadAll();
                News_ViewPager_FragmentAda fragmentAda = new News_ViewPager_FragmentAda(getChildFragmentManager(),othersEntities);
                mViewPager.setAdapter(fragmentAda);
                mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                mTabLayout.setupWithViewPager(mViewPager);
                mTabLayout.setSelectedTabIndicatorColor(0xff0088ff);
            }
        });
        request.setShouldCache(false);
        singleton.addQueue(request,Final_Base.THEME_URL_REQUEUE_TAG);
    }
}
