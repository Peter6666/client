package com.logic.client.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/28
 * @desc
 */

public class IdataNews  {

    public static final int PIC_NO = 1;
    public static final int PIC_ONLY = 2;
    public static final int PIC_MULTI = 3;

    boolean hasNext;
    String retcode;
    String appCode;
    String dataType;
    int pageToken;
    List<Idate> data;



    public class Idate implements MultiItemEntity{
        String commentCount;
        String posterScreenName;
        String posterId;
        String publishDateStr;
        String url;
        String title;
        String id;
        String content;
        String publishDate;
        boolean imageURLs;
        ArrayList<String> imageUrls;
        int itemType;

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }

        public String getPosterScreenName() {
            return posterScreenName;
        }

        public void setPosterScreenName(String posterScreenName) {
            this.posterScreenName = posterScreenName;
        }

        public String getPosterId() {
            return posterId;
        }

        public void setPosterId(String posterId) {
            this.posterId = posterId;
        }

        public String getPublishDateStr() {
            return publishDateStr;
        }

        public void setPublishDateStr(String publishDateStr) {
            this.publishDateStr = publishDateStr;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }

        public boolean isImageURLs() {
            return imageURLs;
        }

        public void setImageURLs(boolean imageURLs) {
            this.imageURLs = imageURLs;
        }

        public ArrayList<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(ArrayList<String> imageUrls) {
            this.imageUrls = imageUrls;
        }
    }

    public boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getPageToken() {
        return pageToken;
    }

    public void setPageToken(int pageToken) {
        this.pageToken = pageToken;
    }

    public List<Idate> getData() {
        return data;
    }

    public void setData(List<Idate> data) {
        this.data = data;
    }
}
