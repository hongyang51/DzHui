package com.lanou3g.mydazahui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.bean.HappyComment;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dllo on 15/9/29.
 */
public class HappyComment_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<HappyComment.CommentsEntity> commentsEntities;
    private LayoutInflater inflater;
    private VolleySingleton singleton;
    private ImageLoader imageLoader;

    public HappyComment_Adapter(Context context, ArrayList<HappyComment.CommentsEntity> commentsEntities) {
        this.context = context;
        this.commentsEntities = commentsEntities;
        inflater = LayoutInflater.from(context);
        singleton = VolleySingleton.getVolleySingleton(context);
        imageLoader = singleton.getImageLoader();
    }

    @Override
    public int getCount() {
        return commentsEntities != null && commentsEntities.size() > 0 ? commentsEntities.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return commentsEntities != null && commentsEntities.size() > 0 ? commentsEntities.get(position) : null;

    }

    @Override
    public long getItemId(int position) {
        return commentsEntities != null && commentsEntities.size() > 0 ? position : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.happycommnet_listview_item, null);
            holder.groom_img = (CircleImageView) convertView.findViewById(R.id.groom_img);
            holder.Title = (TextView) convertView.findViewById(R.id.Title);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.times = (TextView) convertView.findViewById(R.id.times);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HappyComment.CommentsEntity entity = (HappyComment.CommentsEntity) getItem(position);
        holder.Title.setText(entity.getUsername());
        holder.time.setText(entity.getContent());
        int i = position + 1;
        holder.times.setText(i+"");
        String User_cover_url = Final_Base.HAPPY_URL + entity.getCover_url_100x100();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.groom_img, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(User_cover_url, listener);


        return convertView;
    }

    private class ViewHolder {
        private CircleImageView groom_img;
        private TextView Title, time, times;
    }
}
