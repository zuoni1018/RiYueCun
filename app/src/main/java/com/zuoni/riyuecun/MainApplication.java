package com.zuoni.riyuecun;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.yanzhenjie.nohttp.NoHttp;
import com.zuoni.common.utils.LogUtil;

/**
 * Created by zangyi_shuai_ge on 2017/9/27
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(getApplicationContext());

        SDKInitializer.initialize(getApplicationContext());

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.i("deviceToken"+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.i("deviceToken   onFailure");
            }
        });

    }
}
