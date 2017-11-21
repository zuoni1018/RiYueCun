package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jaeger.library.StatusBarUtil;
import com.zuoni.common.callback.TabOnClickListener;
import com.zuoni.common.widget.MyViewPager;
import com.zuoni.riyuecun.AppUtils;
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
    private List<Fragment> mList;
    private FragmentPagerAdapter mPagerAdapter;
    private MainFragment mainFragment;
    private SettingsFragment settingsFragment;
    private ClubFragment clubFragment;
    private CardFragment cardFragment;
    private NewsFragment newsFragment;
    private FindStoreFragment findStoreFragment;


    public static MainActivity mainActivity;

    //定位
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
//        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), getResources().getColor(R.color.black), 100);
        StatusBarUtil.setTranslucentForDrawerLayout(MainActivity.this, (DrawerLayout) findViewById(R.id.drawer_layout), 0);
        mainActivity = this;
        AppUtils.mainFragmentNeedRefresh = true;


        mLocationClient = new LocationClient(getApplicationContext());




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


        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        initLocationClientOption();
        mLocationClient.start();//开启定位

    }

    private void initLocationClientOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(0);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

//        option.setIgnoreCacheException(false);
//        可选，设置是否收集Crash信息，默认收集，即参数为false

//        option.setWifiValidTime(5 * 60 * 1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    public void setTabOnClickListener01(TabOnClickListener tabOnClickListener01) {
        this.tabOnClickListener01 = tabOnClickListener01;
    }


    /**
     * 刷新所有数据
     */
    public void refreshAllData() {
        //刷新我的俱乐部数据
        clubFragment.refreshData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                cardFragment.refreshData();
            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainFragment.refreshData();
            }
        }, 100);

    }

    /**
     * 跳转界面
     * Fragment 里也可以去切换界面
     */
    public void turnPage(int pageNum) {

        switch (pageNum) {
            case 0:
                turnPage01();
                break;
            case 1:
                turnPage02();
                break;
            case 2:
                turnPage03();
                break;
            case 3:
                turnPage04();
                break;
            case 4:
                turnPage05();
                break;
            case 5:
                turnPage06();
                break;
        }
    }

    /**
     * 跳转到第一页
     * 首页
     */
    private void turnPage01() {
        restMenu();
        setTitle("首页");
        menu1.setBackgroundResource(R.drawable.menu_bg_01);
        viewPager.setCurrentItem(0, false);//切换viewPager
        drawerLayout.closeDrawer(GravityCompat.START);//关闭抽屉
        tabOnClickListener01.onClick();//触发tab 事件
        clubFragment.showClubFragment(false);//关闭我的俱乐部中的某些状态 传感器之类的
    }

    /**
     * 跳转到第二页
     * 我的俱乐部
     */
    private void turnPage02() {
        //我的俱乐部
        if (CacheUtils.isLogin(getContext())) {
            restMenu();
            setTitle("我的俱乐部");
            menu2.setBackgroundResource(R.drawable.menu_bg_01);
            viewPager.setCurrentItem(1, false);
            drawerLayout.closeDrawer(GravityCompat.START);
            clubFragment.showClubFragment(true);
        } else {
            showToast("请先登录");
        }
    }

    /**
     * 跳转到第三页
     * 我的俱乐部
     */
    private void turnPage03() {
        if (CacheUtils.isLogin(getContext())) {
            restMenu();
            setTitle("电子储值卡");
            menu3.setBackgroundResource(R.drawable.menu_bg_01);
            viewPager.setCurrentItem(2, false);
            drawerLayout.closeDrawer(GravityCompat.START);
            clubFragment.showClubFragment(false);
            topBarRight.setVisibility(View.VISIBLE);
        } else {
            showToast("请先登录");
        }
    }

    /**
     * 跳转到第四页
     * 消息中心
     */
    private void turnPage04() {
        restMenu();
        setTitle("消息中心");
        menu4.setBackgroundResource(R.drawable.menu_bg_01);
        viewPager.setCurrentItem(3, false);
        drawerLayout.closeDrawer(GravityCompat.START);
        clubFragment.showClubFragment(false);
    }

    /**
     * 跳转到第五页
     * 查找门店
     */
    private void turnPage05() {
        restMenu();
        setTitle("查找门店");
        menu5.setBackgroundResource(R.drawable.menu_bg_01);
        viewPager.setCurrentItem(4, false);
        drawerLayout.closeDrawer(GravityCompat.START);
        clubFragment.showClubFragment(false);
    }

    /**
     * 跳转到第六页
     * 设置
     */
    private void turnPage06() {
        if (CacheUtils.isLogin(getContext())) {
            restMenu();
            setTitle("设置");
            menu6.setBackgroundResource(R.drawable.menu_bg_01);
            viewPager.setCurrentItem(5, false);
            drawerLayout.closeDrawer(GravityCompat.START);
            clubFragment.showClubFragment(false);
        } else {
            showToast("请先登录");
        }
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
                turnPage01();//首页
                break;
            case R.id.menu_2:
                turnPage02();//我的俱乐部
                break;
            case R.id.menu_3:
                turnPage03();//电子储值卡
                break;
            case R.id.menu_4:
                turnPage04();//消息中心
                break;
            case R.id.menu_5:
                turnPage05();//查找门店
                break;
            case R.id.menu_6:
                turnPage06();//设置
                break;
        }
    }

    /**
     * 重置菜单
     */
    private void restMenu() {
        menu1.setBackgroundResource(R.drawable.menu_bg_02);
        menu2.setBackgroundResource(R.drawable.menu_bg_02);
        menu3.setBackgroundResource(R.drawable.menu_bg_02);

        menu4.setBackgroundResource(R.drawable.menu_bg_02);
        menu5.setBackgroundResource(R.drawable.menu_bg_02);
        menu6.setBackgroundResource(R.drawable.menu_bg_02);

        topBarRight.setVisibility(View.INVISIBLE);//头部右侧按钮不可见
    }

    public void setTopBarRightOnClickListener(View.OnClickListener onClickListener) {
        topBarRight.setOnClickListener(onClickListener);
    }


    private  BDLocation location;
    public BDLocation getLocation() {
        return location;
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            location=location;
//            LogUtil.i("首页定位"+"Latitude=="+location.getLatitude()+"Longitude=="+location.getLongitude());
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

//            LogUtil.i("定位"+location.getAddrStr());
                mainFragment.setNewLocation(location);
        }
    }

}
