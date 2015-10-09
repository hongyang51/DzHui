package com.lanou3g.mydazahui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.base.MainActivity;
import com.lanou3g.mydazahui.utils.SharedPreferUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by xyb on 15/9/21.
 */
public class PatchWorkOneActivity extends MainActivity implements View.OnClickListener {
    private ImageView food_image,read_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    overridePendingTransition
                            (R.anim.translate_enter_in, R.anim.translate_enter_out);

                }else{
                    Intent read_image = new Intent(PatchWorkOneActivity.this,PWRead_Home_Tanhost_Activity.class);
                    startActivity(read_image);
                    overridePendingTransition
                            (R.anim.translate_enter_in, R.anim.translate_enter_out);
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferUtil.setBoolean(PatchWorkOneActivity.this, "is_user_guide", false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition
                    (R.anim.translate_exit_in, R.anim.translate_exit_out);

        }
        return super.onKeyDown(keyCode, event);
    }
}
