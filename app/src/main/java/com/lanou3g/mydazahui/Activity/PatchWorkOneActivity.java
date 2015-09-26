package com.lanou3g.mydazahui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lanou3g.mydazahui.Base.MainActivity;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.utils.SharedPreferUtil;

/**
 * Created by xyb on 15/9/21.
 */
public class PatchWorkOneActivity extends MainActivity implements View.OnClickListener {
    private ImageView food_image,read_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        initFind();
        initData();
    }

    private void initData() {
        food_image.setOnClickListener(this);
        read_image.setOnClickListener(this);
    }

    private void initFind() {
        food_image = (ImageView) findViewById(R.id.food_image);
        read_image = (ImageView) findViewById(R.id.read_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.food_image:

            break;
            case R.id.read_image:
                boolean userGuid = SharedPreferUtil.getBoolean(this, "is_user_guide", false);
                if(!userGuid){
                Intent read_image = new Intent(PatchWorkOneActivity.this,PWReadOneActivity.class);
                startActivity(read_image);
                }else{
                    Intent read_image = new Intent(PatchWorkOneActivity.this,PWRead_Home_Tanhost_Activity.class);
                    startActivity(read_image);
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferUtil.setBoolean(PatchWorkOneActivity.this, "is_user_guide", false);
    }
}