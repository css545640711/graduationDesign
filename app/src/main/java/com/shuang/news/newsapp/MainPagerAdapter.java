package com.shuang.news.newsapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shuang.news.newsapp.home.HomeFragment;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/3/26 下午9:09
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return HomeFragment.newInstance(Constant.TAB_URL_ARRAY[i]);
    }

    @Override
    public int getCount() {
        return Constant.TAB_TITLE_ARRAY.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Constant.TAB_TITLE_ARRAY[position];
    }
}
