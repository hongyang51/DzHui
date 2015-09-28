package com.lanou3g.mydazahui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou3g.mydazahui.base.MainActivity;
import com.lanou3g.mydazahui.bean.GuidePage;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.utils.SharedPreferUtil;
import com.lanou3g.mydazahui.utils.VolleySingleton;

/**
 * Created by xyb on 15/9/21.
 */
public class PWReadOneActivity extends MainActivity {
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private ImageView imageView,read_one_lanniao;
    private static final String Url = "http://news-at.zhihu.com/api/4/start-image/";
    private RelativeLayout relativeLayout;
    private int widthPixels;
    private int heightPixels;
    private String AddUrl;
    private TextView read_one_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取手机分辨率
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
        AddUrl = Url + widthPixels + "*" + heightPixels;//拼接网址
        initView();
        initDatas();
    }


    private void initView() {
        setContentView(R.layout.activity_read_one);
        imageView = (ImageView) findViewById(R.id.read_one_imageView);
        read_one_lanniao = (ImageView) findViewById(R.id.read_one_lanniao);
        read_one_TextView = (TextView) findViewById(R.id.read_one_TextView);
        volleySingleton = VolleySingleton.getVolleySingleton(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.read_one_linearLayout);
    }

    private void initDatas() {
        StringRequest stringRequest = new StringRequest(AddUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                GuidePage guidePage = gson.fromJson(response, GuidePage.class);
                read_one_TextView.setText(guidePage.getText());
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.mipmap.read, R.mipmap.read);
                imageLoader = volleySingleton.getImageLoader();
                String imgUrl = guidePage.getImg();
                imageLoader.get(imgUrl, listener);
                read_one_lanniao.setImageResource(R.mipmap.lanniao);
                StartAnimation();//执行动画
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PWReadOneActivity.this,"出错了呢.请重启应用😢",Toast.LENGTH_LONG).show();
            }
        });
        volleySingleton.addQueue(stringRequest, "one_img");
    }

    private void StartAnimation() {
        // 动画集合
        AnimationSet set = new AnimationSet(false);
        // 旋转动画
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        animation.setDuration(2000);// 设置动画的时间
        animation.setFillAfter(true);// 保持动画状态
        // 缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);// 设置动画的时间
        scaleAnimation.setFillAfter(true);// 保持动画状态

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);//渐变动画
        alphaAnimation.setDuration(3000);// 设置动画的时间
        alphaAnimation.setFillAfter(true);// 保持动画状态
        set.addAnimation(scaleAnimation);
        set.addAnimation(animation);
        set.addAnimation(alphaAnimation);


        // 设置动画监听
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            //动画结束
            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        relativeLayout.startAnimation(set);
    }

    private void jumpNextPage() {

        boolean userGuid = SharedPreferUtil.getBoolean(this,"is_user_guide_showed", false);
        if (!userGuid) {
            startActivity(new Intent(PWReadOneActivity.this, PatchWork_FirstActivity.class));
        } else{
            startActivity(new Intent(PWReadOneActivity.this, PWRead_Home_Tanhost_Activity.class));
        }
        finish();
    }
}
