package com.zuoni.riyuecun.bean.model;

/**
 * Created by zangyi_shuai_ge on 2017/11/27
 */

public class Coupon {
    /**
     * CouponName : 20元减8元
     * CouponDescribe : 当购买价格大于20元时可用，可以减少8元。
     * ExpirationDate : 2018-01-01 10:04:38
     */

    private String CouponName;
    private String CouponDescribe;
    private String ExpirationDate;

    public String getCouponRealtionId() {
        return CouponRealtionId;
    }

    public void setCouponRealtionId(String couponRealtionId) {
        CouponRealtionId = couponRealtionId;
    }

    private String CouponRealtionId;

    public String getCouponName() {
        return CouponName;
    }

    public void setCouponName(String CouponName) {
        this.CouponName = CouponName;
    }

    public String getCouponDescribe() {
        return CouponDescribe;
    }

    public void setCouponDescribe(String CouponDescribe) {
        this.CouponDescribe = CouponDescribe;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(String ExpirationDate) {
        this.ExpirationDate = ExpirationDate;
    }
}
