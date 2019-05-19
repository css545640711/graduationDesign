package com.shuang.news.newsapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

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
        return HomeFragment.newInstance(Constants.TAB_URL_KEY_ARRAY[i]);
    }

    @Override
    public int getCount() {
        return Constants.TAB_TITLE_ARRAY.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Constants.TAB_TITLE_ARRAY[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
    }
}
