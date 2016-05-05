package com.xyb513951.mydazahui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xyb513951.mydazahui.fragment.OneNewFragment;
import com.xyb513951.mydazahui.greendaobean.OthersEntity;

import java.util.ArrayList;

/**
 * Created by dllo on 15/9/23.
 */
public class News_ViewPager_FragmentAda extends FragmentPagerAdapter {
    private ArrayList<OthersEntity> othersEntities;

    public News_ViewPager_FragmentAda(FragmentManager fm, ArrayList<OthersEntity> othersEntities) {
        super(fm);
        this.othersEntities = othersEntities;
    }

    @Override
    public Fragment getItem(int position) {
        return OneNewFragment.getFragment(position, othersEntities);
    }

    @Override
    public int getCount() {
        return othersEntities.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return othersEntities.get(position).getName();
    }
}
