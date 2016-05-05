package com.xyb513951.mydazahui.bean;

import java.util.List;

/**
 * Created by dllo on 15/9/25.
 */
public class Groom {

    /**
     * items : [{"index":1,"recommenders":[{"bio":"优质时间轴信息源，匿名答题为主，即将以倒卖高利贷为生。","zhihu_url_token":"08664d187b1cf17c262b771777973d90","id":8180,"avatar":"http://pic2.zhimg.com/a0c827f01_m.jpg","name":"也之"},{"bio":"唯刀百辟，唯心不易","zhihu_url_token":"7ef43ab4abe532aa5b97f78bd2e71a4f","id":2412,"avatar":"http://pic2.zhimg.com/ee5eaeae9_m.jpg","name":"李傲文"}],"author":{"name":"胡半仙"}}]
     * editors : [{"bio":"树上的女爵","title":"主编","id":79,"avatar":"http://pic1.zhimg.com/0a6456810_m.jpg","name":"刘柯"}]
     * item_count : 1
     */

    private int item_count;
    private List<ItemsEntity> items;
    private List<EditorsEntity> editors;

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public void setEditors(List<EditorsEntity> editors) {
        this.editors = editors;
    }

    public int getItem_count() {
        return item_count;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public List<EditorsEntity> getEditors() {
        return editors;
    }

    public static class ItemsEntity {
        /**
         * index : 1
         * recommenders : [{"bio":"优质时间轴信息源，匿名答题为主，即将以倒卖高利贷为生。","zhihu_url_token":"08664d187b1cf17c262b771777973d90","id":8180,"avatar":"http://pic2.zhimg.com/a0c827f01_m.jpg","name":"也之"},{"bio":"唯刀百辟，唯心不易","zhihu_url_token":"7ef43ab4abe532aa5b97f78bd2e71a4f","id":2412,"avatar":"http://pic2.zhimg.com/ee5eaeae9_m.jpg","name":"李傲文"}]
         * author : {"name":"胡半仙"}
         */

        private int index;
        private AuthorEntity author;
        private List<RecommendersEntity> recommenders;

        public void setIndex(int index) {
            this.index = index;
        }

        public void setAuthor(AuthorEntity author) {
            this.author = author;
        }

        public void setRecommenders(List<RecommendersEntity> recommenders) {
            this.recommenders = recommenders;
        }

        public int getIndex() {
            return index;
        }

        public AuthorEntity getAuthor() {
            return author;
        }

        public List<RecommendersEntity> getRecommenders() {
            return recommenders;
        }

        public static class AuthorEntity {
            /**
             * name : 胡半仙
             */

            private String name;

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }

        public static class RecommendersEntity {
            /**
             * bio : 优质时间轴信息源，匿名答题为主，即将以倒卖高利贷为生。
             * zhihu_url_token : 08664d187b1cf17c262b771777973d90
             * id : 8180
             * avatar : http://pic2.zhimg.com/a0c827f01_m.jpg
             * name : 也之
             */

            private String bio;
            private String zhihu_url_token;
            private int id;
            private String avatar;
            private String name;

            public void setBio(String bio) {
                this.bio = bio;
            }

            public void setZhihu_url_token(String zhihu_url_token) {
                this.zhihu_url_token = zhihu_url_token;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBio() {
                return bio;
            }

            public String getZhihu_url_token() {
                return zhihu_url_token;
            }

            public int getId() {
                return id;
            }

            public String getAvatar() {
                return avatar;
            }

            public String getName() {
                return name;
            }
        }
    }

    public static class EditorsEntity {
        /**
         * bio : 树上的女爵
         * title : 主编
         * id : 79
         * avatar : http://pic1.zhimg.com/0a6456810_m.jpg
         * name : 刘柯
         */

        private String bio;
        private String title;
        private int id;
        private String avatar;
        private String name;

        public void setBio(String bio) {
            this.bio = bio;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBio() {
            return bio;
        }

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getName() {
            return name;
        }
    }
}
