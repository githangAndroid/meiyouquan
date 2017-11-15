package com.mayinews.g.user.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class PayDataBean {

    /**
     * iconUrl : http://fns-system.oss-cn-hangzhou.aliyuncs.com/14996247573863c5a7c.png
     * id : v307cf20ff3754c80a56fba29e4fc4718
     * info : 所有权限及开放特级“私密区”多个神秘入口 送价值298元VR眼镜
     * level : 6
     * mcVipDescribes : [{"iconUrl":"http://fns-system.oss-cn-hangzhou.aliyuncs.com/1499270868310bbdf5f.png","id":"v01543776509f41acb843c9b547b4c1c8","info":"钻石VIP会员有效期一年"},{"iconUrl":"http://fns-system.oss-cn-hangzhou.aliyuncs.com/1499270868231bb28f9.png","id":"vee0326e4975e4797a6cfe155d5dcc648","info":"任意看尤女全部高清图片专辑"},{"iconUrl":"http://fns-system.oss-cn-hangzhou.aliyuncs.com/149927086815718d102.png","id":"v07105754dd084277a157ac36fdfc7c4c","info":"任意看尤女全部高清3D图片专辑"},{"iconUrl":"http://fns-system.oss-cn-hangzhou.aliyuncs.com/1499270868079105b76.png","id":"v031e6390a1ac4d448386d852ba8afd0c","info":"良心出品，模特专辑拍摄花絮任性看"},{"iconUrl":"http://fns-system.oss-cn-hangzhou.aliyuncs.com/14992708680067e46c3.png","id":"vb711636657d946d7aa28017d113dbcc2","info":"国内首家模特VR全景内容，360°随心观看"},{"iconUrl":"http://fns-system.oss-cn-hangzhou.aliyuncs.com/1499270867930bb3d02.png","id":"v496f37108afa42f483c1969e1cf3187c","info":"VIP会员独享下载尤女高清经典期刊专辑"},{"iconUrl":"http://fns-system.oss-cn-hangzhou.aliyuncs.com/14992708678374345d2.png","id":"v43890e147b8e498d8d39c5a1de1b6cc4","info":"VIP会员优先申请参加尤女郎线下摄影及活动交流"}]
     * monthCount : 12
     * name : 钻石VIP
     * price : 698.0
     * type : 1
     */

    private String iconUrl;
    private String id;
    private String info;
    private int level;
    private int monthCount;
    private String name;
    private double price;
    private int type;
    private List<McVipDescribesBean> mcVipDescribes;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<McVipDescribesBean> getMcVipDescribes() {
        return mcVipDescribes;
    }

    public void setMcVipDescribes(List<McVipDescribesBean> mcVipDescribes) {
        this.mcVipDescribes = mcVipDescribes;
    }

    public static class McVipDescribesBean {
        /**
         * iconUrl : http://fns-system.oss-cn-hangzhou.aliyuncs.com/1499270868310bbdf5f.png
         * id : v01543776509f41acb843c9b547b4c1c8
         * info : 钻石VIP会员有效期一年
         */

        private String iconUrl;
        private String id;
        private String info;

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
