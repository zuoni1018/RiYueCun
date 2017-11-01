package com.zuoni.riyuecun.ui.activity.settings;

import android.os.Bundle;

import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

/**
 * Created by zangyi_shuai_ge on 2017/10/20
 */

public class UserInfoActivity extends BaseTitleActivity {
    @Override
    public int setLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("账户信息");
        getUserInfo();
    }

    private void getUserInfo() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetUserInfo);//获取用户信息
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("获取用户信息" + response);
//                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
//                if(info.getHttpCode()==200){
//                    showToast("注册成功");
//                    myFinish();
//                    //进行登录
////                    login();
//
//                }else {
//                    showToast(info.getMessage());
//                }

            }

            @Override
            public void onFailed(Exception exception) {
                closeLoading();
                showToast("服务器异常");
            }
        }, getContext());



    }
}
