package com.lanou3g.mydazahui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.base.DaoSingleton;
import com.lanou3g.mydazahui.base.MainActivity;
import com.lanou3g.mydazahui.greendaobean.User;
import com.lanou3g.mydazahui.greendaobean.UserDao;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.utils.Log;

import java.util.Map;

/**
 * Created by dllo on 15/10/6.
 */
public class UserCenterActivity extends MainActivity implements View.OnClickListener {
    private ImageView QQ, QZONE, SINA;
    private UMSocialService mController;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        initData();

    }

    private void initData() {
        QQ.setOnClickListener(this);
        QZONE.setOnClickListener(this);
        SINA.setOnClickListener(this);
    }

    private void initView() {
        QQ = (ImageView) findViewById(R.id.QQ);
        QZONE = (ImageView) findViewById(R.id.QZONE);
        SINA = (ImageView) findViewById(R.id.SINA);
        userDao = DaoSingleton.getInstance().getUserDao();
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        addQZoneQQPlatform();
    }

    private void addQZoneQQPlatform() {
        String appId = "1104894320";
        String appKey = "sOmOaMZHcMhk0mPF";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this,
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.QQ:
                login(SHARE_MEDIA.QQ);
                break;
            case R.id.QZONE:
                login(SHARE_MEDIA.QZONE);
                break;
            case R.id.SINA:
                login(SHARE_MEDIA.SINA);
                break;
        }
    }
    /**
     * 注销本次登录</br>
     */
    private void logout(final SHARE_MEDIA platform) {
        mController.deleteOauth(UserCenterActivity.this, platform, new SocializeListeners.SocializeClientListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                String showText = "解除" + platform.toString() + "平台授权成功";
                if (status != StatusCode.ST_CODE_SUCCESSED) {
                    showText = "解除" + platform.toString() + "平台授权失败[" + status + "]";
                }
                Toast.makeText(UserCenterActivity.this, showText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 授权。如果授权成功，则获取用户信息</br>
     */
    private void login(final SHARE_MEDIA platform) {
        mController.doOauthVerify(UserCenterActivity.this, platform, new SocializeListeners.UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA platform) {
                Toast.makeText(UserCenterActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                Toast.makeText(UserCenterActivity.this, "授权错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                Toast.makeText(UserCenterActivity.this, "授权完成", Toast.LENGTH_SHORT).show();
                String uid = value.getString("uid");
                if (!TextUtils.isEmpty(uid)) {
                    getUserInfo(platform);
                } else {
                    Toast.makeText(UserCenterActivity.this, "授权失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(UserCenterActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取授权平台的用户信息</br>
     */
    private void getUserInfo(SHARE_MEDIA platform) {
        mController.getPlatformInfo(UserCenterActivity.this, platform, new SocializeListeners.UMDataListener() {

            @Override
            public void onStart() {
                Toast.makeText(UserCenterActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                if (info != null) {
                    userDao.deleteAll();
                    User user = new User();
                    user.setName(info.get("screen_name").toString());
                    user.setProfile_image_url(info.get("profile_image_url").toString());
                    userDao.insertOrReplace(user);
                    finish();
                    Log.e("TestData", info.toString());
                }
            }
        });
    }
}
