package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.RvMoreCardAdapter;
import com.zuoni.riyuecun.bean.gson.GetElecCardList;
import com.zuoni.riyuecun.bean.model.ElectronicCard;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zangyi_shuai_ge on 2017/10/20
 */

public class MoreCardActivity extends BaseTitleActivity {


    @BindView(R.id.mRecyclerView)
    LRecyclerView mRecyclerView;


    private List<ElectronicCard> mList;
    private LRecyclerViewAdapter mAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_more_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("更多储值卡");

        mList = new ArrayList<>();
        mAdapter = new LRecyclerViewAdapter(new RvMoreCardAdapter(getContext(), mList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetElecCardList();
            }
        });
        mRecyclerView.refresh();
    }

    private void GetElecCardList() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetElecCardList);//获得储值卡列表
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                mRecyclerView.refreshComplete(1);
                LogUtil.i("获得储值卡列表" + response);
                GetElecCardList info = gson.fromJson(response, GetElecCardList.class);
                if (info.getHttpCode() == 200) {
                    mList.clear();
                    mList.addAll(info.getModel1());
                    mAdapter.notifyDataSetChanged();
                } else {
                    showToast(info.getMessage());
                }
            }

            @Override
            public void onFailed(Exception exception) {
                mRecyclerView.refreshComplete(1);
                closeLoading();
                showToast("服务器异常");
            }
        }, getContext());
    }
}
