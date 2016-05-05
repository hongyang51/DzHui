package com.xyb513951.mydazahui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanou3g.mydazahui.R;
import com.xyb513951.mydazahui.greendaobean.Collection;

import java.util.ArrayList;

/**
 * Created by dllo on 15/10/7.
 */
public class Collection_List_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Collection> collections;

    public Collection_List_Adapter(Context context,ArrayList<Collection> collections) {
        this.context = context;
        this.collections = collections;

    }

    @Override
    public int getCount() {
        return collections.size();
    }

    @Override
    public Object getItem(int position) {
        return collections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.news_listview_item, null);
            viewHolder.news_list_item_text = (TextView) convertView.findViewById(R.id.news_list_item_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Collection collection = (Collection) getItem(position);
        viewHolder.news_list_item_text.setText(collection.getTitle());

        return convertView;
    }
    private class ViewHolder {
        private TextView news_list_item_text;
    }

}
