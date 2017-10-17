package com.zuoni.riyuecun.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.common.gallery.ViewPagerGallery;
import com.zuoni.common.utils.ToastUtils;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.LoginActivity;
import com.zuoni.riyuecun.ui.activity.MainActivity;
import com.zuoni.riyuecun.ui.activity.PaymentActivity;
import com.zuoni.riyuecun.ui.activity.RegisterActivity;

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
    private View view;
    private MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mian, null);
        unbinder = ButterKnife.bind(this, view);

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


        ViewPagerGallery.setImgResources(views);
        ViewPagerGallery.setOnClickListener(new ViewPagerGallery.GalleryOnClickListener() {
            @Override
            public void onClick(int position) {
                ToastUtils.showToast(getContext(),"点了第"+position);
                mIntent = new Intent(getContext(), PaymentActivity.class);
                startActivity(mIntent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    Intent mIntent;

    @OnClick({R.id.btRegister, R.id.btLogin})
    public void mainLoginView(View view) {
        switch (view.getId()) {
            case R.id.btRegister:
                mIntent = new Intent(getContext(), RegisterActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btLogin:
                mIntent = new Intent(getContext(), LoginActivity.class);
                startActivity(mIntent);
                break;

        }
    }
}
