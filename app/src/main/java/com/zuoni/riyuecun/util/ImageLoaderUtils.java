package com.zuoni.riyuecun.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zuoni.riyuecun.R;

/**
 * Created by zangyi_shuai_ge on 2017/11/13
 * Glide  设置展位图之类的 进行统一管理
 */

public class ImageLoaderUtils {

    public static  void setStoredValueCardImage(Context context, String url, ImageView imageView){
        Glide
                .with(context)
                .load(url)
                .asBitmap()
                .error(R.mipmap.bg666)
                .placeholder(R.mipmap.bg666)
                .into(imageView);
    }
}
