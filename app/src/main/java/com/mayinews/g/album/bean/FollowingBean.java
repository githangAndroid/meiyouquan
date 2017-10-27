package com.mayinews.g.album.bean;

import java.util.List;

/**
 * 获取我关注的用户
 */

public class FollowingBean {


    /**
     * status : 200
     * result : [{"id":"2","realname":"娜木汗","nickname":"娜木汗","city":"北京","size":"90-68-90","height":"163","birthday":"1990-10-01","desc":"90后人气嫩模、国际车展、珠宝秀","avatar":"/Uploads/Avatar/2017/09/59cca33be357b.jpeg","image":"","status":"1","type":"actor"},{"id":"1","realname":"赵伊彤","nickname":"赵伊彤","city":"","size":"88-60-86","height":"165","birthday":"2000-01-05","desc":"","avatar":"/Uploads/Avatar/2017/09/59cca37decd5c.jpeg","image":"","status":"1"}]
     * count : 2
     * total : 2
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
         * id : 2
         * realname : 娜木汗
         * nickname : 娜木汗
         * city : 北京
         * size : 90-68-90
         * height : 163
         * birthday : 1990-10-01
         * desc : 90后人气嫩模、国际车展、珠宝秀
         * avatar : /Uploads/Avatar/2017/09/59cca33be357b.jpeg
         * image :
         * status : 1
         * type : actor
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
        private String image;
        private String status;
        private String type;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
