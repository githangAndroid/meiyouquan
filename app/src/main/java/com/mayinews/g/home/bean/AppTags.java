package com.mayinews.g.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6 0006.
 * App私照的分类标题
 */

public class AppTags {


    /**
     * result : [{"id":"3","name":"meimei","sort":"4","title":"邻家女孩"},{"id":"4","name":"zhifu","sort":"0","title":"制服诱惑"},{"id":"5","name":"meixiong","sort":"1","title":"美胸爆乳"},{"id":"6","name":"meiniu","sort":"2","title":"美妞范儿"},{"id":"7","name":"siwameitui","sort":"3","title":"丝袜美腿"},{"id":"8","name":"chuangyi","sort":"5","title":"光影创意"}]
     * status : 200
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
         * id : 3
         * name : meimei
         * sort : 4
         * title : 邻家女孩
         */

        private String id;
        private String name;
        private String sort;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

