package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.RvRecordsOfConsumptionAdapter;
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

    @Override
    public int setLayoutId() {
        return R.layout.activity_records_of_consumption;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("消费记录");
        List<String > mList=new ArrayList<>();
        mList.add("");
        mList.add("");

        LRecyclerViewAdapter mAdapter=new LRecyclerViewAdapter(new RvRecordsOfConsumptionAdapter(getContext(),mList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }
}
