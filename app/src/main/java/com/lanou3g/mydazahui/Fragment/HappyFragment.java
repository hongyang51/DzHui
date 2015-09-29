package com.lanou3g.mydazahui.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.adapter.HappyFragment_ViewPager_Adapter;
import com.lanou3g.mydazahui.base.BaseFragment;

/**
 * Created by dllo on 15/9/22.
 */
public class HappyFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private ViewPager viewPager;
    private HappyFragment_ViewPager_Adapter adapter;
    private RadioGroup happy_bar;
    private RadioButton happy_popular, happy_new;




    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_happy, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        happy_bar = (RadioGroup) view.findViewById(R.id.happy_bar);
        happy_popular = (RadioButton) view.findViewById(R.id.happy_popular);
        happy_new = (RadioButton) view.findViewById(R.id.happy_new);
        return view;
    }

    @Override
    public void initData() {
        adapter = new HappyFragment_ViewPager_Adapter(mActivity);
        happy_bar.setOnCheckedChangeListener(this);
        happy_popular.setChecked(true);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.happy_popular:
                viewPager.setCurrentItem(0);
            break;
            case R.id.happy_new:
                viewPager.setCurrentItem(1);
                break;
        }

    }
}
