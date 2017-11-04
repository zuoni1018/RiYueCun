package com.zuoni.riyuecun.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.common.callback.TabOnClickListener;
import com.zuoni.common.gallery.ViewPagerGallery;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.common.utils.ToastUtils;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.cache.CacheUtils;
import com.zuoni.riyuecun.ui.activity.LoginActivity;
import com.zuoni.riyuecun.ui.activity.MainActivity;
import com.zuoni.riyuecun.ui.activity.MyClubActivity;
import com.zuoni.riyuecun.ui.activity.PaymentActivity;
import com.zuoni.riyuecun.ui.activity.register.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 */

public class MainFragment extends Fragment {
    @BindView(R.id.btRegister)
    Button btRegister;
    @BindView(R.id.btLogin)
    Button btLogin;
    Unbinder unbinder;
    @BindView(R.id.ViewPagerGallery)
    com.zuoni.common.gallery.ViewPagerGallery ViewPagerGallery;
    @BindView(R.id.zzzzz)
    SelectableRoundedImageView zzzzz;
    @BindView(R.id.ivCard2)
    SelectableRoundedImageView ivCard2;
    @BindView(R.id.layoutlogin)
    LinearLayout layoutlogin;
    @BindView(R.id.layoutmain)
    LinearLayout layoutmain;

    @BindView(R.id.layoutMain2)
    LinearLayout layoutMain2;
    @BindView(R.id.layoutMain03)
    LinearLayout layoutMain03;

    private View view;
    private MainActivity mainActivity;
    private boolean isLogin = false;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i("首页Fragment创建了");
        view = inflater.inflate(R.layout.fragment_mian, null);
        unbinder = ButterKnife.bind(this, view);

        loginShow();


        List<View> views = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SelectableRoundedImageView selectableRoundedImageView = new SelectableRoundedImageView(getContext());
            selectableRoundedImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            selectableRoundedImageView.setCornerRadiiDP(4, 4, 4, 4);

            Glide.with(getContext())
                    .load(GlobalVariable.TEST_IMAGE_URL)
                    .asBitmap()
                    .into(selectableRoundedImageView);
            views.add(selectableRoundedImageView);
        }
        Glide.with(getContext())
                .load(GlobalVariable.TEST_IMAGE_URL)
                .asBitmap()
                .into(zzzzz);

        Glide.with(getContext())
                .load(GlobalVariable.TEST_IMAGE_URL)
                .asBitmap()
                .into(ivCard2);

        ivCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(getContext(), MyClubActivity.class);
                startActivity(mIntent);
            }
        });

        ViewPagerGallery.setImgResources(views);
        ViewPagerGallery.setOnClickListener(new ViewPagerGallery.GalleryOnClickListener() {
            @Override
            public void onClick(int position) {
                ToastUtils.showToast(getContext(), "点了第" + position);
                mIntent = new Intent(getContext(), PaymentActivity.class);
                startActivity(mIntent);
            }
        });

        mainActivity.setTabOnClickListener01(new TabOnClickListener() {
            @Override
            public void onClick() {
                LogUtil.i("点击首页刷新状态");
                loginShow();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginShow();

    }

    private void loginShow() {
        isLogin = CacheUtils.isLogin(getContext());
        if (isLogin) {
            layoutlogin.setVisibility(View.GONE);
            layoutmain.setVisibility(View.VISIBLE);
            layoutMain2.setVisibility(View.VISIBLE);
            layoutMain03.setVisibility(View.VISIBLE);
        } else {
            layoutlogin.setVisibility(View.VISIBLE);
            layoutmain.setVisibility(View.GONE);
            layoutMain2.setVisibility(View.GONE);
            layoutMain03.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private Intent mIntent;


    private static int LOGIN_TAG=10086;

    @OnClick({R.id.btRegister, R.id.btLogin})
    public void mainLoginView(View view) {
        switch (view.getId()) {
            case R.id.btRegister:
                mIntent = new Intent(getContext(), RegisterActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btLogin:
                mIntent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(mIntent,LOGIN_TAG);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==LOGIN_TAG){
            if(resultCode==1){
                //登录成功
            }
        }
    }
}
