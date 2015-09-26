package com.lanou3g.mydazahui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.lanou3g.mydazahui.Activity.WebViewActivity;
import com.lanou3g.mydazahui.Adapter.NewsFragment_List_Adapter;
import com.lanou3g.mydazahui.Base.AbsBaseFragment;
import com.lanou3g.mydazahui.Base.Final_Base;
import com.lanou3g.mydazahui.Bean.Theme;
import com.lanou3g.mydazahui.Bean.ThemeNews;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by xyb on 15/9/21.
 */
public class OneNewFragment extends AbsBaseFragment {
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


    public static OneNewFragment getFragment(int position,ArrayList<Theme.OthersEntity> othersEntities){
        OneNewFragment f = new OneNewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("others",othersEntities);
        bundle.putInt("arg",position);
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container) {
        static_img_view = inflater.inflate(R.layout.fragment_static_img,null);
        return inflater.inflate(R.layout.allnews_listview,container,false);
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
        listener = ImageLoader.getImageListener(static_img,R.mipmap.lanniao,R.mipmap.lanniao);
        int arg = (int) getArguments().get("arg");
        ArrayList<Theme.OthersEntity> others = (ArrayList<Theme.OthersEntity>) getArguments().getSerializable("others");
        // 拼接链接
        String UrlAdd =Final_Base.THEMES_URL+others.get(arg).getId();
        storiesEntities = new ArrayList<>();
        list_adapter = new NewsFragment_List_Adapter(mActivity,storiesEntities);
        allnews_listview.addHeaderView(static_img_view);
        allnews_listview.setAdapter(list_adapter);
        allnews_listview.setDivider(null);
        allnews_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position>0) {
                    Log.e("ID", storiesEntities.get(position - 1).getId() + "");
                    int newsId = storiesEntities.get(position - 1).getId();// 因为设置了list头所有position-1
                    Intent intent = new Intent(mActivity, WebViewActivity.class);
                    intent.putExtra(Final_Base.NEWSID, newsId);
                    startActivity(intent);
                }
            }
        });
        request = new StringRequest(UrlAdd, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ThemeNews themeNews = JSON.parseObject(response, ThemeNews.class);
                static_text.setText(themeNews.getDescription());
                imageLoader.get(themeNews.getBackground(),listener);
                storiesEntities = (ArrayList<ThemeNews.StoriesEntity>) themeNews.getStories();
                list_adapter.addDatas(storiesEntities);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        singleton.addQueue(request, Final_Base.THEMES_URL_REQUEUE_TAG);

    }


}