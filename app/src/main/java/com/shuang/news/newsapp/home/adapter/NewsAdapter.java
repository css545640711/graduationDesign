package com.shuang.news.newsapp.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuang.news.newsapp.R;
import com.shuang.news.newsapp.home.data.NewsData;
import com.shuang.news.newsapp.wrapper.adapter.EaseAdapter;
import com.shuang.news.newsapp.wrapper.adapter.ViewHolder;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/4/9 下午2:31
 */
public class NewsAdapter extends EaseAdapter<NewsData> {

    public NewsAdapter() {
        super(R.layout.item_news, null);
    }

    @Override
    protected void convert(ViewHolder holder, NewsData data) {
        holder.setText(R.id.tv_title, data.getTitle())
                .setText(R.id.tv_source, data.source);
        ImageView ivBg = holder.getView(R.id.iv_background);

        Glide.with(ivBg.getContext())
                .asDrawable()
                .load(data.imgsrc)
                .into(ivBg);
    }

}
