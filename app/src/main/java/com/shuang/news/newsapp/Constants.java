package com.shuang.news.newsapp;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/3/26 下午9:12
 */
public class Constants {

    private static final int PAGE_SIZE = 10;

    private static final String FORMAT_PAGE = "/%d-%d.html";

    private static final String HOST = "http://c.m.163.com/nc/article/list/";

    public static final String[] TAB_TITLE_ARRAY = {
            "娱乐",
            "体育",
            "财经",
            "科技",
            "影视",
    };

    public static final String[] TAB_URL_KEY_ARRAY = {
            "T1348648517839",
            "T1348649079062",
            "T1348648756099",
            "T1348649580692",
            "T1348648650048",
    };

    public static String format(String url, int startPage) {
        int end = (startPage + 1) * PAGE_SIZE;
        return String.format(HOST + url + FORMAT_PAGE, 0, end);
    }

}
