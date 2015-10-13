package com.lanou3g.mydazahui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.adapter.HappyComment_Adapter;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.base.ListViewForScrollView;
import com.lanou3g.mydazahui.base.MainActivity;
import com.lanou3g.mydazahui.bean.Happy;
import com.lanou3g.mydazahui.bean.HappyComment;
import com.lanou3g.mydazahui.utils.VolleySingleton;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dllo on 15/9/29.
 */
public class HappyComment_Activity extends MainActivity implements View.OnClickListener {
    private Happy.jokes jokes;
    private TextView Title, time, happy_content, like_text, unlike_text, comment_text, head;
    private CircleImageView groom_img;
    private VolleySingleton singleton;
    private ImageLoader imageLoader;
    private ListViewForScrollView Comment_list;
    private HappyComment_Adapter adapter;
    private ImageView default_img;
    private ImageView share;
    private ImageView back;
    private CardView List_cardView;
    private RelativeLayout title_relative;
    private Button button;

    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private SpeechSynthesizer mTts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initViewListen();
        // 配置需要分享的相关平台
        configPlatforms();
        // 设置分享的内容
        setShareContent();
    }

    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    private void setShareContent() {
        UMImage urlImage = new UMImage(this,
                Final_Base.HAPPY_URL + jokes.getUri());
        // 配置SSO
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.setShareContent(jokes.getContent() + "http://m.lengxiaohua.com/p/joke/" + jokes.getJokeid() + "      ---来自大杂烩");
        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent("---来自大杂烩--http://www.513951.com");
        qzone.setTargetUrl("http://m.lengxiaohua.com/p/joke/" + jokes.getJokeid());
        qzone.setTitle(jokes.getContent());
        qzone.setShareMedia(urlImage);

        mController.setShareMedia(qzone);
        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent("---来自大杂烩--http://www.513951.com");
        qqShareContent.setTitle(jokes.getContent());
        qqShareContent.setTargetUrl("http://m.lengxiaohua.com/p/joke/" + jokes.getJokeid());
        mController.setShareMedia(qqShareContent);

    }

    private void configPlatforms() {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        // 添加QQ、QZone平台
        addQQQZonePlatform();
    }

    /**
     * @return
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     * image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     * 要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     * : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     */
    private void addQQQZonePlatform() {
        String appId = "1104894320";
        String appKey = "sOmOaMZHcMhk0mPF";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this,
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.513951.com/");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    private void initViewListen() {
        share.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initView() {
        setContentView(R.layout.happy_listview_item);
        Title = (TextView) findViewById(R.id.Title);
        mTts = SpeechSynthesizer.createSynthesizer(this, null);
        time = (TextView) findViewById(R.id.time);
        button = (Button) findViewById(R.id.button);
        title_relative = (RelativeLayout) findViewById(R.id.title_relative);
        title_relative.setVisibility(View.VISIBLE);
        happy_content = (TextView) findViewById(R.id.happy_content);
        like_text = (TextView) findViewById(R.id.like_text);
        unlike_text = (TextView) findViewById(R.id.unlike_text);
        comment_text = (TextView) findViewById(R.id.comment_text);
        groom_img = (CircleImageView) findViewById(R.id.groom_img);
        Comment_list = (ListViewForScrollView) findViewById(R.id.Comment_list);
        default_img = (ImageView) findViewById(R.id.default_img);
        back = (ImageView) findViewById(R.id.back);
        share = (ImageView) findViewById(R.id.share);
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
            String string = jokes.getUri().substring(0, 1);
            if (string.equals("/")) {
                ImageLoader.ImageListener default_img_listener = ImageLoader.getImageListener(default_img, R.mipmap.joke_default_img, R.mipmap.joke_default_img);
                String default_img_uri = Final_Base.HAPPY_URL + jokes.getUri();
                imageLoader.get(default_img_uri, default_img_listener);
                default_img.setVisibility(View.VISIBLE);
                Log.e("网址", "网址为" + jokes.getUri());
            } else {
                ImageLoader.ImageListener default_img_listener = ImageLoader.getImageListener(default_img, R.mipmap.joke_default_img, R.mipmap.joke_default_img);

                imageLoader.get(jokes.getUri(), default_img_listener);
                default_img.setVisibility(View.VISIBLE);
                Log.e("网址", "网址为" + jokes.getUri());
            }
        } else {
            default_img.setVisibility(View.GONE);
            Log.e("网址", "图像网址为空");
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
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                // 返回
                finish();
                overridePendingTransition
                        (R.anim.translate_exit_in, R.anim.translate_exit_out);
                break;
            case R.id.share:
                // 分享
                mController.getConfig().setPlatforms(
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA
                );
                mController.openShare(this, false);
                break;
            case R.id.button:
                // 语音朗读
                if (!mTts.isSpeaking()) {
                    mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaokun");
                    mTts.setParameter(SpeechConstant.SPEED, "50");
                    mTts.setParameter(SpeechConstant.VOLUME, "80");
                    mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
                    mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
                    mTts.startSpeaking(jokes.getContent(), mSynListener);
                }


                break;
        }

    }

    /**
     * 语音朗读的监听
     */
    private SynthesizerListener mSynListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTts.isSpeaking()) {
            mTts.stopSpeaking();
        }

    }
}
