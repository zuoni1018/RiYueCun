package com.zuoni.riyuecun.ui.fragment.main;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.mobike.library.MobikeView;
import com.zuoni.common.utils.DensityUtils;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.AppUtils;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.RvMianItem02Adapter;
import com.zuoni.riyuecun.bean.gson.GetMyCoupon;
import com.zuoni.riyuecun.bean.gson.GetMyLevelInfo;
import com.zuoni.riyuecun.bean.model.Coupon;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.MainActivity;
import com.zuoni.riyuecun.ui.activity.MyClubActivity;
import com.zuoni.riyuecun.ui.activity.MyClubRankActivity;
import com.zuoni.riyuecun.ui.activity.RecordsOfConsumptionActivity2;
import com.zuoni.riyuecun.ui.activity.TestActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 * 我的俱乐部
 * 这个界面 第一次加载和手动刷新的时候需要重新刷新数据
 * 添加了会员卡后 由于新增会员卡 需要重新刷新数据
 */

public class ClubFragment extends Fragment {

    Unbinder unbinder;
    //头布局
    private MobikeView mobikeView;
    private TextView UserLevelName;
    private TextView NeedThing;
    private TextView CurrentThingCount;
    private TextView tvDescribe;
    private LinearLayout layoutRank;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private TextView clubItem01;
    private TextView clubItem02;
    private ImageView ivBottleHead;

    private View view;

    @BindView(R.id.mRecyclerView)
    LRecyclerView mRecyclerView;
    private LRecyclerViewAdapter mAdapter;
    private MainActivity mainActivity;

    private int[] imgs = {
            R.mipmap.moon,
            R.mipmap.moon,
            R.mipmap.moon,
            R.mipmap.club_17,
            R.mipmap.club_9,
            R.mipmap.club_9,
            R.mipmap.club_9,
            R.mipmap.club_9,
    };
    private SensorManager sensorManager;
    private Sensor defaultSensor;


    private boolean nowIsShow = false;
    private List<Coupon> mList;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private Handler handler = new Handler();
    private boolean isFirst=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i("Fragment", "我的俱乐部onCreateView");
        view = inflater.inflate(R.layout.fragment_my_club, null);
        unbinder = ButterKnife.bind(this, view);
        mList = new ArrayList<>();
        mAdapter = new LRecyclerViewAdapter(new RvMianItem02Adapter(getContext(), mList));
        initHead();

        //获取重力传感器
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                GetMyCoupon();
            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetMyLevelInfo();
            }
        });

        // 如果没有登录  感觉这边数据都不用初始化了
        if (CacheUtils.isLogin(getContext())) {
            mRecyclerView.refresh();
        }


        return view;
    }


    private void initHead() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.fragment_my_club_head, null, false);
        //瓶子
        mobikeView = header.findViewById(R.id.mobike_view);
        //等级信息
        UserLevelName = header.findViewById(R.id.UserLevelName);
        NeedThing = header.findViewById(R.id.NeedThing);
        CurrentThingCount = header.findViewById(R.id.CurrentThingCount);
        tvDescribe = header.findViewById(R.id.tvDescribe);
        layoutRank = header.findViewById(R.id.layoutRank);
        ivBottleHead=header.findViewById(R.id.ivBottleHead);

        layoutRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(MyClubRankActivity.class);
            }
        });
        mAdapter.addHeaderView(header);

        //消费记录
        clubItem01 = header.findViewById(R.id.clubItem01);
        clubItem02 = header.findViewById(R.id.clubItem02);

        layout1 = header.findViewById(R.id.layout1);

        layout2 = header.findViewById(R.id.layout2);

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(MyClubActivity.class);
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(RecordsOfConsumptionActivity2.class);
            }
        });
    }


    /**
     * 刷新数据
     */
    public void refreshData() {
        GetMyLevelInfo();
    }


    //当前Fragment 是否显示
    public void showClubFragment(boolean isShow) {
        nowIsShow = isShow;
        if (isShow) {
            if (mobikeView != null) {
                mobikeView.getmMobike().onStart();
            }
            if (sensorManager != null) {
                sensorManager.registerListener(listerner, defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        } else {
            if (mobikeView != null) {
                mobikeView.getmMobike().onStop();
            }
            if (sensorManager != null) {
                sensorManager.unregisterListener(listerner);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("Fragment", "我的俱乐部onResume");
        if (nowIsShow) {
            //获取数据
            if(AppUtils.clubFragmentNeedRefresh){
                mRecyclerView.refresh();
            }

            if (sensorManager != null) {
                mobikeView.getmMobike().onStart();
                sensorManager.registerListener(listerner, defaultSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    public void onPause() {
        LogUtil.i("我的俱乐部onPause");
        super.onPause();
        if (sensorManager != null) {
            mobikeView.getmMobike().onStop();
            sensorManager.unregisterListener(listerner);
        }
    }

    float lastX = 0;
    float lastY = 0;
    private SensorEventListener listerner = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1] * 2.0f;
                if (Math.abs(lastX * 1000 - x * 1000) > 400 | Math.abs(lastY * 1000 - y * 1000) > 400) {
                    mobikeView.getmMobike().onSensorChanged(-x, y);
                    lastX = x;
                    lastY = y;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void initViews(String needThingName,int num) {

        //把原来的清除掉
        mobikeView.getmMobike().clearBody();
        //盖子动起来
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivBottleHead.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivBottleHead, "translationY",0, DensityUtils.dp2px(getContext(),12));
                objectAnimator.setDuration(1000).start();
            }
        },1000);


        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        List<Integer> imgList=new ArrayList<>();
        for (int i = 0; i <num ; i++) {
            imgList.add( R.mipmap.moon);
        }
        for (int i = 0; i <imgList.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(imgList.get(i));
//            imageView.setBackgroundColor(getResources().getColor(R.color.color_calendar_state_02));
            imageView.setTag(R.id.mobike_view_circle_tag, false);
            mobikeView.addView(imageView, layoutParams);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void jumpToActivity(Class<?> cls) {
        Intent mIntent = new Intent(getContext(), cls);
        startActivity(mIntent);
    }

    public void GetMyLevelInfo() {
        ivBottleHead.setVisibility(View.GONE);
        //把原来的清除掉
        mobikeView.getmMobike().clearBody();

        pageNum = 1;
        mList.clear();
        mAdapter.notifyDataSetChanged();
        mainActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetMyLevelInfo);//获取等级信息
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                mainActivity.closeLoading();
                mRecyclerView.refreshComplete(1);
                LogUtil.i("获取等级信息" + response);
                GetMyLevelInfo info = gson.fromJson(response, GetMyLevelInfo.class);
                if (info.getHttpCode() == 200) {
                    AppUtils.clubFragmentNeedRefresh=false;
                    //获取等级成功
                    UserLevelName.setText(info.getModel1().getUserLevelName());
                    NeedThing.setText("目前获赠" + info.getModel1().getNeedThing() + "数");

                    CurrentThingCount.setText(info.getModel1().getCurrentThingCount() + "");
                    //集齐58个小太阳,\n升级为至尊太阳系会员会员
                    String describe = "集齐" + info.getModel1().getNextThingCount() + "个" + info.getModel1().getNeedThing() + " , \n"
                            + "升级为" + info.getModel1().getNextLevelName();
                    tvDescribe.setText(describe);

                    //设置瓶子里面的东西
                    initViews(info.getModel1().getNeedThing(),info.getModel1().getCurrentThingCount());


                    //消费记录
                    if (info.getModel2() != null && info.getModel2().size() != 0) {
                        clubItem01.setText(info.getModel2().get(0).getShopName());
                        clubItem02.setText(info.getModel2().get(0).getShopTime());
                    }
                    pageNum++;
                    mList.clear();
                    //优惠券
                    if (info.getModel3() != null) {
                        mList.addAll(info.getModel3());
                    }
                    //小于5条关闭上拉加载更多功能
                    if (mList.size() < 5) {
                        mRecyclerView.setLoadMoreEnabled(false);
                    } else {
                        mRecyclerView.setLoadMoreEnabled(true);
                    }
                    mAdapter.notifyDataSetChanged();

                } else {
                    mainActivity.showToast(info.getMessage());
                }
            }

            @Override
            public void onFailed(Exception exception) {
                mRecyclerView.refreshComplete(1);
                mainActivity.closeLoading();
                mainActivity.showToast("服务器异常");
            }
        }, getContext());

    }

    private int pageNum = 1;

    public void GetMyCoupon() {

        mainActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetMyCoupon);//获取优惠券
        httpRequest.add("PageNo", pageNum + "");
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                mRecyclerView.refreshComplete(1);
                mainActivity.closeLoading();
                mRecyclerView.refreshComplete(1);
                LogUtil.i("获取优惠券" + response);
                GetMyCoupon info = gson.fromJson(response, GetMyCoupon.class);
                if (info.getHttpCode() == 200) {
                    pageNum++;
                    mList.addAll(info.getModel1());
                    mAdapter.notifyDataSetChanged();
                } else {
                    mainActivity.showToast(info.getMessage());
                }
            }

            @Override
            public void onFailed(Exception exception) {
                mRecyclerView.refreshComplete(1);
                mainActivity.closeLoading();
                mainActivity.showToast("服务器异常");
            }
        }, getContext());
    }

    private void jumpToActivity(String title) {
        Intent mIntent = new Intent(getContext(), TestActivity.class);
        mIntent.putExtra("title", title);
        startActivity(mIntent);
    }
}
