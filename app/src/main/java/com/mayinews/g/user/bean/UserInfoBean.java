package com.mayinews.g.user.bean;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class UserInfoBean {


    /**
     * status : 200
     * result : {"id":"3","nickname":"h","gender":"1","avatar":"http://static.mayinews.comhttp://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIPGzZ02dANoZ5ACqAWvO4icQ4IwpJeicX09u9Aib5UVRo6bzYyOWXI3NPeGicZoxrCYWyZeCS13XFdfA/0","desc":""}
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
         * id : 3
         * nickname : h
         * gender : 1
         * avatar : http://static.mayinews.comhttp://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIPGzZ02dANoZ5ACqAWvO4icQ4IwpJeicX09u9Aib5UVRo6bzYyOWXI3NPeGicZoxrCYWyZeCS13XFdfA/0
         * desc :
         */

        private String id;
        private String nickname;
        private String gender;
        private String avatar;
        private String desc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
