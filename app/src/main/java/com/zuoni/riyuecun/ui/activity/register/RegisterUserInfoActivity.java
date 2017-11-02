package com.zuoni.riyuecun.ui.activity.register;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
 * Created by zangyi_shuai_ge on 2017/10/31
 */

public class RegisterUserInfoActivity extends BaseTitleActivity {


    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.et03)
    EditText et03;
    @BindView(R.id.et04)
    EditText et04;
    @BindView(R.id.tv04)
    TextView tv04;
    @BindView(R.id.et05)
    EditText et05;
    @BindView(R.id.et06)
    EditText et06;
    @BindView(R.id.iv07nan)
    ImageView iv07nan;
    @BindView(R.id.iv07nv)
    ImageView iv07nv;
    @BindView(R.id.tv08)
    TextView tv08;


    private boolean UserSex = true;//true 为男

    private CountDownTimer timer;

    private String phone="";
    private String password="";

    @Override
    public int setLayoutId() {
        return R.layout.activity_register_user_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        setTitle("账户信息");
        timer = new CountDownTimer(GlobalVariable.codeTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv04.setText((int) (millisUntilFinished / 1000) + " 秒");
            }

            @Override
            public void onFinish() {
                tv04.setClickable(true);
                tv04.setText("验证码");
            }
        };
    }

    @OnClick({R.id.tv04, R.id.iv07nan, R.id.iv07nv, R.id.tv08})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv04:
                String Phone = et03.getText().toString().trim();
                if (Phone.equals("")) {
                    showToast("请先输入手机号");
                } else {
                    sendEmail(Phone);
                }
                break;
            case R.id.tv08:
                //完成按钮
//                "Phone": "string",
//                    "Code": "string",
//                    "PassWord": "string",
//                    "UserName": "string",
//                    "UserNickName": "string",
//                    "UserSex": true,
//                    "Active": true


                String UserName=et02.getText().toString().trim();
                String UserNickName=et01.getText().toString().trim();
                String Phone2=et03.getText().toString().trim();
                String Code=et04.getText().toString().trim();
                String PassWord1=et05.getText().toString().trim();
                String PassWord2=et06.getText().toString().trim();

                if(UserName.equals("")){
                    showToast("请输入姓名");
                }else {
                    if(UserNickName.equals("")){
                        showToast("请输入用户名");
                    }else {
                        if(Phone2.equals("")){
                            showToast("请输入手机号");
                        }else {
                            if(Code.equals("")){
                                showToast("请输入验证码");
                            }else {
                                if(PassWord1.equals("")){
                                    showToast("请输入密码");
                                }else {
                                    if(PassWord2.equals("")){
                                        showToast("请输入确认密码");
                                    }else {
                                        if(PassWord2.equals(PassWord1)){
                                            phone=Phone2;
                                            password=PassWord1;
                                            mailRegister(Phone2,Code,PassWord1,UserName,UserNickName,UserSex);
                                        }else {
                                            showToast("密码与确认密码不一致");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case R.id.iv07nan:
                UserSex = true;
                iv07nan.setImageResource(R.mipmap.ryc_18);
                iv07nv.setImageResource(R.mipmap.ryc_16);
                break;
            case R.id.iv07nv:
                UserSex = false;
                iv07nv.setImageResource(R.mipmap.ryc_18);
                iv07nan.setImageResource(R.mipmap.ryc_16);
                break;
        }
    }

    private void mailRegister(String phone2, String code, String passWord1, String userName, String userNickName, boolean userSex) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.MailRegister);//注册
        httpRequest.add("Phone", phone2);
        httpRequest.add("Code", code);
        httpRequest.add("PassWord", passWord1);
        httpRequest.add("UserName", userName);
        httpRequest.add("UserNickName",userNickName);
        httpRequest.add("UserSex", userSex);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("注册" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                if(info.getHttpCode()==200){
                    showToast("注册成功");
                    myFinish();
                    //进行登录
//                    login();

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

    private void login() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.Login);//登录
        httpRequest.add("UserPhone", phone);
        httpRequest.add("UserPassword",password);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("登录" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
//                showToast(info.getMessage());
//                if (info.getHttpCode() == 200) {
//                    tv04.setClickable(false);
//                    timer.start();
//                }
            }

            @Override
            public void onFailed(Exception exception) {
                closeLoading();
                showToast("服务器异常");
            }
        }, getContext());

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
                    tv04.setClickable(false);
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
