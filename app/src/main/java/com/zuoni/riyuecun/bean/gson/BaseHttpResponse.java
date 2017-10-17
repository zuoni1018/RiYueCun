package com.zuoni.riyuecun.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2017/9/27
 */

public class BaseHttpResponse  {
    private int httpCode;
    private String httpMessage;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getHttpMessage() {
        return httpMessage;
    }

    public void setHttpMessage(String httpMessage) {
        this.httpMessage = httpMessage;
    }
}
