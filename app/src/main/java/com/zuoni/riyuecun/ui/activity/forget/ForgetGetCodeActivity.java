package com.zuoni.riyuecun.ui.activity.forget;

import android.content.Intent;
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
 * Created by zangyi_shuai_ge on 2017/10/17
 */

public class ForgetGetCodeActivity extends BaseTitleActivity {
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.bt02)
    Button bt02;


    private String phone="";

    @Override
    public int setLayoutId() {
        return R.layout.activity_forget_get_code;
    }

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("验证手机号");


        phone=getIntent().getStringExtra("phone");
        timer = new CountDownTimer(GlobalVariable.codeTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv01.setText((int) (millisUntilFinished / 1000) + " 秒");
            }

            @Override
            public void onFinish() {
                tv01.setClickable(true);
                tv01.setText("验证码");
            }
        };
        timer.start();
        tv01.setClickable(false);
    }

    @OnClick({R.id.tv01, R.id.bt02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv01:
                sendEmail(phone);
                break;
            case R.id.bt02:

                String Code=et01.getText().toString().trim();
                if(Code.equals("")){
                    showToast("请输入验证码");
                }else {
                    Intent mIntent=new Intent(getContext(),ForgetChangePasswordActivity.class);
                    mIntent.putExtra("phone",phone);
                    mIntent.putExtra("code",Code);
                    startActivity(mIntent);
                    myFinish();
                }
                break;
        }
    }
    private void sendEmail(String Phone) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.SendEmail);//获取验证码
        httpRequest.add("Phone", Phone);
        httpRequest.add("SendEmailType", "Registration");
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("获取验证码" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                showToast(info.getMessage());
                if (info.getHttpCode() == 200) {
                    tv01.setClickable(false);
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
}
