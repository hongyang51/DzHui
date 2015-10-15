package com.lanou3g.mydazahui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lanou3g.mydazahui.adapter.PatchWork_First_PageAdapter;
import com.lanou3g.mydazahui.base.MainActivity;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.utils.sharedprefer.SharedPreferUtil;

import java.util.ArrayList;

/**
 * Created by xyb on 15/9/22.
 */
public class PatchWork_FirstActivity extends MainActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager patchWorkFirst;
    private PatchWork_First_PageAdapter patchWork_first_pageAdapter;
    private LinearLayout linearLayout; //引导的小圆点
    private ImageView imageView;
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private Button pacthWork_First_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initViewListener();
    }


    private void initView() {
        setContentView(R.layout.patchwork_first_viewpager);
        patchWork_first_pageAdapter = new PatchWork_First_PageAdapter(this);
        patchWorkFirst = (ViewPager) findViewById(R.id.PatchWork_First_Viewpager);
        linearLayout = (LinearLayout) findViewById(R.id.patchWork_first_LinearLayout);
        pacthWork_First_Button = (Button) findViewById(R.id.pacthwork_first_button);

        for (int i = 0; i < 3; i++) {
            imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            if (i != 0) {
                params.leftMargin = 10;
            }
            imageView.setLayoutParams(params);
            imageViews.add(imageView);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.patchwork_first_red);
            } else {
                imageViews.get(i).setBackgroundResource(R.drawable.patchwork_first_guid);
            }
            linearLayout.addView(imageViews.get(i));
        }
        patchWorkFirst.setAdapter(patchWork_first_pageAdapter);

    }


    private void initViewListener() {
        pacthWork_First_Button.setOnClickListener(this);
        patchWorkFirst.addOnPageChangeListener(this);// Viewpager监听
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pacthwork_first_button:
                SharedPreferUtil.setBoolean(PatchWork_FirstActivity.this, "is_user_guide_showed", true);
                startActivity(new Intent(PatchWork_FirstActivity.this, PWRead_Home_Tanhost_Activity.class));
                finish();
                break;
        }

    }

    /**
     * Viewpager监听
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < 3; i++) {
            if (i == position) {
                imageViews.get(i).setBackgroundResource(R.drawable.patchwork_first_red);
            } else {
                imageViews.get(i).setBackgroundResource(R.drawable.patchwork_first_guid);
            }
        }
        if (position == 2) {
            pacthWork_First_Button.setVisibility(View.VISIBLE);
        } else {
            pacthWork_First_Button.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
