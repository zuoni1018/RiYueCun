package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.base.BMapLocationBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FindStoresActivity extends BMapLocationBaseActivity {

    @BindView(R.id.layoutLeft)
    RelativeLayout layoutLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private BaiduMap baiduMap;

    private Handler handler=new Handler();

    //    Latitude:30.281103mCurrentLon:120.026879  当前位置
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvTitle.setText("查找门店");
        layoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFinish();
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                showLoading();
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                baiduMap = getBaiduMap();
                setLocationMode(MyLocationConfiguration.LocationMode.FOLLOWING);
                setLocationMode(MyLocationConfiguration.LocationMode.NORMAL);

                drawMarker(30.281103, 120.026879);
                drawMarker(30.271103, 120.026879);
                drawMarker(30.281103, 120.016879);
                drawMarker(30.291103, 120.036879);

                baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker.getTitle();
                        showToast(marker.getTitle());
                        LogUtil.i("" + marker.getTitle());
                        return false;
                    }
                });
                closeLoading();
            }
        }, 2000);

    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_find_stores;
    }


}
