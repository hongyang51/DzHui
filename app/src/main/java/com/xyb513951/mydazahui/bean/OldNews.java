package com.xyb513951.mydazahui.bean;

import java.util.List;

/**
 *
 * 往期日报实体类
 * Created by xyb on 15/9/21.
 */
public class OldNews {

    /**
     * date : 20150914
     * stories : [{"images":["http://pic3.zhimg.com/13631a43a0227d61a93ba336fc467b2a.jpg"],"type":0,"id":7112228,"ga_prefix":"091422","title":"深夜惊奇 · 前任后悔吗"},{"title":"生活苦，生活累，不如看看日本黑社会","ga_prefix":"091421","images":["http://pic2.zhimg.com/122c2790bf0c70940a0fe95bed3da181.jpg"],"multipic":true,"type":0,"id":7112221},{"title":"央视版《三国演义》有哪些原创情节？","ga_prefix":"091420","images":["http://pic3.zhimg.com/2ccf26e97ecf3e0f2f1d8f05d6e9b92a.jpg"],"multipic":true,"type":0,"id":7111800},{"theme":{"id":2,"subscribed":false,"name":"开始游戏"},"type":0,"id":7112210,"ga_prefix":"091419","title":"在这个游戏里，太太和我一起搭建了一个新世界"},{"images":["http://pic2.zhimg.com/5ab6d4684114d27e9dd5e6be2d7c5f31.jpg"],"type":0,"id":7112074,"ga_prefix":"091418","title":"一个可能很多人都误解了的概念：速度的极限并非光速"},{"images":["http://pic4.zhimg.com/8b240deec300a4479beb4c58e7c82693.jpg"],"type":0,"id":7110840,"ga_prefix":"091417","title":"这篇文章，仅仅是看看，就觉得特别痛"},{"images":["http://pic3.zhimg.com/906cd3c3585de1019846517545e3a252.jpg"],"type":0,"id":7112191,"ga_prefix":"091416","title":"「忘记了呼吸」也就在歌词里唱唱，如果真的发生了就\u2026\u2026"},{"title":"一幅毫无违和感的合成图片作品该分几步做？","ga_prefix":"091415","images":["http://pic4.zhimg.com/60f47d53c0e03c3aa352215219002e6f.jpg"],"multipic":true,"type":0,"id":7102781},{"images":["http://pic1.zhimg.com/94b5920dee3f74b3a5732e6b2e9c691c.jpg"],"type":0,"id":7110988,"ga_prefix":"091414","title":"为什么不能盗墓？因为悲伤的故事发生了一次又一次"},{"images":["http://pic1.zhimg.com/dcf8f517beb08125dbb99a7bd9569ea0.jpg"],"type":0,"id":7110842,"ga_prefix":"091413","title":"有的企业贷款交百分之百的保证金，那还贷款干嘛呢？"},{"images":["http://pic3.zhimg.com/77a57a0f32cd8abdaf051da0339c01c2.jpg"],"type":0,"id":7111475,"ga_prefix":"091412","title":"希望读完后，你可以打破鸡汤的魔咒"},{"images":["http://pic1.zhimg.com/69949096f94fbaa550170d108bfbc27c.jpg"],"type":0,"id":7111460,"ga_prefix":"091411","title":"你怎么知道我是关机了还是不在服务区"},{"images":["http://pic2.zhimg.com/f042840445700c077651f6165ff21b6d.jpg"],"type":0,"id":7107952,"ga_prefix":"091410","title":"先是让你感到愧疚，接下来，你就被操控了"},{"images":["http://pic4.zhimg.com/1f9922275dd8bb3817e0e8cbba1d2127.jpg"],"type":0,"id":7103463,"ga_prefix":"091409","title":"新闻常说「中国经济中库存大量增加，必须去库存」，「库存」具体指的是什么？"},{"images":["http://pic1.zhimg.com/8d1429d01e4f705b28257c65905a7e3c.jpg"],"type":0,"id":7111354,"ga_prefix":"091408","title":"居然能把李小璐认成范冰冰，外国人为什么分不清中国人长什么样？"},{"images":["http://pic4.zhimg.com/76fb38c2522777d92d66e7facf0a4f8f.jpg"],"type":0,"id":7111211,"ga_prefix":"091407","title":"趁欧洲遭遇难民潮，来说说争论了 25 年的观点：移民会影响本地劳动力市场吗？"},{"title":"多图多细节，教你鉴定不能买的事故车","ga_prefix":"091407","images":["http://pic1.zhimg.com/a1ea7b51670ce3fccd4f94cc92006dc8.jpg"],"multipic":true,"type":0,"id":7111374},{"images":["http://pic1.zhimg.com/61adf24e02a29481392805e1d0413810.jpg"],"type":0,"id":7111436,"ga_prefix":"091407","title":"个人所得税免征额是 3500，提得高一点，能缓解贫富差距吗？"},{"images":["http://pic3.zhimg.com/1d7e8662f842e799f8ae21bd7cb227de.jpg"],"type":0,"id":7110787,"ga_prefix":"091406","title":"瞎扯 · 如何正确地吐槽"}]
     */

    private String date;
    private List<StoriesEntity> stories;

    public void setDate(String date) {
        this.date = date;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public static class StoriesEntity {
        /**
         * images : ["http://pic3.zhimg.com/13631a43a0227d61a93ba336fc467b2a.jpg"]
         * type : 0
         * id : 7112228
         * ga_prefix : 091422
         * title : 深夜惊奇 · 前任后悔吗
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

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

        public void setImages(List<String> images) {
            this.images = images;
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

        public List<String> getImages() {
            return images;
        }
    }
}
