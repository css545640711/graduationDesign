package com.shuang.news.newsapp.wrapper;

import android.util.Log;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/4/9 下午3:28
 */
public class LogUtil {

    private static final String TAG = "NewsApp_Log";


    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }


}
