package com.zuoni.riyuecun.bean.gson;

import com.zuoni.riyuecun.bean.model.ElectronicCard;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/8
 */

public class GetElecCardList extends BaseHttpResponse {

    private List<ElectronicCard> Model1;

    public List<ElectronicCard> getModel1() {
        return Model1;
    }

    public void setModel1(List<ElectronicCard> Model1) {
        this.Model1 = Model1;
    }


}
