package com.zuoni.riyuecun.ui.activity.forget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

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
 * Created by zangyi_shuai_ge on 2017/10/17
 */

public class ForgetActivity extends BaseTitleActivity {
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.tv01)
    TextView tv01;

    @Override
    public int setLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("忘记密码");
    }

    @OnClick(R.id.tv01)
    public void onViewClicked() {
        String Phone = et01.getText().toString().trim();
        if (Phone.equals("")) {
            showToast("请先输入手机号");
        } else {
            sendEmail(Phone);
        }
    }

    private void sendEmail(final String Phone) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.SendEmail);//获取验证码
        httpRequest.add("Phone", Phone);
        httpRequest.add("SendEmailType", "ResetPassword");
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("获取验证码" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                showToast(info.getMessage());
                if (info.getHttpCode() == 200) {
                    Intent mIntent = new Intent(getContext(), ForgetGetCodeActivity.class);
                    mIntent.putExtra("phone", Phone);
                    startActivity(mIntent);
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

//    @OnClick(R.id.btSure)
//    public void onViewClicked() {
//        jumpToActivity(ForgetGetCodeActivity.class);
//    }
}
