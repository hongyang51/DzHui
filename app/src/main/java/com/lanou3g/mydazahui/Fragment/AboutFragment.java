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
import com.lanou3g.mydazahui.utils.daosingleton.DaoSingleton;
import com.lanou3g.mydazahui.greendaobean.User;
import com.lanou3g.mydazahui.greendaobean.UserDao;
import com.lanou3g.mydazahui.utils.data_clean.DataCleanManager;
import com.lanou3g.mydazahui.utils.sharedprefer.SharedPreferUtil;
import com.lanou3g.mydazahui.utils.volley.VolleySingleton;
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
 * 个人中心页面
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

    /**
     * 初始化视图
     */
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
        // 视图的监听
        initViewListener();


        return view;
    }

    /**
     * 视图监听的方法
     */
    private void initViewListener() {
        relativeLayout.setOnClickListener(this);
        collection.setOnClickListener(this);
        clear_card.setOnClickListener(this);
        button.setOnClickListener(this);
        push_CheckBox.setOnCheckedChangeListener(this);
        // 记录一下用户选择的状态 个性保存
        boolean userGuid = SharedPreferUtil.getBoolean(mActivity, "is_user_push", true);
        if (userGuid) {
            push_CheckBox.setChecked(true);
        } else {
            push_CheckBox.setChecked(false);
        }
    }

    /**
     * 设置数据
     */
    @Override
    public void initData() {
        try {
            String i = DataCleanManager.getCacheSize(mActivity.getCacheDir());
            clear_text.setText(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据缓存记录用户的状态并设置
        ArrayList<User> users = (ArrayList<User>) userDao.loadAll();
        if (users.size() > 0) {
            User user = users.get(0);
            imageLoader.get(user.getProfile_image_url(), listener);
            person_textview.setText(user.getName());
        }
    }

    /**
     * 点击监听
     */
    @Override
    public void onClick(View v) {
        ArrayList<User> users = (ArrayList<User>) userDao.loadAll();
        switch (v.getId()) {

            // 头像部分的点击监听

            case R.id.relativeLayout:
                if (users.size() == 0) {
                    // 没有缓存就跳转登陆
                    Intent intent = new Intent(mActivity, UserCenterActivity.class);
                    mActivity.startActivity(intent);
                } else {
                    // 有缓存就提示已登录
                    Toast.makeText(mActivity, "您已登陆", Toast.LENGTH_SHORT).show();
                }
                break;
            // 登陆按钮的监听
            case R.id.button:
                if (users.size() > 0) {
                    // 判断登陆的平台类型进行退出
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
                    // 如果没有登陆 提示先登陆
                    Toast.makeText(mActivity, "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            // 点击收藏的监听
            case R.id.collection:
                if (users.size() == 0) {
                    // 如果没有登陆 提示先登陆 并跳转登陆页面
                    Toast.makeText(mActivity, "请登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mActivity, UserCenterActivity.class);
                    mActivity.startActivity(intent);
                    mActivity.overridePendingTransition
                            (R.anim.translate_enter_in, R.anim.translate_enter_out);
                } else {
                    // 如果登陆 跳转到收藏页面
                    Intent intent = new Intent(mActivity, CollectionActivity.class);
                    mActivity.startActivity(intent);
                    mActivity.overridePendingTransition
                            (R.anim.translate_enter_in, R.anim.translate_enter_out);
                }

                break;
            // 点击清楚的监听
            case R.id.clear_card:
                // 这个类中的清除不好使 需要用自己写的文件地址进行删除
                DataCleanManager.cleanInternalCache(mActivity);
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

    /**
     * 推送选择的监听
     **/
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

    /**
     * 可见可交互状态
     */
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
