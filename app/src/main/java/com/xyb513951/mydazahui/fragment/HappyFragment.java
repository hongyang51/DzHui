package com.xyb513951.mydazahui.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lanou3g.mydazahui.R;
import com.xyb513951.mydazahui.adapter.HappyFragment_ViewPager_Adapter;
import com.xyb513951.mydazahui.base.BaseFragment;

/**
 * 开心页面
 * Created by dllo on 15/9/22.
 */
public class HappyFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private ViewPager viewPager;
    private HappyFragment_ViewPager_Adapter adapter;
    private RadioGroup happy_bar;
    private RadioButton happy_popular, happy_new;


    /**
     * 初始化视图
     */
    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_happy, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        happy_bar = (RadioGroup) view.findViewById(R.id.happy_bar);
        happy_popular = (RadioButton) view.findViewById(R.id.happy_popular);
        happy_new = (RadioButton) view.findViewById(R.id.happy_new);
        return view;
    }

    /**
     * 视图数据
     */
    @Override
    public void initData() {
        adapter = new HappyFragment_ViewPager_Adapter(mActivity);
        happy_popular.setChecked(true);
        viewPager.setAdapter(adapter);
        initViewLinstener();
    }

    /**
     * 试图监听
     */

    private void initViewLinstener() {
        happy_bar.setOnCheckedChangeListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // viewpager第几个页被选择
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        happy_popular.setChecked(true);
                        break;
                    case 1:
                        happy_new.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 页首checkBox监听
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.happy_popular:
                viewPager.setCurrentItem(0);
                break;
            case R.id.happy_new:
                viewPager.setCurrentItem(1);
                break;
        }

    }
}
