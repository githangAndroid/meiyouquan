package com.mayinews.g.user.bean;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class PostAvaterResultBean {


    /**
     * status : 200
     * result : {"name":"8e9aabbd2311c2168bc01e145e3c166a_1.jpg","type":"image/jpeg","size":12230,"key":"file","ext":"jpg","md5":"699c2bf6adc497f3645936f89da1ad7f","sha1":"b320d7b8f37aa3ee802186e87f69bf2c95387204","savename":"59effea938a75.jpg","savepath":"2017-10-25/","path":"/Uploads/Picture/2017-10-25/59effea938a75.jpg","id":"801434"}
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
         * name : 8e9aabbd2311c2168bc01e145e3c166a_1.jpg
         * type : image/jpeg
         * size : 12230
         * key : file
         * ext : jpg
         * md5 : 699c2bf6adc497f3645936f89da1ad7f
         * sha1 : b320d7b8f37aa3ee802186e87f69bf2c95387204
         * savename : 59effea938a75.jpg
         * savepath : 2017-10-25/
         * path : /Uploads/Picture/2017-10-25/59effea938a75.jpg
         * id : 801434
         */

        private String name;
        private String type;
        private int size;
        private String key;
        private String ext;
        private String md5;
        private String sha1;
        private String savename;
        private String savepath;
        private String path;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getSha1() {
            return sha1;
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }

        public String getSavename() {
            return savename;
        }

        public void setSavename(String savename) {
            this.savename = savename;
        }

        public String getSavepath() {
            return savepath;
        }

        public void setSavepath(String savepath) {
            this.savepath = savepath;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
