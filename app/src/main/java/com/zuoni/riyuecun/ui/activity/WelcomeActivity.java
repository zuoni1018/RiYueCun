package com.zuoni.riyuecun.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.base.BaseActivity;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/1
 */

public class WelcomeActivity extends BaseActivity {
    private static final int REQUEST_CODE_PERMISSION = 100;
    @Override
    public int setLayoutId() {
        return R.layout.activity_welcom;
    }

    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucent(this, 0);


         timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                jumpToActivity(MainActivity.class);
                finish();
            }
        };
        getPermission();
//        timer.start();
    }

    private void getPermission() {

        // 申请权限。
        AndPermission.with(getContext())
                .requestCode(REQUEST_CODE_PERMISSION)
                .permission(Permission.LOCATION)
                .callback(permissionListener)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(rationaleListener)
                .start();

    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：
            // AndPermission.rationaleDialog(Context, Rationale).show();

            // 自定义对话框。
            AlertDialog.newBuilder(getContext())
                    .setTitle("提示")
                    .setMessage("我们需要的一些必要权限被禁止，请授权给我们。")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("就不", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };
    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            LogUtil.i("获取权限成功");
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    timer.start();
//                    Toast.makeText(MainActivity.this,, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    showToast("授权失败");
                    finish();
                    break;
                }
            }

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(getContext(), deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(WelcomeActivity.this, REQUEST_CODE_SETTING).show();

                // 第二种：用自定义的提示语。
//             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                     .setTitle("权限申请失败")
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                     .setPositiveButton("好，去设置")
//                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingHandle = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingHandle.execute();
//            你的dialog点击了取消调用：
//            settingHandle.cancel();
            }
        }
    };


    /**
     * <p>权限全部申请成功才会回调这个方法，否则回调失败的方法。</p>
     * 日历权限申请成功；使用@PermissionYes(RequestCode)注解。
     *
     * @param grantedPermissions AndPermission回调过来的申请成功的权限。
     */
    @PermissionYes(REQUEST_CODE_PERMISSION_SINGLE)
    private void getSingleYes(@NonNull List<String> grantedPermissions) {
        LogUtil.i("回调getSingleYes");
//        Toast.makeText(this, R.string.successfully, Toast.LENGTH_SHORT).show();
    }

    /**
     * <p>只要有一个权限申请失败就会回调这个方法，并且不会回调成功的方法。</p>
     * 日历权限申请失败，使用@PermissionNo(RequestCode)注解。
     *
     * @param deniedPermissions AndPermission回调过来的申请失败的权限。
     */
    @PermissionNo(REQUEST_CODE_PERMISSION_SINGLE)
    private void getSingleNo(@NonNull List<String> deniedPermissions) {
        LogUtil.i("回调getSingleNo");
//        Toast.makeText(this, R.string.failure, Toast.LENGTH_SHORT).show();
//
//        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
//        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
//            // 第一种：用默认的提示语。
//            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING).show();
//
//            // 第二种：用自定义的提示语。
////             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
////                     .setTitle("权限申请失败")
////                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
////                     .setPositiveButton("好，去设置")
////                     .show();
//
////            第三种：自定义dialog样式。
////            SettingService settingService = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
////            你的dialog点击了确定调用：
////            settingService.execute();
////            你的dialog点击了取消调用：
////            settingService.cancel();
//        }
    }

    //----------------------------------联系人、短信权限----------------------------------//

    private static final int REQUEST_CODE_PERMISSION_SINGLE = 100;
    private static final int REQUEST_CODE_PERMISSION_MULTI = 101;

    private static final int REQUEST_CODE_SETTING = 300;
    @PermissionYes(REQUEST_CODE_PERMISSION_MULTI)
    private void getMultiYes(@NonNull List<String> grantedPermissions) {
        LogUtil.i("回调getMultiYes");
//        Toast.makeText(this, R.string.successfully, Toast.LENGTH_SHORT).show();
    }

    @PermissionNo(REQUEST_CODE_PERMISSION_MULTI)
    private void getMultiNo(@NonNull List<String> deniedPermissions) {
        LogUtil.i("回调getMultiNo");
//        Toast.makeText(this, R.string.failure, Toast.LENGTH_SHORT).show();
//
//        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
//        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
//            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                    .setTitle(R.string.title_dialog)
//                    .setMessage(R.string.message_permission_failed)
//                    .setPositiveButton(R.string.ok)
//                    .setNegativeButton(R.string.no, null)
//                    .show();
//
//            // 更多自定dialog，请看上面。
//        }
    }

}
