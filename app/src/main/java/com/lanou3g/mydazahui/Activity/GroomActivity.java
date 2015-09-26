package com.lanou3g.mydazahui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou3g.mydazahui.Adapter.GroomAdapter;
import com.lanou3g.mydazahui.Base.MainActivity;
import com.lanou3g.mydazahui.Bean.Groom;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 15/9/25.
 */
public class GroomActivity extends MainActivity {
    private VolleySingleton singleton;
    private ListView listView;
    private String url;
    private GroomAdapter adapter;
    private ArrayList<Groom.ItemsEntity.RecommendersEntity> recommendersEntities;
    private ArrayList<Groom.EditorsEntity> editorsEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_groom);
        listView = (ListView) findViewById(R.id.listView);
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
        singleton.addQueue(request, "sss");
    }
}