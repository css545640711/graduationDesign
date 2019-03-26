package com.shuang.news.newsapp.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuang.news.newsapp.R;

public class HomeFragment extends Fragment {

    private String mServerUrl;

    public static HomeFragment newInstance(String url) {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mServerUrl = getArguments().getString("url");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置当前页面展示UI样式
        //LayoutInflater 布局填充器，将自定义的布局填充到父布局中
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //进行View初始化操作

        TextView tvContent = view.findViewById(R.id.tv_content);
        tvContent.setText(mServerUrl);

        // TODO: 2019/3/26 数据加载
    }
}
