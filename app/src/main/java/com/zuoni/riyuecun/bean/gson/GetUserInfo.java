package com.zuoni.riyuecun.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2017/11/2
 */

public class GetUserInfo extends  BaseHttpResponse {

    /**
     * Model1 : {"UserName":"用户弄","UserNickName":"臧艺","UserSex":true,"Phone":"15168212330"}
     */

    private Model1Bean Model1;

    public Model1Bean getModel1() {
        return Model1;
    }

    public void setModel1(Model1Bean Model1) {
        this.Model1 = Model1;
    }

    public static class Model1Bean {
        /**
         * UserName : 用户弄
         * UserNickName : 臧艺
         * UserSex : true
         * Phone : 15168212330
         */

        private String UserName;
        private String UserNickName;
        private boolean UserSex;
        private String Phone;

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getUserNickName() {
            return UserNickName;
        }

        public void setUserNickName(String UserNickName) {
            this.UserNickName = UserNickName;
        }

        public boolean isUserSex() {
            return UserSex;
        }

        public void setUserSex(boolean UserSex) {
            this.UserSex = UserSex;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }
    }
}
