package com.zuoni.riyuecun;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by zangyi_shuai_ge on 2017/9/27
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());

    }
}
