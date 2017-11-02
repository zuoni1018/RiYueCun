package com.zuoni.riyuecun.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.zuoni.common.utils.DensityUtils;
import com.zuoni.qrcode.zxing.QRCode;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.ui.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zangyi_shuai_ge on 2017/9/28
 */

public class CreateQrCodeActivity extends BaseActivity {
    @BindView(R.id.ivTest)
    ImageView ivTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ivTest.postDelayed(new Runnable() {
            @Override
            public void run() {
                        ivTest.setImageBitmap(QRCode.createQRCodeWithLogo2(CacheUtils.getToken(getContext()),
                DensityUtils.dp2px(getContext(),165), drawableToBitmap(getResources().getDrawable(R.mipmap.zzz))));
            }
        },1000);

    }

    @Override
    public int setLayoutId() {
        return R.layout.create;
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
