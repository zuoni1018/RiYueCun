package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;

import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

/**
 * Created by zangyi_shuai_ge on 2017/10/17
 */

public class RegisterActivity extends BaseTitleActivity {
    @Override
    public int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("注册");
    }
}
