package com.lanou3g.mydazahui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lanou3g.mydazahui.R;

import java.util.ArrayList;

/**
 * Created by xyb on 15/9/22.
 */
public class PatchWork_First_PageAdapter extends PagerAdapter {
    private Context context;
    private static final int[] imgs = new int[]{R.mipmap.read, R.mipmap.read, R.mipmap.read};
    private ArrayList<ImageView> imageViews;

    public PatchWork_First_PageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < imgs.length ; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(imgs[i]);
            imageViews.add(imageView);
        }
        container.addView(imageViews.get(position));
        return imageViews.get(position);


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
