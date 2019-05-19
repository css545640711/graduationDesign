package com.shuang.news.newsapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.shuang.news.newsapp.collection.CollectionActivity;
import com.shuang.news.newsapp.history.HistoryActivity;

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
     * findViewById 通过ID定位到对应控件
     */
    private void initView() {
        setSupportActionBar((Toolbar) findViewById(R.id.title));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_collection_list:
                startActivity(new Intent(this, CollectionActivity.class));
                break;
            case R.id.action_history_list:
                startActivity(new Intent(this, HistoryActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
