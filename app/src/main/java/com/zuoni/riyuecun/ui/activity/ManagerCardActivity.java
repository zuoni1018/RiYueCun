package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2017/10/20
 */

public class ManagerCardActivity extends BaseTitleActivity {
    @BindView(R.id.ivCard)
    SelectableRoundedImageView ivCard;

    @Override
    public int setLayoutId() {
        return R.layout.activity_manage_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("管理储值卡");
        Glide
                .with(this)
                .load(GlobalVariable.TEST_IMAGE_URL)
                .asBitmap()
                .into(ivCard);
    }

    @OnClick({R.id.layout1, R.id.layout2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout1:
                jumpToActivity(RecordsOfConsumptionActivity.class);
                break;
            case R.id.layout2:
                break;
        }
    }

    @OnClick(R.id.ivCard)
    public void onViewClicked() {
        jumpToActivity(PaymentActivity.class);
    }
}
