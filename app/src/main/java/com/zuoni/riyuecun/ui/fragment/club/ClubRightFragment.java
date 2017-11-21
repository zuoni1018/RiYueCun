package com.zuoni.riyuecun.ui.fragment.club;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.AppUtils;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.RvClubCardAdapter;
import com.zuoni.riyuecun.bean.gson.BaseHttpResponse;
import com.zuoni.riyuecun.bean.gson.GetMyCardList;
import com.zuoni.riyuecun.callback.ItemOnClickListener;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.MyClubActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/19
 */

public class ClubRightFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.layoutNoData)
    RelativeLayout layoutNoData;
    private View view;
    private RvClubCardAdapter adapter;

    private List<GetMyCardList.ListDataBean> mList;
    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_club_right, null);
        unbinder = ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mList = new ArrayList<>();
        adapter = new RvClubCardAdapter(getContext(), mList);
        mRecyclerView.setAdapter(adapter);

        adapter.setItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onClick(final GetMyCardList.ListDataBean card, int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("是否将这张卡设置为活跃卡？");
                builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setActiveCard(card.getMemberShipCardId()+"");
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("否", null);
                dialog = builder.create();
                dialog.show();

            }
        });


        refresh();


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private MyClubActivity myClubActivity;

    public void setMyClubActivity(MyClubActivity myClubActivity) {
        this.myClubActivity = myClubActivity;
    }


    private void jumpToActivity(Class<?> cls) {
        Intent mIntent = new Intent(getContext(), cls);
        startActivity(mIntent);
    }
    public void setActiveCard(String CardID) {
        myClubActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.SetActiveCard);//设置活跃卡
        httpRequest.add("CardID", CardID);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                myClubActivity.closeLoading();
                LogUtil.i("设置活跃卡" + response);
                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                if (info.getHttpCode() == 200) {
                    myClubActivity.showToast("设置活跃卡成功");
                    myClubActivity.refreshAll();
                    //设置成功后首页数据需要刷新
                    AppUtils.mainFragmentNeedRefresh=true;

                }else {
                    myClubActivity.showToast(info.getMessage());
                }
            }

            @Override
            public void onFailed(Exception exception) {
                myClubActivity.closeLoading();
                myClubActivity.showToast("服务器异常");
            }
        }, getContext());

    }
    public void refresh() {
        myClubActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetMyCardList);//会员卡列表
        httpRequest.add("Active", "false");
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                myClubActivity.closeLoading();
                LogUtil.i("会员卡列表" + response);
                GetMyCardList info = gson.fromJson(response, GetMyCardList.class);
                if (info.getHttpCode() == 200) {
                    mList.clear();
                    mList.addAll(info.getListData());
                    adapter.notifyDataSetChanged();
                }
                if (mList.size() == 0) {
                    layoutNoData.setVisibility(View.VISIBLE);
                } else {
                    layoutNoData.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailed(Exception exception) {
                myClubActivity.closeLoading();
                myClubActivity.showToast("服务器异常");
            }
        }, getContext());

    }
}
