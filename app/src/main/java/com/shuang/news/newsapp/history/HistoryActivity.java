package com.shuang.news.newsapp.history;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.shuang.news.newsapp.R;
import com.shuang.news.newsapp.collection.CollectionActivity;
import com.shuang.news.newsapp.home.DetailWebActivity;
import com.shuang.news.newsapp.home.adapter.NewsAdapter;
import com.shuang.news.newsapp.home.data.NewsData;
import com.shuang.news.newsapp.wrapper.FileHelper;
import com.shuang.news.newsapp.wrapper.SPHelper;
import com.shuang.news.newsapp.wrapper.adapter.EaseAdapter;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();

        initData();

        initListener();
    }

    private void initView() {
        setSupportActionBar((Toolbar) findViewById(R.id.title));

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NewsAdapter();
        recyclerView.setAdapter(mAdapter);

    }

    private void initData() {
        List<NewsData> collectionList = FileHelper.getHistoryList(this);
        if (collectionList != null) {
            mAdapter.addData(collectionList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new EaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(EaseAdapter adapter, int pos) {
                NewsData itemData = mAdapter.getItemData(pos);
                if (itemData == null)
                    return;
                startActivity(new Intent(HistoryActivity.this, DetailWebActivity.class)
                        .putExtra("data", (Parcelable) itemData)
                        .putExtra("position", pos));
            }
        });
    }
}
