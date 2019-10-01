package com.kutayeen.comics;

import android.graphics.Bitmap;


public class Item {

    String bookName;
    Bitmap bookimg;
    String entries;

    public Item(String bookName,Bitmap bookimg, String entries)
    {
        this.bookName=bookName;
        this.bookimg=bookimg;
        this.entries=entries;
    }
    public String getbookName()
    {
        return bookName;
    }
    public Bitmap getbookimg()
    {
        return bookimg;
    }
    public String getentries(){
        return entries;
    }
}
