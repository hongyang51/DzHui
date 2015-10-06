package com.lanou3g.mydazahui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.base.BaseFragment;
import com.lanou3g.mydazahui.utils.CircleImageView;
import com.lanou3g.mydazahui.utils.VolleySingleton;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.utils.Log;

import java.util.Map;
import java.util.Set;

/**
 * Created by dllo on 15/9/22.
 */
public class AboutFragment extends BaseFragment implements View.OnClickListener {
    private TextView person_textview;
    private UMSocialService mController;
    private CircleImageView circleImageView;
    private VolleySingleton singleton;
    private ImageLoader imageLoader;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_about, null);
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        singleton = VolleySingleton.getVolleySingleton(mActivity);
        imageLoader = singleton.getImageLoader();
        addQZoneQQPlatform();
        circleImageView = (CircleImageView) view.findViewById(R.id.circleImageView);
        person_textview = (TextView) view.findViewById(R.id.person_textview);
        person_textview.setOnClickListener(this);
        circleImageView.setOnClickListener(this);
        return view;
    }

    private void addQZoneQQPlatform() {
        String appId = "1104894320";
        String appKey = "sOmOaMZHcMhk0mPF";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(),
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(getActivity(), appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    @Override
    public void initData() {

    }


    private void loadQQ() {
        mController.doOauthVerify(mActivity, SHARE_MEDIA.QQ, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                Toast.makeText(mActivity, "授权开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                Toast.makeText(mActivity, "授权错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                Toast.makeText(mActivity, "授权完成", Toast.LENGTH_SHORT).show();
                //获取相关授权信息
                mController.getPlatformInfo(mActivity, SHARE_MEDIA.QQ, new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(mActivity, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if (status == 200 && info != null) {
                            StringBuilder sb = new StringBuilder();
                            Set<String> keys = info.keySet();
                            for (String key : keys) {
                                sb.append(key + "=" + info.get(key).toString() + "\r\n");
                            }
                            ImageLoader.ImageListener listener = ImageLoader.getImageListener(circleImageView, R.mipmap.dzhreceiver, R.mipmap.dzhreceiver);
                            imageLoader.get(info.get("profile_image_url").toString(), listener);


                            person_textview.setText(info.get("screen_name").toString());

                            Log.d("TestData", sb.toString());
                        } else {
                            Log.d("TestData", "发生错误：" + status);
                        }
                    }
                });
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(mActivity, "授权取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_textview:
                loadQQ();
                break;
            case R.id.circleImageView:
                loadQQ();
                break;

        }

    }
}
