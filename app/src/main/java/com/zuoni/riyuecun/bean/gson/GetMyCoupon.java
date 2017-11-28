package com.zuoni.riyuecun.bean.gson;

import com.zuoni.riyuecun.bean.model.Coupon;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/27
 */

public class GetMyCoupon extends BaseHttpResponse {
    private List<Coupon> Model1;

    public List<Coupon> getModel1() {
        return Model1;
    }

    public void setModel1(List<Coupon> Model1) {
        this.Model1 = Model1;
    }
}
