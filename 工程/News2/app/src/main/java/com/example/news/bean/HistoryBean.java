package com.example.news.bean;

import java.util.List;

public class HistoryBean {

    /**
     * username : 13260515335
     * record : [{"title":"吴谨言的苏妲己，杨幂的王昭君，刘亦菲的貂蝉，都败给了她的西施","imgurl":"http://07imgmini.eastday.com/mobile/20190513/2019051318_6b6bb269bfa84059811738d8980b3d6e_7936_mwpm_03200403.jpg","details":"http://mini.eastday.com/mobile/190513183722559.html"},{"title":"\u201c90后\u201d瑶族姐妹花返乡创业成\u201c家乡代言人\u201d","imgurl":"http://04imgmini.eastday.com/mobile/20190513/20190513195108_3fab4c5f84b2c4e2fd2df61eea0a7132_2_mwpm_03200403.jpg","details":"http://mini.eastday.com/mobile/190513195108753.html"}]
     */

    private String username;
    private List<RecordBean> record;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean {
        /**
         * title : 吴谨言的苏妲己，杨幂的王昭君，刘亦菲的貂蝉，都败给了她的西施
         * imgurl : http://07imgmini.eastday.com/mobile/20190513/2019051318_6b6bb269bfa84059811738d8980b3d6e_7936_mwpm_03200403.jpg
         * details : http://mini.eastday.com/mobile/190513183722559.html
         */

        private String title;
        private String imgurl;
        private String details;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
