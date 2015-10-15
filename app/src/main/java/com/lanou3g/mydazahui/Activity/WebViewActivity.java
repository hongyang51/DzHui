package com.lanou3g.mydazahui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.utils.daosingleton.DaoSingleton;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.base.MainActivity;
import com.lanou3g.mydazahui.bean.NewsContent;
import com.lanou3g.mydazahui.greendaobean.Collection;
import com.lanou3g.mydazahui.greendaobean.CollectionDao;
import com.lanou3g.mydazahui.greendaobean.User;
import com.lanou3g.mydazahui.greendaobean.UserDao;
import com.lanou3g.mydazahui.utils.volley.VolleySingleton;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dllo on 15/9/24.
 */
public class WebViewActivity extends MainActivity implements View.OnClickListener {
    private int newsId;
    private VolleySingleton singleton;
    private ImageView news_img, more, back;
    private CircleImageView groom_img;
    private TextView news_title, news_img_text_from, load;
    private ImageLoader.ImageListener listener, listener_user;
    private ImageLoader imageLoader;
    private FrameLayout gonn_fm;
    private ArrayList<NewsContent.recommenders> recommenderses;
    private ArrayList<NewsContent.recommenderss> recommendersses;
    private RelativeLayout user_rela;
    private WebView webview;
    private NewsContent newsContent;
    private String news;
    private ProgressDialog dialog;
    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private CollectionDao collectionDao;
    private Collection collection;
    private UserDao userDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();
        initData();
        // 配置需要分享的相关平台
        configPlatforms();

    }


    private void initView() {
        setContentView(R.layout.activity_webview);
        recommenderses = new ArrayList<>();
        recommendersses = new ArrayList<>();
        singleton = VolleySingleton.getVolleySingleton(this);
        imageLoader = singleton.getImageLoader();
        webview = (WebView) findViewById(R.id.webview);
        news_img = (ImageView) findViewById(R.id.news_img);
        back = (ImageView) findViewById(R.id.back);
        news_title = (TextView) findViewById(R.id.news_title);
        load = (TextView) findViewById(R.id.load);
        more = (ImageView) findViewById(R.id.more);
        gonn_fm = (FrameLayout) findViewById(R.id.gonn_fm);
        groom_img = (CircleImageView) findViewById(R.id.groom_img);
        news_img_text_from = (TextView) findViewById(R.id.news_img_text_from);
        user_rela = (RelativeLayout) findViewById(R.id.user_rela);
        listener = ImageLoader.getImageListener(news_img, R.mipmap.lanniao, R.mipmap.lanniao);
        listener_user = ImageLoader.getImageListener(groom_img, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        dialog = new ProgressDialog(this);
        collectionDao = DaoSingleton.getInstance().getCollectionDao();
        collection = new Collection();
        userDao = DaoSingleton.getInstance().getUserDao();

    }

    private void initData() {
        dialog.setMessage("正在加载中.....");
        dialog.show();
        load.setText("新闻内容");
        Intent intent = getIntent();
        newsId = intent.getIntExtra(Final_Base.NEWSID, 1);
        more.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        news = Final_Base.NEWS_URL + newsId;
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(WebViewActivity.this, more);
                popup.getMenuInflater().inflate(R.menu.menu_pop, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.lpig:
                                mController.getConfig().setPlatforms(
                                        SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA
                                );
                                mController.openShare(WebViewActivity.this, false);
                                Toast.makeText(WebViewActivity.this, "您点了分享", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.bpig:
                                //select username from 表名 where condition;
                                //取缓存
//                                String userName = MyConfig.getSharePreStr(WebViewActivity.this, "users", "userName");
                                //判断缓存里面是否有值

                                ArrayList<User> users = (ArrayList<User>) userDao.loadAll();
                                if (users.size() > 0) {
                                    User user = users.get(0);
                                    String userName = user.getName();
                                    QueryBuilder qb = collectionDao.queryBuilder();
                                    qb.where(CollectionDao.Properties.Title.eq(newsContent.getTitle()));
                                    qb.where(CollectionDao.Properties.User_name.eq(userName));
                                    ArrayList<Collection> collections = (ArrayList<Collection>) qb.list();
                                    if (collections.size() > 0) {
                                        Toast.makeText(WebViewActivity.this, "您已收藏", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //插入数据库
                                        collection.setUser_name(userName);
                                        collection.setTitle(newsContent.getTitle());
                                        collection.setBody(newsContent.getBody());
                                        collection.setNewsId(newsId);
                                        collectionDao.insert(collection);
                                        Toast.makeText(WebViewActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(WebViewActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(WebViewActivity.this, UserCenterActivity.class);
                                    startActivity(intent);
                                }


                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });


        Log.e("News", news);
        StringRequest request = new StringRequest(news, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                newsContent = gson.fromJson(response, NewsContent.class);
                Log.e("News", newsContent.getTitle());
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
                    imageLoader.get(User_img, listener_user);
                    user_rela.setVisibility(View.VISIBLE);
                } else if (recommendersses != null) {
                    String User_img = recommendersses.get(0).getAvatar();
                    imageLoader.get(User_img, listener_user);
                    user_rela.setVisibility(View.VISIBLE);
                } else {
                    user_rela.setVisibility(View.GONE);
                }

                webView(newsContent);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
// 设置分享的内容
                setShareContent();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setShouldCache(false);
        singleton.addQueue(request, news);


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
        final String data = newsContent.getBody();
        if (data != null) {
            webview.loadDataWithBaseURL("", data, "text/html", "utf-8", null);
            webview.setVisibility(View.VISIBLE);
        } else if (newsContent.getShare_url() != null) {
            webview.setWebViewClient(new WebViewClient());
            webview.loadUrl(newsContent.getShare_url());
            webview.setVisibility(View.VISIBLE);
        }
    }


    private void configPlatforms() {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        // 添加QQ、QZone平台
        addQQQZonePlatform();
    }


    private void addQQQZonePlatform() {
        String appId = "1104894320";
        String appKey = "sOmOaMZHcMhk0mPF";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this,
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.513951.com");
        qqSsoHandler.addToSocialSDK();
        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId, appKey);
        qZoneSsoHandler.setTargetUrl("http://www.513951.com");
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    private void setShareContent() {


        // 配置SSO
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.setShareContent(newsContent.getTitle() + newsContent.getShare_url() + "      ---来自大杂烩");
        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        if (newsContent.getImage() != null) {
            UMImage urlImage = new UMImage(this,
                    newsContent.getImage());
            qzone.setShareMedia(urlImage);
        }
        qzone.setShareContent("---来自大杂烩--http://www.513951.com");
        qzone.setTargetUrl(newsContent.getShare_url() + "");
        qzone.setTitle(newsContent.getTitle());

        mController.setShareMedia(qzone);

        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent("---来自大杂烩--http://www.513951.com");
        if (newsContent.getImage() != null) {
            UMImage urlImage = new UMImage(this,
                    newsContent.getImage());
            qqShareContent.setShareMedia(urlImage);
        }
        qqShareContent.setTitle(newsContent.getTitle());
        qqShareContent.setTargetUrl(newsContent.getShare_url() + "");
        mController.setShareMedia(qqShareContent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        singleton.removeRequest(news);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition
                    (R.anim.translate_exit_in, R.anim.translate_exit_out);

        }
        return super.onKeyDown(keyCode, event);
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
