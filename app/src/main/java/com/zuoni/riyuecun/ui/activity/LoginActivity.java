package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.gson.Login;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.ui.activity.forget.ForgetActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2017/10/17
 */

public class LoginActivity extends BaseTitleActivity {
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.iv02)
    ImageView iv02;
    @BindView(R.id.tv03)
    TextView tv03;
    @BindView(R.id.bt04)
    Button bt04;

    @Override
    public int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("登录");
        setResult(0);
    }

    private boolean isShow = false;

    @OnClick({R.id.iv02, R.id.tv03, R.id.bt04})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv02:
                //眼睛
                isShow = !isShow;
                if (isShow) {
                    et02.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv02.setImageResource(R.mipmap.ryc_10);
                    et02.setSelection(et02.length());
                } else {
                    et02.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv02.setImageResource(R.mipmap.ryc_9);
                    et02.setSelection(et02.length());
                }
                break;
            case R.id.tv03:
                //忘记密码
                jumpToActivity(ForgetActivity.class);
                break;
            case R.id.bt04:
                //确定
                String UserPassword = et02.getText().toString().trim();
                String UserPhone = et01.getText().toString().trim();

                if (UserPhone.equals("")) {
                    showToast("请输入用户名或手机号");
                } else {
                    if (UserPassword.equals("")) {
                        showToast("请输入密码");
                    } else {
                        login(UserPassword, UserPhone);
                    }
                }


                break;
        }
    }

    private void login(String userPassword, String userPhone) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.Login);//登录
        httpRequest.add("UserPhone", userPhone);
        httpRequest.add("UserPassword", userPassword);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("登录" + response);
                Login info = gson.fromJson(response, Login.class);
                if (info.getHttpCode() == 200) {
                    CacheUtils.setToken(info.getModel1().getUserToken(),getContext());
                    CacheUtils.setLogin(true,getContext());//设置为已经登录
                    setResult(1);
                    myFinish();
                }else {
                    showToast(info.getMessage());
                }
            }

            @Override
            public void onFailed(Exception exception) {
                closeLoading();
                showToast("服务器异常");
            }
        }, getContext());
    }
}
