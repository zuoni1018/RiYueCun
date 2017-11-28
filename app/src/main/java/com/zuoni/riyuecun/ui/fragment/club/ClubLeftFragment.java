package com.zuoni.riyuecun.ui.fragment.club;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.gson.GetMyCardList;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.CardActivity;
import com.zuoni.riyuecun.ui.activity.MyClubActivity;
import com.zuoni.riyuecun.util.ImageLoaderUtils;

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
    @BindView(R.id.tv01)
    TextView tv01;
    private View view;
    private MyClubActivity myClubActivity;
    GetMyCardList.ListDataBean cardInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_club_left, null);
        unbinder = ButterKnife.bind(this, view);
        setCardSize();
        refresh();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setMyClubActivity(MyClubActivity myClubActivity) {
        this.myClubActivity = myClubActivity;
    }


    public void refresh() {

        HttpRequest httpRequest = new HttpRequest(AppUrl.GetMyCardList);//会员卡列表
        httpRequest.add("Active", "true");
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                LogUtil.i("会员卡列表1" + response);
                GetMyCardList info = gson.fromJson(response, GetMyCardList.class);
                if (info.getHttpCode() == 200) {
                    cardInfo = info.getListData().get(0);
                    ImageLoaderUtils.setCardImage01(getContext(), cardInfo.getImgUrl(), ivCard);
                }
            }

            @Override
            public void onFailed(Exception exception) {
                myClubActivity.showToast("服务器异常");
            }
        }, getContext());

    }

    /**
     * 动态设置卡片尺寸
     */
    private void setCardSize() {
//        LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) ivCard.getLayoutParams();
//        l.width = (int) (ScreenUtils.getScreenWidth(getContext()) * (250.00 / 430));
//        l.height = (int) (l.width * GlobalVariable.CARD_PROPORTION_1);
//        ivCard.setLayoutParams(l);
    }


    private void jumpToActivity(Class<?> cls) {
        Intent mIntent = new Intent(getContext(), cls);
        startActivity(mIntent);
    }

    @OnClick({R.id.ivCard, R.id.tv01})
    public void onViewClicked(View view) {
        if (cardInfo == null) {
            myClubActivity.showToast("会员卡获取失败");
            return;
        }

        //卡号
        Intent mIntent = new Intent(getContext(), CardActivity.class);
        mIntent.putExtra("cardNum", cardInfo.getMemberShipCardId() + "");
        startActivity(mIntent);
        switch (view.getId()) {
            case R.id.ivCard:
                break;
            case R.id.tv01:
                break;
        }
    }
}
