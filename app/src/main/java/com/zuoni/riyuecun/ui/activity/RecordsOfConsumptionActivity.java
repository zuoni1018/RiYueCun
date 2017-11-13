package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.RvRecordsOfConsumptionAdapter;
import com.zuoni.riyuecun.bean.gson.ConsumptionRecords;
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
 */

public class RecordsOfConsumptionActivity extends BaseTitleActivity {
    @BindView(R.id.mRecyclerView)
    LRecyclerView mRecyclerView;

    private LRecyclerViewAdapter mAdapter;
    private String CardId;

    private List<ConsumptionRecord> mList;



    @Override
    public int setLayoutId() {
        return R.layout.activity_records_of_consumption;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("消费记录");

        CardId = getIntent().getStringExtra("CardId");

        if (CardId==null||CardId.equals("")) {
            showToast("获取失败");
            myFinish();
            return;
        }
        ConsumptionRecords(CardId);

        mList = new ArrayList<>();


        mAdapter = new LRecyclerViewAdapter(new RvRecordsOfConsumptionAdapter(getContext(), mList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void ConsumptionRecords(String CardId) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.ConsumptionRecords);//储值卡消费
        httpRequest.add("CardId", CardId);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                mRecyclerView.refreshComplete(1);
                closeLoading();
                LogUtil.i("储值卡消费" + response);
                ConsumptionRecords info = gson.fromJson(response, ConsumptionRecords.class);
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
