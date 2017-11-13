package com.zuoni.riyuecun.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.common.utils.DensityUtils;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.gson.GetFirstElecCard;
import com.zuoni.riyuecun.bean.model.ElectronicCard;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.AddStoredValueCardActivity;
import com.zuoni.riyuecun.ui.activity.MainActivity;
import com.zuoni.riyuecun.ui.activity.ManagerCardActivity;
import com.zuoni.riyuecun.ui.activity.MoreCardActivity;
import com.zuoni.riyuecun.ui.activity.PaymentActivity;
import com.zuoni.riyuecun.util.ImageLoaderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 * 电子储值卡
 */

public class CardFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.ivCard)
    SelectableRoundedImageView ivCard;
    @BindView(R.id.CardName)
    TextView CardName;
    @BindView(R.id.CardMoney)
    TextView CardMoney;
    @BindView(R.id.layoutManager)
    LinearLayout layoutManager;
    @BindView(R.id.tvMore)
    TextView tvMore;
    @BindView(R.id.layoutMain)
    LinearLayout layoutMain;
    @BindView(R.id.layoutNoData)
    RelativeLayout layoutNoData;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private View view;
    private MainActivity mainActivity;
    private ElectronicCard electronicCard;
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    private boolean isGetInfo = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card, null);
        unbinder = ButterKnife.bind(this, view);
        initRefreshLayout();

        ivCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (electronicCard == null) {
                    mainActivity.showToast("还未获得储值卡");
                    return;
                }
                mIntent = new Intent(getContext(), PaymentActivity.class);
                mIntent.putExtra("electronicCard", electronicCard);
                startActivity(mIntent);
            }
        });

        mainActivity.setTopBarRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getContext(), AddStoredValueCardActivity.class);
                startActivityForResult(mIntent, 100);
            }
        });

        layoutMain.setVisibility(View.GONE);
        layoutNoData.setVisibility(View.VISIBLE);
        //没登录就不去刷
        if (CacheUtils.isLogin(getContext())) {
            GetFirstElecCard(true);
        }
        return view;
    }

    public void refreshData() {
        GetFirstElecCard(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 10087) {
            GetFirstElecCard(true);//刷新当前界面数据
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    Intent mIntent;

    @OnClick({R.id.tvMore, R.id.layoutManager})
    public void mainLoginView(View view) {
        switch (view.getId()) {
            case R.id.tvMore:
                mIntent = new Intent(getContext(), MoreCardActivity.class);
                startActivity(mIntent);
                break;
            case R.id.layoutManager:
                if (electronicCard == null) {
                    mainActivity.showToast("还未获得储值卡");
                } else {
                    mIntent = new Intent(getContext(), ManagerCardActivity.class);
                    mIntent.putExtra("electronicCard", electronicCard);
                    startActivityForResult(mIntent, 100);
                }
                break;
        }
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
                GetFirstElecCard(false);
            }
        });
        refreshLayout.setProgressViewEndTarget(false, DensityUtils.dp2px(getContext(), 120));
    }



    private void GetFirstElecCard(final boolean isShowLoading) {
        if (isShowLoading) {
            mainActivity.showLoading();
        }
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetFirstElecCard);//获得第一张储值卡
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                LogUtil.i("获得第一张储值卡" + response);
                if (isShowLoading) {
                    mainActivity.closeLoading();
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false);
                        }
                    }, 500);
                }
                //卡信息
                GetFirstElecCard info = gson.fromJson(response, GetFirstElecCard.class);
                if (info.getHttpCode() == 200) {
                    electronicCard = info.getModel1();
                    ImageLoaderUtils.setStoredValueCardImage(getContext(), electronicCard.getCardImage(), ivCard); //卡图片
                    CardMoney.setText(" ￥" + electronicCard.getCardMoney());
                    CardName.setText("卡号( " + electronicCard.getCardName() + " )");
                    layoutMain.setVisibility(View.VISIBLE);
                    layoutNoData.setVisibility(View.GONE);
                } else if (info.getHttpCode() == 300) {
                    layoutMain.setVisibility(View.GONE);
                    layoutNoData.setVisibility(View.VISIBLE);
                } else {
                    mainActivity.showToast(info.getMessage());
                }
            }

            @Override
            public void onFailed(Exception exception) {
                if (isShowLoading) {
                    mainActivity.closeLoading();
                } else {
                    refreshLayout.setRefreshing(false);
                }
                mainActivity.showToast("服务器异常");
            }
        }, getContext());
    }
}
