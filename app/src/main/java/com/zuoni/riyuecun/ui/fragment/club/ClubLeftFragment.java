package com.zuoni.riyuecun.ui.fragment.club;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.common.utils.ScreenUtils;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.CardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/19
 */

public class ClubLeftFragment extends Fragment {
    @BindView(R.id.ivCard)
    SelectableRoundedImageView ivCard;
    Unbinder unbinder;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_club_left, null);
        unbinder = ButterKnife.bind(this, view);

        setCardSize();


        Glide
                .with(getContext())
                .load(GlobalVariable.TEST_IMAGE_URL)
                .asBitmap()
                .into(ivCard);


        return view;
    }

    /**
     * 动态设置卡片尺寸
     */
    private void setCardSize() {
        LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) ivCard.getLayoutParams();
        l.width = (int) (ScreenUtils.getScreenWidth(getContext()) * (250.00 / 430));
        l.height = (int) (l.width * GlobalVariable.CARD_PROPORTION_1);
        ivCard.setLayoutParams(l);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv01)
    public void onViewClicked() {
        jumpToActivity(CardActivity.class);
    }

    private void jumpToActivity(Class<?> cls) {
        Intent mIntent = new Intent(getContext(), cls);
        startActivity(mIntent);
    }
}
