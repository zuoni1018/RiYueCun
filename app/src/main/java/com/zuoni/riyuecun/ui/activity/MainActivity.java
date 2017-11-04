package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.zuoni.common.callback.TabOnClickListener;
import com.zuoni.common.widget.MyViewPager;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.FragmentPagerAdapter;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.ui.activity.base.BaseActivity;
import com.zuoni.riyuecun.ui.fragment.main.CardFragment;
import com.zuoni.riyuecun.ui.fragment.main.ClubFragment;
import com.zuoni.riyuecun.ui.fragment.main.FindStoreFragment;
import com.zuoni.riyuecun.ui.fragment.main.MainFragment;
import com.zuoni.riyuecun.ui.fragment.main.NewsFragment;
import com.zuoni.riyuecun.ui.fragment.main.SettingsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    //顶部
    @BindView(R.id.top_bar)
    LinearLayout topBar;
    @BindView(R.id.top_bar_left)
    RelativeLayout topBarLeft;
    @BindView(R.id.top_bar_right)
    RelativeLayout topBarRight;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.top_bar_right_icon)
    ImageView topBarRightIcon;

    //侧滑菜单
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.menu_2)
    LinearLayout menu2;
    @BindView(R.id.menu_3)
    LinearLayout menu3;
    @BindView(R.id.menu_4)
    LinearLayout menu4;
    @BindView(R.id.menu_5)
    LinearLayout menu5;
    @BindView(R.id.menu_6)
    LinearLayout menu6;
    @BindView(R.id.menu_1)
    LinearLayout menu1;

    @BindView(R.id.viewPager)
    MyViewPager viewPager;


    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int REQUEST_CODE_SETTING = 300;


    private TabOnClickListener tabOnClickListener01;

    public void setTabOnClickListener01(TabOnClickListener tabOnClickListener01) {
        this.tabOnClickListener01 = tabOnClickListener01;
    }

    private List<Fragment> mList;
    private FragmentPagerAdapter mPagerAdapter;
    private MainFragment mainFragment;
    private SettingsFragment settingsFragment;
    private ClubFragment clubFragment;
    private CardFragment cardFragment;
    private NewsFragment newsFragment;
    private FindStoreFragment findStoreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
//        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), getResources().getColor(R.color.black), 100);
        StatusBarUtil.setTranslucentForDrawerLayout(MainActivity.this, (DrawerLayout) findViewById(R.id.drawer_layout), 0);


        mList = new ArrayList<>();
        //首页
        mainFragment = new MainFragment();
        mainFragment.setMainActivity(this);
        mList.add(mainFragment);

        //我的俱乐部
        clubFragment = new ClubFragment();
        clubFragment.setMainActivity(this);
        mList.add(clubFragment);

        //电子储值卡
        cardFragment = new CardFragment();
        cardFragment.setMainActivity(this);
        mList.add(cardFragment);

        //消息中心
        newsFragment = new NewsFragment();
        newsFragment.setMainActivity(this);
        mList.add(newsFragment);

        //查找门店
        findStoreFragment = new FindStoreFragment();
        findStoreFragment.setMainActivity(this);
        mList.add(findStoreFragment);

        //设置
        settingsFragment = new SettingsFragment();
        settingsFragment.setMainActivity(this);
        mList.add(settingsFragment);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mList);
        viewPager.setOffscreenPageLimit(-1);//设置缓存最大
        viewPager.setAdapter(mPagerAdapter);

    }

    /**
     * 刷新所有数据
     */
    public void refreshAllData() {
        //刷新我的俱乐部数据
        clubFragment.refreshData();
    }


    public void turnFirstPage() {
        restMenu();
        menu1.setBackgroundResource(R.drawable.menu_bg_01);
        viewPager.setCurrentItem(0, false);
        setTitle("首页");
        clubFragment.showClubFragment(false);
        tabOnClickListener01.onClick();
    }

    private void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    private long lastTime = 0;

    @Override
    public void onBackPressed() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - lastTime > 2000) {
            lastTime = nowTime;
            showToast("再按一次退出App");
        } else {
            finish();
        }
    }

    @OnClick({R.id.top_bar_left, R.id.top_bar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_bar_left:
                drawerLayout.openDrawer(GravityCompat.START);//打开抽屉
                break;
            case R.id.top_bar_right:
                break;
        }
    }

    @OnClick({R.id.menu_2, R.id.menu_3, R.id.menu_4, R.id.menu_5, R.id.menu_6, R.id.menu_1})
    public void onMenuClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_1:
                //首页
                restMenu();
                menu1.setBackgroundResource(R.drawable.menu_bg_01);
                viewPager.setCurrentItem(0, false);
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle("首页");
                clubFragment.showClubFragment(false);
                tabOnClickListener01.onClick();
                break;
            case R.id.menu_2:
                //我的俱乐部
                if (CacheUtils.isLogin(getContext())) {
                    restMenu();
                    menu2.setBackgroundResource(R.drawable.menu_bg_01);
                    viewPager.setCurrentItem(1, false);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    setTitle("我的俱乐部");
                    clubFragment.showClubFragment(true);

                } else {
                    showToast("请先登录");
                }

                break;
            case R.id.menu_3:
                //电子储值卡

                if (CacheUtils.isLogin(getContext())) {
                    restMenu();
                    topBarRight.setVisibility(View.VISIBLE);
                    menu3.setBackgroundResource(R.drawable.menu_bg_01);
                    viewPager.setCurrentItem(2, false);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    setTitle("电子储值卡");
                    clubFragment.showClubFragment(false);
                } else {
                    showToast("请先登录");
                }

                break;
            case R.id.menu_4:
                restMenu();
                menu4.setBackgroundResource(R.drawable.menu_bg_01);
                viewPager.setCurrentItem(3, false);
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle("消息中心");
                clubFragment.showClubFragment(false);
                break;
            case R.id.menu_5:
                restMenu();
                menu5.setBackgroundResource(R.drawable.menu_bg_01);
                viewPager.setCurrentItem(4, false);
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle("查找门店");
                clubFragment.showClubFragment(false);
                break;
            case R.id.menu_6:
                if (CacheUtils.isLogin(getContext())) {
                    restMenu();
                    menu6.setBackgroundResource(R.drawable.menu_bg_01);
                    viewPager.setCurrentItem(5, false);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    setTitle("设置");
                    clubFragment.showClubFragment(false);
                } else {
                    showToast("请先登录");
                }
                break;
        }
    }

    private void restMenu() {

        topBarRight.setVisibility(View.INVISIBLE);

        menu1.setBackgroundResource(R.drawable.menu_bg_02);
        menu2.setBackgroundResource(R.drawable.menu_bg_02);
        menu3.setBackgroundResource(R.drawable.menu_bg_02);

        menu4.setBackgroundResource(R.drawable.menu_bg_02);
        menu5.setBackgroundResource(R.drawable.menu_bg_02);
        menu6.setBackgroundResource(R.drawable.menu_bg_02);
    }

    public void  setTopBarRightOnClickListener(View.OnClickListener onClickListener){
        topBarRight.setOnClickListener(onClickListener);
    }

}
