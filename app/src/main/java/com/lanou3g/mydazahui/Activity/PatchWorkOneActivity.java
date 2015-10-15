package com.lanou3g.mydazahui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.base.MainActivity;
import com.lanou3g.mydazahui.utils.sharedprefer.SharedPreferUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by xyb on 15/9/21.
 */
public class PatchWorkOneActivity extends MainActivity implements View.OnClickListener {
    private ImageView food_image,read_image;
    private View view_custom;
    private Context mContext;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData() {
        food_image.setOnClickListener(this);
        read_image.setOnClickListener(this);
    }

    private void initView() {
        mContext = PatchWorkOneActivity.this;
        //初始化Builder
        builder = new AlertDialog.Builder(mContext);
        food_image = (ImageView) findViewById(R.id.food_image);
        read_image = (ImageView) findViewById(R.id.read_image);
        //加载自定义的那个View,同时设置下
        final LayoutInflater inflater = PatchWorkOneActivity.this.getLayoutInflater();
        view_custom = inflater.inflate(R.layout.view_dialog_custom, null,false);
        builder.setView(view_custom);
        builder.setCancelable(false);
        alert = builder.create();
        view_custom.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        view_custom.findViewById(R.id.btn_blog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "访问博客", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("http://blog.csdn.net/wooder111");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                alert.dismiss();
            }
        });

        view_custom.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "关闭", Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.food_image:
                alert.show();
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
