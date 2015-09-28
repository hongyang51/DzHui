package com.lanou3g.mydazahui.bean;

import java.util.List;

/**
 * Created by xyb on 15/9/21.
 */
public class LatestNews {

    /**
     *
     * 最新消息实体类
     * date : 20150921
     * stories : [{"title":"20 岁左右的女性，有哪些推荐的旅行穿衣搭配？","ga_prefix":"092120","images":["http://pic1.zhimg.com/bd2b8830df259527e8afece72cfd7eb0.jpg"],"multipic":true,"type":0,"id":7121468},{"images":["http://pic4.zhimg.com/c042cba5146ab31be7ee6ac8c331bd27.jpg"],"type":0,"id":7119668,"ga_prefix":"092119","title":"谢谢来自海洋、岩石或地下的你，让这个世界有了味道"},{"images":["http://pic3.zhimg.com/7c45dce61d5349c5ca57e61759639f76.jpg"],"type":0,"id":7119510,"ga_prefix":"092118","title":"真实版「盗墓笔记」\u2014\u2014被光顾的皇陵"},{"images":["http://pic4.zhimg.com/2ce04362ec9d806548d822e1114ab5d3.jpg"],"type":0,"id":7119685,"ga_prefix":"092117","title":"我在很多人的厨房里，可你却不一定了解我"},{"images":["http://pic2.zhimg.com/93b37e87bbf31c74c803421abd2776e9.jpg"],"type":0,"id":7114868,"ga_prefix":"092116","title":"一开始是超级英雄，现在越来越「神棍」，机器人和这个国家一起成长"},{"images":["http://pic4.zhimg.com/421662c7e1d503c57a6c074c77f68e9b.jpg"],"type":0,"id":7120576,"ga_prefix":"092115","title":"「病毒」让无数苹果设备中招后，我们都该吸取些教训"},{"images":["http://pic1.zhimg.com/5e3f06d51872eac5628556440bb377f8.jpg"],"type":0,"id":7120723,"ga_prefix":"092114","title":"希拉里克林顿两口子很厉害，但这几个大家族比他们强多了"},{"images":["http://pic2.zhimg.com/702493079b9ae19abdbeea27e5bf4d0d.jpg"],"type":0,"id":7118903,"ga_prefix":"092113","title":"您好，请问需要上门服务吗？"},{"images":["http://pic1.zhimg.com/3d53dbc9d219ea255d5332b48b19e2f8.jpg"],"type":0,"id":7116510,"ga_prefix":"092112","title":"《倚天屠龙记》里的「隔山打牛」真的存在吗？"},{"images":["http://pic1.zhimg.com/ff0ee3bed518d137e1abac6fc91ebb4c.jpg"],"type":0,"id":7120699,"ga_prefix":"092111","title":"很多恐龙，无论大小，都是毛乎乎的大怪物"},{"images":["http://pic2.zhimg.com/425b3ddf6c909d6313b2c4aa59dc2c39.jpg"],"type":0,"id":7117720,"ga_prefix":"092110","title":"为了人类，请放心地「恶心」"},{"images":["http://pic2.zhimg.com/887d6a01bbadef3703eadc845fa551f1.jpg"],"type":0,"id":7120545,"ga_prefix":"092109","title":"北京马拉松刚刚过去，好好喂养你的身体，用这些方法准备下一次 42 公里吧"},{"images":["http://pic1.zhimg.com/00ae76c2620f88b87dba902a65eaacec.jpg"],"type":0,"id":7116910,"ga_prefix":"092108","title":"中国有多少人？嗯\u2026\u2026得再算算"},{"images":["http://pic3.zhimg.com/b0052e06f5627a40c8d6211e772b5ae2.jpg"],"type":0,"id":7120337,"ga_prefix":"092107","title":"看到新推出的「苹方」， iOS 用户都觉得苹果字体安卓化了"},{"images":["http://pic4.zhimg.com/b4684cb5f979f97abeb30d5acea93f27.jpg"],"type":0,"id":7120724,"ga_prefix":"092107","title":"教育公平这件事，即使在美国也很难办"},{"images":["http://pic4.zhimg.com/7a4986280c1123c7c807fd859ec244f3.jpg"],"type":0,"id":7117700,"ga_prefix":"092107","title":"吊打黑心的「左旋 VC」概念，看清 VC 护肤真功效"},{"images":["http://pic1.zhimg.com/d7e0f1932c3c2d3265b1b86ed0d2ae70.jpg"],"type":0,"id":7120466,"ga_prefix":"092106","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic2.zhimg.com/832b171a70a4a3d79410e6af5608d9b9.jpg","type":0,"id":7119510,"ga_prefix":"092118","title":"真实版「盗墓笔记」\u2014\u2014被光顾的皇陵"},{"image":"http://pic2.zhimg.com/4caad3e6c27226b42d050796bc9a8481.jpg","type":0,"id":7120576,"ga_prefix":"092115","title":"「病毒」让无数苹果设备中招后，我们都该吸取些教训"},{"image":"http://pic1.zhimg.com/3ebd568898708c7005128554eeedeb10.jpg","type":0,"id":7120337,"ga_prefix":"092107","title":"看到新推出的「苹方」， iOS 用户都觉得苹果字体安卓化了"},{"image":"http://pic4.zhimg.com/9588d6f037f3622e127a81f06fc5e9f3.jpg","type":0,"id":7116910,"ga_prefix":"092108","title":"中国有多少人？嗯\u2026\u2026得再算算"},{"image":"http://pic2.zhimg.com/5440eb7499328fe70c67390b52213cc5.jpg","type":0,"id":7117700,"ga_prefix":"092107","title":"吊打黑心的「左旋 VC」概念，看清 VC 护肤真功效"}]
     */

    private String date;
    private List<StoriesEntity> stories;
    private List<TopStoriesEntity> top_stories;

    public void setDate(String date) {
        this.date = date;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public void setTop_stories(List<TopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }

    public String getDate() {
        return date;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public List<TopStoriesEntity> getTop_stories() {
        return top_stories;
    }

    public static class StoriesEntity {
        /**
         * title : 20 岁左右的女性，有哪些推荐的旅行穿衣搭配？
         * ga_prefix : 092120
         * images : ["http://pic1.zhimg.com/bd2b8830df259527e8afece72cfd7eb0.jpg"]
         * multipic : true
         * type : 0
         * id : 7121468
         */

        private String title;
        private String ga_prefix;
        private boolean multipic;
        private int type;
        private int id;
        private List<String> images;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getTitle() {
            return title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public boolean getMultipic() {
            return multipic;
        }

        public int getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public List<String> getImages() {
            return images;
        }
    }

    public static class TopStoriesEntity {
        /**
         * image : http://pic2.zhimg.com/832b171a70a4a3d79410e6af5608d9b9.jpg
         * type : 0
         * id : 7119510
         * ga_prefix : 092118
         * title : 真实版「盗墓笔记」——被光顾的皇陵
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public void setImage(String image) {
            this.image = image;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public int getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public String getTitle() {
            return title;
        }
    }
}
