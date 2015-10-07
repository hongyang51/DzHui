package com.lanou3g.mydazahui.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.activity.CollectionActivity;
import com.lanou3g.mydazahui.activity.UserCenterActivity;
import com.lanou3g.mydazahui.base.BaseFragment;
import com.lanou3g.mydazahui.base.DaoSingleton;
import com.lanou3g.mydazahui.greendaobean.User;
import com.lanou3g.mydazahui.greendaobean.UserDao;
import com.lanou3g.mydazahui.utils.CircleImageView;
import com.lanou3g.mydazahui.utils.VolleySingleton;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;

import java.util.ArrayList;

/**
 * Created by dllo on 15/9/22.
 */
public class AboutFragment extends BaseFragment implements View.OnClickListener {
    private TextView person_textview;
    private UMSocialService mController;
    private CircleImageView circleImageView;
    private VolleySingleton singleton;
    private UserDao userDao;
    private RelativeLayout relativeLayout;
    private Button button;
    private ImageLoader imageLoader;
    private ImageLoader.ImageListener listener;
    private TextView collection;


    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_about, null);
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        singleton = VolleySingleton.getVolleySingleton(mActivity);
        imageLoader = singleton.getImageLoader();
        circleImageView = (CircleImageView) view.findViewById(R.id.circleImageView);
        listener = ImageLoader.getImageListener(circleImageView, R.mipmap.dzhreceiver, R.mipmap.dzhreceiver);
        person_textview = (TextView) view.findViewById(R.id.person_textview);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        button = (Button) view.findViewById(R.id.button);
        collection = (TextView) view.findViewById(R.id.collection);
        userDao = DaoSingleton.getInstance().getUserDao();
        relativeLayout.setOnClickListener(this);
        collection.setOnClickListener(this);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        ArrayList<User> users = (ArrayList<User>) userDao.loadAll();
        if (users.size() > 0) {
            User user = users.get(0);
            imageLoader.get(user.getProfile_image_url(), listener);
            person_textview.setText(user.getName());
        }
    }

    @Override
    public void onClick(View v) {
        ArrayList<User> users = (ArrayList<User>) userDao.loadAll();
        switch (v.getId()) {
            case R.id.relativeLayout:
                if (users.size() == 0) {
                    Intent intent = new Intent(mActivity, UserCenterActivity.class);
                    mActivity.startActivity(intent);
                } else {
                    Toast.makeText(mActivity, "您已登陆", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button:
                if (users.size() > 0) {
                    if (users.get(0).getPlatform().equals("qq")) {
                        logout(SHARE_MEDIA.QQ);
                    } else if (users.get(0).getPlatform().equals("qzone")) {
                        logout(SHARE_MEDIA.QZONE);
                    } else if (users.get(0).getPlatform().equals("sina")) {
                        logout(SHARE_MEDIA.SINA);
                    }
                    Log.e("Platform", users.get(0).getPlatform());
                    userDao.deleteAll();
                    Toast.makeText(mActivity, "退出成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.collection:
                Intent intent = new Intent(mActivity, CollectionActivity.class);
                mActivity.startActivity(intent);
                break;
        }

    }

    /**
     * 注销本次登录</br>
     */
    private void logout(final SHARE_MEDIA platform) {
        mController.deleteOauth(mActivity, platform, new SocializeListeners.SocializeClientListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {

                String showText = "解除" + platform.toString() + "平台授权成功";
                if (status != StatusCode.ST_CODE_SUCCESSED) {
                    showText = "解除" + platform.toString() + "平台授权失败[" + status + "]";
                }
                Toast.makeText(mActivity, showText, Toast.LENGTH_SHORT).show();
                if (iChangePage != null) {
                    iChangePage.changePage();
                }
            }
        });
    }

    public interface IChangePage {
        void changePage();
    }

    private IChangePage iChangePage;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iChangePage = context instanceof IChangePage ? ((IChangePage) context) : null;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<User> users = (ArrayList<User>) userDao.loadAll();
        if (users.size() > 0) {
            User user = users.get(0);
            imageLoader.get(user.getProfile_image_url(), listener);
            person_textview.setText(user.getName());
        }
    }
}
