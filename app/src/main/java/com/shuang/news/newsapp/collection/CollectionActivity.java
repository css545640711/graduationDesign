package com.shuang.news.newsapp.collection;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shuang.news.newsapp.R;
import com.shuang.news.newsapp.home.DetailWebActivity;
import com.shuang.news.newsapp.home.adapter.NewsAdapter;
import com.shuang.news.newsapp.home.data.NewsData;
import com.shuang.news.newsapp.wrapper.FileHelper;
import com.shuang.news.newsapp.wrapper.SPHelper;
import com.shuang.news.newsapp.wrapper.adapter.EaseAdapter;

import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

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
        List<NewsData> collectionList = FileHelper.getCollectionList(this);
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
                startActivityForResult(new Intent(CollectionActivity.this, DetailWebActivity.class)
                                .putExtra("data", (Parcelable) itemData)
                                .putExtra("position", pos),
                        10010);
                SPHelper.addBrowseNews(CollectionActivity.this, itemData.docid);
            }
        });
        mAdapter.setOnCollectionChangedListener(new NewsAdapter.OnCollectionChangedListener() {
            @Override
            public void onCollectionChanged(NewsAdapter adapter, int pos) {
                NewsData itemData = mAdapter.getItemData(pos);
                if (itemData != null) {
                    if (!SPHelper.hasCollectionNews(CollectionActivity.this, itemData.docid)) {
                        mAdapter.notifyItemRemoved(pos);
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10010 && resultCode == 100) {
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                NewsData itemData = mAdapter.getItemData(position);
                if (itemData != null && !SPHelper.hasCollectionNews(this, itemData.docid)) {
                    mAdapter.notifyItemRemoved(position);
                }
            }
        }
    }
}
