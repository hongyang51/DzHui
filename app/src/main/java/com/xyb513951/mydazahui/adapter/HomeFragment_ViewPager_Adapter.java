package com.xyb513951.mydazahui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou3g.mydazahui.R;
import com.xyb513951.mydazahui.activity.WebViewActivity;
import com.xyb513951.mydazahui.base.Final_Base;
import com.xyb513951.mydazahui.greendaobean.TopStoriesEntity;
import com.xyb513951.mydazahui.utils.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 15/9/23.
 */
public class HomeFragment_ViewPager_Adapter extends PagerAdapter {
    private Context context;
    private ImageView home_viewpager_item_imageView;
    private TextView home_viewpager_item_textView;
    private View view;
    private VolleySingleton singleton;
    private ImageLoader imageLoader;
    private ArrayList<TopStoriesEntity> topStories;

    public HomeFragment_ViewPager_Adapter(Context context, ArrayList<TopStoriesEntity> topStories) {
        this.context = context;
        this.topStories = topStories;
        singleton = VolleySingleton.getVolleySingleton(context);
        imageLoader = singleton.getImageLoader();
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        view = View.inflate(context, R.layout.home_viewpager_item, null);
        home_viewpager_item_imageView = (ImageView) view.findViewById(R.id.home_viewpager_item_imageView);
        home_viewpager_item_textView = (TextView) view.findViewById(R.id.home_viewpager_item_textView);
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(home_viewpager_item_imageView, R.mipmap.lanniao, R.mipmap.lanniao);
        String imgUrl = topStories.get(position % topStories.size()).getImage();
        imageLoader.get(imgUrl, listener);
        String text = topStories.get(position % topStories.size()).getTitle();
        home_viewpager_item_textView.setText(text);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                int newsId = topStories.get(position % topStories.size()).getId();
                Log.e("sss", position % topStories.size() + "----->" + topStories.get(position % topStories.size()).getTitle());
                intent.putExtra(Final_Base.NEWSID, newsId);
                context.startActivity(intent);
            }
        });


        /**
         * //千万不要忘了 添加view集合
         * */
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
