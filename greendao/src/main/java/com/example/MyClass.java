package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public  static void main(String args []){

        Schema  schema = new Schema(1,"com.lanou3g.mydazahui.GreenDao.bean");// 版本  数据库实体类地址
        addData(schema);
        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   private static void addData(Schema schema){
       // 指定生成的实体类,类名
       Entity entity = schema.addEntity("Person");
       entity.addStringProperty("name");// 标签
       entity.addStringProperty("arg");

    }
}
