package com.zuoni.riyuecun.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2017/11/13
 */

public class GetFirstPageNoLogin extends  BaseHttpResponse {

    /**
     * Model1 : null
     * Model2 : {"MessageID":1,"MessageName":"饮食文化","MessageImage":"http://192.168.2.13:8056/upload/MemberManage/3UVEAO636461660232470000.jpg","MessageDescribe":"中国饮食文化涉及到食源的开发与利用、食具的运用与创新、食品的生产与消费、餐饮的服务与接待、餐饮业与食品业的经营与管理，以及饮食与国泰民安、饮食与文学艺术、饮食与人生境界的关系等，深厚广博。"}
     */

    private Object Model1;
    private Model2Bean Model2;

    public Object getModel1() {
        return Model1;
    }

    public void setModel1(Object Model1) {
        this.Model1 = Model1;
    }

    public Model2Bean getModel2() {
        return Model2;
    }

    public void setModel2(Model2Bean Model2) {
        this.Model2 = Model2;
    }

    public static class Model2Bean {
        /**
         * MessageID : 1
         * MessageName : 饮食文化
         * MessageImage : http://192.168.2.13:8056/upload/MemberManage/3UVEAO636461660232470000.jpg
         * MessageDescribe : 中国饮食文化涉及到食源的开发与利用、食具的运用与创新、食品的生产与消费、餐饮的服务与接待、餐饮业与食品业的经营与管理，以及饮食与国泰民安、饮食与文学艺术、饮食与人生境界的关系等，深厚广博。
         */

        private int MessageID;
        private String MessageName;
        private String MessageImage;
        private String MessageDescribe;

        public int getMessageID() {
            return MessageID;
        }

        public void setMessageID(int MessageID) {
            this.MessageID = MessageID;
        }

        public String getMessageName() {
            return MessageName;
        }

        public void setMessageName(String MessageName) {
            this.MessageName = MessageName;
        }

        public String getMessageImage() {
            return MessageImage;
        }

        public void setMessageImage(String MessageImage) {
            this.MessageImage = MessageImage;
        }

        public String getMessageDescribe() {
            return MessageDescribe;
        }

        public void setMessageDescribe(String MessageDescribe) {
            this.MessageDescribe = MessageDescribe;
        }
    }
}
