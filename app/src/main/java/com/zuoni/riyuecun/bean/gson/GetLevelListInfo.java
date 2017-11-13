package com.zuoni.riyuecun.bean.gson;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/13
 */

public class GetLevelListInfo extends  BaseHttpResponse {

    /**
     * Model1 : [{"MembershipLevelId":1,"LevelName":"铜黄豆级会员","Options":"<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><\/head><body style=\"color:white;background:black; width:100%;height:100%;margin:0px;\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><ol><li>铜黄豆级会员(Welcome Level)新会员（购买会员卡价值88元） 9折<\/li><li>&nbsp;三张亲友邀请券（1.猪排蛋烧饼买一赠一2.全家福饭团买一赠一3.红烧牛肉面买一增一）；每次邀请亲友限用一张。<\/li><li>一张深夜食堂邀请券 夜宵邀请券可享7.8折夜宵优惠（使用时间为门店深夜营业期间，夜间19:00至23:00。）<\/li><li>一张饮品升级邀请券。（可点任意饮品升级成高价饮品等）<\/li><li>早餐豆浆邀请券一张<\/li><\/ol><\/body>"},{"MembershipLevelId":2,"LevelName":"银繁星级会员","Options":"<div><br><\/div><ol><li>银繁星级会员(Silver Level)  标志\u201d\u203b\u201d为长期有效可循环  9.0折<\/li><li>一张生日邀请券:在您的生日当月，获赠\u201c生日我最大\u201d免单券一张+定制蛋糕一份 \u203b<\/li><li>饮品享邀请（买二赠一）：&nbsp;<span style=\"font-size: 0.85em;\">&nbsp;<\/span><span style=\"font-size: 0.85em;\">单张收银条中每买两杯任意饮料就可以获得一张邀请券。\u203b &nbsp;&nbsp;<\/span><\/li><li>一张深夜食堂邀请券：深夜食堂邀请券可享7.8折夜宵优惠。 &nbsp;<\/li><li>一张饮品升级邀请券。（可点任意饮品升级成高价饮品等）<\/li><\/ol>"},{"MembershipLevelId":3,"LevelName":"金浩月级会员","Options":"<div><ol><li>金皓月级会员(Gold Level) 标志\u201d\u203b\u201d为长期有效可循环 8.8折<\/li><li>一张生日邀请券:在您的生日当月，获赠\u201c生日我最大\u201d免单券一张+定制蛋糕一份。\u203b<\/li><li>您的生日当月，获赠\u201c生日我最大\u201d免单券一张+定制蛋糕一份。<\/li><li>消费之王：消费次数累计满6次，赠送免单券一张；\u203b<\/li><li>周年庆邀请礼包：为庆祝您的帐户周年您将可获得周年庆邀请礼包一组；<\/li><li>雨天半价券X3张（最高优惠25元）<\/li><li>深夜达人：深夜食堂永久7.8折优惠。\u203b &nbsp;<\/li><\/ol><\/div>"},{"MembershipLevelId":4,"LevelName":"至尊太阳系级会员","Options":"<div><ol><li>至尊太阳系级会员(King Level) 8.5折<br><\/li><li>专属至尊金卡：将专属为您定制刻有您姓名的金卡，将寄至您的注册邮寄地址。<\/li><li>专属餐具：太阳系级会员我们将为您提供日月村至尊专属餐具，感受至尊服务；<\/li><li>饮品享邀请（买二赠一）：单张收银条中每买两杯任意饮料就可以获得一张邀请券。\u203b<\/li><li>消费之王：消费次数累计满6次，赠送免单券一张；\u203b<\/li><li>深夜达人：深夜食堂永久7.8折优惠。\u203b<\/li><li>雨天匠人：每逢下雨天进店消费永久7折。\u203b<\/li><li>一张生日邀请券:在您的生日当月，获赠\u201c生日我最大\u201d免单券一张+定制蛋糕一份。\u203b<\/li><li>周年庆邀请礼包：为庆祝您的帐户周年您将可获得周年庆邀请礼包一组；<\/li><\/ol><\/div>"}]
     * Model2 : {"UserLevelName":"铜黄豆级会员","NextLevelName":"银繁星级会员","NeedThing":"小星星","Diamonds":0,"CurrentThingCount":0,"NextThingCount":10}
     */

    private Model2Bean Model2;
    private List<Model1Bean> Model1;

    public Model2Bean getModel2() {
        return Model2;
    }

    public void setModel2(Model2Bean Model2) {
        this.Model2 = Model2;
    }

    public List<Model1Bean> getModel1() {
        return Model1;
    }

    public void setModel1(List<Model1Bean> Model1) {
        this.Model1 = Model1;
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

    public static class Model1Bean {
        /**
         * MembershipLevelId : 1
         * LevelName : 铜黄豆级会员
         * Options : <head><meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head><body style="color:white;background:black; width:100%;height:100%;margin:0px;"><meta http-equiv="Content-Type" content="text/html; charset=utf-8"><ol><li>铜黄豆级会员(Welcome Level)新会员（购买会员卡价值88元） 9折</li><li>&nbsp;三张亲友邀请券（1.猪排蛋烧饼买一赠一2.全家福饭团买一赠一3.红烧牛肉面买一增一）；每次邀请亲友限用一张。</li><li>一张深夜食堂邀请券 夜宵邀请券可享7.8折夜宵优惠（使用时间为门店深夜营业期间，夜间19:00至23:00。）</li><li>一张饮品升级邀请券。（可点任意饮品升级成高价饮品等）</li><li>早餐豆浆邀请券一张</li></ol></body>
         */

        private int MembershipLevelId;
        private String LevelName;
        private String Options;

        public int getMembershipLevelId() {
            return MembershipLevelId;
        }

        public void setMembershipLevelId(int MembershipLevelId) {
            this.MembershipLevelId = MembershipLevelId;
        }

        public String getLevelName() {
            return LevelName;
        }

        public void setLevelName(String LevelName) {
            this.LevelName = LevelName;
        }

        public String getOptions() {
            return Options;
        }

        public void setOptions(String Options) {
            this.Options = Options;
        }
    }
}
