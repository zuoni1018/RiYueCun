package com.zuoni.riyuecun.bean.model;

import java.io.Serializable;

/**
 * Created by zangyi_shuai_ge on 2017/11/20
 * 日月村门店
 */

public class Store  implements Serializable {
    /**
     * Coord : 120.033901,30.24977
     * Adress : 杭州市西湖区西溪印象城
     * StoreName : 古早日月村主店
     * Distance : 2500
     */

    private String Coord;
    private String Adress;
    private String StoreName;
    private String Distance;

    public String getCoord() {
        return Coord;
    }

    public void setCoord(String Coord) {
        this.Coord = Coord;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String Distance) {
        this.Distance = Distance;
    }
}
