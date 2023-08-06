package com.pdftron.completereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DBcartDataHelper extends SQLiteOpenHelper {

    private static String dbname = "cart.db";
    private static String book_name = "bookname";
    private static String book_id = "bookid";
    private static String book_price = "bookprice";
    private static String book_imagelink = "bookimagelink";
    private static String book_quantity = "bookquantity";
    private static String book_totalquantity = "totalbookquantity";



    public static String table_name = "CartData";
    public static String col_id = "ID";
    SQLiteDatabase sqLiteDatabase;



    public DBcartDataHelper(Context context) {
        super(context,dbname,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "create Table " + table_name +
                " (" + col_id + " integer primary key autoincrement,"
                + book_id + " text,"
                + book_name + " text,"
                + book_price + " text,"
                + book_quantity + " text,"
                + book_imagelink + " text,"

                + book_totalquantity + " text" + ")";


        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + table_name);
        onCreate(sqLiteDatabase);

    }

    public boolean insertdata(String str_bookid, String str_bookname, String str_discountprice, String str_bookquantity, String str_bookfrontimage,String quantity) {
         sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(book_id, str_bookid);
        contentValues.put(book_name, str_bookname);
        contentValues.put(book_price, str_discountprice);
        contentValues.put(book_quantity, str_bookquantity);

        contentValues.put(book_imagelink, str_bookfrontimage);
        contentValues.put(book_totalquantity, quantity);

        long result = sqLiteDatabase.insert(table_name, null, contentValues);
        if (result == -1) {
            Log.d(TAG, "insertdata:false ");
            return false;
        } else {
            Log.d(TAG, "insertdata: true");
            return true;
        }



    }

    public Cursor getcartListContents() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor data = database.rawQuery("SELECT * FROM " + table_name, null);
        return data;
    }

    public boolean deletecartdata(Integer bookid) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + table_name + " WHERE " + book_id + "= '" + bookid + "'");
        sqLiteDatabase.close();
        return true;

      //  return sqLiteDatabase.delete("table_name","book_id=?",new String[]{bookid.toString()});

    }

    public boolean updatecart(String bookid, String bookname, String bookprice, String bookquantity, String bookimagelink) {
    sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(book_name, bookname);
        contentValues.put(book_price, bookprice);
        contentValues.put(book_quantity, bookquantity);
        contentValues.put(book_imagelink, bookimagelink);
        sqLiteDatabase.update(table_name,contentValues, book_id+ " =? ",new String[]{String.valueOf(bookid)} );

        return true;

    }

    

    /*public boolean deletecartdata(String drugname, String drugtype, String drugamount, String instructions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table_name + " WHERE " + drug_name + "= '" + drugname + "'");
        db.close();
        return true;


    }*/


}
