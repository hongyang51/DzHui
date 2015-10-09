package com.lanou3g.mydazahui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.base.MainActivity;
import com.lanou3g.mydazahui.bean.Theme;
import com.lanou3g.mydazahui.fragment.AboutFragment;
import com.lanou3g.mydazahui.fragment.HappyFragment;
import com.lanou3g.mydazahui.fragment.HomeFragment;
import com.lanou3g.mydazahui.fragment.NewsFragment;
import com.lanou3g.mydazahui.utils.SharedPreferUtil;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dllo on 15/9/23.
 */
public class PWRead_Home_Tanhost_Activity extends MainActivity implements HomeFragment.NewsOnclick, AboutFragment.IChangePage {
    private TabHost myTabhost;
    private FragmentManager manager;
    private long exitTime = 0;// 双击返回间隔时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferUtil.setBoolean(PWRead_Home_Tanhost_Activity.this, "is_user_guide", true);
        initView();
        initTabHost();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        setContentView(R.layout.activity_read_tabhost);
        myTabhost = (TabHost) findViewById(android.R.id.tabhost);
        myTabhost.setup();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.tabhost_frameLayout_home, new HomeFragment());
        transaction.replace(R.id.tabhost_frameLayout_news, new NewsFragment(), Final_Base.TABSPC_NEWS_TAG);
        transaction.replace(R.id.tabhost_frameLayout_happy, new HappyFragment());
        transaction.replace(R.id.tabhost_frameLayout_about, new AboutFragment());
        transaction.commit();//提交事务
    }

    /**
     * 定义TabHost单元块
     */
    private void initTabHost() {
        // 定义一个tabspec单元 参数代表的是tabspec的tag名称
        // 定义首页标签
        TabHost.TabSpec tabSpec_home = myTabhost.newTabSpec(Final_Base.TABSPC_HOME_TAG);
        View home = getLayoutInflater().inflate(R.layout.tab_home, null);
        tabSpec_home.setIndicator(home);
        tabSpec_home.setContent(R.id.tabhost_frameLayout_home);
        myTabhost.addTab(tabSpec_home);
        // 定义新闻标签
        TabHost.TabSpec tabSpec_news = myTabhost.newTabSpec(Final_Base.TABSPC_NEWS_TAG);
        View news = getLayoutInflater().inflate(R.layout.tab_news, null);
        tabSpec_news.setIndicator(news);
        tabSpec_news.setContent(R.id.tabhost_frameLayout_news);
        myTabhost.addTab(tabSpec_news);
        // 定义开心标签
        TabHost.TabSpec tabSpec_happy = myTabhost.newTabSpec(Final_Base.TABSPC_HAPPY_TAG);
        View happy = getLayoutInflater().inflate(R.layout.tab_happy, null);
        tabSpec_happy.setIndicator(happy);
        tabSpec_happy.setContent(R.id.tabhost_frameLayout_happy);
        myTabhost.addTab(tabSpec_happy);
        // 定义关于标签
        TabHost.TabSpec tabSpec_about = myTabhost.newTabSpec(Final_Base.TABSPC_ABOUT_TAG);
        View about = getLayoutInflater().inflate(R.layout.tab_about, null);
        tabSpec_about.setIndicator(about);
        tabSpec_about.setContent(R.id.tabhost_frameLayout_about);
        myTabhost.addTab(tabSpec_about);

    }


    /**
     * 双击返回的Toast提示
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(PWRead_Home_Tanhost_Activity.this, "再按一次退出新闻大杂烩", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                overridePendingTransition
                        (R.anim.translate_exit_in, R.anim.translate_exit_out);
            }
        }
        return false;
    }

    /**
     * 点击伪瀑布流跳转到对应的页面
     */
    @Override
    public void OnNewsOnclick(String s, ArrayList<Theme.OthersEntity> othersEntities) {
        if (s != null) {
            myTabhost.setCurrentTab(1);
        }
        NewsFragment fragment = (NewsFragment) manager.findFragmentByTag(Final_Base.TABSPC_NEWS_TAG);
        ViewPager viewPager = fragment.getmViewPager();
        for (int i = 0; i < othersEntities.size(); i++) {
            boolean b = s == othersEntities.get(i).getName();
            if (b) {
                viewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void changePage() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.tabhost_frameLayout_about, new AboutFragment());
        transaction.commit();//提交事务
        Log.e("taggg", "执行跳转");
    }

    public class SerInfo implements Serializable {

    }
}
