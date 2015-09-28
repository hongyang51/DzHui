package com.lanou3g.mydazahui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou3g.mydazahui.bean.Groom;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.utils.CircleImageView;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 15/9/25.
 */
public class GroomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Groom.ItemsEntity.RecommendersEntity> recommendersEntities;
    private ArrayList<Groom.EditorsEntity> editorsEntity;
    private VolleySingleton singleton;
    private ImageLoader imageLoader;

    public GroomAdapter(Context context, ArrayList<Groom.ItemsEntity.RecommendersEntity> recommendersEntities,
                        ArrayList<Groom.EditorsEntity> editorsEntity) {
        this.context = context;
        this.recommendersEntities = recommendersEntities;
        this.editorsEntity = editorsEntity;
        singleton = VolleySingleton.getVolleySingleton(context);
        imageLoader = singleton.getImageLoader();
    }


    @Override
    public int getCount() {

        int i = 0;
        if (recommendersEntities != null && recommendersEntities.size() > 0) {
            i = recommendersEntities.size();
        } else if (editorsEntity != null && editorsEntity.size() > 0) {
            i = editorsEntity.size();
        }
        return i;
    }

    @Override
    public Object getItem(int position) {
        Object o = null;
        if (recommendersEntities != null && recommendersEntities.size() > 0) {
            o = recommendersEntities.get(position);
        } else if (editorsEntity != null && editorsEntity.size() > 0) {
            o = editorsEntity.get(position);
        }
        return o;
    }

    @Override
    public long getItemId(int position) {
        int i = 0;
        if (recommendersEntities != null && recommendersEntities.size() > 0) {
            i = position;
        } else if (editorsEntity != null && editorsEntity.size() > 0) {
            i = position;
        }

        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.groom_list_item, null);
            viewHolder.avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.bio = (TextView) convertView.findViewById(R.id.bio);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.avatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);;
        if (recommendersEntities != null && recommendersEntities.size() > 0) {
            Groom.ItemsEntity.RecommendersEntity recommendersEntity = (Groom.ItemsEntity.RecommendersEntity) getItem(position);


            String url = recommendersEntity.getAvatar();
            imageLoader.get(url,listener);
            viewHolder.name.setText(recommendersEntity.getName());
            viewHolder.bio.setText(recommendersEntity.getBio());
//            Log.e("sssss", recommendersEntity.getName() + "ssss");
        } else if (editorsEntity != null && editorsEntity.size() > 0) {
            Groom.EditorsEntity EditorsEntity = (Groom.EditorsEntity) getItem(position);
//        recommendersEntity.getAvatar();
            viewHolder.name.setText(EditorsEntity.getName());
            viewHolder.bio.setText(EditorsEntity.getBio());
            String url = EditorsEntity.getAvatar();
            imageLoader.get(url, listener);
//            Log.e("sssss", EditorsEntity.getName() + "ssss");
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView name, bio;
        private CircleImageView avatar;
    }

}
