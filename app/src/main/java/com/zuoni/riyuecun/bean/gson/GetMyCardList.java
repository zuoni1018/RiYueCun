package com.zuoni.riyuecun.bean.gson;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/2
 */

public class GetMyCardList extends  BaseHttpResponse {


    private List<ListDataBean> ListData;

    public List<ListDataBean> getListData() {
        return ListData;
    }

    public void setListData(List<ListDataBean> ListData) {
        this.ListData = ListData;
    }

    public static class ListDataBean {
        /**
         * MemberShipCardId : 4
         * ImgUrl : 1
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
}
