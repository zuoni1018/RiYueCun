package com.zuoni.riyuecun.bean.model;

import java.io.Serializable;

/**
 * Created by zangyi_shuai_ge on 2017/11/8
 * 储值卡
 */

public class ElectronicCard implements Serializable {
    /**
     * ElectronicCardRelationId : 11
     * CardImage : http://192.168.2.13:8056/upload/Desert.jpg
     * CardMoney : 250
     * CardTypeName : 黄金卡
     * CardName : 0000000010
     * EffectiveTime : null
     */

    private int ElectronicCardRelationId;
    private String CardImage;
    private int CardMoney;
    private String CardTypeName;
    private String CardName;
    private Object EffectiveTime;

    private String CardId;

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public int getElectronicCardRelationId() {
        return ElectronicCardRelationId;
    }

    public void setElectronicCardRelationId(int ElectronicCardRelationId) {
        this.ElectronicCardRelationId = ElectronicCardRelationId;
    }

    public String getCardImage() {
        return CardImage;
    }

    public void setCardImage(String CardImage) {
        this.CardImage = CardImage;
    }

    public int getCardMoney() {
        return CardMoney;
    }

    public void setCardMoney(int CardMoney) {
        this.CardMoney = CardMoney;
    }

    public String getCardTypeName() {
        return CardTypeName;
    }

    public void setCardTypeName(String CardTypeName) {
        this.CardTypeName = CardTypeName;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String CardName) {
        this.CardName = CardName;
    }

    public Object getEffectiveTime() {
        return EffectiveTime;
    }

    public void setEffectiveTime(Object EffectiveTime) {
        this.EffectiveTime = EffectiveTime;
    }
}
