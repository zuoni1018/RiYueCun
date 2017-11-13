package com.zuoni.riyuecun.bean.gson;

import com.zuoni.riyuecun.bean.model.ElectronicCard;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/13
 */

public class GetFirstPage extends  BaseHttpResponse {


    /**
     * Model1 : {"MemberShipCardId":5,"ImgUrl":"http://192.168.2.13:8056/upload/MemberManage/TXKL30636461660863330000.jpg"}
     * Model2 : {"UserLevelName":"铜黄豆级会员","NextLevelName":"银繁星级会员","NeedThing":"小星星","Diamonds":0,"CurrentThingCount":0,"NextThingCount":10}
     * Model3 : [{"ElectronicCardRelationId":22,"CardId":26,"CardImage":"http://192.168.2.13:8056/upload/ElectronicCardType/PJWN7G636461647759800000.jpg","CardMoney":500,"CardTypeName":"金星卡","CardName":"0000000014","EffectiveTime":"2017-11-24 11:56:07"},{"ElectronicCardRelationId":21,"CardId":27,"CardImage":"http://192.168.2.13:8056/upload/ElectronicCardType/S2S0MO636461648190550000.jpg","CardMoney":600,"CardTypeName":"木星卡","CardName":"0000000015","EffectiveTime":"2017-11-24 11:56:07"},{"ElectronicCardRelationId":16,"CardId":25,"CardImage":"http://192.168.2.13:8056/upload/ElectronicCardType/T5G0SX636461648847890000.jpg","CardMoney":400,"CardTypeName":"土星卡","CardName":"0000000013","EffectiveTime":"2017-11-24 11:56:07"},{"ElectronicCardRelationId":15,"CardId":24,"CardImage":"http://192.168.2.13:8056/upload/ElectronicCardType/IPQO48636461648625710000.jpg","CardMoney":350,"CardTypeName":"火星卡","CardName":"0000000012","EffectiveTime":"2017-11-24 11:56:07"},{"ElectronicCardRelationId":14,"CardId":23,"CardImage":"http://192.168.2.13:8056/upload/ElectronicCardType/7H9AJ6636461648417830000.jpg","CardMoney":300,"CardTypeName":"水星卡","CardName":"0000000011","EffectiveTime":"2017-11-24 11:56:07"}]
     * Model4 : [{"CouponName":"20元减8元","CouponDescribe":"当购买价格大于20元时可用，可以减少8元。","ExpirationDate":"2018-01-01 10:04:38"},{"CouponName":"20元减8元","CouponDescribe":"当购买价格大于20元时可用，可以减少8元。","ExpirationDate":"2018-01-01 10:05:02"},{"CouponName":"20元减8元","CouponDescribe":"当购买价格大于20元时可用，可以减少8元。","ExpirationDate":"2018-01-01 10:05:08"},{"CouponName":"20元减8元","CouponDescribe":"当购买价格大于20元时可用，可以减少8元。","ExpirationDate":"2018-01-01 10:05:13"},{"CouponName":"20元减8元","CouponDescribe":"当购买价格大于20元时可用，可以减少8元。","ExpirationDate":"2018-01-01 10:05:23"},{"CouponName":"20元减8元","CouponDescribe":"当购买价格大于20元时可用，可以减少8元。","ExpirationDate":"2018-01-01 10:06:04"},{"CouponName":"10元减4元","CouponDescribe":"当购买价格大于10元时可用，可以减少4元。","ExpirationDate":"2017-12-02 15:22:07"}]
     * Model5 : null
     * Model6 : {"MessageID":1,"MessageName":"饮食文化","MessageImage":"http://192.168.2.13:8056/upload/MemberManage/3UVEAO636461660232470000.jpg","MessageDescribe":"中国饮食文化涉及到食源的开发与利用、食具的运用与创新、食品的生产与消费、餐饮的服务与接待、餐饮业与食品业的经营与管理，以及饮食与国泰民安、饮食与文学艺术、饮食与人生境界的关系等，深厚广博。"}
     */

    private Model1Bean Model1;
    private Model2Bean Model2;
    private Object Model5;
    private Model6Bean Model6;
    private List<ElectronicCard> Model3;
    private List<Model4Bean> Model4;

    public Model1Bean getModel1() {
        return Model1;
    }

    public void setModel1(Model1Bean Model1) {
        this.Model1 = Model1;
    }

    public Model2Bean getModel2() {
        return Model2;
    }

    public void setModel2(Model2Bean Model2) {
        this.Model2 = Model2;
    }

    public Object getModel5() {
        return Model5;
    }

    public void setModel5(Object Model5) {
        this.Model5 = Model5;
    }

    public Model6Bean getModel6() {
        return Model6;
    }

    public void setModel6(Model6Bean Model6) {
        this.Model6 = Model6;
    }

    public List<ElectronicCard> getModel3() {
        return Model3;
    }

    public void setModel3(List<ElectronicCard> Model3) {
        this.Model3 = Model3;
    }

    public List<Model4Bean> getModel4() {
        return Model4;
    }

    public void setModel4(List<Model4Bean> Model4) {
        this.Model4 = Model4;
    }

    public static class Model1Bean {
        /**
         * MemberShipCardId : 5
         * ImgUrl : http://192.168.2.13:8056/upload/MemberManage/TXKL30636461660863330000.jpg
         */

        private int MemberShipCardId;
        private String ImgUrl;

        public int getMemberShipCardId() {
            return MemberShipCardId;
        }

        public void setMemberShipCardId(int MemberShipCardId) {
            this.MemberShipCardId = MemberShipCardId;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String ImgUrl) {
            this.ImgUrl = ImgUrl;
        }
    }

    public static class Model2Bean {
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

    public static class Model6Bean {
        /**
         * MessageID : 1
         * MessageName : 饮食文化
         * MessageImage : http://192.168.2.13:8056/upload/MemberManage/3UVEAO636461660232470000.jpg
         * MessageDescribe : 中国饮食文化涉及到食源的开发与利用、食具的运用与创新、食品的生产与消费、餐饮的服务与接待、餐饮业与食品业的经营与管理，以及饮食与国泰民安、饮食与文学艺术、饮食与人生境界的关系等，深厚广博。
         */

        private int MessageID;
        private String MessageName;
        private String MessageImage;
        private String MessageDescribe;

        public int getMessageID() {
            return MessageID;
        }

        public void setMessageID(int MessageID) {
            this.MessageID = MessageID;
        }

        public String getMessageName() {
            return MessageName;
        }

        public void setMessageName(String MessageName) {
            this.MessageName = MessageName;
        }

        public String getMessageImage() {
            return MessageImage;
        }

        public void setMessageImage(String MessageImage) {
            this.MessageImage = MessageImage;
        }

        public String getMessageDescribe() {
            return MessageDescribe;
        }

        public void setMessageDescribe(String MessageDescribe) {
            this.MessageDescribe = MessageDescribe;
        }
    }

    public static class Model3Bean {
        /**
         * ElectronicCardRelationId : 22
         * CardId : 26
         * CardImage : http://192.168.2.13:8056/upload/ElectronicCardType/PJWN7G636461647759800000.jpg
         * CardMoney : 500
         * CardTypeName : 金星卡
         * CardName : 0000000014
         * EffectiveTime : 2017-11-24 11:56:07
         */

        private int ElectronicCardRelationId;
        private int CardId;
        private String CardImage;
        private int CardMoney;
        private String CardTypeName;
        private String CardName;
        private String EffectiveTime;

        public int getElectronicCardRelationId() {
            return ElectronicCardRelationId;
        }

        public void setElectronicCardRelationId(int ElectronicCardRelationId) {
            this.ElectronicCardRelationId = ElectronicCardRelationId;
        }

        public int getCardId() {
            return CardId;
        }

        public void setCardId(int CardId) {
            this.CardId = CardId;
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

        public String getEffectiveTime() {
            return EffectiveTime;
        }

        public void setEffectiveTime(String EffectiveTime) {
            this.EffectiveTime = EffectiveTime;
        }
    }

    public static class Model4Bean {
        /**
         * CouponName : 20元减8元
         * CouponDescribe : 当购买价格大于20元时可用，可以减少8元。
         * ExpirationDate : 2018-01-01 10:04:38
         */

        private String CouponName;
        private String CouponDescribe;
        private String ExpirationDate;

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
}
