package com.zuoni.riyuecun.ui.activity.forget;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
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
 * Created by zangyi_shuai_ge on 2017/11/1
 */

public class ForgetChangePasswordActivity extends BaseTitleActivity {
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.iv01)
    ImageView iv01;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.iv02)
    ImageView iv02;
    @BindView(R.id.bt03)
    Button bt03;

    @Override
    public int setLayoutId() {
        return R.layout.activity_forget_change_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("重置密码");
    }


    private  boolean isShow1=false;
    private  boolean isShow2=false;
    @OnClick({R.id.iv01, R.id.iv02, R.id.bt03})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv01:
                //眼睛
                isShow1 = !isShow1;
                if (isShow1) {
                    et01.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv01.setImageResource(R.mipmap.ryc_10);
                    et01.setSelection(et01.length());
                } else {
                    et01.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv01.setImageResource(R.mipmap.ryc_9);
                    et01.setSelection(et01.length());
                }
                break;
            case R.id.iv02:
                isShow2 = !isShow2;
                if (isShow2) {
                    et02.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv02.setImageResource(R.mipmap.ryc_10);
                    et02.setSelection(et02.length());
                } else {
                    et02.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv02.setImageResource(R.mipmap.ryc_9);
                    et02.setSelection(et02.length());
                }
                break;
            case R.id.bt03:
                String password1=et01.getText().toString().trim();
                String password2=et02.getText().toString().trim();
                if(password1.equals("")){
                    showToast("请输入新密码");
                }else {
                    if(password2.equals("")){
                        showToast("请输入确认密码");
                    }else {
                        if(password2.equals(password1)){
                            forgotPassword(password1,getIntent().getStringExtra("code"),getIntent().getStringExtra("phone"));

                        }else {
                            showToast("新密码与确认密码不一致");
                        }
                    }
                }
                break;
        }
    }

    private void forgotPassword(String password, String code, String phone) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.ForgotPassword);//修改密码
        httpRequest.add("Phone", phone);
        httpRequest.add("PassWord", password);
        httpRequest.add("Code", code);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("修改密码" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                if (info.getHttpCode() == 200) {
                    myFinish();
                } else {
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
