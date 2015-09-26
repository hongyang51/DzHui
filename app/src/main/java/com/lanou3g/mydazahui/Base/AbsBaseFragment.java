package com.lanou3g.mydazahui.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 老师写的fragment基类
 * Created by dllo on 15/9/24.
 */
public abstract class AbsBaseFragment extends Fragment {
    protected View view;
    protected Context mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflaterView(inflater, container);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        initData();
    }

    // 设置布局数据
    protected abstract void initData();

    // 初始化布局
    protected abstract void initView();

    // 加载布局
    protected abstract View inflaterView(LayoutInflater inflater, ViewGroup container);
}
