package com.zuoni.riyuecun.ui.fragment.main;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuoni.common.utils.ToastUtils;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.ui.activity.settings.ChangePasswordActivity;
import com.zuoni.riyuecun.ui.activity.MainActivity;
import com.zuoni.riyuecun.ui.activity.TestActivity;
import com.zuoni.riyuecun.ui.activity.settings.UserInfoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 */

public class SettingsFragment extends Fragment {
    //    @BindView(R.id.btRegister)
//    Button btRegister;
//    @BindView(R.id.btLogin)
//    Button btLogin;
    Unbinder unbinder;
    private View view;
    private MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    AlertDialog dialog;
    @OnClick({R.id.settings_item_1, R.id.settings_item_2, R.id.settings_item_3, R.id.settings_item_4, R.id.settings_item_5,R.id.btGo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.settings_item_1:
                jumpToActivity(UserInfoActivity.class);
                break;
            case R.id.settings_item_2:
                jumpToActivity(ChangePasswordActivity.class);
                break;
            case R.id.settings_item_3:
                jumpToActivity("常见问题");
                break;
            case R.id.settings_item_4:
                jumpToActivity("使用条款");
                break;
            case R.id.settings_item_5:

                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("是否退出登录?");
                builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CacheUtils.setLogin(false,getContext());
                        CacheUtils.setToken("",getContext());
                        ToastUtils.showToast(getContext(),"退出登录成功");
                        mainActivity.turnFirstPage();
                    }
                });

                builder.setNegativeButton("取消",null);
                dialog=builder.create();
                dialog.show();
                break;
            case R.id.btGo:
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://fir.im/2p1r");
                intent.setData(content_url);
                startActivity(intent);
                break;
        }
    }

    private void jumpToActivity(Class<?> cls){
        Intent mIntent=new Intent(getContext(),cls);
        startActivity(mIntent);
    }
    private void jumpToActivity(String title){
        Intent mIntent=new Intent(getContext(), TestActivity.class);
        mIntent.putExtra("title",title);
        startActivity(mIntent);
    }
}
