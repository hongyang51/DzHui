package com.xyb513951.mydazahui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou3g.mydazahui.R;
import com.xyb513951.mydazahui.adapter.GroomAdapter;
import com.xyb513951.mydazahui.base.MainActivity;
import com.xyb513951.mydazahui.bean.Groom;
import com.xyb513951.mydazahui.utils.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 15/9/25.
 */
public class GroomActivity extends MainActivity implements View.OnClickListener {
    private VolleySingleton singleton;
    private ListView listView;
    private String url;
    private GroomAdapter adapter;
    private ArrayList<Groom.ItemsEntity.RecommendersEntity> recommendersEntities;
    private ArrayList<Groom.EditorsEntity> editorsEntity;
    private ImageView back;
    private TextView load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_groom);
        listView = (ListView) findViewById(R.id.listView);
        back = (ImageView) findViewById(R.id.back);
        load = (TextView) findViewById(R.id.load);
        back.setOnClickListener(this);
        load.setText("推荐者");
        singleton = VolleySingleton.getVolleySingleton(this);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Groom groom = gson.fromJson(response, Groom.class);
                if (groom.getItems() != null && groom.getItems().size() > 0) {
                    recommendersEntities = (ArrayList<Groom.ItemsEntity.RecommendersEntity>) groom.getItems().get(0).getRecommenders();
                    adapter = new GroomAdapter(GroomActivity.this, recommendersEntities,editorsEntity);
                    listView.setAdapter(adapter);
                } else if(groom.getEditors() != null && groom.getEditors().size() > 0) {
                    editorsEntity = (ArrayList<Groom.EditorsEntity>) groom.getEditors();
                    adapter = new GroomAdapter(GroomActivity.this, recommendersEntities,editorsEntity);
                    listView.setAdapter(adapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        singleton.addQueue(request, "GroomActivity_request");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        singleton.removeRequest("GroomActivity_request");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                overridePendingTransition
                        (R.anim.translate_exit_in, R.anim.translate_exit_out);
                break;
        }
    }
}
