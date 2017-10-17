package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;

import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2017/10/17
 */

public class ForgrtActivity extends BaseTitleActivity {
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

    @OnClick(R.id.btSure)
    public void onViewClicked() {
        jumpToActivity(CheckPhoneActivity.class);
    }
}
