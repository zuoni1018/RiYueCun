package com.zuoni.riyuecun.ui.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.RvMainNewsAdapter;
import com.zuoni.riyuecun.bean.gson.GetMessageList;
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

public class NewsFragment extends Fragment {
    //    @BindView(R.id.btRegister)
//    Button btRegister;
//    @BindView(R.id.btLogin)
//    Button btLogin;
    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    LRecyclerView mRecyclerView;
    private View view;
    private MainActivity mainActivity;
    private List<GetMessageList.Model1Bean> mList;

    private LRecyclerViewAdapter mAdapter;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, null);
        unbinder = ButterKnife.bind(this, view);
        mList = new ArrayList<>();
        mAdapter = new LRecyclerViewAdapter(new RvMainNewsAdapter(getContext(), mList));
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        GetMessageList();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void GetMessageList() {
        mainActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetMessageList);//用户消费记录
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                mRecyclerView.refreshComplete(1);
                mainActivity.closeLoading();
                LogUtil.i("消息列表" + response);
                GetMessageList info = gson.fromJson(response, GetMessageList.class);
                if (info.getHttpCode() == 200) {
                    mList.addAll(info.getModel1());
                    mAdapter.notifyDataSetChanged();
                } else {
                    mainActivity.showToast(info.getMessage());
                }
            }

            @Override
            public void onFailed(Exception exception) {
                mRecyclerView.refreshComplete(1);
                mainActivity.closeLoading();
                mainActivity.showToast("服务器异常");
            }
        }, getContext());
    }

//    @OnClick(R.id.tvTest)
//    public void onViewClicked() {
//        Intent mIntent = new Intent(getContext(), PaymentActivity.class);
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
