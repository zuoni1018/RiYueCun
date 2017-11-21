package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.common.dialog.loading.circle.ProgressWheel;
import com.zuoni.common.utils.DensityUtils;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.gson.GetFirstElecCard;
import com.zuoni.riyuecun.bean.gson.QRCode;
import com.zuoni.riyuecun.bean.model.ElectronicCard;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;
import com.zuoni.riyuecun.util.ImageLoaderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zangyi_shuai_ge on 2017/10/17
 */

public class CardActivity extends BaseTitleActivity {
    @BindView(R.id.qrCode)
    ImageView qrCode;
    @BindView(R.id.progressWheel)
    ProgressWheel progressWheel;
    @BindView(R.id.tvCardNum)
    TextView tvCardNum;
    @BindView(R.id.ivCard)
    SelectableRoundedImageView ivCard;
    @BindView(R.id.tvInfo)
    TextView tvInfo;
    @BindView(R.id.layout03)
    LinearLayout layout03;


    private CountDownTimer timer;

    @Override
    public int setLayoutId() {
        return R.layout.activity_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        String cardNum = getIntent().getStringExtra("cardNum");
        tvCardNum.setText("卡号(" + cardNum + ")");
        //获取第一张储值卡
        //设置一个计时器
        timer = new CountDownTimer(60000, 30000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //时间到了重新获取
                progressWheel.setVisibility(View.VISIBLE);
                qrCode.setImageResource(R.mipmap.blank);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        QRCode();
                    }
                }, 0);
            }

            @Override
            public void onFinish() {
                timer.start();//循环执行
            }
        };
        GetFirstElecCard();
    }

    @Override
    protected void onDestroy() {
        if(timer!=null){
            timer.cancel();
        }
        super.onDestroy();
    }

    /**
     * 生成二维码
     */
    private void QRCode() {
        HttpRequest httpRequest = new HttpRequest(AppUrl.QRCode);//会员卡二维码
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                progressWheel.setVisibility(View.GONE);
                LogUtil.i("会员卡二维码" + response);
                QRCode info = gson.fromJson(response, QRCode.class);
                if (info.getHttpCode() == 200) {
                    qrCode.setImageBitmap(com.zuoni.qrcode.zxing.QRCode.createQRCode(info.getModel1(), DensityUtils.dp2px(getContext(), 165)));
                } else {
                    showToast(info.getMessage());
                }
            }

            @Override
            public void onFailed(Exception exception) {
                progressWheel.setVisibility(View.GONE);
                showToast("服务器异常");
            }
        }, getContext());
    }

    /**
     * 获得第一张储值卡
     */
    private void GetFirstElecCard() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetFirstElecCard);//获得第一张储值卡
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                LogUtil.i("获得第一张储值卡" + response);
                closeLoading();
                //卡信息
                GetFirstElecCard info = gson.fromJson(response, GetFirstElecCard.class);
                if (info.getHttpCode() == 200) {
                    //有储值卡
                    ivCard.setVisibility(View.VISIBLE);
                    ElectronicCard electronicCard = info.getModel1();
                    ImageLoaderUtils.setStoredValueCardImage(getContext(), electronicCard.getCardImage(), ivCard); //卡图片
                    tvInfo.setText("用这张储值卡付款 余额" + electronicCard.getCardMoney());
                    timer.start();
                    layout03.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            jumpToActivity(MoreCardActivity.class);
                        }
                    });
                } else if (info.getHttpCode() == 300) {
                    //没有储值卡
                    ivCard.setVisibility(View.GONE);
                    tvInfo.setText("您还未绑定储值卡,请使用其他方式付款");
                    timer.start();
                    layout03.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showToast("您还未绑定储值卡,请使用其他方式付款");
                        }
                    });
                } else {
                    //服务器异常
                    showToast(info.getMessage());
                    myFinish();
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
}
