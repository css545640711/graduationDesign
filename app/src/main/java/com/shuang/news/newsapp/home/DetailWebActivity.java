package com.shuang.news.newsapp.home;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shuang.news.newsapp.R;
import com.shuang.news.newsapp.wrapper.LogUtil;

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
        //WebView 配置
        WebSettings settings = view.getSettings();
        settings.setSupportZoom(false);//禁止缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        view.setWebViewClient(new MyWebViewClient());
        view.loadUrl(url);
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
}
