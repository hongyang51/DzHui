package com.lanou3g.mydazahui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.lanou3g.mydazahui.activity.WebViewActivity;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.bean.ThemeNews;
import com.lanou3g.mydazahui.listview.SwipeRefreshLoadingLayout;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 15/9/23.
 */
public class NewsFragment_List_Adapter extends BaseAdapter {
    private VolleySingleton singleton;
    private ImageLoader imageLoader;
    private List<ThemeNews.StoriesEntity> storiesEntities;
    private List<ThemeNews.StoriesEntity> storiesEntitiesTwo;
    private LayoutInflater infalter;
    private Context context;


    public NewsFragment_List_Adapter(Context context, List<ThemeNews.StoriesEntity> storiesEntities) {
        this.context = context;
        this.infalter = LayoutInflater.from(context);
        this.storiesEntities = storiesEntities;
        singleton = VolleySingleton.getVolleySingleton(context);
        imageLoader = singleton.getImageLoader();
    }

    public void OnLoading(int newsId, final SwipeRefreshLoadingLayout swipeRefreshLoadingLayout) {
        storiesEntitiesTwo = new ArrayList<>();
        int LoadId = storiesEntities.get(storiesEntities.size() - 1).getId();

        final String s = Final_Base.THEMES_URL + newsId + "/before/" + LoadId;
        Log.e("sss", "" + s);
        StringRequest stringRequest = new StringRequest(s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ThemeNews themeNews = JSON.parseObject(response, ThemeNews.class);
                storiesEntitiesTwo = themeNews.getStories();
                swipeRefreshLoadingLayout.setLoading(false);
                initOnloading();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        singleton.addQueue(stringRequest, "Onrefreshing");
        Log.e("sss", "加载更多");
    }

    private void initOnloading() {
        this.storiesEntities.addAll(storiesEntitiesTwo);
        Log.e("ssss", "s111ss");
        notifyDataSetChanged();
    }

    public void OnRefreshing(String urlAdd, final SwipeRefreshLoadingLayout swipeRefreshLoadingLayout) {
        storiesEntitiesTwo = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(urlAdd, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ThemeNews themeNews = JSON.parseObject(response, ThemeNews.class);
                storiesEntitiesTwo = themeNews.getStories();
                swipeRefreshLoadingLayout.setRefreshing(false);
                initOnrefreshing();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        singleton.addQueue(stringRequest, "Onrefreshing");

    }

    private void initOnrefreshing() {
        this.storiesEntities.clear();
        this.storiesEntities.addAll(storiesEntitiesTwo);
        notifyDataSetChanged();
    }


    public void addDatas(List<ThemeNews.StoriesEntity> storiesEntities) {
        if (this.storiesEntities == null) {
            this.storiesEntities = new ArrayList<>();
        } else {
            this.storiesEntities.clear();
        }
        this.storiesEntities.addAll(storiesEntities);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return storiesEntities != null && storiesEntities.size() > 0 ? storiesEntities.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return storiesEntities != null && storiesEntities.size() > 0 ? storiesEntities.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return storiesEntities != null && storiesEntities.size() > 0 ? position : 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = infalter.inflate(R.layout.news_listview_item, null);
            viewHolder.news_list_item_text = (TextView) convertView.findViewById(R.id.news_list_item_text);
            viewHolder.img = (NetworkImageView) convertView.findViewById(R.id.news_list_item_img);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.cardView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ThemeNews.StoriesEntity storiesEntity = (ThemeNews.StoriesEntity) getItem(position);
        if (storiesEntity != null) {
            viewHolder.news_list_item_text.setText(storiesEntity.getTitle());
            String[] urls = storiesEntity.getImages();
            String url = "";
            if (urls != null) {
                url = urls[0];
                viewHolder.img.setVisibility(View.VISIBLE);
                viewHolder.img.setDefaultImageResId(R.mipmap.ic_launcher);
                viewHolder.img.setErrorImageResId(R.mipmap.ic_launcher);
                viewHolder.img.setImageUrl(url, imageLoader);
            } else {
                viewHolder.img.setVisibility(View.GONE);
            }
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("ID", storiesEntities.get(position).getId() + ".......");
                    int newsId = storiesEntities.get(position).getId();// 因为设置了list头所有position-1
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(Final_Base.NEWSID, newsId);
                    context.startActivity(intent);
                }
            });

        }
        return convertView;
    }

    private class ViewHolder {
        private TextView news_list_item_text;
        private NetworkImageView img;
        private CardView cardView;
    }


//    public interface toFragment {
//
//    }
}
