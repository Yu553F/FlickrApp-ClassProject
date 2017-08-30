package com.example.josepablo.flickrapp;

/**
 * Created by Jose Pablo on 8/28/2017.
 */

public class Category {
    private String sImage;
    private String sText;
    private String sTags;

    public Category(String image, String text, String tags){
        if(image == null || text == null){
            throw new NullPointerException();
        }
        this.sImage = image;
        this.sText = text;
        this.sTags = tags;
    }

    public String getsImage() {
        return sImage;
    }

    public String getsText() {
        return sText;
    }

    public String getsTags() {
        return sTags;
    }
}
