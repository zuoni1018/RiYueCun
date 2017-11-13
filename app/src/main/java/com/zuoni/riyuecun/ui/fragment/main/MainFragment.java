package com.zuoni.riyuecun.ui.fragment.main;

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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.common.callback.TabOnClickListener;
import com.zuoni.common.gallery.ViewPagerGallery;
import com.zuoni.common.utils.DensityUtils;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.MyPagerAdapter;
import com.zuoni.riyuecun.bean.gson.GetFirstPage;
import com.zuoni.riyuecun.bean.gson.GetFirstPageNoLogin;
import com.zuoni.riyuecun.bean.model.ElectronicCard;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.LoginActivity;
import com.zuoni.riyuecun.ui.activity.MainActivity;
import com.zuoni.riyuecun.ui.activity.MyClubActivity;
import com.zuoni.riyuecun.ui.activity.PaymentActivity;
import com.zuoni.riyuecun.ui.activity.register.RegisterActivity;
import com.zuoni.riyuecun.util.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 */

public class MainFragment extends Fragment {
    @BindView(R.id.btRegister)
    Button btRegister;
    @BindView(R.id.btLogin)
    Button btLogin;
    Unbinder unbinder;
    @BindView(R.id.ViewPagerGallery)
    com.zuoni.common.gallery.ViewPagerGallery ViewPagerGallery;
    @BindView(R.id.MessageImage)
    SelectableRoundedImageView zzzzz;
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
    @BindView(R.id.vpCoupon)
    ViewPager vpCoupon;
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
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.CardName)
    TextView CardName;
    @BindView(R.id.size)
    TextView size;


    private List<View> couponViews;

    private View view;
    private MainActivity mainActivity;
    private boolean isLogin = false;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i("首页Fragment创建了");
        view = inflater.inflate(R.layout.fragment_mian, null);
        unbinder = ButterKnife.bind(this, view);

        //设置下拉刷新参数
        initRefreshLayout();
        this.inflater = inflater;
//        LayoutInflater lf=
//        lf.inflate(R.layout.activity_layout1,null)
        loginShow();

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
                loginShow();
            }
        });


        return view;
    }

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
                if (CacheUtils.isLogin(getContext())) {
                    GetFirstPage();
                } else {
                    GetFirstPageNoLogin();
                }

            }
        });
        refreshLayout.setProgressViewEndTarget(false, DensityUtils.dp2px(getContext(), 120));
    }

    @Override
    public void onResume() {
        super.onResume();
        loginShow();
    }

    private void loginShow() {
        isLogin = CacheUtils.isLogin(getContext());
        if (isLogin) {
            layoutlogin.setVisibility(View.GONE);
            layoutmain.setVisibility(View.VISIBLE);
            layoutMain2.setVisibility(View.VISIBLE);
            layoutMain03.setVisibility(View.VISIBLE);
            GetFirstPage();
//            GetFirstPageNoLogin();
        } else {
            layoutlogin.setVisibility(View.VISIBLE);
            layoutmain.setVisibility(View.GONE);
            layoutMain2.setVisibility(View.GONE);
            layoutMain03.setVisibility(View.GONE);
            GetFirstPageNoLogin();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private Intent mIntent;


    private static int LOGIN_TAG = 10086;

    @OnClick({R.id.btRegister, R.id.btLogin})
    public void mainLoginView(View view) {
        switch (view.getId()) {
            case R.id.btRegister:
                mIntent = new Intent(getContext(), RegisterActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btLogin:
                mIntent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(mIntent, LOGIN_TAG);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_TAG) {
            if (resultCode == 1) {
                //登录成功
                mainActivity.refreshAllData();//重新刷新所有数据
            }
        }
    }

    public void refreshData() {
        GetFirstPage();
    }

    private List<ElectronicCard> electronicCards;

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
                    //文章
                    if (info.getModel2() != null) {
                        Glide.with(getContext())
                                .load(info.getModel2().getMessageImage())
                                .asBitmap()
                                .into(zzzzz);

                        MessageName.setText(info.getModel2().getMessageName());
                        MessageDescribe.setText(info.getModel2().getMessageDescribe());
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

    private void GetFirstPage() {
        mainActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetFirstPage);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                mainActivity.closeLoading();
                refreshLayout.setRefreshing(false);
                LogUtil.i("首页" + response);
                GetFirstPage info = gson.fromJson(response, GetFirstPage.class);
                if (info.getHttpCode() == 200) {
                    //会员卡图片

                    if (info.getModel1() != null) {
                        Glide.with(getContext())
                                .load(info.getModel1().getImgUrl())
                                .asBitmap()
                                .into(ivCard2);
                    }

                    //等级
                    if (info.getModel2() != null) {
                        UserLevelName.setText(info.getModel2().getUserLevelName());
                        NextLevelName.setText("再消费" + info.getModel2().getNextThingCount() + "次升级为" + info.getModel2().getNextLevelName());
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
                        size.setText(couponViews.size()+"");
                        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(couponViews);
                        vpCoupon.setAdapter(myPagerAdapter);
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

                            ImageLoaderUtils.setStoredValueCardImage(getContext(),info.getModel3().get(i).getCardImage(),selectableRoundedImageView);
//                            Glide.with(getContext())
//                                    .load(info.getModel3().get(i).getCardImage())
//                                    .asBitmap()
//                                    .into(selectableRoundedImageView);
                            views.add(selectableRoundedImageView);
                        }

                        ViewPagerGallery.setImgResources(views);

                        ViewPagerGallery.setOnClickListener(new ViewPagerGallery.GalleryOnClickListener() {
                            @Override
                            public void onClick(int position) {
//                                ToastUtils.showToast(getContext(), "点了第" + position);
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

                        if(electronicCards.size()<=2){
                            tvMoney.setText(" ￥" + electronicCards.get(0).getCardMoney());
                            CardName.setText("储值卡(" + electronicCards.get(0).getCardName() + ")" + "有效期至" + electronicCards.get(0).getEffectiveTime());
                        }else  if(electronicCards.size()>3){
                            tvMoney.setText(" ￥" + electronicCards.get(1).getCardMoney());
                            CardName.setText("储值卡(" + electronicCards.get(1).getCardName() + ")" + "有效期至" + electronicCards.get(1).getEffectiveTime());
                        }


                    } else {
                        layoutMain2.setVisibility(View.GONE);
                    }


                    //文章
                    if (info.getModel6() != null) {
                        Glide.with(getContext())
                                .load(info.getModel6().getMessageImage())
                                .asBitmap()
                                .into(zzzzz);

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
}
