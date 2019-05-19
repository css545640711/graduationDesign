package com.shuang.news.newsapp.wrapper.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/4/9 下午2:32
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mArray = new SparseArray<>();

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public <T extends View> T getView(int id) {
        T view = (T) mArray.get(id);
        if (view != null) {
            return view;
        } else {
            view = itemView.findViewById(id);
            mArray.put(id, view);
            return view;
        }
    }

    public ViewHolder setText(int id, String text) {
        TextView textView = getView(id);
        textView.setText(text);
        return this;
    }


    public static ViewHolder createViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new ViewHolder(itemView);
    }
}
