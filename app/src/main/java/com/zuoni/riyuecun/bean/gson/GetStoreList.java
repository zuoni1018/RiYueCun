package com.zuoni.riyuecun.bean.gson;

import com.zuoni.riyuecun.bean.model.Store;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/18
 */

public class GetStoreList extends  BaseHttpResponse {

    private List<Store> Model1;

    public List<Store> getModel1() {
        return Model1;
    }

    public void setModel1(List<Store> Model1) {
        this.Model1 = Model1;
    }
}
