package com.zuoni.riyuecun.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zuoni.common.callback.TabOnClickListener;
import com.zuoni.common.widget.MyViewPager;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.FragmentPagerAdapter;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.ui.activity.base.BaseActivity;
import com.zuoni.riyuecun.ui.fragment.main.CardFragment;
import com.zuoni.riyuecun.ui.fragment.main.MainFragment;
import com.zuoni.riyuecun.ui.fragment.main.NewsFragment;
import com.zuoni.riyuecun.ui.fragment.main.ClubFragment;
import com.zuoni.riyuecun.ui.fragment.main.FindStoreFragment;
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


//    @BindView(R.id.topView)
//    View topView;


    private ClubFragment clubFragment;
    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int REQUEST_CODE_SETTING = 300;

    private List<Fragment> mList;
    private FragmentPagerAdapter mPagerAdapter;


    private Handler handler = new Handler();

    android.support.v7.app.AlertDialog alertDialog;


    //tab
    private MainFragment mainFragment;

    private TabOnClickListener tabOnClickListener01;

    public void setTabOnClickListener01(TabOnClickListener tabOnClickListener01) {
        this.tabOnClickListener01 = tabOnClickListener01;
    }

    private SettingsFragment settingsFragment;


//    private T

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
//        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), getResources().getColor(R.color.black), 100);
        StatusBarUtil.setTranslucentForDrawerLayout(MainActivity.this, (DrawerLayout) findViewById(R.id.drawer_layout), 0);
        getPermission();

        showLoading();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeLoading();
            }
        }, 3000);


        clubFragment = new ClubFragment();
        clubFragment.showClubFragment(false);
//        init
        mList = new ArrayList<>();

        //首页
        mainFragment = new MainFragment();
        mainFragment.setMainActivity(this);
        mList.add(mainFragment);


        mList.add(clubFragment);
        mList.add(new CardFragment());
        mList.add(new NewsFragment());
        mList.add(new FindStoreFragment());


        //
        settingsFragment = new SettingsFragment();
        settingsFragment.setMainActivity(this);
        mList.add(settingsFragment);


        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mList);
        viewPager.setOffscreenPageLimit(-1);
        viewPager.setAdapter(mPagerAdapter);

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
//                jumpToActivity(FindStoresActivity.class);
//                drawerLayout.closeDrawer(GravityCompat.START);
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
        menu1.setBackgroundResource(R.drawable.menu_bg_02);
        menu2.setBackgroundResource(R.drawable.menu_bg_02);
        menu3.setBackgroundResource(R.drawable.menu_bg_02);

        menu4.setBackgroundResource(R.drawable.menu_bg_02);
        menu5.setBackgroundResource(R.drawable.menu_bg_02);
        menu6.setBackgroundResource(R.drawable.menu_bg_02);
    }

    private void getPermission() {

        // 申请权限。
        AndPermission.with(MainActivity.this)
                .requestCode(REQUEST_CODE_PERMISSION)
                .permission(Permission.LOCATION)
                .callback(permissionListener)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(rationaleListener)
                .start();

    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：
            // AndPermission.rationaleDialog(Context, Rationale).show();

            // 自定义对话框。
            AlertDialog.newBuilder(MainActivity.this)
                    .setTitle("提示")
                    .setMessage("我们需要的一些必要权限被禁止，请授权给我们。")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("就不", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };
    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
//                    Toast.makeText(MainActivity.this,, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                    break;
                }
            }

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(MainActivity.this, REQUEST_CODE_SETTING).show();

                // 第二种：用自定义的提示语。
//             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                     .setTitle("权限申请失败")
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                     .setPositiveButton("好，去设置")
//                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingHandle = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingHandle.execute();
//            你的dialog点击了取消调用：
//            settingHandle.cancel();
            }
        }
    };
}
