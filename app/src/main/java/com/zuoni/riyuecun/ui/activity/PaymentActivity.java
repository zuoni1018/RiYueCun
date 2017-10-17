package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zangyi_shuai_ge on 2017/10/17
 * 扫一扫支付
 */

public class PaymentActivity extends BaseTitleActivity {


    @BindView(R.id.layout222)
    LinearLayout layout222;
    @BindView(R.id.image)
    ImageView image;

    @Override
    public int setLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, layout222);
        ButterKnife.bind(this);
        Glide
                .with(getContext())
                .load(GlobalVariable.TEST_IMAGE_URL)
                .into(image);
    }
}
