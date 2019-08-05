package com.example.sookchat.Watson;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Message implements Serializable {
  String id, message;
  String options;
  String url;

  public Message() {
  }

  public Message(String id, String message, String url) {
    this.id = id;
    this.message = message;
    this.url=url;

  }

  public String getId() {

    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMessage() {

    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getOptions(){
    return options;

  }

  public void setOptions(String options) {

    this.options=options;
  }

  public String getUrl(){
    return url;
  }

  public void setUrl(String url){
    this.url=url;
  }

  public String getCreatedAt(){
    Date rightNow = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(
            "hh:mm");
    String timeString = formatter.format(rightNow);
    return timeString;
  }
}

