package com.zuoni.riyuecun.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.AppUtils;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.gson.BaseHttpResponse;
import com.zuoni.riyuecun.bean.model.ElectronicCard;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;
import com.zuoni.riyuecun.util.ImageLoaderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2017/10/20
 */

public class ManagerCardActivity extends BaseTitleActivity {
    @BindView(R.id.ivCard)
    SelectableRoundedImageView ivCard;
    @BindView(R.id.CardName)
    TextView CardName;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.layout2)
    LinearLayout layout2;

    private ElectronicCard electronicCard;

    @Override
    public int setLayoutId() {
        return R.layout.activity_manage_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("管理储值卡");

        electronicCard = (ElectronicCard) getIntent().getSerializableExtra("electronicCard");//获取储值卡

        if (electronicCard == null) {
            myFinish();
            return;
        }

        ImageLoaderUtils.setCardImage02(getContext(),electronicCard.getCardImage(),ivCard);
        tvMoney.setText(" ￥" + electronicCard.getCardMoney());
        CardName.setText("储值卡(" + electronicCard.getCardName() + ")" + "有效期至" + electronicCard.getEffectiveTime());
    }

    private Intent mIntent;

    @OnClick({R.id.layout1, R.id.layout2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout1:
                //卡消费记录
                mIntent = new Intent(getContext(), RecordsOfConsumptionActivity.class);
                mIntent.putExtra("CardId", electronicCard.getCardId()+"");
                startActivity(mIntent);
                break;
            case R.id.layout2:
                //解除绑定
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("是否解除绑定该储值卡?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RelieveBindElecCard(electronicCard.getElectronicCardRelationId() + "");
                    }
                });

                builder.setNegativeButton("否", null);
                builder.create().show();

                break;
        }
    }

    @OnClick(R.id.ivCard)
    public void onViewClicked() {
        mIntent = new Intent(getContext(), PaymentActivity.class);
        mIntent.putExtra("electronicCard", electronicCard);
        startActivity(mIntent);
    }

    private void RelieveBindElecCard(String RelationId) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.RelieveBindElecCard);//解除绑定
        httpRequest.add("RelationId", RelationId);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("解除绑定" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                if (info.getHttpCode() == 200) {
                    AppUtils.mainFragmentNeedRefresh=true;
                    setResult(10087);
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
