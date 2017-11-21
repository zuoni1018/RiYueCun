package com.zuoni.riyuecun.ui.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.RvFindStoresAdapter;
import com.zuoni.riyuecun.bean.gson.GetStoreList;
import com.zuoni.riyuecun.bean.model.Store;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 */

public class FindStoreFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    LRecyclerView mRecyclerView;

    private View view;
    private MainActivity mainActivity;
    private List<Store> mList;
    private LRecyclerViewAdapter mAdapter;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("Fragment", "附近门店onResume");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find_store, null);
        unbinder = ButterKnife.bind(this, view);
        LogUtil.i("Fragment", "附近门店onCreateView");
        mList = new ArrayList<>();

        mAdapter = new LRecyclerViewAdapter(new RvFindStoresAdapter(getContext(), mList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                mAdapter.notifyDataSetChanged();
                GetStoreList();
            }
        });
        mRecyclerView.refresh();

        return view;
    }

    /**
     * 生成储值卡二维码
     */
    private void GetStoreList() {
        mainActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetStoreList);//门店列表
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                mainActivity.closeLoading();
                mRecyclerView.refreshComplete(1);
//                progressWheel.setVisibility(View.GONE);
                LogUtil.i("门店列表" + response);
                GetStoreList info = gson.fromJson(response, GetStoreList.class);
                if (info.getHttpCode() == 200) {
                    mList.addAll(info.getModel1());
                    mAdapter.notifyDataSetChanged();
                } else {
                    mainActivity.showToast(info.getMessage());
                }
            }

            @Override
            public void onFailed(Exception exception) {
                mainActivity.closeLoading();
                mainActivity.showToast("服务器异常");
                mRecyclerView.refreshComplete(1);
            }
        }, getContext());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
