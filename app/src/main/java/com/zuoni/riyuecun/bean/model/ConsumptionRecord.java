package com.zuoni.riyuecun.bean.model;

/**
 * Created by zangyi_shuai_ge on 2017/11/13
 */

public class ConsumptionRecord {
    /**
     * ShopName : 周黑鸭
     * ShopTime : 2017-10-24T10:37:47
     * CardMoney : 50
     */

    private String ShopName;
    private String ShopTime;
    private int CardMoney;

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    private String PhoneNumber;


    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public String getShopTime() {
        return ShopTime;
    }

    public void setShopTime(String ShopTime) {
        this.ShopTime = ShopTime;
    }

    public int getCardMoney() {
        return CardMoney;
    }

    public void setCardMoney(int CardMoney) {
        this.CardMoney = CardMoney;
    }
}
