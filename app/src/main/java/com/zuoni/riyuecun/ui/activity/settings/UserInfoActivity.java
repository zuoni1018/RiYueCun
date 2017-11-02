package com.zuoni.riyuecun.ui.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.gson.BaseHttpResponse;
import com.zuoni.riyuecun.bean.gson.GetUserInfo;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2017/10/20
 */

public class UserInfoActivity extends BaseTitleActivity {
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.et03)
    EditText et03;
    @BindView(R.id.tv03)
    TextView tv03;
    @BindView(R.id.iv04nan)
    ImageView iv04nan;
    @BindView(R.id.iv04nv)
    ImageView iv04nv;

    @Override
    public int setLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("账户信息");
        getUserInfo();
    }
    private void modifyUserInfo(String UserNickName) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.ModifyUserInfo);//修改用户信息
        httpRequest.add("UserNickName",UserNickName);
        httpRequest.add("UserSex",UserSex);

        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("修改用户信息" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);

                if(info.getHttpCode()==200){
                    showToast("修改成功");
                }else {
                    showToast(info.getMessage());
                }


            }

            @Override
            public void onFailed(Exception exception) {
                closeLoading();
                showToast("服务器异常");
                myFinish();
            }
        }, getContext());


    }
    private void getUserInfo() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetUserInfo);//获取用户信息
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("获取用户信息" + response);
                GetUserInfo info = gson.fromJson(response, GetUserInfo.class);
                if (info.getHttpCode() == 200) {
                    GetUserInfo.Model1Bean mode = info.getModel1();
                    et01.setText(mode.getUserNickName());
                    et02.setText(mode.getUserName());
                    et02.setEnabled(false);
                    et03.setText(mode.getPhone());
                    et03.setEnabled(false);
                    if(mode.isUserSex()){
                        UserSex = true;
                        iv04nan.setImageResource(R.mipmap.ryc_18);
                        iv04nv.setImageResource(R.mipmap.ryc_16);
                    }else {
                        UserSex = false;
                        iv04nv.setImageResource(R.mipmap.ryc_18);
                        iv04nan.setImageResource(R.mipmap.ryc_16);
                    }

                } else {
                    showToast(info.getMessage());
                }

            }

            @Override
            public void onFailed(Exception exception) {
                closeLoading();
                showToast("服务器异常");
                myFinish();
            }
        }, getContext());


    }

    private boolean UserSex = true;//true 为男

    @OnClick({R.id.tv03, R.id.iv04nan, R.id.iv04nv,R.id.tv05})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv03:
                Intent mIntent=new Intent(getContext(),ChangePhoneOldActivity.class);
                mIntent.putExtra("phone",et03.getText().toString().trim());
                myStartActivity(mIntent);
                break;
            case R.id.iv04nan:
                UserSex = true;
                iv04nan.setImageResource(R.mipmap.ryc_18);
                iv04nv.setImageResource(R.mipmap.ryc_16);
                break;
            case R.id.iv04nv:
                UserSex = false;
                iv04nv.setImageResource(R.mipmap.ryc_18);
                iv04nan.setImageResource(R.mipmap.ryc_16);
                break;
            case R.id.tv05:
                if(et01.getText().toString().trim().equals("")){
                    showToast("请输入姓名");
                }else {
                    modifyUserInfo(et01.getText().toString().trim());
                }

                break;
        }
    }
}
