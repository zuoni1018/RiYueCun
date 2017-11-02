package com.zuoni.riyuecun.callback;

import com.zuoni.riyuecun.bean.gson.GetMyCardList;

public  interface ItemOnClickListener {
    void onClick(GetMyCardList.ListDataBean card,int pos);
}
