package com.zuoni.riyuecun.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zuoni.riyuecun.R;

/**
 * Created by zangyi_shuai_ge on 2017/7/26
 * 首页积分仪表盘
 */

public class IntegralDashboard extends View {

    private Paint mPaint;//画笔
    private Bitmap dashboardBg;//仪表盘背景
    private RectF oval;//绘制扇形所在区域
    private RectF dstRect, srcRect;
    private int sweepAngle = 0;
    private Xfermode mXfermode;
    private Xfermode mXfermode2;
    //自定义View尺寸
    private int viewW;
    private int viewH;
    private Context context;
    private ValueAnimator animatorFloat;//浮动动画

    public IntegralDashboard(Context context) {
        this(context, null);
    }

    public IntegralDashboard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IntegralDashboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        dashboardBg = BitmapFactory.decodeResource(getResources(), R.mipmap.dashboard_bg);

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        mXfermode2 = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        LogUtil.i("onSizeChanged");
        viewW = w;
        viewH = h;
        srcRect = new RectF(0, 0, w, h);
        dstRect = new RectF(0, 0, w, h);
        oval = new RectF(-(h-w/2), 0, w +(h-w/2), 2*h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //背景色设为白色，方便比较效果
        //canvas.drawColor(Color.WHITE);
        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        int saveCount = canvas.saveLayer(srcRect, mPaint, Canvas.ALL_SAVE_FLAG);
        //绘制目标图
        canvas.drawBitmap(dashboardBg, null, dstRect, mPaint);
        //设置混合模式
        mPaint.setXfermode(mXfermode);
        //绘制源图
        //canvas.drawBitmap(srcBmp, null, srcRect, mPaint);
        mPaint.setColor(getResources().getColor(R.color.color_white));
        canvas.drawCircle(viewW / 2, viewH, viewW, mPaint);//绘制白色背景
        //canvas.drawArc(dstRect, 0, 60, true, mPaint);
        mPaint.setXfermode(mXfermode2);
        mPaint.setColor(getResources().getColor(R.color.dashboard_red));
        canvas.drawArc(oval, 180, sweepAngle, true, mPaint);
        //清除混合模式
        mPaint.setXfermode(null);
        //还原画布
        canvas.restoreToCount(saveCount);
    }

    /**
     * 开启动画
     */
    public void invalidateView() {
        animatorFloat = ValueAnimator.ofInt(0, 180, 0);
        animatorFloat.setDuration(1500);
        animatorFloat.setRepeatCount(-1);
        animatorFloat.start();
        animatorFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        invalidate();
    }
    public void setValue(int pSweepAngle) {
        if(pSweepAngle==0){
            pSweepAngle=1;
        }
        if(animatorFloat!=null){
            animatorFloat.cancel();
        }
        animatorFloat = ValueAnimator.ofInt(0, pSweepAngle);
        animatorFloat.setDuration(1000);
        animatorFloat.setRepeatCount(0);
        animatorFloat.start();
        animatorFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }
}
