package com.mayinews.g.app.bean;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class UserInfoBean {

    /**
     * status : 200
     * result : {"uid":"12","nickname":"17664083810","sex":"0","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png","desc":null}
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
         * uid : 12
         * nickname : 17664083810
         * sex : 0
         * avatar : http://static.mayinews.com/Uploads/Picture/video_avatar.png
         * desc : null
         */

        private String uid;
        private String nickname;
        private String sex;
        private String avatar;
        private Object desc;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getDesc() {
            return desc;
        }

        public void setDesc(Object desc) {
            this.desc = desc;
        }
    }
}
