package com.zuoni.riyuecun.ui.activity.base;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.zuoni.common.dialog.loading.LoadingDialog;
import com.zuoni.common.utils.EmptyUtils;
import com.zuoni.common.utils.KeyBoardUtils;
import com.zuoni.common.utils.ToastUtils;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.ui.activity.MainActivity;


/**
 * Created by zangyi_shuai_ge on 2017/4/21
 * Activity 基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Context mContext;
    private LoadingDialog loadingDialog;
    private TokenErrorBroadcastReceiver tokenErrorBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ActivityCollector.addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        mContext = BaseActivity.this;
        LoadingDialog.Builder builder = new LoadingDialog.Builder(BaseActivity.this);
        builder.setMessage("载入中...");
        loadingDialog = builder.create();
    }
    public abstract int setLayoutId();

    @Override
    protected void onResume() {
        super.onResume();
        //显示的时候去注册
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GlobalVariable.BROADCAST_TOKEN_ERROR);
        tokenErrorBroadcastReceiver = new TokenErrorBroadcastReceiver();
        registerReceiver(tokenErrorBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (tokenErrorBroadcastReceiver != null) {
            //不显示的时候取消监听
            //只需要栈顶的去接收就好了
            unregisterReceiver(tokenErrorBroadcastReceiver);
            tokenErrorBroadcastReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    class TokenErrorBroadcastReceiver extends BroadcastReceiver {
        private AlertDialog alertDialog;

        @Override
        public void onReceive(final Context context, Intent intent) {
            //动态注册的广播 context 是Activity

            if (alertDialog == null || !alertDialog.isShowing()) {
                //接收到广播的时候
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("账号密码失效,请重新登录");
                builder.setCancelable(false);
                builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CacheUtils.setLogin(false,getContext());
                        ActivityCollector.finishAll();
                        Intent mIntent = new Intent(context, MainActivity.class);
                        context.startActivity(mIntent);
                        alertDialog.dismiss();
                    }
                });
                builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCollector.finishAll();
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }

    /**
     * Loading
     */
    public void showLoading() {
        loadingDialog.show();
    }

    public void closeLoading() {
        loadingDialog.dismiss();
    }

    /**
     * 判断不为空
     */
    public boolean isNotEmpty(String input) {
        return EmptyUtils.isNotEmpty(input.trim());
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * Toast  Snackbar
     */
    public void showToast(String message) {
        ToastUtils.showToast(BaseActivity.this.getApplicationContext(), message);
    }

    public void showSnackbar(View mView, String message) {
        Snackbar.make(mView, message, Snackbar.LENGTH_SHORT)
//                .setAction("", click listener)
                .show();
    }

    /**
     * 普通的跳转界面
     */
    public void jumpToActivity(Class<?> cls) {
        Intent mIntent = new Intent(BaseActivity.this, cls);
        myStartActivity(mIntent);
    }

    /**
     * 添加了动画效果的跳转和销毁
     */
    public void myStartActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    public void myFinish() {
        finish();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    /**
     * 软键盘操作
     */
    public void openKeyboard(EditText mEditText) {
        KeyBoardUtils.openKeyboard(mEditText, BaseActivity.this);
    }

    public void closeKeyboard(EditText mEditText) {
        KeyBoardUtils.closeKeyboard(mEditText, BaseActivity.this);
    }

    /**
     * 缓存相关
     */
    public void getToken() {
    }

    public void getUserId() {
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        myFinish();

    }
}
