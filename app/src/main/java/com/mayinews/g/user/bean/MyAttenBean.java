package com.mayinews.g.user.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class MyAttenBean {


    /**
     * status : 200
     * result : [{"id":"9","realname":"夏茉GIGI","nickname":"夏茉GIGI","city":"北京","size":"88-60-86","height":"165","birthday":"2000-01-01","desc":"","avatar":"/Uploads/Avatar/2017/09/59cc986254974.jpeg","image":null,"status":"1"}]
     * count : 1
     * total : 1
     */

    private int status;
    private int count;
    private int total;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 9
         * realname : 夏茉GIGI
         * nickname : 夏茉GIGI
         * city : 北京
         * size : 88-60-86
         * height : 165
         * birthday : 2000-01-01
         * desc :
         * avatar : /Uploads/Avatar/2017/09/59cc986254974.jpeg
         * image : null
         * status : 1
         */

        private String id;
        private String realname;
        private String nickname;
        private String city;
        private String size;
        private String height;
        private String birthday;
        private String desc;
        private String avatar;
        private Object image;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
