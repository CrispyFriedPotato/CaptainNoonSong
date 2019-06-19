package com.example.sookchat;

public class ImageItem {
    private int imageid;
    private int catid;
    private String filename;
    private String memo;

    public ImageItem(int imageid, int catid, String filename, String memo) {
        this.imageid = imageid;
        this.catid = catid;
        this.filename = filename;
        this.memo = memo;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
