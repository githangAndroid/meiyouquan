package com.mayinews.g.home.bean;

/**
 * Created by Administrator on 2017/9/11 0011.
 */

public class ModelDetailBean1 {


    /**
     * status : 200
     * result : {"id":"2","realname":"娜木汗","nickname":"娜木汗","city":"北京","size":"90-68-90","height":"163","birthday":"1990-10-01","desc":"90后人气嫩模、国际车展、珠宝秀","avatar":"/Uploads/Avatar/2017/09/59cca33be357b.jpeg","image":null,"status":"1"}
     */

    private int status;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
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
