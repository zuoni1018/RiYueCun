package com.zuoni.riyuecun.util.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Created by zangyi_shuai_ge on 2017/9/29
 * 微信网页分享类
 */

public class WxShareWeb {

    private String webUrl = "";
    private String title = "";
    private String description = "";
    private int bitmapId;

    public WxShareWeb(String webUrl, String title, String description, int bitmapId) {
        this.webUrl = webUrl;
        this.title = title;
        this.description = description;
        this.bitmapId = bitmapId;
    }
    public void share(Context context, IWXAPI api) {
        share(context,api,SendMessageToWX.Req.WXSceneSession);
    }

    public void share(Context context, IWXAPI api,int shareType) {

//        mTargetScene = SendMessageToWX.Req.WXSceneSession; //分享好友
//        mTargetScene = SendMessageToWX.Req.WXSceneTimeline; //朋友圈
//        mTargetScene = SendMessageToWX.Req.WXSceneFavorite;  //收藏

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),bitmapId);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);


        //只是换了个message
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = shareType;
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
