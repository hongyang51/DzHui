package com.lanou3g.mydazahui.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.adapter.HappyFragment_ViewPager_Adapter;
import com.lanou3g.mydazahui.base.BaseFragment;

/**
 * Created by dllo on 15/9/22.
 */
public class HappyFragment extends BaseFragment {
    private ViewPager viewPager;
    private HappyFragment_ViewPager_Adapter adapter;




    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_happy, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        return view;
    }

    @Override
    public void initData() {
        adapter = new HappyFragment_ViewPager_Adapter(mActivity);
        viewPager.getId();
        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Handler handler = new Handler();
//                Message msg = new Message();
//                msg.what = 207;
//                msg.obj = position;
//                handler.sendMessage(msg);
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }
}
