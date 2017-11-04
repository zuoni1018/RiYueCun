package com.zuoni.riyuecun.bean.gson;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/3
 */

public class GetMyLevelInfo extends  BaseHttpResponse {


    private List<ListDataBean> ListData;

    public List<ListDataBean> getListData() {
        return ListData;
    }

    public void setListData(List<ListDataBean> ListData) {
        this.ListData = ListData;
    }

    public static class ListDataBean {
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
        private String Diamonds;
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

        public String getDiamonds() {
            return Diamonds;
        }

        public void setDiamonds(String Diamonds) {
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
}
