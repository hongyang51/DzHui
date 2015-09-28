package com.lanou3g.mydazahui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.bean.Happy;
import com.lanou3g.mydazahui.listview.SwipeRefreshLoadingLayout;
import com.lanou3g.mydazahui.utils.CircleImageView;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 15/9/28.
 */
public class HappyFragment_ListView_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Happy.jokes> jokes;
    private VolleySingleton singleton;
    private ImageLoader imageLoader;
    private String Url;
    private String[] Urls = {Final_Base.HAPPY_URL_TOP + "popular" + Final_Base.HAPPY_URL_CENTER
            + 0 + Final_Base.HAPPY_URL_BOTTOM, Final_Base.HAPPY_URL_TOP + "new" + Final_Base.HAPPY_URL_CENTER
            + 0 + Final_Base.HAPPY_URL_BOTTOM};



    public HappyFragment_ListView_Adapter(Context context, ArrayList<Happy.jokes> jokes, String Url,SwipeRefreshLoadingLayout loadingLayout) {
        this.context = context;
        this.jokes = jokes;
        this.Url = Url;
        Refreshing(loadingLayout);
        Bundle bundle = new Bundle();
        bundle.putString("arg",Url);
//        this.context.getAssets()
//        Log.e("ssss",Url);
        singleton = VolleySingleton.getVolleySingleton(context);
        imageLoader = singleton.getImageLoader();
    }

    public void Loading(SwipeRefreshLoadingLayout loadingLayout) {
        if (Url.equals(Urls[0])) {

        }
        this.jokes.addAll(jokes);
        notifyDataSetChanged();

    }


    public void Refreshing(SwipeRefreshLoadingLayout loadingLayout) {
        if (Url.equals(Urls[0])) {
            Log.e("当前位","当前为人气");
//            StringRequest request = new StringRequest(Urls[0], new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.e("sss", "------------>" + response);
//                    Gson gson = new Gson();
//                    Happy happy = gson.fromJson(response, Happy.class);
//                    jokes = (ArrayList<Happy.jokes>) happy.getJokes();
//                    refreshData(jokes);
////                adapter.Refreshing(jokes);
////                loadingLayout.setRefreshing(false);
//                    loadingLayout.setRefreshing(false);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            });
//            singleton.addQueue(request, "request");
        }else if(Url.equals(Urls[1])){
            Log.e("当前位","当前为最新");
        }


    }

    private void refreshData(ArrayList<Happy.jokes> jokes) {
        this.jokes.clear();
        this.jokes.addAll(jokes);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return jokes != null && jokes.size() > 0 ? jokes.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return jokes != null && jokes.size() > 0 ? jokes.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return jokes != null && jokes.size() > 0 ? position : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.happy_listview_item, null);
            holder.groom_img = (CircleImageView) convertView.findViewById(R.id.groom_img);
            holder.Title = (TextView) convertView.findViewById(R.id.Title);
            holder.comment_text = (TextView) convertView.findViewById(R.id.comment_text);
            holder.happy_content = (TextView) convertView.findViewById(R.id.happy_content);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.unlike_text = (TextView) convertView.findViewById(R.id.unlike_text);
            holder.like_text = (TextView) convertView.findViewById(R.id.like_text);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Happy.jokes joke = (Happy.jokes) getItem(position);

        holder.Title.setText(joke.getUser_name() + "");
        int s = joke.getLike_count();
        holder.like_text.setText(s + "");
        holder.unlike_text.setText(joke.getUnlike_count() + "");
        holder.happy_content.setText(joke.getContent() + "");
        holder.time.setText(joke.getCreated() + "");
        holder.comment_text.setText(joke.getComment_count() + "");
        String User_cover_url = Final_Base.HAPPY_URL + joke.getUser_cover_url_100x100();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.groom_img, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(User_cover_url, listener);
        return convertView;
    }


    private class ViewHolder {
        private TextView Title, time, happy_content, like_text, unlike_text, comment_text;
        private CircleImageView groom_img;


    }
}
