package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.model.Store;
import com.zuoni.riyuecun.ui.activity.base.BMapLocationBaseActivity;
import com.zuoni.riyuecun.ui.fragment.main.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FindStoresActivity extends BMapLocationBaseActivity {

    @BindView(R.id.layoutLeft)
    RelativeLayout layoutLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.StoreName)
    TextView StoreName;
    @BindView(R.id.Adress)
    TextView Adress;

    private BaiduMap baiduMap;

    private Handler handler = new Handler();

    //    Latitude:30.281103mCurrentLon:120.026879  当前位置
   private Store store;

    private String a;
    private String b;

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
        store = (Store) getIntent().getSerializableExtra("store");
        String coord = store.getCoord();
        a = coord.split(",")[0];
        b = coord.split(",")[1];
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                baiduMap = getBaiduMap();
                setLocationMode(MyLocationConfiguration.LocationMode.FOLLOWING);
                setLocationMode(MyLocationConfiguration.LocationMode.NORMAL);
                LogUtil.i("附近店面坐标" + Double.parseDouble(a) + "=======" + Double.parseDouble(b));
                drawMarker(Double.parseDouble(b), Double.parseDouble(a));

                StoreName.setText(store.getStoreName());
                Adress.setText(store.getAdress());

//                drawMarker(30.271103, 120.026879);
//                drawMarker(30.281103, 120.016879);
//                drawMarker(30.291103, 120.036879);

                baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker.getTitle();
//                        showToast(marker.getTitle());
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


    @OnClick(R.id.go)
    public void onViewClicked() {
        String coord = store.getCoord();
        a = coord.split(",")[0];
        b = coord.split(",")[1];
        LatLng pt1 = new LatLng(Double.parseDouble(MainFragment.Latitude), Double.parseDouble(MainFragment.Longitude));
        LatLng pt2 = new LatLng(Double.parseDouble(b), Double.parseDouble(a));
        // 构建 route搜索参数
        RouteParaOption para = new RouteParaOption()
                .startPoint(pt1)
                .endPoint(pt2);
        try {
            //轨迹规划
            BaiduMapRoutePlan.setSupportWebRoute(false);
            BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, this);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("您还未安装百度地图哦！");
        }
    }
}
