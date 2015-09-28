package com.lanou3g.mydazahui.bean;

import java.util.List;

/**
 * Created by dllo on 15/9/28.
 */
public class Happy {
    private List<jokes> jokes;

    public void setJokes(List<jokes> jokes) {
        this.jokes = jokes;
    }

    public List<jokes> getJokes() {
        return this.jokes;
    }

    public static class jokes {
        private String video_uri;

        private String user_cover_url_100x100;

        private String user_cover_url;
        private int Jokeid;

        private String user_name;

        private String created;

        private int Userid;

        private String next_joke_id;

        private String uri;

        private String content;

        private int unlike_count;

        private int comment_count;

        private int like_count;

        private int pre_joke_id;

        private String less_image_uri;

        private int user_id;

        private String type;

        private String created_cn;

        public void setVideo_uri(String video_uri) {
            this.video_uri = video_uri;
        }

        public String getVideo_uri() {
            return this.video_uri;
        }

        public void setUser_cover_url_100x100(String user_cover_url_100x100) {
            this.user_cover_url_100x100 = user_cover_url_100x100;
        }

        public String getUser_cover_url_100x100() {
            return this.user_cover_url_100x100;
        }

        public void setUser_cover_url(String user_cover_url) {
            this.user_cover_url = user_cover_url;
        }

        public String getUser_cover_url() {
            return this.user_cover_url;
        }


        public void setJokeid(int Jokeid) {
            this.Jokeid = Jokeid;
        }

        public int getJokeid() {
            return this.Jokeid;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_name() {
            return this.user_name;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getCreated() {
            return this.created;
        }

        public void setUserid(int Userid) {
            this.Userid = Userid;
        }

        public int getUserid() {
            return this.Userid;
        }

        public void setNext_joke_id(String next_joke_id) {
            this.next_joke_id = next_joke_id;
        }

        public String getNext_joke_id() {
            return this.next_joke_id;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getUri() {
            return this.uri;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return this.content;
        }

        public void setUnlike_count(int unlike_count) {
            this.unlike_count = unlike_count;
        }

        public int getUnlike_count() {
            return this.unlike_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getComment_count() {
            return this.comment_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getLike_count() {
            return this.like_count;
        }

        public void setPre_joke_id(int pre_joke_id) {
            this.pre_joke_id = pre_joke_id;
        }

        public int getPre_joke_id() {
            return this.pre_joke_id;
        }

        public void setLess_image_uri(String less_image_uri) {
            this.less_image_uri = less_image_uri;
        }

        public String getLess_image_uri() {
            return this.less_image_uri;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getUser_id() {
            return this.user_id;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public void setCreated_cn(String created_cn) {
            this.created_cn = created_cn;
        }

        public String getCreated_cn() {
            return this.created_cn;
        }
    }
}
