package com.lanou3g.mydazahui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lanou3g.mydazahui.Bean.ThemeNews;
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
    private LayoutInflater infalter;

    public NewsFragment_List_Adapter(Context context, List<ThemeNews.StoriesEntity> storiesEntities) {
        this.infalter = LayoutInflater.from(context);
        this.storiesEntities = storiesEntities;
        singleton = VolleySingleton.getVolleySingleton(context);
        imageLoader = singleton.getImageLoader();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = infalter.inflate(R.layout.news_listview_item, null);
            viewHolder.news_list_item_text = (TextView) convertView.findViewById(R.id.news_list_item_text);
            viewHolder.img = (NetworkImageView) convertView.findViewById(R.id.news_list_item_img);
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


        }
        return convertView;
    }

    private class ViewHolder {
        private TextView news_list_item_text;
        private NetworkImageView img;
    }
}
