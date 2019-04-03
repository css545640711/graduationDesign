package com.shuang.news.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * 新闻详情页面
 */
public class DetailWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_web);

        String url = getIntent().getStringExtra("url");

        WebView view = findViewById(R.id.web_view);
        view.loadUrl(url);
    }
}
