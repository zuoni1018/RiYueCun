package com.zuoni.riyuecun.ui.fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.common.callback.TabOnClickListener;
import com.zuoni.common.gallery.ViewPagerGallery;
import com.zuoni.common.utils.DensityUtils;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.AppUtils;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.MyPagerAdapter;
import com.zuoni.riyuecun.bean.gson.GetFirstPage;
import com.zuoni.riyuecun.bean.gson.GetFirstPageNoLogin;
import com.zuoni.riyuecun.bean.model.ElectronicCard;
import com.zuoni.riyuecun.bean.model.Message;
import com.zuoni.riyuecun.bean.model.Store;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.FindStoresActivity;
import com.zuoni.riyuecun.ui.activity.LoginActivity;
import com.zuoni.riyuecun.ui.activity.MainActivity;
import com.zuoni.riyuecun.ui.activity.MyClubActivity;
import com.zuoni.riyuecun.ui.activity.PaymentActivity;
import com.zuoni.riyuecun.ui.activity.WebViewActivity;
import com.zuoni.riyuecun.ui.activity.register.RegisterActivity;
import com.zuoni.riyuecun.util.ImageLoaderUtils;
import com.zuoni.riyuecun.view.IntegralDashboard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 * 首页
 * 首页Fragment 刷新数据的状态
 * 1、会员卡的增删
 * 2、储值卡的增删
 * 3、会员卡设置活跃卡
 */

public class MainFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.btRegister)
    Button btRegister;
    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.MessageImage)
    SelectableRoundedImageView MessageImage;
    @BindView(R.id.ivCard2)
    SelectableRoundedImageView ivCard2;
    @BindView(R.id.layoutlogin)
    LinearLayout layoutlogin;
    @BindView(R.id.layoutmain)
    LinearLayout layoutmain;

    @BindView(R.id.layoutMain2)
    LinearLayout layoutMain2;
    @BindView(R.id.layoutMain03)
    LinearLayout layoutMain03;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.UserLevelName)
    TextView UserLevelName;
    @BindView(R.id.NextLevelName)
    TextView NextLevelName;
    @BindView(R.id.MessageName)
    TextView MessageName;
    @BindView(R.id.MessageDescribe)
    TextView MessageDescribe;


    @BindView(R.id.treeView)
    IntegralDashboard integralDashboard;
    //我的俱乐部

    @BindView(R.id.tvGoMyClub)
    TextView tvGoMyClub;
    @BindView(R.id.size)
    TextView size;//优惠券数量

    //附近门店
    @BindView(R.id.tvGoStore)
    TextView tvGoStore;
    //消息
    @BindView(R.id.vpCoupon)
    ViewPager vpCoupon;

    @BindView(R.id.StoreName)
    TextView StoreName;
    @BindView(R.id.Adress)
    TextView Adress;
    @BindView(R.id.Distance)
    TextView Distance;
    @BindView(R.id.layoutGoStore)
    LinearLayout layoutGoStore;

    private Intent mIntent;
    private View view;
    private MainActivity mainActivity;


    private List<View> couponViews;
    private boolean isLogin = false;
    private static int LOGIN_TAG = 10086;

    //当前定位坐标
    public static String Latitude = "";
    public static String Longitude = "";


    //储值卡
    private List<ElectronicCard> electronicCards;
    @BindView(R.id.ViewPagerGallery)
    ViewPagerGallery ViewPagerGallery;
    @BindView(R.id.tvMoney)
    TextView tvMoney;//储值卡可余额
    @BindView(R.id.CardName)
    TextView CardName;//储值卡信息

    //消息


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mian, null);
        unbinder = ButterKnife.bind(this, view);
        LogUtil.i("Fragment", "首页onCreateView");
        this.inflater = inflater;
        //从缓存中拿定位
        Latitude = CacheUtils.getLatitude(getContext());
        Longitude = CacheUtils.getLongitude(getContext());

        //设置下拉刷新参数
        initRefreshLayout();

        ivCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(getContext(), MyClubActivity.class);
                startActivity(mIntent);
            }
        });

        mainActivity.setTabOnClickListener01(new TabOnClickListener() {
            @Override
            public void onClick() {
                refreshUI();
            }
        });

        return view;
    }

    /**
     * 每次进来刷新下UI
     */
    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("Fragment", "首页onResume");
        refreshUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        CacheUtils.setLocation(Latitude, Longitude, getContext());
        super.onDestroy();
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private LayoutInflater inflater;

    /**
     * 设置下拉刷新
     */
    private void initRefreshLayout() {
        //设置头部加载颜色
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        refreshLayout.setColorSchemeResources(R.color.refresh_color_02, R.color.refresh_color_03);
        refreshLayout.setDistanceToTriggerSync(DensityUtils.dp2px(getContext(), 70));// 设置手指在屏幕下拉多少距离会触发下拉刷新
        refreshLayout.setProgressBackgroundColor(R.color.refresh_color_01); // 设定下拉圆圈的背景
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AppUtils.mainFragmentNeedRefresh = true;
                refreshUI();

            }
        });
        refreshLayout.setProgressViewEndTarget(false, DensityUtils.dp2px(getContext(), 120));
    }

    /**
     * 刷新界面
     */
    private void refreshUI() {
        isLogin = CacheUtils.isLogin(getContext());//获取当前登录状态
        if (isLogin) {
            layoutlogin.setVisibility(View.GONE);
            layoutmain.setVisibility(View.VISIBLE);
            layoutMain2.setVisibility(View.VISIBLE);
            layoutMain03.setVisibility(View.VISIBLE);
            //判断是否有必要取刷新数据
            if (AppUtils.mainFragmentNeedRefresh) {
                GetFirstPage();
            }
        } else {
            layoutlogin.setVisibility(View.VISIBLE);
            layoutmain.setVisibility(View.GONE);
            layoutMain2.setVisibility(View.GONE);
            layoutMain03.setVisibility(View.GONE);
            if (AppUtils.mainFragmentNeedRefresh) {
                GetFirstPageNoLogin();
            }
        }
    }

    /**
     * 没有登陆状态下的2个按钮事件
     */
    @OnClick({R.id.btRegister, R.id.btLogin})
    public void mainLoginView(View view) {
        switch (view.getId()) {
            case R.id.btRegister:
                mIntent = new Intent(getContext(), RegisterActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btLogin:
                mIntent = new Intent(getContext(), LoginActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    /**
     * 提供给MainActivity 调用
     */
    public void refreshData() {
        GetFirstPage();
    }

    /**
     * 没有登录状态下 获取首页数据
     */
    private void GetFirstPageNoLogin() {
        mainActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetFirstPageNoLogin);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                mainActivity.closeLoading();
                refreshLayout.setRefreshing(false);
                LogUtil.i("没登录首页" + response);
                GetFirstPageNoLogin info = gson.fromJson(response, GetFirstPageNoLogin.class);

                if (info.getHttpCode() == 200) {
                    AppUtils.mainFragmentNeedRefresh = false;
                    //文章
                    if (info.getModel2() != null) {
                        message = info.getModel2();
                        ImageLoaderUtils.setCardImage01(getContext(), info.getModel2().getMessageImage(), MessageImage);
                        MessageName.setText(info.getModel2().getMessageName());
                        MessageDescribe.setText(info.getModel2().getMessageDescribe());
                    }
                    //附近门店
                    if (info.getModel1() != null) {
                        Adress.setText(info.getModel1().getAdress());
                        StoreName.setText(info.getModel1().getStoreName());
                        Distance.setText(info.getModel1().getDistance());
                        store = info.getModel1();
                    }
                }
            }

            @Override
            public void onFailed(Exception exception) {
                refreshLayout.setRefreshing(false);
                mainActivity.closeLoading();
                mainActivity.showToast("服务器异常");
            }
        }, getContext());
    }


    private boolean isFirstLocation = true;

    public void setNewLocation(BDLocation location) {
        LogUtil.i("Fragment", "获取到定位");
        Latitude = location.getLatitude() + "";
        Longitude = location.getLongitude() + "";
        if (isFirstLocation) {
            AppUtils.mainFragmentNeedRefresh = true;
            if (CacheUtils.getLatitude(getContext()).equals("")) {
                refreshUI();//如果原来没有缓存定位立马刷新 否则回来再刷 增强用户体验
            }
            isFirstLocation = false;
        }
    }

    /**
     * 登录状态下 获取首页数据
     */
    private void GetFirstPage() {
        mainActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetFirstPage);
        httpRequest.add("Latitude", Latitude);
        httpRequest.add("Longitude", Longitude);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSucceed(String response, Gson gson) {
                mainActivity.closeLoading();
                refreshLayout.setRefreshing(false);
                LogUtil.i("首页Fragment" + response);
                GetFirstPage info = gson.fromJson(response, GetFirstPage.class);
                if (info.getHttpCode() == 200) {
                    //首页数据获取成功后改变需要刷新状态
                    AppUtils.mainFragmentNeedRefresh = false;
                    //加载首页数据
                    //会员卡图片
                    if (info.getModel1() != null) {
                        ImageLoaderUtils.setCardImage01(getContext(), info.getModel1().getImgUrl(), ivCard2);
                    }
                    //等级
                    if (info.getModel2() != null) {
                        UserLevelName.setText(info.getModel2().getUserLevelName());
                        NextLevelName.setText("再消费" + (info.getModel2().getNextThingCount() - info.getModel2().getCurrentThingCount())
                                + "次升级为" + info.getModel2().getNextLevelName());
                        //设置进度条
                        //计算角度
                        int sp = (int) ((info.getModel2().getCurrentThingCount() * 1.000 / (info.getModel2().getNextThingCount() * 1.000)) * 180);
                        integralDashboard.setValue(sp);
                    }
                    //优惠券
                    if (info.getModel4() != null) {
                        couponViews = new ArrayList<>();
                        for (int i = 0; i < info.getModel4().size(); i++) {
                            View view = inflater.inflate(R.layout.vp_main_coupon, null);
                            TextView tv01 = view.findViewById(R.id.CouponName);
                            TextView tv02 = view.findViewById(R.id.ExpirationDate);
                            tv01.setText(info.getModel4().get(i).getCouponName());
                            tv02.setText(info.getModel4().get(i).getExpirationDate());
                            couponViews.add(view);
                        }
                        size.setText(couponViews.size() + "");
                        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(couponViews);
                        vpCoupon.setAdapter(myPagerAdapter);
                    }
                    //附近门店
                    if (info.getModel5() != null) {
                        Adress.setText(info.getModel5().getAdress());
                        StoreName.setText(info.getModel5().getStoreName());
                        Distance.setText(info.getModel5().getDistance());
                        store = info.getModel5();
                    }
                    //储值卡
                    if (info.getModel3() != null) {
                        if (electronicCards == null) {
                            electronicCards = new ArrayList<>();
                        }
                        electronicCards.clear();
                        electronicCards.addAll(info.getModel3());
                        if (electronicCards.size() == 0) {
                            layoutMain2.setVisibility(View.GONE);
                        } else {
                            layoutMain2.setVisibility(View.VISIBLE);
                        }
                        final List<View> views = new ArrayList<>();
                        for (int i = 0; i < info.getModel3().size(); i++) {
                            SelectableRoundedImageView selectableRoundedImageView = new SelectableRoundedImageView(getContext());
                            selectableRoundedImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            selectableRoundedImageView.setCornerRadiiDP(4, 4, 4, 4);
                            ImageLoaderUtils.setCardImage02(getContext(), info.getModel3().get(i).getCardImage(), selectableRoundedImageView);
                            views.add(selectableRoundedImageView);
                        }
                        ViewPagerGallery.setImgResources(views);
                        ViewPagerGallery.setOnClickListener(new ViewPagerGallery.GalleryOnClickListener() {
                            @Override
                            public void onClick(int position) {
                                mIntent = new Intent(getContext(), PaymentActivity.class);
                                mIntent.putExtra("electronicCard", electronicCards.get(position));
                                startActivity(mIntent);
                            }
                        });
                        ViewPagerGallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                tvMoney.setText(" ￥" + electronicCards.get(position).getCardMoney());
                                CardName.setText("储值卡(" + electronicCards.get(position).getCardName() + ")" + "有效期至" + electronicCards.get(position).getEffectiveTime());
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                        //设置默认的储值卡信息
                        if (electronicCards.size() <= 2) {
                            tvMoney.setText(" ￥" + electronicCards.get(0).getCardMoney());
                            CardName.setText("储值卡(" + electronicCards.get(0).getCardName() + ")" + "有效期至" + electronicCards.get(0).getEffectiveTime());
                        } else if (electronicCards.size() > 3) {
                            tvMoney.setText(" ￥" + electronicCards.get(1).getCardMoney());
                            CardName.setText("储值卡(" + electronicCards.get(1).getCardName() + ")" + "有效期至" + electronicCards.get(1).getEffectiveTime());
                        }
                        layoutMain2.setVisibility(View.VISIBLE);
                    } else {
                        layoutMain2.setVisibility(View.GONE);//没有储值卡
                    }
                    //文章
                    if (info.getModel6() != null) {
                        message = info.getModel6();
                        ImageLoaderUtils.setCardImage01(getContext(), info.getModel6().getMessageImage(), MessageImage);
                        MessageName.setText(info.getModel6().getMessageName());
                        MessageDescribe.setText(info.getModel6().getMessageDescribe());
                    }
                } else {
                    mainActivity.showToast(info.getMessage());
                }
            }

            @Override
            public void onFailed(Exception exception) {
                refreshLayout.setRefreshing(false);
                mainActivity.closeLoading();
                mainActivity.showToast("服务器异常");
            }
        }, getContext());
    }

    @OnClick({R.id.tvGoMyClub, R.id.tvGoStore})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvGoMyClub:
                mainActivity.turnPage(1);
                break;
            case R.id.tvGoStore:
                mainActivity.turnPage(4);
                break;
        }
    }

    //附近门店
    private Store store;

    @OnClick(R.id.layoutGoStore)
    public void onViewClicked() {
        if (store != null) {
            Intent mIntent = new Intent(getContext(), FindStoresActivity.class);
            mIntent.putExtra("store", store);
            startActivity(mIntent);
        }
    }

    //消息
    private Message message;

    @OnClick(R.id.MessageImage)
    public void onMessageClicked() {
        if (message != null) {
            Intent mIntent = new Intent(getContext(), WebViewActivity.class);
            mIntent.putExtra("MessageId", message.getMessageID() + "");
            startActivity(mIntent);
        }
    }
}
