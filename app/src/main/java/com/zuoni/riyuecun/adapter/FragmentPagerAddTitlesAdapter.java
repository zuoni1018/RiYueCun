package com.zuoni.riyuecun.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/4/21
 */

public class FragmentPagerAddTitlesAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private List<Fragment> mList;
    private List<String> mTitles;
    public FragmentPagerAddTitlesAdapter(FragmentManager fm, List<Fragment> pList, List<String> titles) {
        super(fm);
        mList = pList;
        mTitles = titles;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
