package com.mayinews.g.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class RankBean {


    /**
     * code : 1
     * data : [{"collectionCount":3417,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1505219094946396def.jpg","id":"test2","name":"陆の瓷ピンク兎女郎制服誘惑","seeCount":2453,"user_name":"陆瓷"},{"collectionCount":3676,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1505221066329ef8ea0.jpg","id":"test14","name":"王馨瑶翘臀露乳","seeCount":877,"user_name":"王馨瑶"},{"collectionCount":110,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1505218967616529f5c.jpg","id":"test1","name":"牛仔爆乳誘惑自動車の光","seeCount":740,"user_name":"嘉宝贝儿(Vetiver)"},{"collectionCount":3983,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1505220662006e8bc8e.jpg","id":"test10","name":"八宝icey&Fiona伊雨蔓","seeCount":1320,"user_name":"八宝icey"},{"collectionCount":102,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/15052192058583bb830.jpg","id":"test3","name":"许诺","seeCount":1246,"user_name":"许诺Sabrina"},{"collectionCount":5978,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/150522096744430ec05.jpg","id":"test13","name":"王馨瑶","seeCount":849,"user_name":"王馨瑶"},{"collectionCount":88,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/150522249284267d49c.jpg","id":"p2f475255d8474189aad990b4af7bc2de","name":"夏瑶baby","seeCount":1556,"user_name":"夏瑶baby"},{"collectionCount":466,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1505220316559ac9aa0.jpg","id":"test12","name":"杨晓青儿","seeCount":976,"user_name":"杨晓青儿"},{"collectionCount":235,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1505218891180d533f9.jpg","id":"test0","name":"刘雪妮ロケ清純誘惑","seeCount":977,"user_name":"刘雪妮"},{"collectionCount":38,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/15052203943776931f2.jpg","id":"test4","name":"绮里嘉","seeCount":759,"user_name":"绮里嘉ula"},{"collectionCount":3351,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/15034713186477a09e6.jpg","id":"test15","name":"小时候的生活","seeCount":1748,"user_name":"刘飞儿"},{"collectionCount":1140,"coverPath":"http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1505220761236733e7c.jpg","id":"test5","name":"嘉宝贝儿","seeCount":755,"user_name":"嘉宝贝儿(Vetiver)"}]
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
         * collectionCount : 3417
         * coverPath : http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1505219094946396def.jpg
         * id : test2
         * name : 陆の瓷ピンク兎女郎制服誘惑
         * seeCount : 2453
         * user_name : 陆瓷
         */

        private int collectionCount;
        private String coverPath;
        private String id;
        private String name;
        private int seeCount;
        private String user_name;

        public int getCollectionCount() {
            return collectionCount;
        }

        public void setCollectionCount(int collectionCount) {
            this.collectionCount = collectionCount;
        }

        public String getCoverPath() {
            return coverPath;
        }

        public void setCoverPath(String coverPath) {
            this.coverPath = coverPath;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSeeCount() {
            return seeCount;
        }

        public void setSeeCount(int seeCount) {
            this.seeCount = seeCount;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
