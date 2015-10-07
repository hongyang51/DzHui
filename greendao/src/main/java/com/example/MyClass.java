package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String args[]) {

        Schema schema = new Schema(1, "com.lanou3g.mydazahui.greendaobean");// 版本  数据库实体类地址
        addData(schema);
        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addData(Schema schema) {
        // 指定生成的实体类,类名
        Entity entity = schema.addEntity("GuidePage");
        entity.addStringProperty("text");// 标签
        entity.addStringProperty("img");

        // 指定生成的实体类,类名
        Entity Latestentity = schema.addEntity("StoriesEntity");
        Latestentity.addStringProperty("title");// 标签
        Latestentity.addStringProperty("ga_prefix");
        Latestentity.addStringProperty("multipic");
        Latestentity.addStringProperty("type");
        Latestentity.addIntProperty("id");

        Entity TopStoriesEntity = schema.addEntity("TopStoriesEntity");
        TopStoriesEntity.addStringProperty("title");// 标签
        TopStoriesEntity.addStringProperty("ga_prefix");
        TopStoriesEntity.addStringProperty("image");
        TopStoriesEntity.addStringProperty("type");
        TopStoriesEntity.addIntProperty("id");

        Entity images = schema.addEntity("images");
        images.addStringProperty("images");

        Entity user = schema.addEntity("User");
        user.addStringProperty("name");
        user.addStringProperty("profile_image_url");
        user.addStringProperty("platform");

        Entity collection = schema.addEntity("Collection");
        collection.addStringProperty("user_name");
        collection.addStringProperty("title");
        collection.addStringProperty("body");
        collection.addIntProperty("newsId");


//
//        // 指定生成的实体类,类名
//        Entity Theme_daoentity = schema.addEntity("OthersEntity");
//        Theme_daoentity.addStringProperty("color");// 标签
//        Theme_daoentity.addStringProperty("thumbnail");
//        Theme_daoentity.addStringProperty("description");// 标签
//        Theme_daoentity.addStringProperty("id");
//        Theme_daoentity.addStringProperty("name");// 标签
//        // 指定生成的实体类,类名
//        Entity ThemeNews_daoentity = schema.addEntity("ThemeNews_dao");
//        ThemeNews_daoentity.addStringProperty("description");// 标签
//        ThemeNews_daoentity.addStringProperty("background");
//        ThemeNews_daoentity.addStringProperty("color");// 标签
//        ThemeNews_daoentity.addStringProperty("name");
//        ThemeNews_daoentity.addStringProperty("image");// 标签
//        ThemeNews_daoentity.addStringProperty("image_source");


    }
}
