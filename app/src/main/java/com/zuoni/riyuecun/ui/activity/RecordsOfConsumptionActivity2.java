package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.RvRecordsOfConsumptionAdapter;
import com.zuoni.riyuecun.bean.gson.ConsumptionRecordPage;
import com.zuoni.riyuecun.bean.model.ConsumptionRecord;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zangyi_shuai_ge on 2017/10/19
 * 用户消费记录
 */

public class RecordsOfConsumptionActivity2 extends BaseTitleActivity {
    @BindView(R.id.mRecyclerView)
    LRecyclerView mRecyclerView;

    private LRecyclerViewAdapter mAdapter;
    private List<ConsumptionRecord> mList;
    private int  nowPageNum;
    @Override
    public int setLayoutId() {
        return R.layout.activity_records_of_consumption;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("消费记录");
        mList = new ArrayList<>();
        mAdapter = new LRecyclerViewAdapter(new RvRecordsOfConsumptionAdapter(getContext(), mList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                nowPageNum=1;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                ConsumptionRecordPage(nowPageNum+"");
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                ConsumptionRecordPage(nowPageNum+"");
            }
        });
        mRecyclerView.refresh();

    }

    private void ConsumptionRecordPage(String PageNo) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.ConsumptionRecordPage);//用户消费记录
        httpRequest.add("PageNo", PageNo);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                mRecyclerView.refreshComplete(1);
                closeLoading();
                LogUtil.i("用户消费记录" + response);
                ConsumptionRecordPage info = gson.fromJson(response, ConsumptionRecordPage.class);
                if (info.getHttpCode() == 200) {
                    mList.addAll(info.getModel1());
                    mAdapter.notifyDataSetChanged();
                    nowPageNum++;
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
