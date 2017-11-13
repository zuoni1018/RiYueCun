package com.zuoni.riyuecun.bean.gson;

import com.zuoni.riyuecun.bean.model.ConsumptionRecord;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/8
 */

public class ConsumptionRecords extends  BaseHttpResponse {

    private List<ConsumptionRecord> Model1;

    public List<ConsumptionRecord> getModel1() {
        return Model1;
    }

    public void setModel1(List<ConsumptionRecord> Model1) {
        this.Model1 = Model1;
    }


}
