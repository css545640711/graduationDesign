package com.shuang.news.newsapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.shuang.news.newsapp.Constants;
import com.shuang.news.newsapp.R;
import com.shuang.news.newsapp.home.adapter.NewsAdapter;
import com.shuang.news.newsapp.home.data.NewsData;
import com.shuang.news.newsapp.home.format.GsonUtil;
import com.shuang.news.newsapp.wrapper.FileHelper;
import com.shuang.news.newsapp.wrapper.SPHelper;
import com.shuang.news.newsapp.wrapper.HttpCallback;
import com.shuang.news.newsapp.wrapper.HttpConnectionHelper;
import com.shuang.news.newsapp.wrapper.adapter.EaseAdapter;
import com.shuang.news.newsapp.wrapper.adapter.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 列表内容展示页，例如“娱乐”列表页
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, EaseAdapter.OnItemClickListener, LoadMoreWrapper.OnLoadMoreListener {

    private String mServerUrlKey;
    private NewsAdapter mNewsAdapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    private AtomicInteger mIndex = new AtomicInteger();
    private SwipeRefreshLayout mRefreshLayout;
    private boolean isLoading;

    public static HomeFragment newInstance(String url) {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        args.putString("urlKey", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mServerUrlKey = getArguments().getString("urlKey");
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

        mRefreshLayout = view.findViewById(R.id.layout_refresh);
        mRefreshLayout.setOnRefreshListener(this);
        RecyclerView list = view.findViewById(R.id.recycler_view);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        mNewsAdapter = new NewsAdapter();
        mLoadMoreWrapper = new LoadMoreWrapper(mNewsAdapter);
        list.setAdapter(mLoadMoreWrapper);

        mNewsAdapter.setOnItemClickListener(this);
        mLoadMoreWrapper.setOnLoadMoreListener(this);

        mIndex.set(0);
        mRefreshLayout.setRefreshing(true);
        loadData();
    }

    private void loadData() {
        HttpConnectionHelper.get(Constants.format(mServerUrlKey, mIndex.get()), new HttpCallback<String>() {
            @Override
            public void success(String result) {
                mRefreshLayout.setRefreshing(false);//停止刷新动画

                //解析json数据
                List<NewsData> list = GsonUtil.format(result, mServerUrlKey, new TypeToken<List<NewsData>>() {
                }.getType(), mIndex.get());

                if (list != null) {
                    //数据由适配器适配到列表试图上
                    mNewsAdapter.addData(list);
                    mIndex.getAndIncrement();//下标加1
                    mLoadMoreWrapper.setLoadMoreView(R.layout.view_load_more);
                } else {
                    Toast.makeText(getContext(), "暂无数据，请重试", Toast.LENGTH_SHORT).show();
                    mLoadMoreWrapper.setLoadMoreView(0);
                }

                //刷新列表操作
                mLoadMoreWrapper.notifyDataSetChanged();

                isLoading = false;
            }

            @Override
            public void fail(Throwable t) {
                Toast.makeText(getContext(), "数据请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        if (isLoading)
            return;
        mIndex.set(0);
        loadData();
        mNewsAdapter.clear();
        isLoading = true;
    }

    @Override
    public void onLoadMoreRequested() {
        if (isLoading)
            return;
        isLoading = true;
        loadData();
    }

    @Override
    public void onItemClick(EaseAdapter adapter, int pos) {
        NewsData itemData = mNewsAdapter.getItemData(pos);
        if (itemData == null)
            return;
        startActivityForResult(new Intent(getContext(), DetailWebActivity.class)
                        .putExtra("data", (Parcelable) itemData)
                        .putExtra("position", pos),
                10010);
        saveHistory(itemData);
        mLoadMoreWrapper.notifyItemChanged(pos);
    }

    private void saveHistory(NewsData data) {
        if (SPHelper.hasBrowseNews(getContext(), data.docid))
            return;

        SPHelper.addBrowseNews(getContext(), data.docid);

        List<NewsData> historyList = FileHelper.getHistoryList(getContext());
        if (historyList == null)
            historyList = new ArrayList<>();
        historyList.add(data);
        FileHelper.saveHistoryList(getContext(), historyList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10010 && resultCode == 100) {
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                mLoadMoreWrapper.notifyItemChanged(position);
            }
        }
    }
}
