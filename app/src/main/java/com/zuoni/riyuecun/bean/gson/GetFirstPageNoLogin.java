package com.zuoni.riyuecun.bean.gson;

import com.zuoni.riyuecun.bean.model.Message;
import com.zuoni.riyuecun.bean.model.Store;

/**
 * Created by zangyi_shuai_ge on 2017/11/13
 */

public class GetFirstPageNoLogin extends BaseHttpResponse {

    /**
     * Model1 : null
     * Model2 : {"MessageID":1,"MessageName":"饮食文化","MessageImage":"http://192.168.2.13:8056/upload/MemberManage/3UVEAO636461660232470000.jpg","MessageDescribe":"中国饮食文化涉及到食源的开发与利用、食具的运用与创新、食品的生产与消费、餐饮的服务与接待、餐饮业与食品业的经营与管理，以及饮食与国泰民安、饮食与文学艺术、饮食与人生境界的关系等，深厚广博。"}
     */

    private Store Model1;
    private Message Model2;

    public Store getModel1() {
        return Model1;
    }

    public void setModel1(Store Model1) {
        this.Model1 = Model1;
    }

    public Message getModel2() {
        return Model2;
    }

    public void setModel2(Message Model2) {
        this.Model2 = Model2;
    }
}
