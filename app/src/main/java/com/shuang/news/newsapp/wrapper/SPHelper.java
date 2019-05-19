package com.shuang.news.newsapp.wrapper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/5/19 下午6:18
 */
public class SPHelper {

    private static final String BROWSE = "browse";
    private static final String COLLECTION = "collection";

    /**
     * 判断本地是否有该新闻的浏览记录
     */
    public static boolean hasBrowseNews(Context context, String docid) {
        SharedPreferences sp = getSP(context, BROWSE);
        return sp.contains(docid);
    }

    /**
     * 判断本地是否收藏该新闻
     */
    public static boolean hasCollectionNews(Context context, String docid) {
        SharedPreferences sp = getSP(context, COLLECTION);
        return sp.contains(docid);
    }

    public static void addBrowseNews(Context context, String docid) {
        SharedPreferences sp = getSP(context, BROWSE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(docid, "");
        edit.apply();
    }

    public static void addCollectionNews(Context context, String docid) {
        SharedPreferences sp = getSP(context, COLLECTION);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(docid, "");
        edit.apply();
    }

    public static void removeCollectionNews(Context context, String docid) {
        SharedPreferences sp = getSP(context, COLLECTION);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(docid);
        edit.apply();
    }

    private static SharedPreferences getSP(Context context, String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

}
