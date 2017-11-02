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

import com.mobike.library.MobikeView;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.R;
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


    private boolean nowIsShow=false;


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i("我的俱乐部Fragment创建了");
        view = inflater.inflate(R.layout.fragment_my_club, null);
        unbinder = ButterKnife.bind(this, view);

        //这里要异步
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                initViews();

                sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
                defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//                mobikeView.getmMobike().onStart();
//                sensorManager.registerListener(listerner, defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);
//            }
//        }, 500);


        return view;
    }

    @Override
    public void onStart() {
        LogUtil.i("我的俱乐部onStart");
        super.onStart();
        if (mobikeView != null) {
            mobikeView.getmMobike().onStart();
        }
    }

    //当前Fragment 是否显示
    public void showClubFragment(boolean isShow) {
        nowIsShow=isShow;
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
    public void onStop() {
        LogUtil.i("我的俱乐部onStop");

        if (mobikeView != null) {
            mobikeView.getmMobike().onStop();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        LogUtil.i("我的俱乐部onResume");
        super.onResume();
        if(nowIsShow){
            if (sensorManager != null) {
                sensorManager.registerListener(listerner, defaultSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

//    @Override
    public void onPause() {
        LogUtil.i("我的俱乐部onPause");
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listerner);
        }
    }

    private long lastTime = 0;
    float lastX = 0;
    float lastY = 0;
    private SensorEventListener listerner = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1] * 2.0f;
                if (Math.abs(lastX * 1000 - x * 1000) > 200|Math.abs(lastY * 1000 - y * 1000) > 200) {
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
            imageView.setTag(R.id.mobike_view_circle_tag, true);
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
