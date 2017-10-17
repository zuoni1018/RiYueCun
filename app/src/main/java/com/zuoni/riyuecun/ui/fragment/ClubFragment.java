package com.zuoni.riyuecun.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 */

public class ClubFragment extends Fragment {
    //    @BindView(R.id.btRegister)
//    Button btRegister;
//    @BindView(R.id.btLogin)
//    Button btLogin;
    Unbinder unbinder;
//    @BindView(R.id.ivCard)
//    SelectableRoundedImageView ivCard;
    private View view;
    private MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_club, null);
        unbinder = ButterKnife.bind(this, view);

//        Glide
//                .with(getContext())
//                .load(GlobalVariable.TEST_IMAGE_URL)
//                .asBitmap()
//                .into(ivCard);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @OnClick(R.id.tvTest)
//    public void onViewClicked() {
//        Intent mIntent = new Intent(getContext(), CardActivity.class);
//        startActivity(mIntent);
//    }

//    Intent mIntent;
//    @OnClick({R.id.btRegister, R.id.btLogin})
//    public void mainLoginView(View view) {
//        switch (view.getId()) {
//            case R.id.btRegister:
//                mIntent=new Intent(getContext(), RegisterActivity.class);
//                startActivity(mIntent);
//                break;
//            case R.id.btLogin:
//                mIntent=new Intent(getContext(), LoginActivity.class);
//                startActivity(mIntent);
//                break;
//
//        }
//    }
}
