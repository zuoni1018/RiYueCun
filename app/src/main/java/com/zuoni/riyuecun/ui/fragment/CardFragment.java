package com.zuoni.riyuecun.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.MainActivity;
import com.zuoni.riyuecun.ui.activity.ManagerCardActivity;
import com.zuoni.riyuecun.ui.activity.MoreCardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 */

public class CardFragment extends Fragment {
    //    @BindView(R.id.btRegister)
//    Button btRegister;
//    @BindView(R.id.btLogin)
//    Button btLogin;
    Unbinder unbinder;
    @BindView(R.id.ivCard)
    SelectableRoundedImageView ivCard;
    private View view;
    private MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card, null);
        unbinder = ButterKnife.bind(this, view);

        Glide
                .with(getContext())
                .load(GlobalVariable.TEST_IMAGE_URL)
                .asBitmap()
                .into(ivCard);




        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    Intent mIntent;
    @OnClick({R.id.tvMore,R.id.layoutManager})
    public void mainLoginView(View view) {
        switch (view.getId()) {
            case R.id.tvMore:
                mIntent=new Intent(getContext(), MoreCardActivity.class);
                startActivity(mIntent);
                break;
            case R.id.layoutManager:
                mIntent=new Intent(getContext(), ManagerCardActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
