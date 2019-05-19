package com.shuang.news.newsapp.wrapper;

import android.content.Context;
import android.support.annotation.Nullable;

import com.shuang.news.newsapp.home.data.NewsData;

import java.io.Serializable;
import java.util.List;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/5/19 下午7:41
 */
public class FileHelper {

    private static final String KEY_COLLECTION = "collection";
    private static final String KEY_HISTORY = "history";

    public static void saveCollectionList(Context context, List<NewsData> list) {
        ACache aCache = getACache(context);
        if (aCache == null)
            return;
        aCache.put(KEY_COLLECTION, (Serializable) list);
    }

    @Nullable
    public static List<NewsData> getCollectionList(Context context) {
        ACache aCache = getACache(context);
        if (aCache == null)
            return null;
        return (List<NewsData>) aCache.getParAsObject(KEY_COLLECTION);
    }

    public static void saveHistoryList(Context context, List<NewsData> list) {
        ACache aCache = getACache(context);
        if (aCache == null)
            return;
        aCache.put(KEY_HISTORY, (Serializable) list);
    }

    @Nullable
    public static List<NewsData> getHistoryList(Context context) {
        ACache aCache = getACache(context);
        if (aCache == null)
            return null;
        return (List<NewsData>) aCache.getParAsObject(KEY_HISTORY);
    }

    private static ACache getACache(Context context) {
        if (context == null)
            return null;
        return ACache.get(context.getApplicationContext());
    }

}
