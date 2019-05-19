package com.shuang.news.newsapp.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shuang.news.newsapp.R;
import com.shuang.news.newsapp.home.data.NewsData;
import com.shuang.news.newsapp.wrapper.FileHelper;
import com.shuang.news.newsapp.wrapper.LogUtil;
import com.shuang.news.newsapp.wrapper.SPHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻详情页面
 */
public class DetailWebActivity extends AppCompatActivity {

    private NewsData mNewsData;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_web);

        mNewsData = getIntent().getParcelableExtra("data");
        if (mNewsData == null)
            mNewsData = new NewsData();
        mPosition = getIntent().getIntExtra("position", 0);

        setSupportActionBar((Toolbar) findViewById(R.id.title));

        WebView view = findViewById(R.id.web_view);
        //WebView 配置
        WebSettings settings = view.getSettings();
        settings.setSupportZoom(false);//禁止缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        view.setWebViewClient(new MyWebViewClient());
        view.loadUrl(mNewsData.getUrl());
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //禁止跳转到外部浏览器
            LogUtil.e("Detail =========" + url);
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        MenuItem item = menu.findItem(R.id.action_collection);
        if (SPHelper.hasCollectionNews(this, mNewsData.docid)) {
            item.setIcon(R.mipmap.ic_collection_green);
        } else {
            item.setIcon(R.mipmap.ic_collection_gray);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_collection) {
            if (SPHelper.hasCollectionNews(this, mNewsData.docid)) {
                item.setIcon(R.mipmap.ic_collection_gray);
                SPHelper.removeCollectionNews(this, mNewsData.docid);
                unCollection(mNewsData);
            } else {
                item.setIcon(R.mipmap.ic_collection_green);
                SPHelper.addCollectionNews(this, mNewsData.docid);
                collection(mNewsData);
            }
        }
        setResult(100, new Intent().putExtra("position", mPosition));
        return super.onOptionsItemSelected(item);
    }


    private void unCollection(NewsData data) {
        if (TextUtils.isEmpty(data.docid))
            return;
        List<NewsData> collectionList = FileHelper.getCollectionList(this);
        if (collectionList == null) {
            return;
        }
        NewsData targetData = null;
        for (NewsData newsData : collectionList) {
            if (TextUtils.equals(newsData.docid, data.docid)) {
                targetData = newsData;
                break;
            }
        }
        if (targetData != null)
            collectionList.remove(targetData);
        FileHelper.saveCollectionList(this, collectionList);
    }

    private void collection(NewsData data) {
        if (TextUtils.isEmpty(data.docid))
            return;
        List<NewsData> collectionList = FileHelper.getCollectionList(this);
        if (collectionList == null) {
            collectionList = new ArrayList<>();
        }
        collectionList.add(data);
        FileHelper.saveCollectionList(this, collectionList);
    }

}
