package com.zuoni.riyuecun.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.AppUrl;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.gson.GetMessageHtml;
import com.zuoni.riyuecun.http.CallServer;
import com.zuoni.riyuecun.http.HttpRequest;
import com.zuoni.riyuecun.http.HttpResponseListener;
import com.zuoni.riyuecun.ui.activity.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zangyi_shuai_ge on 2017/11/13
 */

public class WebViewActivity extends BaseTitleActivity {
    @BindView(R.id.webView)
    WebView webView;

    private String MessageId;

    @Override
    public int setLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("消息详情");
        MessageId = getIntent().getStringExtra("MessageId");
        GetMessageHtml(MessageId);
    }

    private void GetMessageHtml(String MessageId) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.GetMessageHtml);
        httpRequest.add("MessageId", MessageId);
        CallServer.getInstance().request(httpRequest, new HttpResponseListener() {
            @Override
            public void onSucceed(String response, Gson gson) {
                closeLoading();
                LogUtil.i("网页" + response);
                GetMessageHtml info = gson.fromJson(response, GetMessageHtml.class);
                if (info.getHttpCode() == 200) {
                    if (!info.getModel1().equals("")) {
                        webView.loadDataWithBaseURL("about:blank", info.getModel1(), "text/html", "utf-8", null);
                    } else {
                        showToast("获取失败");
                        myFinish();
                    }
                } else {
                    showToast(info.getMessage());
                    myFinish();
                }
            }

            @Override
            public void onFailed(Exception exception) {
                closeLoading();
                showToast("服务器异常");
            }
        }, getContext());
    }
}
