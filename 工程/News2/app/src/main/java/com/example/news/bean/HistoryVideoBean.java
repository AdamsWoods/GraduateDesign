package com.example.news.bean;

import java.util.List;

public class HistoryVideoBean {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * video_title : kksdl
         * video_url : http://www.baidu.com
         */

        private String video_title;
        private String video_url;
        private String video_bg_img;

        public String getVideo_title() {
            return video_title;
        }

        public void setVideo_title(String video_title) {
            this.video_title = video_title;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getVideo_bg_img() {
            return video_bg_img;
        }

        public void setVideo_bg_img(String bg_img) {
            this.video_bg_img = bg_img;
        }
    }
}
