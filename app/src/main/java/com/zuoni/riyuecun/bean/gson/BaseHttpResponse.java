package com.zuoni.riyuecun.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2017/9/27
 */

public class BaseHttpResponse  {


    /**
     * HttpCode : 300
     * Message : 验证卡片失败
     */

    private int HttpCode;
    private String Message;

    public int getHttpCode() {
        return HttpCode;
    }

    public void setHttpCode(int HttpCode) {
        this.HttpCode = HttpCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
