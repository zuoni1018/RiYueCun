package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zuoni.common.callback.SimpleTextWatcher;
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
 * Created by zangyi_shuai_ge on 2017/10/26
 * 添加储值卡
 */

public class AddStoredValueCardActivity extends BaseTitleActivity {


    @BindView(R.id.layoutLeft)
    RelativeLayout layoutLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
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
    private boolean Active = false;

    @Override
    public int setLayoutId() {
        return R.layout.activity_add_stored_value_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("添加储值卡");

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

    @OnClick({R.id.iv01, R.id.iv02, R.id.tv04})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv01:
                //卡号
                et01.setText("");
                iv01.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv02:
                //密码
                et02.setText("");
                iv02.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv04:

                String CardName = et01.getText().toString().trim();
                String CardPassword = et02.getText().toString().trim();

                if (CardName.equals("")) {
                    showToast("请输入卡号");
                } else {
                    if (CardPassword.equals("")) {
                        showToast("请输入密码");
                    } else {
                        BindElecCard(CardName, CardPassword);
                    }
                }
                break;
        }
    }

    private void BindElecCard(String CardName, String CardPassword) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.BindElecCard);//绑定储值卡
        httpRequest.add("CardName", CardName);
        httpRequest.add("PassWord", CardPassword);
//        httpRequest.add("Active", Active);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("绑定储值卡" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                if (info.getHttpCode() == 200) {
                    com.zuoni.riyuecun.AppUtils.mainFragmentNeedRefresh=true;
                    setResult(10087);//绑定成功
                    myFinish();
                    showToast(info.getMessage());
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
