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
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zuoni.common.widget.MyViewPager;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.FragmentPagerAdapter;
import com.zuoni.riyuecun.ui.activity.base.BaseActivity;
import com.zuoni.riyuecun.ui.fragment.CardFragment;
import com.zuoni.riyuecun.ui.fragment.ClubFragment;
import com.zuoni.riyuecun.ui.fragment.MainFragment;
import com.zuoni.riyuecun.ui.fragment.NewsFragment;
import com.zuoni.riyuecun.ui.fragment.SettingsFragment;

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


    private List<Fragment> mList;
    private FragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
//        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), getResources().getColor(R.color.black), 100);
        StatusBarUtil.setTranslucentForDrawerLayout(MainActivity.this, (DrawerLayout) findViewById(R.id.drawer_layout), 0);
        setTopView();
//        init
        mList = new ArrayList<>();
        mList.add(new MainFragment());
        mList.add(new ClubFragment());
        mList.add(new CardFragment());
        mList.add(new NewsFragment());
        mList.add(new SettingsFragment());
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mList);
        viewPager.setAdapter(mPagerAdapter);
//        showXEdit.setSeparator(" ");
//        showXEdit.setPattern(new int[]{3, 4, 4, 5, 5});

        api = WXAPIFactory.createWXAPI(this, GlobalVariable.WX_APP_ID);
//        HttpRequest  mHttpRequest=new HttpRequest("");
//
//
//        CallServer.getInstance().request(mHttpRequest, new HttpResponseListener() {
//            @Override
//            public void onSucceed(String response, Gson gson) {
//                BaseHttpResponse baseHttpResponse=gson.fromJson(response,BaseHttpResponse.class);
//            }
//
//            @Override
//            public void onFailed(Exception exception) {
//
//            }
//        });

    }


    private void setTopView() {
//        ViewGroup.LayoutParams layoutParams =  topView.getLayoutParams();
//        layoutParams.height= ScreenUtils.getStatusHeight(getContext());
//        topView.setLayoutParams(layoutParams);
    }
    private void setTitle(String title) {
        tvTitle.setText(title);
    }
    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    private IWXAPI api;

//    @OnClick(R.id.btTest)
//    public void onViewClicked() {
////        Intent mIntent=new Intent(GlobalVariable.BROADCAST_TOKEN_ERROR);
////        sendBroadcast(mIntent);
////        sendBroadcast(mIntent);
////        jumpToActivity(CreateQrCodeActivity.class);
//
//        //
////        WxShareWeb wxWebShare=new WxShareWeb(
////                "https://www.baidu.com/index.php?tn=02049043_14_pg",
////                "古早日月村标题",
////                "古早日月村描述",
////                R.mipmap.zzz);
////
////        wxWebShare.share(getContext(),api);
//
//        jumpToActivity(FindStoresActivity.class);
//
//    }


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
                menu1.setBackgroundColor(getResources().getColor(R.color.menu_color_1));
                viewPager.setCurrentItem(0, false);
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle("首页");
                break;
            case R.id.menu_2:
                //我的俱乐部
                restMenu();
                menu2.setBackgroundColor(getResources().getColor(R.color.menu_color_1));
                viewPager.setCurrentItem(1, false);
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle("我的俱乐部");
                break;
            case R.id.menu_3:
                //电子储值卡
                restMenu();
                menu3.setBackgroundColor(getResources().getColor(R.color.menu_color_1));
                viewPager.setCurrentItem(2, false);
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle("电子储值卡");
                break;
            case R.id.menu_4:
                restMenu();
                menu4.setBackgroundColor(getResources().getColor(R.color.menu_color_1));
                viewPager.setCurrentItem(3, false);
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle("消息中心");
                break;
            case R.id.menu_5:
                jumpToActivity(FindStoresActivity.class);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_6:
                restMenu();
                menu6.setBackgroundColor(getResources().getColor(R.color.menu_color_1));
                viewPager.setCurrentItem(4, false);
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle("设置");
                break;

        }
    }

    private void restMenu() {
        menu1.setBackgroundColor(getResources().getColor(R.color.menu_color_2));
        menu2.setBackgroundColor(getResources().getColor(R.color.menu_color_2));
        menu3.setBackgroundColor(getResources().getColor(R.color.menu_color_2));
        menu4.setBackgroundColor(getResources().getColor(R.color.menu_color_2));
        menu6.setBackgroundColor(getResources().getColor(R.color.menu_color_2));
    }
}
