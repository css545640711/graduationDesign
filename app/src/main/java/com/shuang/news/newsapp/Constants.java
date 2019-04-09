package com.shuang.news.newsapp;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/3/26 下午9:12
 */
public class Constants {

    private static final int PAGE_SIZE = 10;

    private static final String HOST = "http://c.m.163.com/nc/article/list/";

    public static final String[] TAB_TITLE_ARRAY = {
            "娱乐",
            "体育",
            "财经",
            "科技",
            "社会",
    };

    public static final String[] TAB_URL_ARRAY = {
            "T1348648517839/%d-%d.html",
            "T1348649079062/%d-%d.html",
            "T1348648756099/%d-%d.html",
            "T1348649580692/%d-%d.html",
            "T1348648037603/%d-%d.html",
    };

    public static String format(String url, int startPage) {
        int end = (startPage + 1) * PAGE_SIZE;
        return String.format( HOST + url, 0, end);
    }

}
