package com.lanou3g.mydazahui.adapter;

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
import com.lanou3g.mydazahui.activity.HappyComment_Activity;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.bean.Happy;
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




    public HappyFragment_ListView_Adapter(Context context, ArrayList<Happy.jokes> jokes) {
        this.context = context;
        this.jokes = jokes;
        singleton = VolleySingleton.getVolleySingleton(context);
        imageLoader = singleton.getImageLoader();
    }

    public void Loading(ArrayList<Happy.jokes> jokes) {
        this.jokes.addAll(jokes);
        notifyDataSetChanged();
    }


    public void Refreshing(ArrayList<Happy.jokes> jokes) {
        this.jokes.clear();
        this.jokes.addAll(jokes);
        notifyDataSetChanged();

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
    }

//    private void refreshData(ArrayList<Happy.jokes> jokes) {
//        this.jokes.clear();
//        this.jokes.addAll(jokes);
//        notifyDataSetChanged();
//    }

    @Override
    public int getCount() {
        return jokes != null && jokes.size() > 0 ? jokes.size() : 5;
    }

    @Override
    public Object getItem(int position) {
        return jokes != null && jokes.size() > 0 ? jokes.get(position) : 5;
    }

    @Override
    public long getItemId(int position) {
        return jokes != null && jokes.size() > 0 ? position : 5;
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
            holder.cardView = (CardView) convertView.findViewById(R.id.cardView);
            holder.default_img = (ImageView) convertView.findViewById(R.id.default_img);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Happy.jokes joke = (Happy.jokes) getItem(position);

        holder.Title.setText(joke.getUser_name() + "");
        holder.like_text.setText(joke.getLike_count() + "");
        holder.unlike_text.setText(joke.getUnlike_count() + "");
        holder.happy_content.setText(joke.getContent() + "");
        holder.time.setText(joke.getCreated() + "");
        holder.comment_text.setText(joke.getComment_count() + "");
        String User_cover_url = Final_Base.HAPPY_URL + joke.getUser_cover_url_100x100();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.groom_img, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(User_cover_url, listener);
        if (!joke.getUri().equals("")) {

            ImageLoader.ImageListener default_img_listener = ImageLoader.getImageListener(holder.default_img, R.mipmap.joke_default_img, R.mipmap.joke_default_img);
            String default_img_uri = Final_Base.HAPPY_URL + joke.getUri();
            imageLoader.get(default_img_uri, default_img_listener);
            holder.default_img.setVisibility(View.VISIBLE);
        } else {
            Log.e("网址", joke.getUri() + "网址为空");
            holder.default_img.setVisibility(View.GONE);
            Log.e("网址", "得到的网址为空");
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HappyComment_Activity.class);
                intent.putExtra("jokes",joke);
//                Bundle bundle = new Bundle();
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private TextView Title, time, happy_content, like_text, unlike_text, comment_text;
        private CircleImageView groom_img;
        private CardView cardView;
        private ImageView default_img;

    }
}
