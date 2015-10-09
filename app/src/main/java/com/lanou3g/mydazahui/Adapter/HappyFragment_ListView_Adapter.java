package com.lanou3g.mydazahui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.activity.HappyComment_Activity;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.bean.Happy;
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
 * Created by dllo on 15/9/28.
 */
public class HappyFragment_ListView_Adapter extends BaseAdapter {
    private Activity context;
    private ArrayList<Happy.jokes> jokes;
    private VolleySingleton singleton;
    private ImageLoader imageLoader;
    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");


    public HappyFragment_ListView_Adapter(Activity context, ArrayList<Happy.jokes> jokes) {
        this.context = context;
        this.jokes = jokes;
        singleton = VolleySingleton.getVolleySingleton(context);
        imageLoader = singleton.getImageLoader();
        // 配置需要分享的相关平台
        configPlatforms();
    }

    public void Loading(ArrayList<Happy.jokes> jokes) {
        this.jokes.addAll(jokes);
        notifyDataSetChanged();
    }


    public void Refreshing(ArrayList<Happy.jokes> jokes) {
        this.jokes.clear();
        this.jokes.addAll(jokes);
        notifyDataSetChanged();

    }



    @Override
    public int getCount() {
        return jokes != null && jokes.size() > 0 ? jokes.size() : 5;
    }

    @Override
    public Object getItem(int position) {
        return jokes != null && jokes.size() > 0 ? jokes.get(position) : 5;
    }

    @Override
    public long getItemId(int position) {
        return jokes != null && jokes.size() > 0 ? position : 5;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.happy_listview_item, null);
            holder.groom_img = (CircleImageView) convertView.findViewById(R.id.groom_img);
            holder.Title = (TextView) convertView.findViewById(R.id.Title);
            holder.comment_text = (TextView) convertView.findViewById(R.id.comment_text);
            holder.happy_content = (TextView) convertView.findViewById(R.id.happy_content);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.unlike_text = (TextView) convertView.findViewById(R.id.unlike_text);
            holder.like_text = (TextView) convertView.findViewById(R.id.like_text);
            holder.cardView = (CardView) convertView.findViewById(R.id.cardView);
            holder.default_img = (ImageView) convertView.findViewById(R.id.default_img);
            holder.share = (ImageView) convertView.findViewById(R.id.share);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Happy.jokes  joke = (Happy.jokes) getItem(position);

        holder.Title.setText(joke.getUser_name() + "");
        holder.like_text.setText(joke.getLike_count() + "");
        holder.unlike_text.setText(joke.getUnlike_count() + "");
        holder.happy_content.setText(joke.getContent() + "");
        holder.time.setText(joke.getCreated() + "");
        holder.comment_text.setText(joke.getComment_count() + "");
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 设置分享的内容
                UMImage urlImage = new UMImage(context,
                        Final_Base.HAPPY_URL + joke.getUri());
                // 配置SSO
                mController.getConfig().setSsoHandler(new SinaSsoHandler());
                mController.setShareContent(joke.getContent() + "http://m.lengxiaohua.com/p/joke/" + joke.getJokeid() + "      ---来自大杂烩");
                // 设置QQ空间分享内容
                QZoneShareContent qzone = new QZoneShareContent();
                qzone.setShareContent("---来自大杂烩--http://www.513951.com");
                qzone.setTargetUrl("http://m.lengxiaohua.com/p/joke/" + joke.getJokeid());
                qzone.setTitle(joke.getContent());
                qzone.setShareMedia(urlImage);

                mController.setShareMedia(qzone);
                QQShareContent qqShareContent = new QQShareContent();
                qqShareContent.setShareContent("---来自大杂烩--http://www.513951.com");
                qqShareContent.setTitle(joke.getContent());
                qqShareContent.setTargetUrl("http://m.lengxiaohua.com/p/joke/" + joke.getJokeid());
                mController.setShareMedia(qqShareContent);
                mController.getConfig().setPlatforms(
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA
                );
                mController.openShare(context, false);
            }
        });
        String User_cover_url = Final_Base.HAPPY_URL + joke.getUser_cover_url_100x100();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.groom_img, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(User_cover_url, listener);
        if (!joke.getUri().equals("")) {
            String string =joke.getUri().substring(0,1);
            if(string.equals("/")){
                ImageLoader.ImageListener default_img_listener = ImageLoader.getImageListener(holder.default_img, R.mipmap.joke_default_img, R.mipmap.joke_default_img);
                String default_img_uri = Final_Base.HAPPY_URL + joke.getUri();
                imageLoader.get(default_img_uri, default_img_listener);
                holder.default_img.setVisibility(View.VISIBLE);
                Log.e("网址", "网址为"+joke.getUri());
            }else{
                ImageLoader.ImageListener default_img_listener = ImageLoader.getImageListener(holder.default_img, R.mipmap.joke_default_img, R.mipmap.joke_default_img);
                imageLoader.get(joke.getUri(), default_img_listener);
                holder.default_img.setVisibility(View.VISIBLE);
                Log.e("网址", "网址为"+joke.getUri());
            }

        } else {
            holder.default_img.setVisibility(View.GONE);
            Log.e("网址", "图像网址为空");
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HappyComment_Activity.class);
                intent.putExtra("jokes", joke);
                context.startActivity(intent);
            }
        });
        return convertView;
    }



    private class ViewHolder {
        private TextView Title, time, happy_content, like_text, unlike_text, comment_text;
        private CircleImageView groom_img;
        private CardView cardView;
        private ImageView default_img,share;

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
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(context,
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(context, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }
}
