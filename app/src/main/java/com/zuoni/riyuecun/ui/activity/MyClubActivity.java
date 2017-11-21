package com.zuoni.riyuecun.ui.activity;

import android.content.Intent;
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
    private ClubLeftFragment clubLeftFragment;
    private ClubRightFragment clubRightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("我的俱乐部");

        clubLeftFragment = new ClubLeftFragment();
        clubRightFragment = new ClubRightFragment();

        clubLeftFragment.setMyClubActivity(this);
        clubRightFragment.setMyClubActivity(this);

        mList = new ArrayList<>();
        mList.add(clubLeftFragment);
        mList.add(clubRightFragment);

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

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_club;
    }


    /**
     * 刷新2个Fragment的数据
     */
    public void refreshAll() {
        clubLeftFragment.refresh();
        clubRightFragment.refresh();
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
        Intent mIntent = new Intent(getContext(), AddCardActivity.class);
        startActivityForResult(mIntent, 10086);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086 && resultCode == 10087) {
            refreshAll();//添加储值卡后需要刷新界面
        }
    }
}
