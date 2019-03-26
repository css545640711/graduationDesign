package com.shuang.news.newsapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        initListener();
    }

    /**
     * 读取xml样式文件中的控件
     * <p>
     * 通过标签定义的ID找到对应的控件
     */
    private void initView() {
        tabLayout = findViewById(R.id.tab_layout);
        pager = findViewById(R.id.view_pager);

        //进行控件绑定
        tabLayout.setupWithViewPager(pager, true);
    }

    /**
     * 进行数据处理
     * 一般指网络请求或者本地数据处理
     */
    private void initData() {
        //适配模式，设计模式的一种，这里将数据是配到试图中
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
    }

    /**
     * 进行交互处理
     * 点击事件，滑动事件处理
     */
    private void initListener() {

    }
}
