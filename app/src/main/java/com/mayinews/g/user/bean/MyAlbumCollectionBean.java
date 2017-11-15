package com.mayinews.g.user.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class MyAlbumCollectionBean {


    /**
     * status : 200
     * result : [{"id":"65","title":"旗袍","cid":"4","description":"","cover_id":"780572","cover":"/Uploads/Girls/2017-10-17/59e57875a2e56.jpg","view":"0","comment":"0","create_time":"1508210821","actor":"赵伊彤","actor_id":"1","actor_avatar":"/Uploads/Avatar/2017/09/59cca37decd5c.jpeg","actor_city":"","actor_size":"88-60-86","actor_height":"165","actor_image":null},{"id":"64","title":"旗袍","cid":"4","description":"","cover_id":"780562","cover":"/Uploads/Girls/2017-10-17/59e57831a258a.jpg","view":"0","comment":"0","create_time":"1508210765","actor":"赵伊彤","actor_id":"1","actor_avatar":"/Uploads/Avatar/2017/09/59cca37decd5c.jpeg","actor_city":"","actor_size":"88-60-86","actor_height":"165","actor_image":null}]
     * count : 2
     * total : 3
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
         * id : 65
         * title : 旗袍
         * cid : 4
         * description :
         * cover_id : 780572
         * cover : /Uploads/Girls/2017-10-17/59e57875a2e56.jpg
         * view : 0
         * comment : 0
         * create_time : 1508210821
         * actor : 赵伊彤
         * actor_id : 1
         * actor_avatar : /Uploads/Avatar/2017/09/59cca37decd5c.jpeg
         * actor_city :
         * actor_size : 88-60-86
         * actor_height : 165
         * actor_image : null
         */

        private String id;
        private String title;
        private String cid;
        private String description;
        private String cover_id;
        private String cover;
        private String view;
        private String comment;
        private String create_time;
        private String actor;
        private String actor_id;
        private String actor_avatar;
        private String actor_city;
        private String actor_size;
        private String actor_height;
        private Object actor_image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCover_id() {
            return cover_id;
        }

        public void setCover_id(String cover_id) {
            this.cover_id = cover_id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
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

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getActor_id() {
            return actor_id;
        }

        public void setActor_id(String actor_id) {
            this.actor_id = actor_id;
        }

        public String getActor_avatar() {
            return actor_avatar;
        }

        public void setActor_avatar(String actor_avatar) {
            this.actor_avatar = actor_avatar;
        }

        public String getActor_city() {
            return actor_city;
        }

        public void setActor_city(String actor_city) {
            this.actor_city = actor_city;
        }

        public String getActor_size() {
            return actor_size;
        }

        public void setActor_size(String actor_size) {
            this.actor_size = actor_size;
        }

        public String getActor_height() {
            return actor_height;
        }

        public void setActor_height(String actor_height) {
            this.actor_height = actor_height;
        }

        public Object getActor_image() {
            return actor_image;
        }

        public void setActor_image(Object actor_image) {
            this.actor_image = actor_image;
        }
    }
}
