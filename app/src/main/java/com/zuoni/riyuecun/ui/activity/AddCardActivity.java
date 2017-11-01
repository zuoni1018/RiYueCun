package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;

import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2017/10/26
 */

public class AddCardActivity extends BaseTitleActivity {
    @Override
    public int setLayoutId() {
        return R.layout.activity_add_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("添加会员卡");
    }

    @OnClick(R.id.tvOK)
    public void onViewClicked() {
        finish();
    }
}
