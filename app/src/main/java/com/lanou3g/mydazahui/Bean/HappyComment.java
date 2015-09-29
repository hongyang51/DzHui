package com.lanou3g.mydazahui.bean;

import java.util.List;

/**
 * 开心一下! 评论
 * Created by dllo on 15/9/29.
 */
public class HappyComment {

    /**
     * comments : [{"username":"老衲射了","created":"2015-09-29 10:54:14","Userid":610887,"content":"你麻麻会说，有人给你擦屁股就好了","cover_url_100x100":"/img/page/default_user_100x100.png","created_cn":"4小时前"},{"username":"灭你男神做你男人","created":"2015-09-29 11:30:15","Userid":592701,"content":"在撸管，麻麻说：儿子，你粑粑走这么多年了，想死你了","cover_url_100x100":"/img/page/default_user_100x100.png","created_cn":"4小时前"},{"username":"2274256627","created":"2015-09-29 13:43:58","Userid":611712,"content":"在一个风和日丽突然鸡扒更出来了","cover_url_100x100":"/img/page/default_user_100x100.png","created_cn":"1小时前"}]
     */

    private List<CommentsEntity> comments;

    public void setComments(List<CommentsEntity> comments) {
        this.comments = comments;
    }

    public List<CommentsEntity> getComments() {
        return comments;
    }

    public static class CommentsEntity {
        /**
         * username : 老衲射了
         * created : 2015-09-29 10:54:14
         * Userid : 610887
         * content : 你麻麻会说，有人给你擦屁股就好了
         * cover_url_100x100 : /img/page/default_user_100x100.png
         * created_cn : 4小时前
         */

        private String username;
        private String created;
        private int Userid;
        private String content;
        private String cover_url_100x100;
        private String created_cn;

        public void setUsername(String username) {
            this.username = username;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public void setUserid(int Userid) {
            this.Userid = Userid;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setCover_url_100x100(String cover_url_100x100) {
            this.cover_url_100x100 = cover_url_100x100;
        }

        public void setCreated_cn(String created_cn) {
            this.created_cn = created_cn;
        }

        public String getUsername() {
            return username;
        }

        public String getCreated() {
            return created;
        }

        public int getUserid() {
            return Userid;
        }

        public String getContent() {
            return content;
        }

        public String getCover_url_100x100() {
            return cover_url_100x100;
        }

        public String getCreated_cn() {
            return created_cn;
        }
    }
}
