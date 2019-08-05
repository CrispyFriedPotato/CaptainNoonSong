package com.example.sookchat.Data;

import java.util.Comparator;

public class Data {

    private int catid;
    private String title;
    private String content;
    private String filename;
    private String views;

    public Data(int catid, String title, String content, String filename, String views) {
        this.catid = catid;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.views = views;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public static Comparator<Data> ById = new Comparator<Data>() {
        @Override
        public int compare(Data one, Data two) {
            return Integer.valueOf(one.catid).compareTo(Integer.valueOf(two.catid));
        }
    };


    public static Comparator<Data> ByViewers = new Comparator<Data>() {
        @Override
        public int compare(Data one, Data two) {
            return -Integer.valueOf(one.views).compareTo(Integer.valueOf(two.views));
        }
    };

    @Override
    public String toString() {
        return "Data{" +
                "catid=" + catid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", filename='" + filename + '\'' +
                ", views='" + views + '\'' +
                '}';
    }
}