package com.shuang.news.newsapp.wrapper.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/4/9 下午2:33
 */
public abstract class EaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private List<T> mDatas;
    private int mResId;
    private OnItemClickListener mOnItemClickListener;

    public EaseAdapter(int resId, List<T> datas) {
        mDatas = datas == null ? new ArrayList<T>() : datas;
        mResId = resId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //布局填充器，负责将mResId对应布局填充到父布局中-viewGroup
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //将mResId对应布局填充到父布局viewGroup中
        View view = inflater.inflate(mResId, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(EaseAdapter.this, viewHolder.getAdapterPosition());
                }
            }
        });
        convert(viewHolder, mDatas.get(i));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    protected abstract void convert(ViewHolder holder, T data);

    public void addData(List<T> list) {
        if (list == null || list.size() == 0)
            return;
        int fromPos = mDatas.size();
        int size = list.size();
        if (fromPos >= size) {
            mDatas.clear();
            mDatas.addAll(list);
        } else {
            mDatas.addAll(list.subList(fromPos, list.size()));
        }
        notifyItemRangeChanged(fromPos, size);
    }

    public void clear() {
        mDatas.clear();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Nullable
    public T getItemData(int pos) {
        if (pos < mDatas.size() - 1)
            return mDatas.get(pos);
        return null;
    }

    public interface OnItemClickListener {
        void onItemClick(EaseAdapter adapter, int pos);
    }
}
