package com.mayinews.g.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/30 0030.
 */
  //获取专辑图片
public class SingleAlbum {
    /**
     * result : {"cid":"6","comment":"0","content":"745452,745453,745454,745455,745456,745457,745458,745459,745460,745461,745459,745462,745463,745464,745465","cover":"/Uploads/Girls/2017-09-29/59cdc75eba662.jpg","cover_id":"745454","create_time":"1506658174","description":"张小西Ann粉面含春比基尼写真","id":"62","picture":["/Uploads/Girls/2017-09-29/59cdc75de9ecf.jpg","/Uploads/Girls/2017-09-29/59cdc75e57df2.jpg","/Uploads/Girls/2017-09-29/59cdc75eba662.jpg","/Uploads/Girls/2017-09-29/59cdc75f267e9.jpg","/Uploads/Girls/2017-09-29/59cdc75f8e93a.jpg","/Uploads/Girls/2017-09-29/59cdc7600228d.jpg","/Uploads/Girls/2017-09-29/59cdc760635fa.jpg","/Uploads/Girls/2017-09-29/59cdc760c2258.jpg","/Uploads/Girls/2017-09-29/59cdc7612e012.jpg"],"picture_num":15,"title":"张小西Ann粉面含春比基尼写真","view":"0"}
     * status : 200
     */

    private ResultBean result;
    private int status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * cid : 6
         * comment : 0
         * content : 745452,745453,745454,745455,745456,745457,745458,745459,745460,745461,745459,745462,745463,745464,745465
         * cover : /Uploads/Girls/2017-09-29/59cdc75eba662.jpg
         * cover_id : 745454
         * create_time : 1506658174
         * description : 张小西Ann粉面含春比基尼写真
         * id : 62
         * picture : ["/Uploads/Girls/2017-09-29/59cdc75de9ecf.jpg","/Uploads/Girls/2017-09-29/59cdc75e57df2.jpg","/Uploads/Girls/2017-09-29/59cdc75eba662.jpg","/Uploads/Girls/2017-09-29/59cdc75f267e9.jpg","/Uploads/Girls/2017-09-29/59cdc75f8e93a.jpg","/Uploads/Girls/2017-09-29/59cdc7600228d.jpg","/Uploads/Girls/2017-09-29/59cdc760635fa.jpg","/Uploads/Girls/2017-09-29/59cdc760c2258.jpg","/Uploads/Girls/2017-09-29/59cdc7612e012.jpg"]
         * picture_num : 15
         * title : 张小西Ann粉面含春比基尼写真
         * view : 0
         */

        private String cid;
        private String comment;
        private String content;
        private String cover;
        private String cover_id;
        private String create_time;
        private String description;
        private String id;
        private int picture_num;
        private String title;
        private String view;
        private List<String> picture;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCover_id() {
            return cover_id;
        }

        public void setCover_id(String cover_id) {
            this.cover_id = cover_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPicture_num() {
            return picture_num;
        }

        public void setPicture_num(int picture_num) {
            this.picture_num = picture_num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public List<String> getPicture() {
            return picture;
        }

        public void setPicture(List<String> picture) {
            this.picture = picture;
        }
    }
}
