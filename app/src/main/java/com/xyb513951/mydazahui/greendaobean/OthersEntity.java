package com.xyb513951.mydazahui.greendaobean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import java.io.Serializable;

/**
 * Entity mapped to table "OTHERS_ENTITY".
 */
public class OthersEntity implements Serializable {

    private String thumbnail;
    private String description;
    private String name;
    private Integer color;
    private Integer id;
    private Integer newsThemeId;

    public OthersEntity() {
    }

    public OthersEntity(String thumbnail, String description, String name, Integer color, Integer id, Integer newsThemeId) {
        this.thumbnail = thumbnail;
        this.description = description;
        this.name = name;
        this.color = color;
        this.id = id;
        this.newsThemeId = newsThemeId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNewsThemeId() {
        return newsThemeId;
    }

    public void setNewsThemeId(Integer newsThemeId) {
        this.newsThemeId = newsThemeId;
    }

}
