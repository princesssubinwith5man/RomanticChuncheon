package com.example.practice;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private int iconDrawable;
    private String Like;
    private String addressStr;
    private String CenterNameStr;
    //final private double lat, lng;

    public void setCenterName(String title) {
        CenterNameStr = title;
    }

    public void setLike(String content) {
        Like = content;
    }

    public void setIcon(int icon) {
        iconDrawable = icon;
    }

    public void setAddress(String address) {
        addressStr = address;
    }

    public int getIcon() {
        return this.iconDrawable;
    }


    public String getLike() {
        return this.Like;
    }

    public String getCenterNameStr() {
        return this.CenterNameStr;
    }

    public String getAddressStr() {
        return this.addressStr;
    }
}
