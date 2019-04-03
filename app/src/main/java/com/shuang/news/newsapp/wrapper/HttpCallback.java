package com.shuang.news.newsapp.wrapper;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/3/27 下午8:58
 */
public interface HttpCallback<T> {

    void success(T result);

    void fail(Throwable t);

}
