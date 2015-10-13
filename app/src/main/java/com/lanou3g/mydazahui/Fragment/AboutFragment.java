package com.lanou3g.mydazahui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.lanou3g.mydazahui.utils.DataCleanManager;
import com.lanou3g.mydazahui.utils.SharedPreferUtil;
import com.lanou3g.mydazahui.utils.VolleySingleton;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dllo on 15/9/22.
 */
public class AboutFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView person_textview, clear_text;
    private UMSocialService mController;
    private CircleImageView circleImageView;
    private VolleySingleton singleton;
    private UserDao userDao;
    private RelativeLayout relativeLayout;
    private Button button;
    private ImageLoader imageLoader;
    private ImageLoader.ImageListener listener;
    private CardView collection, clear_card;
    private CheckBox push_CheckBox;


    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_about, null);
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        singleton = VolleySingleton.getVolleySingleton(mActivity);
        imageLoader = singleton.getImageLoader();
        circleImageView = (CircleImageView) view.findViewById(R.id.circleImageView);
        listener = ImageLoader.getImageListener(circleImageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        person_textview = (TextView) view.findViewById(R.id.person_textview);
        clear_text = (TextView) view.findViewById(R.id.clear_text);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        button = (Button) view.findViewById(R.id.button);
        collection = (CardView) view.findViewById(R.id.collection);
        clear_card = (CardView) view.findViewById(R.id.clear_card);
        push_CheckBox = (CheckBox) view.findViewById(R.id.push_CheckBox);
        userDao = DaoSingleton.getInstance().getUserDao();
        relativeLayout.setOnClickListener(this);
        collection.setOnClickListener(this);
        clear_card.setOnClickListener(this);
        button.setOnClickListener(this);
        push_CheckBox.setOnCheckedChangeListener(this);
        boolean userGuid = SharedPreferUtil.getBoolean(mActivity, "is_user_push", true);
        if (userGuid) {
            push_CheckBox.setChecked(true);
        } else {
            push_CheckBox.setChecked(false);
        }

        return view;
    }

    @Override
    public void initData() {
        try {
            String i = DataCleanManager.getCacheSize(mActivity.getCacheDir());
            clear_text.setText(i);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
                if (users.size() == 0) {
                    Toast.makeText(mActivity, "请登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mActivity, UserCenterActivity.class);
                    mActivity.startActivity(intent);
                    mActivity.overridePendingTransition
                            (R.anim.translate_enter_in, R.anim.translate_enter_out);
                } else {
                    Intent intent = new Intent(mActivity, CollectionActivity.class);
                    mActivity.startActivity(intent);
                    mActivity.overridePendingTransition
                            (R.anim.translate_enter_in, R.anim.translate_enter_out);
                }

                break;
            case R.id.clear_card:
                DataCleanManager.deleteFolderFile(mActivity.getCacheDir().toString(), true);
                try {
                    String i = DataCleanManager.getCacheSize(mActivity.getCacheDir());
                    clear_text.setText(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(mActivity, "清除成功", Toast.LENGTH_SHORT).show();
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

                }
                Toast.makeText(mActivity, showText, Toast.LENGTH_SHORT).show();
                if (iChangePage != null) {
                    iChangePage.changePage();
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked == true) {
            JPushInterface.resumePush(mActivity);
            SharedPreferUtil.setBoolean(mActivity, "is_user_push", true);
        } else {
            JPushInterface.stopPush(mActivity);
            SharedPreferUtil.setBoolean(mActivity, "is_user_push", false);
        }
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
        try {
            String i = DataCleanManager.getCacheSize(mActivity.getCacheDir());
            clear_text.setText(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
