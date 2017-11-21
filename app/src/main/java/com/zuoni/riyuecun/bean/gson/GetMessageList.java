package com.zuoni.riyuecun.bean.gson;

import com.zuoni.riyuecun.bean.model.Message;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/11/13
 */

public class GetMessageList extends BaseHttpResponse {

    private List<Message> Model1;

    public List<Message> getModel1() {
        return Model1;
    }

    public void setModel1(List<Message> Model1) {
        this.Model1 = Model1;
    }


}
