package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.zuoni.common.utils.DensityUtils;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.common.utils.ScreenUtils;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.FragmentPagerAddTitlesAdapter;
import com.zuoni.riyuecun.bean.gson.GetLevelListInfo;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
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

    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.tv02)
    TextView tv02;
    @BindView(R.id.view03)
    View view03;
    @BindView(R.id.layout03)
    LinearLayout layout03;


    private List<Fragment> mList;
    private List<String> titles;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_club_rank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("我的俱乐部");
        titles = new ArrayList<>();
        titles.add("铜黄豆级");
        titles.add("银繁星级");
        titles.add("金皓月级");
        titles.add("至尊太阳系");
        mList = new ArrayList<>();

        GetLevelListInfo();
    }


    private void GetLevelListInfo() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetLevelListInfo);//储值卡消费
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
//                mRecyclerView.refreshComplete(1);

                LogUtil.i("等级列表" + response);
                GetLevelListInfo info = gson.fromJson(response, GetLevelListInfo.class);
                if (info.getHttpCode() == 200) {
                    List<GetLevelListInfo.Model1Bean> mList2 = info.getModel1();
                    if (mList2 != null && mList2.size() == 4) {
                        mList.add(new ClubRankFragment(mList2.get(0).getOptions()));
                        mList.add(new ClubRankFragment(mList2.get(1).getOptions()));
                        mList.add(new ClubRankFragment(mList2.get(2).getOptions()));
                        mList.add(new ClubRankFragment(mList2.get(3).getOptions()));
                        FragmentPagerAddTitlesAdapter mPagerAdapter = new FragmentPagerAddTitlesAdapter(getSupportFragmentManager(), mList, titles);
                        viewPager.setAdapter(mPagerAdapter);
                        viewPager.setOffscreenPageLimit(4);
                        xTablayout.setupWithViewPager(viewPager);
                    } else {
                        showToast("获取等级信息失败");
                        myFinish();
                        return;
                    }

                    if (info.getModel2() != null) {
                        tv01.setText(info.getModel2().getUserLevelName());
                        if("".equals(info.getModel2().getNextLevelName())){
                            tv02.setText("每消费满30元产生一个钻石" );
                        }else {
                            tv02.setText("集齐" + info.getModel2().getNextThingCount() + "个" + info.getModel2().getNeedThing() + "立即升级为" + info.getModel2().getNextLevelName());
                        }

                        //设置进度条
                        double a = info.getModel2().getCurrentThingCount() * 1.000 / info.getModel2().getNextThingCount() * 1.000;
                        setBar(a);
                    }else {
                        showToast("获取等级信息失败");
                        myFinish();
                    }

                } else {
                    showToast(info.getMessage());
                    myFinish();
                }
                closeLoading();
            }

            @Override
            public void onFailed(Exception exception) {
                closeLoading();
                showToast("服务器异常");
                myFinish();
            }
        }, getContext());
    }

    private void setBar(double a) {
//        a=0.5;
        int allw  =  ScreenUtils.getScreenWidth(getContext())- DensityUtils.dp2px(getContext(),40);
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) view03.getLayoutParams();
        params2.width = (int) (a * (allw));
        view03.setLayoutParams(params2);
    }
}
