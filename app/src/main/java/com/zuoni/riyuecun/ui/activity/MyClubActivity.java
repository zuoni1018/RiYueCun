package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.FragmentPagerAdapter;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;
import com.zuoni.riyuecun.ui.fragment.club.ClubLeftFragment;
import com.zuoni.riyuecun.ui.fragment.club.ClubRightFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2017/10/19
 */

public class MyClubActivity extends BaseTitleActivity {
    @BindView(R.id.left)
    TextView left;
    @BindView(R.id.right)
    TextView right;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> mList;
    private FragmentPagerAdapter mPagerAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_club;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("我的俱乐部");
        mList = new ArrayList<>();
        mList.add(new ClubLeftFragment());
        mList.add(new ClubRightFragment());
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mList);
        viewPager.setAdapter(mPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        left.setBackgroundColor(getResources().getColor(R.color.club_tab_color_02));
                        right.setBackgroundColor(getResources().getColor(R.color.club_tab_color_01));
                        break;
                    case 1:
                        right.setBackgroundColor(getResources().getColor(R.color.club_tab_color_02));
                        left.setBackgroundColor(getResources().getColor(R.color.club_tab_color_01));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.left, R.id.right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left:
                left.setBackgroundColor(getResources().getColor(R.color.club_tab_color_02));
                right.setBackgroundColor(getResources().getColor(R.color.club_tab_color_01));
                viewPager.setCurrentItem(0);
                break;
            case R.id.right:
                right.setBackgroundColor(getResources().getColor(R.color.club_tab_color_02));
                left.setBackgroundColor(getResources().getColor(R.color.club_tab_color_01));
                viewPager.setCurrentItem(1);
                break;
        }
    }

    @OnClick(R.id.layoutRight)
    public void onViewClicked() {


    }
}