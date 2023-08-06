package com.pdftron.completereader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdftron.completereader.Response.PublisherAcceptedBooksResponse;
import com.pdftron.completereader.Response.StudenteBookResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class GridPendingbookviewAdapter extends BaseAdapter {
    ArrayList<PublisherAcceptedBooksResponse.Datum> al_display_mybookorder;
    Context context;
    int originalprice,discountprice,temp;
    double discount;
    String date,language,strbookfree,TAG="TAG",toolbarid="e-book",str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;

    public GridPendingbookviewAdapter(Context context, ArrayList<PublisherAcceptedBooksResponse.Datum> al_display_mybookorder) {
        this.context=context;
        this.al_display_mybookorder=al_display_mybookorder;


    }

    @Override
    public int getCount() {
        return al_display_mybookorder.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View v;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.gridbookdesign, null);
        }
        else {
            v = (View) convertView;
        }
        ImageView imageView = v.findViewById(R.id.img_book);
        final TextView textView = v.findViewById(R.id.tv_bookname);
        TextView textView1 = v.findViewById(R.id.tv_book_price);
        TextView textView2 = v.findViewById(R.id.tv_book_discountprice);
        final TextView textView3=v.findViewById(R.id.tv_discount_percent);
        final TextView textView4=v.findViewById(R.id.tv_book_free);
        FrameLayout counter_ValuePanel=v.findViewById(R.id.counterValuePanel);
        final TextView tvauthornmae=v.findViewById(R.id. tv_authorname);
        tvauthornmae.setText(al_display_mybookorder.get(i).getAuthor());

        textView1.setPaintFlags(textView1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        originalprice= Integer.parseInt(al_display_mybookorder.get(i).getPrice());
        discountprice= Integer.parseInt(al_display_mybookorder.get(i).getDiscount());
        discount = originalprice-discountprice;
        temp= (int) ((discount/originalprice)*100);
        textView3.setText(String.valueOf(temp)+"%");

        textView.setText(al_display_mybookorder.get(i).getBookName());


        textView1.setText("₹"+al_display_mybookorder.get(i).getPrice());
        textView2.setText("₹"+al_display_mybookorder.get(i).getDiscount());
        Picasso
                .get()
                .load(al_display_mybookorder.get(i).getBookImage())
                .into(imageView);

    /*    Picasso
                .with(context)
                .load(al_display_studentebook.get(i).getBookImage())
                .into(imageView);
*/


        return v;
    }
}
