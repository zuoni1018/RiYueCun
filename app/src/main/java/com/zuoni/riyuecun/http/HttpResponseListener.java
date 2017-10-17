package com.zuoni.riyuecun.http;

import com.google.gson.Gson;

/**
 * Created by zangyi_shuai_ge on 2017/9/27
 */

public interface HttpResponseListener {
    void onSucceed(String response, Gson gson);

    void onFailed(Exception exception);
}
