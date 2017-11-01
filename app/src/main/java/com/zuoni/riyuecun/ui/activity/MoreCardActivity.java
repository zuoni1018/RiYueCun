package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.RvMoreCardAdapter;
import com.zuoni.riyuecun.adapter.RvMoreCardTabAdapter;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zangyi_shuai_ge on 2017/10/20
 */

public class MoreCardActivity extends BaseTitleActivity {


    @BindView(R.id.mRecyclerViewTop)
    RecyclerView mRecyclerViewTop;
    @BindView(R.id.mRecyclerView)
    LRecyclerView mRecyclerView;

    @Override
    public int setLayoutId() {
        return R.layout.activity_more_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("更多储值卡");

//        List<String > mList2=new ArrayList<>();
//        mList2.add("");
//        mList2.add("");
//



        List<String> mList = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            mList.add("余额"+i);
        }
        mRecyclerViewTop.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewTop.setAdapter(new RvMoreCardTabAdapter(getContext(),mList));
        LRecyclerViewAdapter mAdapter = new LRecyclerViewAdapter(new RvMoreCardAdapter(getContext(), mList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);


    }
}
