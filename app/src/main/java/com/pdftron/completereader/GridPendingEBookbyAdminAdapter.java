package com.pdftron.completereader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdftron.completereader.Response.AcceptedEBooksByAdminResponse;
import com.pdftron.completereader.Response.PendingEBooksByAdminResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class GridPendingEBookbyAdminAdapter extends BaseAdapter {
    ArrayList<PendingEBooksByAdminResponse.Datum> al_display_mypendingebooks;
    Context context;
    int originalprice,discountprice,temp;
    double discount;
    String free,author,ebookfile,category,description,price,publishdate,strdiscountprice,bookid,bookname,bookfrontimage,bookindeximage,bookbackimage,isbn,publisher,date,language,strbookfree,TAG="TAG",toolbarid="e-book",str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;

    public GridPendingEBookbyAdminAdapter(Context context, ArrayList<PendingEBooksByAdminResponse.Datum> al_display_mypendingebooks) {
        this.context=context;
        this.al_display_mypendingebooks=al_display_mypendingebooks;

    }

    @Override
    public int getCount() {
        return al_display_mypendingebooks.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
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
        tvauthornmae.setText(al_display_mypendingebooks.get(i).getAuthor());

        textView1.setPaintFlags(textView1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        originalprice= Integer.parseInt(al_display_mypendingebooks.get(i).getPrice());
        discountprice= Integer.parseInt(al_display_mypendingebooks.get(i).getDiscount());
        discount = originalprice-discountprice;
        temp= (int) ((discount/originalprice)*100);
        textView3.setText(String.valueOf(temp)+"%");

        textView.setText(al_display_mypendingebooks.get(i).getEbookName());
        if (al_display_mypendingebooks.get(i).getIsFree().matches("1"))
        {
            counter_ValuePanel.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            textView4.setVisibility(View.VISIBLE);
        }
        else if (al_display_mypendingebooks.get(i).getIsFree().matches("0"))
        {
            textView4.setVisibility(View.GONE);

            textView1.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            counter_ValuePanel.setVisibility(View.VISIBLE);
        }

        textView1.setText("₹"+al_display_mypendingebooks.get(i).getPrice());
        textView2.setText("₹"+al_display_mypendingebooks.get(i).getDiscount());
        Picasso
                .get()
                .load(al_display_mypendingebooks.get(i).getBookImage())
                .into(imageView);

    /*    Picasso
                .with(context)
                .load(al_display_studentebook.get(i).getBookImage())
                .into(imageView);
*/
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookid=al_display_mypendingebooks.get(i).getId();

                bookname=al_display_mypendingebooks.get(i).getEbookName();
                author=al_display_mypendingebooks.get(i).getAuthor();
/*
        publishdate=al_display_ebooks.get(i).getd();
*/
                isbn=al_display_mypendingebooks.get(i).getCopyrightPerson();
                publisher=al_display_mypendingebooks.get(i).getPublisher();
                ebookfile=al_display_mypendingebooks.get(i).getBookFile();
                category=al_display_mypendingebooks.get(i).getCategoryId();
                description=al_display_mypendingebooks.get(i).getDescription();
                price=al_display_mypendingebooks.get(i).getPrice();
                strdiscountprice=al_display_mypendingebooks.get(i).getDiscount();
                language=al_display_mypendingebooks.get(i).getLang();
                bookfrontimage=al_display_mypendingebooks.get(i).getBookImage();
                bookindeximage=al_display_mypendingebooks.get(i).getBookIndexPage();
                bookbackimage=al_display_mypendingebooks.get(i).getBookBackImage();
                free=al_display_mypendingebooks.get(i).getIsFree();
                Intent intent=new Intent(context,EBookDetailsByPublisherActivity.class);
                intent.putExtra("bookid",bookid);
                intent.putExtra("free",free);

                intent.putExtra("bookname",bookname);
                intent.putExtra("author",author);
                intent.putExtra("publishdate",publishdate);
                intent.putExtra("ebookfile",ebookfile);
                intent.putExtra("category",category);
                intent.putExtra("description",description);
                intent.putExtra("price",price);
                intent.putExtra("discountprice",strdiscountprice);
                intent.putExtra("language",language);
                intent.putExtra("bookfrontimage",bookfrontimage);
                intent.putExtra("bookindeximage",bookindeximage);
                intent.putExtra("bookbackimage",bookbackimage);
                intent.putExtra("publisher",publisher);
                intent.putExtra("isbn",isbn);

                context.startActivity(intent);



            }
        });

        return v;

    }
}
