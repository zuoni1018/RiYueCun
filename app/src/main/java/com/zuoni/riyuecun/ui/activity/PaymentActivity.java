package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.model.ElectronicCard;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;
import com.zuoni.riyuecun.util.ImageLoaderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2017/10/17
 * 扫一扫支付
 */

public class PaymentActivity extends BaseTitleActivity {


    @BindView(R.id.layout222)
    LinearLayout layout222;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.CardName)
    TextView CardName;
    @BindView(R.id.EffectiveTime)
    TextView EffectiveTime;
    @BindView(R.id.CardMoney)
    TextView CardMoney;
    @BindView(R.id.finish)
    TextView finish;

    private ElectronicCard electronicCard;

    @Override
    public int setLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, 20, layout222);
        ButterKnife.bind(this);
        electronicCard = (ElectronicCard) getIntent().getSerializableExtra("electronicCard");
        if (electronicCard == null) {
            showToast("该卡无效");
            myFinish();
            return;
        }
        ImageLoaderUtils.setStoredValueCardImage(getContext(), electronicCard.getCardImage(), image);
        CardName.setText("卡号(" + electronicCard.getCardName() + ")");
        EffectiveTime.setText("有效期至" + electronicCard.getEffectiveTime());
        CardMoney.setText("￥" + electronicCard.getCardMoney());
    }

    @OnClick(R.id.finish)
    public void onViewClicked() {
        myFinish();
    }
}
