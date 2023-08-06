package com.pdftron.completereader.Response;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class Uploadcart {
    public String bookimagelink,bookname,bookid,bookprice,TAG="TAG";
    public Uploadcart(Context context, ArrayList<String> cartlist)
    {

    }

    public Uploadcart(String str_bookid, String str_bookname, String str_discountprice, String str_bookfrontimage) {
        bookid=str_bookid;
bookname=str_bookname;
bookprice=str_discountprice;
bookimagelink=str_bookfrontimage;
        Log.d(TAG, "Uploadcart:str_bookname "+str_bookname);

    }

    public String getBookimagelink() {
        return bookimagelink;
    }

    public void setBookimagelink(String bookimagelink) {
        this.bookimagelink = bookimagelink;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBookprice() {
        return bookprice;
    }

    public void setBookprice(String bookprice) {
        this.bookprice = bookprice;
    }
}
