package com.lanou3g.mydazahui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou3g.mydazahui.Base.Final_Base;
import com.lanou3g.mydazahui.Base.MainActivity;
import com.lanou3g.mydazahui.Bean.NewsContent;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 15/9/24.
 */
public class WebViewActivity extends MainActivity {
    private int newsId;
    private VolleySingleton singleton;
    private ImageView news_img, groom_img;
    private TextView news_title, news_img_text_from;
    private ImageLoader.ImageListener listener, listener_user;
    private ImageLoader imageLoader;
    private FrameLayout gonn_fm;
    private ArrayList<NewsContent.recommenders> recommenderses;
    private ArrayList<NewsContent.recommenderss> recommendersses;
    private RelativeLayout user_rela;
    private WebView webview;
    private NewsContent newsContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }


    private void initView() {
        setContentView(R.layout.activity_webview);
        recommenderses = new ArrayList<>();
        recommendersses = new ArrayList<>();
        singleton = VolleySingleton.getVolleySingleton(this);
        imageLoader = singleton.getImageLoader();
        webview = (WebView) findViewById(R.id.webview);
        news_img = (ImageView) findViewById(R.id.news_img);
        news_title = (TextView) findViewById(R.id.news_title);
        gonn_fm = (FrameLayout) findViewById(R.id.gonn_fm);
        groom_img = (ImageView) findViewById(R.id.groom_img);
        news_img_text_from = (TextView) findViewById(R.id.news_img_text_from);
        user_rela = (RelativeLayout) findViewById(R.id.user_rela);
        listener = ImageLoader.getImageListener(news_img, R.mipmap.lanniao, R.mipmap.lanniao);
        listener_user = ImageLoader.getImageListener(groom_img, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
    }

    private void initData() {
        Intent intent = getIntent();
        newsId = intent.getIntExtra(Final_Base.NEWSID, 1);
        String News = Final_Base.NEWS_URL + newsId;
        StringRequest request = new StringRequest(News, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                newsContent = gson.fromJson(response, NewsContent.class);

                news_title.setText(newsContent.getTitle());
                news_img_text_from.setText(newsContent.getImage_source());
                if (newsContent.getImage() != null) {
                    imageLoader.get(newsContent.getImage(), listener);
                    gonn_fm.setVisibility(View.VISIBLE);
                } else {
                    gonn_fm.setVisibility(View.GONE);
                }
                recommenderses = (ArrayList<NewsContent.recommenders>) newsContent.getRecommenders();
                recommendersses = (ArrayList<NewsContent.recommenderss>) newsContent.getRecommenderss();

                if (recommenderses != null) {
                    String User_img = recommenderses.get(0).getAvatar();
                    Log.e("SSSS", User_img);
                    imageLoader.get(User_img, listener_user);
                    user_rela.setVisibility(View.VISIBLE);
                } else if (recommendersses != null) {
                    String User_img = recommendersses.get(0).getAvatar();
                    Log.e("SSSS", User_img);
                    imageLoader.get(User_img, listener_user);
                    user_rela.setVisibility(View.VISIBLE);
                } else {
                    user_rela.setVisibility(View.GONE);
                }
                webView(newsContent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        singleton.addQueue(request, "tab");
        groom_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int NewsId = newsContent.getId();
                String url = Final_Base.GROOM_URL_TOP + NewsId + Final_Base.GROOM_URL_BOT;
                Intent intent = new Intent(WebViewActivity.this, GroomActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }

    private void webView(NewsContent newsContent) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setDefaultFixedFontSize(20);
        //适应屏幕
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(false); // 设置不显示缩放按钮
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 图片自适应
        String data = newsContent.getBody();
        if (data != null) {
            webview.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
            webview.setVisibility(View.VISIBLE);
        } else if (newsContent.getShare_url() != null) {

//            webview.setWebChromeClient(new WebChromeClient() {
//                @Override
//                public void onProgressChanged(WebView view, int newProgress) {
//                    super.onProgressChanged(view, newProgress);
//                }
//            });
            webview.setWebViewClient(new WebViewClient() );
            webview.loadUrl(newsContent.getShare_url());
            webview.setVisibility(View.VISIBLE);
        }

    }


}
