package com.lanou3g.mydazahui.Bean;

import java.util.List;

/**
 *
 * 具体内容
 * Created by xyb on 15/9/21.
 */
public class NewsContent {
    private String body;// Html网页

    private String image_source;//图片版权

    private String title;// 新闻标题

    private String image;// 图片地址

    private String share_url;// 在线查看的内容或者分享用得Url

    private List<recommenders> recommenders;//文章推荐者
    private List<recommenderss> recommenderss;

    private String ga_prefix;//供 Google Analytics 使用

    private int type;// 新闻的类型

    private int id;// 新闻的id



    private List<css> csss;

    public List<NewsContent.recommenderss> getRecommenderss() {
        return recommenderss;
    }

    public void setRecommenderss(List<NewsContent.recommenderss> recommenderss) {
        this.recommenderss = recommenderss;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getImage_source() {
        return this.image_source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_url() {
        return this.share_url;
    }

    public void setRecommenders(List<recommenders> recommenders) {
        this.recommenders = recommenders;
    }

    public List<recommenders> getRecommenders() {
        return this.recommenders;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getGa_prefix() {
        return this.ga_prefix;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setCss(List<css> css) {
        this.csss = css;
    }

    public List<css> getCss() {
        return this.csss;
    }

    public static class css {

    }

    public static class recommenders {
        private String avatar;

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar() {
            return this.avatar;
        }
    }
    public static class recommenderss {
        private String avatar;

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar() {
            return this.avatar;
        }
    }
}
