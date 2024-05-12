package com.example.noticeboard;

public class NoticeData {
    String title,image,time,data,key;

    public NoticeData() {
    }

    public NoticeData(String title, String image, String time, String data, String key) {
        this.title = title;
        this.image = image;
        this.time = time;
        this.data = data;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
