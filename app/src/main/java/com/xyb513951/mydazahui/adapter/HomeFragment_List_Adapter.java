package com.xyb513951.mydazahui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou3g.mydazahui.R;
import com.xyb513951.mydazahui.activity.WebViewActivity;
import com.xyb513951.mydazahui.base.Final_Base;
import com.xyb513951.mydazahui.greendaobean.StoriesEntity;
import com.xyb513951.mydazahui.utils.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 15/9/23.
 */
public class HomeFragment_List_Adapter extends BaseAdapter {
    private Context context;
    private VolleySingleton singleton;
    private ImageLoader imageLoader;
    private ArrayList<StoriesEntity> storiesEntities;



    public HomeFragment_List_Adapter(Context context, ArrayList<StoriesEntity> storiesEntities) {
        this.context = context;
        this.storiesEntities = storiesEntities;
        singleton = VolleySingleton.getVolleySingleton(context);
        imageLoader = singleton.getImageLoader();
    }


    public void OnLoading(ArrayList<StoriesEntity> storiesEntities) {
        this.storiesEntities.addAll(storiesEntities);
        Log.e("sss", "加载更多");
        notifyDataSetChanged();

    }

    public void OnRefreshing(ArrayList<StoriesEntity> storiesEntities) {
        this.storiesEntities.clear();
        this.storiesEntities.addAll(storiesEntities);
        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.home_listview_item, null);
            viewHolder.news_list_item_text = (TextView) convertView.findViewById(R.id.news_list_item_text);
            viewHolder.news_list_item_img = (ImageView) convertView.findViewById(R.id.news_list_item_img);
            viewHolder.cardView  = (CardView) convertView.findViewById(R.id.cardView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        StoriesEntity storiesEntity = (StoriesEntity) getItem(position);
        if (storiesEntity != null) {
            viewHolder.news_list_item_text.setText(storiesEntity.getTitle());
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.news_list_item_img,R.mipmap.lanniao,
                    R.mipmap.lanniao);
            if(storiesEntity.getImages()!=null){
                imageLoader.get(storiesEntity.getImages().get(0),listener);
            }

            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(context, WebViewActivity.class);
                        int newsId = storiesEntities.get(position).getId();
                        intent.putExtra(Final_Base.NEWSID, newsId);
                        context.startActivity(intent);
                        Log.e("ID", storiesEntities.get(position).getId() + "");
                }
            });
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView news_list_item_text;
        private ImageView news_list_item_img;
        private CardView cardView;
    }
}
