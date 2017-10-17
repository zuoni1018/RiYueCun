package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;

import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

/**
 * Created by zangyi_shuai_ge on 2017/10/17
 */

public class CheckPhoneActivity extends BaseTitleActivity {
    @Override
    public int setLayoutId() {
        return R.layout.activity_check_phone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("验证手机号");
    }
}
