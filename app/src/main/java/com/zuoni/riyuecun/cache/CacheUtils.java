package com.zuoni.riyuecun.cache;

import android.content.Context;

import com.zuoni.common.utils.SPUtils;

/**
 * Created by zangyi_shuai_ge on 2017/9/27
 * SharedPreferences 辅助类
 */

public class CacheUtils {
    public static void setToken(String token, Context context) {
        SPUtils.put(context, "UserToken", token);
    }

    public static String getToken(Context context) {
        String token = (String) SPUtils.get(context, "UserToken", "");
        return token;
    }
    public static void setLogin(boolean isLogin, Context context) {
        SPUtils.put(context, "isLogin", isLogin);
    }

    public static boolean isLogin(Context context) {
        boolean isLogin = (boolean) SPUtils.get(context, "isLogin", false);
        return isLogin;
    }
}
