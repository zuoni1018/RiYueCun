package com.zuoni.riyuecun.ui.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mobike.library.MobikeView;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.gson.GetMyLevelInfo;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.MainActivity;
import com.zuoni.riyuecun.ui.activity.MyClubActivity;
import com.zuoni.riyuecun.ui.activity.MyClubRankActivity;
import com.zuoni.riyuecun.ui.activity.RecordsOfConsumptionActivity;
import com.zuoni.riyuecun.ui.activity.TestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 */

public class ClubFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.mobike_view)
    MobikeView mobikeView;
    @BindView(R.id.UserLevelName)
    TextView UserLevelName;
    @BindView(R.id.NeedThing)
    TextView NeedThing;
    @BindView(R.id.CurrentThingCount)
    TextView CurrentThingCount;
    @BindView(R.id.tvDescribe)
    TextView tvDescribe;
    @BindView(R.id.layoutRank)
    LinearLayout layoutRank;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.layout3)
    LinearLayout layout3;
    @BindView(R.id.layout4)
    LinearLayout layout4;

    private View view;
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
//            R.mipmap.club_9,
//            R.mipmap.sun,
//            R.mipmap.club_9,
//            R.mipmap.club_9,
//            R.mipmap.club_9,
//            R.mipmap.club_9,
//            R.mipmap.sun,
    };
    private SensorManager sensorManager;
    private Sensor defaultSensor;


    private boolean nowIsShow = false;


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i("我的俱乐部Fragment创建了");
        view = inflater.inflate(R.layout.fragment_my_club, null);
        unbinder = ButterKnife.bind(this, view);

        if (CacheUtils.isLogin(getContext())) {
            GetMyLevelInfo();
        }

        initViews();
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        return view;
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
        LogUtil.i("我的俱乐部onResume");
        super.onResume();
        if (nowIsShow) {
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

    private void initViews() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        for (int i = 0; i < imgs.length; i++) {
//            View view =LayoutInflater.from(getContext()).inflate(R.layout.test,null);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(imgs[i]);
            imageView.setBackgroundColor(getResources().getColor(R.color.color_calendar_state_02));
            imageView.setTag(R.id.mobike_view_circle_tag, false);
            mobikeView.addView(imageView, layoutParams);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.layout1, R.id.layout2, R.id.layout3, R.id.layout4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout1:
                jumpToActivity(MyClubActivity.class);
                break;
            case R.id.layout2:
                jumpToActivity(RecordsOfConsumptionActivity.class);
                break;
            case R.id.layout3:
                jumpToActivity("早餐甜豆花邀请券");
                break;
            case R.id.layout4:
                jumpToActivity("早餐甜豆花邀请券");
                break;
        }
    }

    private void jumpToActivity(Class<?> cls) {
        Intent mIntent = new Intent(getContext(), cls);
        startActivity(mIntent);
    }

    public void GetMyLevelInfo() {
        mainActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetMyLevelInfo);//获取等级信息
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                mainActivity.closeLoading();
                LogUtil.i("获取等级信息" + response);
                GetMyLevelInfo info = gson.fromJson(response, GetMyLevelInfo.class);
                if (info.getHttpCode() == 200) {
                    //获取等级成功
                    UserLevelName.setText(info.getListData().get(0).getUserLevelName());
                    NeedThing.setText("目前获赠" + info.getListData().get(0).getNeedThing() + "数");

                    CurrentThingCount.setText(info.getListData().get(0).getCurrentThingCount() + "");

                    String describe = "集齐" + info.getListData().get(0).getNextThingCount() + "个" + info.getListData().get(0).getNeedThing() + " , \n"
                            + "升级为" + info.getListData().get(0).getNextLevelName();

                    tvDescribe.setText(describe);

//                    集齐58个小太阳,\n升级为至尊太阳系会员会员
                } else {
                    mainActivity.showToast(info.getMessage());
                }
            }

            @Override
            public void onFailed(Exception exception) {
                mainActivity.closeLoading();
                mainActivity.showToast("服务器异常");
            }
        }, getContext());

    }

    @OnClick(R.id.layoutRank)
    public void onViewClicked() {
        jumpToActivity(MyClubRankActivity.class);
    }

    private void jumpToActivity(String title) {
        Intent mIntent = new Intent(getContext(), TestActivity.class);
        mIntent.putExtra("title", title);
        startActivity(mIntent);
    }
}
