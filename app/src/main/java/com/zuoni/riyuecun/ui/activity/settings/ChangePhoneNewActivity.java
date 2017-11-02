package com.zuoni.riyuecun.ui.activity.settings;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.gson.BaseHttpResponse;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2017/11/2
 */

public class ChangePhoneNewActivity extends BaseTitleActivity {
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.tv02)
    TextView tv02;
    @BindView(R.id.bt03)
    Button bt03;

    private CountDownTimer timer;
    private String phone="";

    @Override
    public int setLayoutId() {
        return R.layout.activity_change_phone_new;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("更换手机号码");
        phone=getIntent().getStringExtra("phone");
        timer = new CountDownTimer(GlobalVariable.codeTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv02.setText((int) (millisUntilFinished / 1000) + " 秒");
            }

            @Override
            public void onFinish() {
                tv02.setClickable(true);
                tv02.setText("验证码");
            }
        };
    }

    @OnClick({R.id.tv02, R.id.bt03})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv02:
                String Phone = et01.getText().toString().trim();
                if (Phone.equals("")) {
                    showToast("请先输入手机号");
                } else {
                    sendEmail(Phone);
                }
                break;
            case R.id.bt03:
                String Phone1=et01.getText().toString().trim();
                String Code=et02.getText().toString().trim();

                if(Phone1.equals("")){
                    showToast("请先输入手机号");
                }else {
                    if(Code.equals("")){
                        showToast("请先输入验证码");
                    }else {
                        resetPhone(Phone1,Code);
                    }
                }

                break;
        }
    }
    private void sendEmail(String Phone) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.SendEmail);//获取验证码
        httpRequest.add("Phone", Phone);
        httpRequest.add("SendEmailType", "ChangePhone");
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("获取验证码" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                showToast(info.getMessage());
                if (info.getHttpCode() == 200) {
                    tv02.setClickable(false);
                    timer.start();
                }
            }

            @Override
            public void onFailed(Exception exception) {
                closeLoading();
                showToast("服务器异常");
            }
        }, getContext());

    }

    private void resetPhone(String Phone,String Code) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.ResetPhone);//重置手机号
        httpRequest.add("Phone", Phone);
        httpRequest.add("Code", Code);
        httpRequest.add("NewPhone", Phone);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("重置手机号" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                showToast(info.getMessage());
                if (info.getHttpCode() == 200) {
                    myFinish();
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
