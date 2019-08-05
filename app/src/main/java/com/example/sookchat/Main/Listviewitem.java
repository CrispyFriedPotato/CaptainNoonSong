package com.example.sookchat.Main;

import android.graphics.drawable.Drawable;

public class Listviewitem {
    private int icon ;
    private String name;
    private String major;
    public int getIcon(){return icon;}
    public String getName(){return name;}
    public String getMajor(){return major;}
    public Listviewitem(int icon, String name,String major){
        this.icon=icon;
        this.name=name;
        this.major=major;
    }
}
