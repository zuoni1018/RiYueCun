package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.FragmentPagerAddTitlesAdapter;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;
import com.zuoni.riyuecun.ui.fragment.club.ClubRankFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zangyi_shuai_ge on 2017/10/20
 */

public class MyClubRankActivity extends BaseTitleActivity {
    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_club_rank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("我的俱乐部");
        List<String> titles = new ArrayList<>();
        titles.add("铜黄豆级");
        titles.add("银繁星级");
        titles.add("金皓月级");
        titles.add("至尊太阳系");
        List<Fragment> mList = new ArrayList<>();
        mList.add(new ClubRankFragment());
        mList.add(new ClubRankFragment());
        mList.add(new ClubRankFragment());
        mList.add(new ClubRankFragment());
        FragmentPagerAddTitlesAdapter mPagerAdapter = new FragmentPagerAddTitlesAdapter(getSupportFragmentManager(), mList, titles);
        viewPager.setAdapter(mPagerAdapter);
        xTablayout.setupWithViewPager(viewPager);


    }
}
