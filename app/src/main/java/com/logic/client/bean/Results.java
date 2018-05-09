package com.logic.client.bean;

import java.util.List;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/24
 * @desc
 */

public class Results {


    /**
     "_id": "5a81333c421aa90d264d0eba",
     "createdAt": "2018-02-12T14:25:00.318Z",
     "desc": "A slider widget with a popup bubble displaying the precise value selected.",
     "images": [
     "http://img.gank.io/fe3c723f-643d-4466-91d5-86d9ed4ca88e"
     ],
     "publishedAt": "2018-02-22T08:24:35.209Z",
     "source": "web",
     "type": "Android",
     "url": "https://github.com/Ramotion/fluid-slider-android",
     "used": true,
     "who": "Alex Mikhnev"
     */

    private String _id;
    private String createdAt;
    private String desc;
    private List<String> images;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
