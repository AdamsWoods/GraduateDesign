package com.example.news.bean;

import java.util.List;

public class NewsPaperBean {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * city : 北京
         * newspaper : 经济日报
         * newspaper_url : http://ipaper.ce.cn/
         * hot : 1
         */

        private String city;
        private String newspaper;
        private String newspaper_url;
        private String hot;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getNewspaper() {
            return newspaper;
        }

        public void setNewspaper(String newspaper) {
            this.newspaper = newspaper;
        }

        public String getNewspaper_url() {
            return newspaper_url;
        }

        public void setNewspaper_url(String newspaper_url) {
            this.newspaper_url = newspaper_url;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }
    }
}
