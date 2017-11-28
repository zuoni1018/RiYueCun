package com.zuoni.riyuecun.bean.gson;

import com.zuoni.riyuecun.bean.model.Coupon;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/3
 */

public class GetMyLevelInfo extends  BaseHttpResponse {


    /**
     * Model1 : {"UserLevelName":"铜黄豆级会员","NextLevelName":"银繁星级会员","NeedThing":"小星星","Diamonds":0,"CurrentThingCount":0,"NextThingCount":10}
     * Model2 : [{"ShopName":"周黑鸭","ShopTime":"2017-10-24 10:37:47","CardMoney":50}]
     * Model3 : [{"CouponName":"20元减8元","CouponDescribe":"当购买价格大于20元时可用，可以减少8元。","ExpirationDate":"2018-01-01 10:04:38"},{"CouponName":"20元减8元","CouponDescribe":"当购买价格大于20元时可用，可以减少8元。","ExpirationDate":"2018-01-01 10:05:02"},{"CouponName":"20元减8元","CouponDescribe":"当购买价格大于20元时可用，可以减少8元。","ExpirationDate":"2018-01-01 10:05:08"},{"CouponName":"20元减8元","CouponDescribe":"当购买价格大于20元时可用，可以减少8元。","ExpirationDate":"2018-01-01 10:05:13"},{"CouponName":"20元减8元","CouponDescribe":"当购买价格大于20元时可用，可以减少8元。","ExpirationDate":"2018-01-01 10:05:23"}]
     */

    private Model1Bean Model1;
    private List<Model2Bean> Model2;
    private List<Coupon> Model3;

    public Model1Bean getModel1() {
        return Model1;
    }

    public void setModel1(Model1Bean Model1) {
        this.Model1 = Model1;
    }

    public List<Model2Bean> getModel2() {
        return Model2;
    }

    public void setModel2(List<Model2Bean> Model2) {
        this.Model2 = Model2;
    }

    public List<Coupon> getModel3() {
        return Model3;
    }

    public void setModel3(List<Coupon> Model3) {
        this.Model3 = Model3;
    }

    public static class Model1Bean {
        /**
         * UserLevelName : 铜黄豆级会员
         * NextLevelName : 银繁星级会员
         * NeedThing : 小星星
         * Diamonds : 0
         * CurrentThingCount : 0
         * NextThingCount : 10
         */

        private String UserLevelName;
        private String NextLevelName;
        private String NeedThing;
        private int Diamonds;
        private int CurrentThingCount;
        private int NextThingCount;

        public String getUserLevelName() {
            return UserLevelName;
        }

        public void setUserLevelName(String UserLevelName) {
            this.UserLevelName = UserLevelName;
        }

        public String getNextLevelName() {
            return NextLevelName;
        }

        public void setNextLevelName(String NextLevelName) {
            this.NextLevelName = NextLevelName;
        }

        public String getNeedThing() {
            return NeedThing;
        }

        public void setNeedThing(String NeedThing) {
            this.NeedThing = NeedThing;
        }

        public int getDiamonds() {
            return Diamonds;
        }

        public void setDiamonds(int Diamonds) {
            this.Diamonds = Diamonds;
        }

        public int getCurrentThingCount() {
            return CurrentThingCount;
        }

        public void setCurrentThingCount(int CurrentThingCount) {
            this.CurrentThingCount = CurrentThingCount;
        }

        public int getNextThingCount() {
            return NextThingCount;
        }

        public void setNextThingCount(int NextThingCount) {
            this.NextThingCount = NextThingCount;
        }
    }

    public static class Model2Bean {
        /**
         * ShopName : 周黑鸭
         * ShopTime : 2017-10-24 10:37:47
         * CardMoney : 50
         */

        private String ShopName;
        private String ShopTime;
        private int CardMoney;

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


}
