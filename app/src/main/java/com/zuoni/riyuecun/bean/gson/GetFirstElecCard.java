package com.zuoni.riyuecun.bean.gson;

import com.zuoni.riyuecun.bean.model.ElectronicCard;

/**
 * Created by zangyi_shuai_ge on 2017/11/8
 */

public class GetFirstElecCard extends BaseHttpResponse {

    /**
     * Model1 : {"ElectronicCardRelationId":11,"CardImage":"http://192.168.2.13:8056/upload/Desert.jpg","CardMoney":250,"CardTypeName":"黄金卡","CardName":"0000000010","EffectiveTime":null}
     */

    private ElectronicCard Model1;

    public ElectronicCard getModel1() {
        return Model1;
    }

    public void setModel1(ElectronicCard Model1) {
        this.Model1 = Model1;
    }
}
