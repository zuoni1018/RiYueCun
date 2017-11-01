package com.zuoni.riyuecun.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2017/11/1
 */

public class Login extends  BaseHttpResponse{

    /**
     * Model1 : {"UserToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJNRDUifQ==.eyJleHAiOiIyMDE35bm0MTLmnIgx5pelIiwiVXNlcklEIjoxOSwiVXNlck5hbWUiOiLnlKjmiLflvIQifQ==.17482cca961d3f1c6fa0199ccaa87e1f"}
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
         * UserToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJNRDUifQ==.eyJleHAiOiIyMDE35bm0MTLmnIgx5pelIiwiVXNlcklEIjoxOSwiVXNlck5hbWUiOiLnlKjmiLflvIQifQ==.17482cca961d3f1c6fa0199ccaa87e1f
         */

        private String UserToken;

        public String getUserToken() {
            return UserToken;
        }

        public void setUserToken(String UserToken) {
            this.UserToken = UserToken;
        }
    }
}
