package com.zuoni.riyuecun.bean.gson;

import com.zuoni.riyuecun.bean.model.ConsumptionRecord;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/13
 */

public class ConsumptionRecordPage extends  BaseHttpResponse{

    /**
     * HttpCode : 200
     * Message : 数据成功返回
     * Model1 : [{"ShopName":"周黑鸭","ShopTime":"2017-10-24 10:37:47","CardMoney":50}]
     */

    private List<ConsumptionRecord> Model1;

    public List<ConsumptionRecord> getModel1() {
        return Model1;
    }

    public void setModel1(List<ConsumptionRecord> Model1) {
        this.Model1 = Model1;
    }


}
