package com.mayinews.g.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class RankListBean implements Serializable{


    /**
     * status : 200
     * result : [{"id":"20","nickname":"谭睿琪","avatar":"/Uploads/Avatar/2017/09/59cc9ed7429c9.jpeg","cover":"/Uploads/Girls/2017-09-28/59cc9f3e47d50.jpg","follow":"3"},{"id":"18","nickname":"若兮","avatar":"/Uploads/Avatar/2017/09/59cc9d6fd533f.jpeg","cover":"/Uploads/Girls/2017-09-28/59cc9da3467a7.jpg","follow":"2"},{"id":"60","nickname":"海边尤物","avatar":"/Uploads/Avatar/2017/11/5a02b4382ef9b.jpeg","cover":"/Uploads/Girls/2017-11-08/5a02b47b5342d.jpg","follow":"2"},{"id":"1","nickname":"赵伊彤","avatar":"/Uploads/Avatar/2017/09/59cca37decd5c.jpeg","cover":"/Uploads/Girls/2017-09-28/59cca3b208330.jpg","follow":"1"},{"id":"61","nickname":"叶子","avatar":"/Uploads/Avatar/2017/11/5a02bae1c6284.jpeg","cover":"/Uploads/Girls/2017-11-08/5a02bb2ca314a.jpg","follow":"1"},{"id":"9","nickname":"夏茉GIGI","avatar":"/Uploads/Avatar/2017/09/59cc986254974.jpeg","cover":"/Uploads/Girls/2017-09-28/59cc989b40f11.jpg","follow":"1"},{"id":"7","nickname":"刘珏儿","avatar":"/Uploads/Avatar/2017/09/59cc94bfc24ac.jpeg","cover":"/Uploads/Girls/2017-11-15/5a0bcc84e640f.jpg","follow":"1"},{"id":"75","nickname":"林美惠子","avatar":"/Uploads/Avatar/2017/11/5a0d26367c715.jpeg","cover":"/Uploads/Girls/2017-11-16/5a0d26656820f.jpg","follow":"1"},{"id":"12","nickname":"伊小七","avatar":"/Uploads/Avatar/2017/09/59cc9a0fb9641.jpeg","cover":"/Uploads/Girls/2017-09-28/59cc9a4ce3683.jpg","follow":"1"},{"id":"2","nickname":"娜木汗","avatar":"/Uploads/Avatar/2017/09/59cca33be357b.jpeg","cover":"/Uploads/Girls/2017-09-28/59cc756f5b566.jpg","follow":"1"}]
     */

    private int status;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * id : 20
         * nickname : 谭睿琪
         * avatar : /Uploads/Avatar/2017/09/59cc9ed7429c9.jpeg
         * cover : /Uploads/Girls/2017-09-28/59cc9f3e47d50.jpg
         * follow : 3
         */

        private String id;
        private String nickname;
        private String avatar;
        private String cover;
        private String follow;

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

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }
    }
}
