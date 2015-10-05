package com.lanou3g.mydazahui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.adapter.HappyComment_Adapter;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.base.MainActivity;
import com.lanou3g.mydazahui.bean.Happy;
import com.lanou3g.mydazahui.bean.HappyComment;
import com.lanou3g.mydazahui.utils.CircleImageView;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 15/9/29.
 */
public class HappyComment_Activity extends MainActivity {
    private Happy.jokes jokes;
    private TextView Title, time, happy_content, like_text, unlike_text, comment_text, head;
    private CircleImageView groom_img;
    private VolleySingleton singleton;
    private ImageLoader imageLoader;
    private ListView Comment_list;
    private HappyComment_Adapter adapter;
    private ImageView default_img;
    private CardView List_cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();


    }

    private void initView() {
        setContentView(R.layout.happy_listview_item);
        Title = (TextView) findViewById(R.id.Title);
        time = (TextView) findViewById(R.id.time);
        happy_content = (TextView) findViewById(R.id.happy_content);
        like_text = (TextView) findViewById(R.id.like_text);
        unlike_text = (TextView) findViewById(R.id.unlike_text);
        comment_text = (TextView) findViewById(R.id.comment_text);
        groom_img = (CircleImageView) findViewById(R.id.groom_img);
        Comment_list = (ListView) findViewById(R.id.Comment_list);
        default_img = (ImageView) findViewById(R.id.default_img);
        singleton = VolleySingleton.getVolleySingleton(this);
        List_cardView = (CardView) findViewById(R.id.List_cardView);
        imageLoader = singleton.getImageLoader();
    }

    private void initData() {
        Intent intent = getIntent();
        jokes = (Happy.jokes) intent.getSerializableExtra("jokes");
        Title.setText(jokes.getUser_name() + "");
        like_text.setText(jokes.getLike_count() + "");
        unlike_text.setText(jokes.getUnlike_count() + "");
        happy_content.setText(jokes.getContent() + "");
        time.setText(jokes.getCreated() + "");
        comment_text.setText(jokes.getComment_count() + "");
        List_cardView.setVisibility(View.VISIBLE);
        String User_cover_url = Final_Base.HAPPY_URL + jokes.getUser_cover_url_100x100();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(groom_img, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(User_cover_url, listener);

        if (!jokes.getUri().equals("")) {

            ImageLoader.ImageListener default_img_listener = ImageLoader.getImageListener(default_img, R.mipmap.joke_default_img, R.mipmap.joke_default_img);
            String default_img_uri = Final_Base.HAPPY_URL + jokes.getUri();
            imageLoader.get(default_img_uri, default_img_listener);
            default_img.setVisibility(View.VISIBLE);
        } else {
            Log.e("网址", jokes.getUri() + "网址为空");
            default_img.setVisibility(View.GONE);
            Log.e("网址", "得到的网址为空");
        }
        String pre_joke_id = Final_Base.HAPPY_COMMENT_TOP + jokes.getJokeid() + Final_Base.HAPPY_COMMENT_Back;
        Log.e("当前的评论", "网址为" + pre_joke_id);
        StringRequest request = new StringRequest(pre_joke_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                HappyComment comment = gson.fromJson(response, HappyComment.class);
                ArrayList<HappyComment.CommentsEntity> commentsEntities = (ArrayList<HappyComment.CommentsEntity>) comment.getComments();
                adapter = new HappyComment_Adapter(HappyComment_Activity.this, commentsEntities);
                View view = LayoutInflater.from(HappyComment_Activity.this).inflate(R.layout.list_head_happycomment, null);
                head = (TextView) view.findViewById(R.id.head);
                head.setText(commentsEntities.size() + "条精彩评论");
                Comment_list.addHeaderView(view);
                Comment_list.setHeaderDividersEnabled(false);
                Comment_list.setVisibility(View.VISIBLE);
                Comment_list.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        singleton.addQueue(request, "request");
    }


}
