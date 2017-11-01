package com.zuoni.riyuecun.ui.activity.register;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zuoni.common.callback.SimpleTextWatcher;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.gson.BaseHttpResponse;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;
import com.zuoni.riyuecun.AppUrl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2017/10/17
 */

public class RegisterActivity extends BaseTitleActivity {
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.iv01)
    ImageView iv01;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.iv02)
    ImageView iv02;
    @BindView(R.id.tv04)
    TextView tv04;
    @BindView(R.id.tv05)
    TextView tv05;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("注册");

        /////
        et01.setText("黄金");
        et02.setText("123456");
        iv01.setVisibility(View.VISIBLE);
        iv02.setVisibility(View.VISIBLE);
        ////


        et01.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    iv01.setVisibility(View.VISIBLE);
                } else {
                    iv01.setVisibility(View.INVISIBLE);
                }
            }
        });
        et02.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    iv02.setVisibility(View.VISIBLE);
                } else {
                    iv02.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_register;
    }

    @OnClick({R.id.iv01, R.id.iv02, R.id.tv04, R.id.tv05})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv01:
                et01.setText("");
                iv01.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv02:
                et02.setText("");
                iv02.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv04:
                break;
            case R.id.tv05:
                String CardName = et01.getText().toString().trim();
                String CardPassword = et02.getText().toString().trim();

                if (CardName.equals("")) {
                    showToast("请输入卡号");
                } else {
                    if (CardPassword.equals("")) {
                        showToast("请输入密码");
                    } else {
                        verificCard(CardName, CardPassword);
                    }
                }
                break;
        }
    }

    private void verificCard(String cardName, String cardPassword) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.VerificCard);//验证卡
        httpRequest.add("CardName", cardName);
        httpRequest.add("CardPassword", cardPassword);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("验证卡" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                if (info.getHttpCode() == 200) {
                    jumpToActivity(RegisterUserInfoActivity.class);
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
