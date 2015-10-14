package com.lanou3g.mydazahui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.adapter.NewsFragment_List_Adapter;
import com.lanou3g.mydazahui.base.AbsBaseFragment;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.bean.ThemeNews;
import com.lanou3g.mydazahui.greendaobean.OthersEntity;
import com.lanou3g.mydazahui.listview.SwipeRefreshLoadingLayout;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by xyb on 15/9/21.
 */
public class OneNewFragment extends AbsBaseFragment implements SwipeRefreshLoadingLayout.OnRefreshListener, SwipeRefreshLoadingLayout.OnLoadListener {
    private ArrayList<ThemeNews.StoriesEntity> storiesEntities;
    private ListView allnews_listview;
    private NewsFragment_List_Adapter list_adapter;
    private StringRequest request;
    private VolleySingleton singleton;
    private View static_img_view;
    private ImageView static_img;
    private TextView static_text;
    private ImageLoader imageLoader;
    private ImageLoader.ImageListener listener;
    private SwipeRefreshLoadingLayout swipeRefreshLoadingLayout;
    private String urlAdd;
    private int newsId;


    public static OneNewFragment getFragment(int position, ArrayList<OthersEntity> othersEntities) {
        OneNewFragment f = new OneNewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("others", othersEntities);
        bundle.putInt("arg", position);
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container) {
        static_img_view = inflater.inflate(R.layout.fragment_static_img, null);

        return inflater.inflate(R.layout.allnews_listview, container, false);
    }

    @Override
    public void initView() {
        allnews_listview = (ListView) view.findViewById(R.id.allnews_listview);
        static_img = (ImageView) static_img_view.findViewById(R.id.static_img);
        static_text = (TextView) static_img_view.findViewById(R.id.static_text);


    }

    @Override
    public void initData() {
        singleton = VolleySingleton.getVolleySingleton(mActivity);
        imageLoader = singleton.getImageLoader();
        listener = ImageLoader.getImageListener(static_img, R.mipmap.lanniao, R.mipmap.lanniao);
        int arg = (int) getArguments().get("arg");
        ArrayList<OthersEntity> others = (ArrayList<OthersEntity>) getArguments().getSerializable("others");
        // 拼接链接
        newsId = others.get(arg).getId();
        urlAdd = Final_Base.THEMES_URL + newsId;
        storiesEntities = new ArrayList<>();
        list_adapter = new NewsFragment_List_Adapter(mActivity, storiesEntities);
        allnews_listview.addHeaderView(static_img_view);
        allnews_listview.setAdapter(list_adapter);
        swipeRefreshLoadingLayout = (SwipeRefreshLoadingLayout) view.findViewById(R.id.swipeRefreshLoadingLayout);
        swipeRefreshLoadingLayout.setOnRefreshListener(this);
        swipeRefreshLoadingLayout.setOnLoadListener(this);
        swipeRefreshLoadingLayout.setColor(R.color.color_2, R.color.color_1, R.color.color_4, R.color.color_3);

        request = new StringRequest(urlAdd, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ThemeNews themeNews = JSON.parseObject(response, ThemeNews.class);
                static_text.setText(themeNews.getDescription());
                imageLoader.get(themeNews.getBackground(), listener);
                storiesEntities = (ArrayList<ThemeNews.StoriesEntity>) themeNews.getStories();
                list_adapter.addDatas(storiesEntities);

                StartAnimation();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("解析失败", "网络拉取失败"+"OneNewFragment");
            }
        });
        request.setShouldCache(false);
        singleton.addQueue(request, Final_Base.THEMES_URL_REQUEUE_TAG);

    }

    private void StartAnimation() {
        // 缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.3f, 1, 1.3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(5000);// 设置动画的时间
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        static_img.setAnimation(scaleAnimation);
    }

    //     刷新方法
    @Override
    public void onRefresh() {
        list_adapter.OnRefreshing(urlAdd, swipeRefreshLoadingLayout);
    }

    //   加载方法
    @Override
    public void onLoad() {
        list_adapter.OnLoading(newsId, swipeRefreshLoadingLayout);
    }
}
