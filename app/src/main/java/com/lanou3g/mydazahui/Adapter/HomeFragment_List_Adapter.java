package com.lanou3g.mydazahui.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou3g.mydazahui.Bean.LatestNews;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 15/9/23.
 */
public class HomeFragment_List_Adapter extends BaseAdapter {
    private Context context;
    private VolleySingleton singleton;
    private ImageLoader imageLoader;
    private ArrayList<LatestNews.StoriesEntity> storiesEntities;

    public HomeFragment_List_Adapter(Context context, ArrayList<LatestNews.StoriesEntity> storiesEntities) {
        this.context = context;
        this.storiesEntities = storiesEntities;
        singleton = VolleySingleton.getVolleySingleton(context);
        imageLoader = singleton.getImageLoader();
    }

    @Override
    public int getCount() {
        return storiesEntities != null && storiesEntities.size() > 0 ? storiesEntities.size() : 5;
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
            convertView = View.inflate(context, R.layout.home_listview_item, null);
            viewHolder.news_list_item_text = (TextView) convertView.findViewById(R.id.news_list_item_text);
            viewHolder.news_list_item_img = (ImageView) convertView.findViewById(R.id.news_list_item_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LatestNews.StoriesEntity storiesEntity = (LatestNews.StoriesEntity) getItem(position);
        if (storiesEntity != null) {
            viewHolder.news_list_item_text.setText(storiesEntity.getTitle());
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.news_list_item_img,R.mipmap.lanniao,
                    R.mipmap.lanniao);
                imageLoader.get(storiesEntity.getImages().get(0),listener);

        }
        return convertView;
    }

    private class ViewHolder {
        private TextView news_list_item_text;
        private ImageView news_list_item_img;
    }
}
