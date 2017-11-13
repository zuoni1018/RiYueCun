package com.zuoni.riyuecun.ui.activity.base;

import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.R;

/**
 * Created by zangyi_shuai_ge on 2017/9/30
 * 百度地图定位基类 地图名称为bmapView
 */

public abstract class BMapLocationBaseActivity extends BaseActivity implements SensorEventListener {

    private SensorManager mSensorManager;//传感器
    private LocationMode mCurrentMode;//定位类型 普通 跟随 罗盘
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;//定位服务
    private UiSettings mUiSettings;
    //传感器
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;

    private MyLocationData locData;
    private float mCurrentAccracy;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    boolean isFirstLoc = true;

    boolean isFirst = true;

    private LinearLayout layoutMap;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutMap = (LinearLayout) findViewById(R.id.layoutMap);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //去延迟初始化
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
                mCurrentMode = LocationMode.NORMAL;//定位类型设置为普通模式
                mMapView = new MapView(getContext());
                mBaiduMap = mMapView.getMap();//获取地图图层
                mUiSettings = mBaiduMap.getUiSettings();

                mUiSettings.setAllGesturesEnabled(false);
                mUiSettings.setZoomGesturesEnabled(true);
                mUiSettings.setScrollGesturesEnabled(true);
                mUiSettings.setRotateGesturesEnabled(false);
                mUiSettings.setOverlookingGesturesEnabled(false);

                MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(17);
                mBaiduMap.animateMapStatus(u);
                mBaiduMap.setMyLocationEnabled(true); // 开启定位图层
                mLocClient = new LocationClient(getContext());

                LocationClientOption option = new LocationClientOption();
                option.setOpenGps(true); // 打开gps
                option.setCoorType("bd09ll"); // 设置坐标类型
                option.setScanSpan(1000);
                mLocClient.setLocOption(option);
                mLocClient.start();

                mLocClient.registerLocationListener(new BDAbstractLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation location) {
                        LogUtil.i("收到定位啦~~~");

                        // map view 销毁后不在处理新接收的位置
                        if (location == null || mMapView == null) {
                            return;
                        }
                        mCurrentLat = location.getLatitude();
                        mCurrentLon = location.getLongitude();
                        mCurrentAccracy = location.getRadius();
                        locData = new MyLocationData.Builder()
                                .accuracy(location.getRadius())
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mCurrentDirection).latitude(location.getLatitude())
                                .longitude(location.getLongitude()).build();

                        mBaiduMap.setMyLocationData(locData);//设置最新的坐标 可以吧它get出来
                        LogUtil.i("定位位置", "Latitude:" + mCurrentLat + "mCurrentLon:" + mCurrentLon);

                        if (isFirstLoc) {
                            isFirstLoc = false;
                            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                            MapStatus.Builder builder = new MapStatus.Builder();
                            builder.target(ll).zoom(18.0f);
                            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));//把坐标移动过去
                        }


                    }
                });
                layoutMap.setVisibility(View.INVISIBLE);
                layoutMap.addView(mMapView);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layoutMap.setVisibility(View.VISIBLE);
                    }
                },300);
            }

        }, 200);
    }

    //
    @Override
    protected void onResume() {
        super.onResume();
        if(mMapView!=null&&mSensorManager!=null){
            mMapView.onResume();
            mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        }

    }
    @Override
    protected void onPause() {
        if(mMapView!=null){
            mMapView.onPause();
        }
        super.onPause();
    }
    @Override
    protected void onStop() {
        if(mSensorManager!=null){
            mSensorManager.unregisterListener(this);
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(mMapView!=null&&mLocClient!=null){
            // 退出时销毁定位
            mLocClient.stop();
//        // 关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
            mMapView.onDestroy();
            mMapView = null;
        }
        super.onDestroy();
    }

    public void setLocationMode(MyLocationConfiguration.LocationMode var1) {
        mCurrentMode = var1;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mCurrentMode, true, null));
        //我也不知道为什么罗盘不需要以下属性
        if (var1 != LocationMode.COMPASS) {
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.overlook(0);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder().accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public BaiduMap getBaiduMap() {
        return mBaiduMap;
    }

    public Marker drawMarker(double var1, double var3){
        //定义Maker坐标点
        LatLng point = new LatLng(var1, var3);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ryc_56);
        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .position(point)
//                .icon(bitmap);
        //在地图上添加Marker，并显示
        MarkerOptions ooD = new MarkerOptions().position(point).icon(bitmap).zIndex(3).title("22222");
        // 生长动画
        ooD.animateType(MarkerOptions.MarkerAnimateType.jump);
       return  (Marker) mBaiduMap.addOverlay(ooD);
    }


    //自定义定位图标
//    mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
//    mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker, accuracyCircleFillColor, accuracyCircleStrokeColor));

}
