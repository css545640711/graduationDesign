package com.shuang.news.newsapp.home.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuang.news.newsapp.R;
import com.shuang.news.newsapp.home.data.NewsData;
import com.shuang.news.newsapp.wrapper.FileHelper;
import com.shuang.news.newsapp.wrapper.SPHelper;
import com.shuang.news.newsapp.wrapper.adapter.EaseAdapter;
import com.shuang.news.newsapp.wrapper.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/4/9 下午2:31
 */
public class NewsAdapter extends EaseAdapter<NewsData> {

    private OnCollectionChangedListener mListener;

    public NewsAdapter() {
        super(R.layout.item_news, null);
    }

    @Override
    protected void convert(final ViewHolder holder, final NewsData data) {
        holder.setText(R.id.tv_title, data.getTitle())
                .setText(R.id.tv_source, String.format("来源：%s", data.source))
                .setText(R.id.tv_vote_num, String.valueOf(data.votecount))
                .setText(R.id.tv_public_time, data.mtime)
                .setText(R.id.tv_replay_num, String.valueOf(data.replyCount));
        ImageView isLook = holder.getView(R.id.iv_is_look);
        isLook.setImageResource(SPHelper.hasBrowseNews(mContext, data.docid) ?
                R.mipmap.ic_look_green : R.mipmap.ic_look_gray);
        ImageView ivBg = holder.getView(R.id.iv_background);

        Glide.with(ivBg.getContext())
                .asDrawable()
                .load(data.imgsrc)
                .into(ivBg);

        final TextView collection = holder.getView(R.id.tv_collection);
        int result = SPHelper.hasCollectionNews(mContext, data.docid) ?
                R.mipmap.ic_collection_green : R.mipmap.ic_collection_gray;
        collection.setText(SPHelper.hasCollectionNews(mContext, data.docid) ?
                R.string.collectioned : R.string.collection);
        collection.setCompoundDrawablesWithIntrinsicBounds(result, 0, 0, 0);
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView view = (TextView) v;
                if (SPHelper.hasCollectionNews(mContext, data.docid)) {
                    view.setText(R.string.collection);
                    view.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_collection_gray, 0, 0, 0);
                    SPHelper.removeCollectionNews(mContext, data.docid);


                    //移除收藏
                    unCollection(data);
                } else {
                    view.setText(R.string.collectioned);
                    view.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_collection_green, 0, 0, 0);
                    SPHelper.addCollectionNews(mContext, data.docid);

                    //添加收藏
                    collection(data);
                }

                if (mListener != null) {
                    mListener.onCollectionChanged(NewsAdapter.this, holder.getAdapterPosition());
                }
            }
        });
    }

    private void unCollection(NewsData data) {
        List<NewsData> collectionList = FileHelper.getCollectionList(mContext);
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
        FileHelper.saveCollectionList(mContext, collectionList);
    }

    private void collection(NewsData data) {
        List<NewsData> collectionList = FileHelper.getCollectionList(mContext);
        if (collectionList == null) {
            collectionList = new ArrayList<>();
        }
        collectionList.add(data);
        FileHelper.saveCollectionList(mContext, collectionList);
    }

    public void setOnCollectionChangedListener(OnCollectionChangedListener listener) {
        mListener = listener;
    }

    public interface OnCollectionChangedListener {
        void onCollectionChanged(NewsAdapter adapter, int pos);
    }

}
