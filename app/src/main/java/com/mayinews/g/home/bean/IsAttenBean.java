package com.mayinews.g.home.bean;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class IsAttenBean {


    /**
     * status : 200
     * result : {"count":0,"isFollow":false}
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
         * count : 0
         * isFollow : false
         */

        private int count;
        private boolean isFollow;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isIsFollow() {
            return isFollow;
        }

        public void setIsFollow(boolean isFollow) {
            this.isFollow = isFollow;
        }
    }
}
