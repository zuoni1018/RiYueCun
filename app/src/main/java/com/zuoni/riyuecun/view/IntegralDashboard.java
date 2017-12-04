package com.zuoni.riyuecun.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.R;

/**
 * Created by zangyi_shuai_ge on 2017/7/26
 * 首页积分仪表盘
 */

public class IntegralDashboard extends View {

    private ValueAnimator animatorFloat;//浮动动画
    //画笔的渐变色
    private int[] circleColors;
    //自定义View尺寸
    private int viewW;
    private int viewH;
    //圆环的宽度
    private int ringWidth;
    //画圆弧的区域
    private RectF oval;
    //画笔
    private  Paint paintBg;//绘制外圆环背景
    private  Paint paintCircle;//绘制刻度画笔

    private Context context;
    private int sweepAngle;




    public IntegralDashboard(Context context) {
        this(context, null);
    }

    public IntegralDashboard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IntegralDashboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始化圆环背景画笔
        paintBg=new Paint();
        paintBg.setStyle(Paint.Style.STROKE);//描边

        paintBg.setAntiAlias(true);//设置抗锯齿
        paintBg.setColor(getResources().getColor(R.color.dashboard_bg));

        paintCircle=new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);//描边
        paintCircle.setAntiAlias(true);//设置抗锯齿
        paintCircle.setColor(getResources().getColor(R.color.refresh_color_02));
        paintCircle.setStrokeCap(Paint.Cap.ROUND);

        circleColors = new int[]{
                getResources().getColor(R.color.circle_color_01),
                getResources().getColor(R.color.circle_color_02),
                getResources().getColor(R.color.circle_color_03),
                getResources().getColor(R.color.circle_color_04),
                getResources().getColor(R.color.circle_color_05),
                getResources().getColor(R.color.circle_color_04),
                getResources().getColor(R.color.circle_color_03),
                getResources().getColor(R.color.circle_color_02),
                getResources().getColor(R.color.circle_color_01),
        };
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //初始化尺寸
        viewW = w;
        viewH = h;

        ringWidth= (int) ((20/250.000)*w);//圆环宽度
        oval = new RectF((ringWidth / 2+1), (ringWidth / 2+1), w-(ringWidth/2+1), w-(ringWidth/2+1));

        //初始化画笔
        paintCircle .setShader(new SweepGradient(viewW / 2, viewW / 2,circleColors , null));
        paintBg.setStrokeWidth(ringWidth);
        paintCircle.setStrokeWidth(ringWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        canvas.drawArc(oval, 0, 360, false, paintBg);
        //绘制刻度
        canvas.drawArc(oval, 180+90, sweepAngle, false, paintCircle);
    }

    public void setValue(int pSweepAngle) {
        if(pSweepAngle==0){
            pSweepAngle=1;
        }
        LogUtil.i("pSweepAngle"+pSweepAngle);
        if(animatorFloat!=null){
            animatorFloat.cancel();
        }
        animatorFloat = ValueAnimator.ofInt(0,360,pSweepAngle);
        animatorFloat.setInterpolator(new AccelerateDecelerateInterpolator());//设置差值器 先加速后减速
        animatorFloat.setDuration(2000);
        animatorFloat.setRepeatCount(0);
        animatorFloat.start();
        animatorFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = -(int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }
}
