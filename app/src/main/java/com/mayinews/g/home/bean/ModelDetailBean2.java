package com.mayinews.g.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11 0011.
 */

public class ModelDetailBean2 {
    /**
     * status : 200
     * result : [{"id":"16","title":"职场女秘刘钰儿性感OL装妩媚妖娆","cid":"4","description":"性感OL装妩媚妖娆","cover_id":"741795","cover":"/Uploads/Girls/2017-09-28/59cc967d4c228.jpg","view":"0","comment":"0","create_time":"1506580122","content":"741794,741795,741796,741797,741798,741799,741800,741801,741802,741803,741804,741805,741806,741807,741808,741809,741810,741811,741812,741813,741814,741815,741816,741817,741818","picture":["/Uploads/Girls/2017-09-28/59cc967ce0980.jpg","/Uploads/Girls/2017-09-28/59cc967d4c228.jpg","/Uploads/Girls/2017-09-28/59cc967db2528.jpg","/Uploads/Girls/2017-09-28/59cc967e1e1c9.jpg","/Uploads/Girls/2017-09-28/59cc967e7d9b1.jpg","/Uploads/Girls/2017-09-28/59cc967eddfce.jpg","/Uploads/Girls/2017-09-28/59cc967f504b4.jpg","/Uploads/Girls/2017-09-28/59cc967fb02d3.jpg","/Uploads/Girls/2017-09-28/59cc96801be72.jpg"],"picture_num":25},{"id":"15","title":"刘珏儿丰乳肥臀火辣动人","cid":"5","description":"丰乳肥臀火辣动人","cover_id":"741759","cover":"/Uploads/Girls/2017-09-28/59cc9555d6f52.jpg","view":"0","comment":"0","create_time":"1506580046","content":"741759,741762,741760,741761,741763,741764,741765,741767,741769,741766,741768,741771,741770,741773,741772,741774,741775,741778,741776,741777,741779,741781,741780,741782,741783,741784,741785,741786,741787,741788,741791,741790,741789,741793,741792","picture":["/Uploads/Girls/2017-09-28/59cc9555d6f52.jpg","/Uploads/Girls/2017-09-28/59cc955726bd9.jpg","/Uploads/Girls/2017-09-28/59cc955659502.jpg","/Uploads/Girls/2017-09-28/59cc9556bb2d3.jpg","/Uploads/Girls/2017-09-28/59cc955786907.jpg","/Uploads/Girls/2017-09-28/59cc9557e5f11.jpg","/Uploads/Girls/2017-09-28/59cc95585adf7.jpg","/Uploads/Girls/2017-09-28/59cc955928ea3.jpg","/Uploads/Girls/2017-09-28/59cc9559e9c63.jpg"],"picture_num":35}]
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

    public static class ResultBean {
        /**
         * id : 16
         * title : 职场女秘刘钰儿性感OL装妩媚妖娆
         * cid : 4
         * description : 性感OL装妩媚妖娆
         * cover_id : 741795
         * cover : /Uploads/Girls/2017-09-28/59cc967d4c228.jpg
         * view : 0
         * comment : 0
         * create_time : 1506580122
         * content : 741794,741795,741796,741797,741798,741799,741800,741801,741802,741803,741804,741805,741806,741807,741808,741809,741810,741811,741812,741813,741814,741815,741816,741817,741818
         * picture : ["/Uploads/Girls/2017-09-28/59cc967ce0980.jpg","/Uploads/Girls/2017-09-28/59cc967d4c228.jpg","/Uploads/Girls/2017-09-28/59cc967db2528.jpg","/Uploads/Girls/2017-09-28/59cc967e1e1c9.jpg","/Uploads/Girls/2017-09-28/59cc967e7d9b1.jpg","/Uploads/Girls/2017-09-28/59cc967eddfce.jpg","/Uploads/Girls/2017-09-28/59cc967f504b4.jpg","/Uploads/Girls/2017-09-28/59cc967fb02d3.jpg","/Uploads/Girls/2017-09-28/59cc96801be72.jpg"]
         * picture_num : 25
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
        private String content;
        private int picture_num;
        private List<String> picture;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPicture_num() {
            return picture_num;
        }

        public void setPicture_num(int picture_num) {
            this.picture_num = picture_num;
        }

        public List<String> getPicture() {
            return picture;
        }

        public void setPicture(List<String> picture) {
            this.picture = picture;
        }
    }
}



