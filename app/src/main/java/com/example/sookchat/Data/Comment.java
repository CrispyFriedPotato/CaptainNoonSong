package com.example.sookchat.Data;

public class Comment {
    int imageid;
    int postid;
    String publisher;
    String comment;
    String date;

    public Comment(int imageid, int postid, String publisher, String comment, String date) {
        this.imageid = imageid;
        this.postid = postid;
        this.publisher = publisher;
        this.comment = comment;
        this.date = date;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "imageid=" + imageid +
                ", postid=" + postid +
                ", publisher='" + publisher + '\'' +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
