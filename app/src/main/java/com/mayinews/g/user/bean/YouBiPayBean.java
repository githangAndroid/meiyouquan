package com.mayinews.g.user.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public class YouBiPayBean {


    /**
     * code : 1
     * data : [{"freeGoldCount":2,"goldCount":29,"id":"test","imgUrl":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1508587951159f3e663.png","info":"赠送2尤币","price":29,"title":"充值29尤币"},{"freeGoldCount":5,"goldCount":66,"id":"rp29eb5f567dcb427097906b070b9662c2","imgUrl":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/15085895783174cf424.png","info":"赠送5尤币","price":66,"title":"充值66尤币"},{"freeGoldCount":20,"goldCount":128,"id":"rp58191d6bbcd14421b44a108e5ec5e5f5","imgUrl":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/15085895729261c0bc0.png","info":"赠送20尤币","price":128,"title":"充值128尤币"},{"freeGoldCount":100,"goldCount":800,"id":"rpdc4f76260b3a428caa5900a353488c81","imgUrl":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1508589584626dd27c3.png","info":"赠送100尤币","price":800,"title":"充值800尤币"}]
     * erroMsg :
     */

    private int code;
    private String erroMsg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErroMsg() {
        return erroMsg;
    }

    public void setErroMsg(String erroMsg) {
        this.erroMsg = erroMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * freeGoldCount : 2
         * goldCount : 29
         * id : test
         * imgUrl : http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1508587951159f3e663.png
         * info : 赠送2尤币
         * price : 29
         * title : 充值29尤币
         */

        private int freeGoldCount;
        private int goldCount;
        private String id;
        private String imgUrl;
        private String info;
        private int price;
        private String title;

        public int getFreeGoldCount() {
            return freeGoldCount;
        }

        public void setFreeGoldCount(int freeGoldCount) {
            this.freeGoldCount = freeGoldCount;
        }

        public int getGoldCount() {
            return goldCount;
        }

        public void setGoldCount(int goldCount) {
            this.goldCount = goldCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
