package com.zuoni.riyuecun.http;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

/**
 * Created by zangyi_shuai_ge on 2017/9/27
 */

public class HttpRequest extends RestRequest<String> {
    public HttpRequest(String url) {
        this(url, RequestMethod.POST);
    }

    public HttpRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public String parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        return StringRequest.parseResponseString(responseHeaders, responseBody);
    }
}
