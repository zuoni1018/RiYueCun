package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.zuoni.common.dialog.loading.circle.ProgressWheel;
import com.zuoni.common.utils.DensityUtils;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.gson.QRCode;
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
    @BindView(R.id.qrCode)
    ImageView qrCode;
    @BindView(R.id.progressWheel)
    ProgressWheel progressWheel;

    private ElectronicCard electronicCard;
    private CountDownTimer timer;
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
        ImageLoaderUtils.setCardImage02(getContext(), electronicCard.getCardImage(), image);
        CardName.setText("卡号(" + electronicCard.getCardName() + ")");
        EffectiveTime.setText("有效期至" + electronicCard.getEffectiveTime());
        CardMoney.setText("￥" + electronicCard.getCardMoney());



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
                }, 500);
            }

            @Override
            public void onFinish() {
                timer.start();//循环执行
            }
        };
        timer.start();
    }

    @OnClick(R.id.finish)
    public void onViewClicked() {
        myFinish();
    }
    @Override
    protected void onDestroy() {
        if(timer!=null){
            timer.cancel();
        }
        super.onDestroy();
    }

    /**
     * 生成储值卡二维码
     */
    private void QRCode() {
        HttpRequest httpRequest = new HttpRequest(AppUrl.QRCode2);//生成储值卡二维码
        httpRequest.add("CardId",electronicCard.getCardId()+"");
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                progressWheel.setVisibility(View.GONE);
                LogUtil.i("生成储值卡二维码" + response);
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
}
