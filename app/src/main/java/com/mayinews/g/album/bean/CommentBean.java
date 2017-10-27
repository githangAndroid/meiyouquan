package com.mayinews.g.album.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27 0027.
 */

public class CommentBean {


    /**
     * status : 200
     * count : 1
     * result : [{"gid":"63","uid":"12","tocid":"0","comment":"好美","create_time":"1509075683","id":"1","nickname":"gh","avatar":"http://static.mayinews.com/Uploads/Picture/2017-10-24/59ef0fb0479f7.jpg"}]
     */

    private int status;
    private int count;
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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * gid : 63
         * uid : 12
         * tocid : 0
         * comment : 好美
         * create_time : 1509075683
         * id : 1
         * nickname : gh
         * avatar : http://static.mayinews.com/Uploads/Picture/2017-10-24/59ef0fb0479f7.jpg
         */

        private String gid;
        private String uid;
        private String tocid;
        private String comment;
        private String create_time;
        private String id;
        private String nickname;
        private String avatar;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTocid() {
            return tocid;
        }

        public void setTocid(String tocid) {
            this.tocid = tocid;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
