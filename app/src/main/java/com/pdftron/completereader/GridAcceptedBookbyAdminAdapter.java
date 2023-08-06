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

import com.pdftron.completereader.Response.AcceptedBooksByAdminResponse;
import com.pdftron.completereader.Response.PublisherEbooksStatusResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class GridAcceptedBookbyAdminAdapter extends BaseAdapter {
    ArrayList<AcceptedBooksByAdminResponse.Datum> al_display_myacceptedbooks;
    Context context;
    int originalprice,discountprice,temp;
    double discount;
    String  strdiscount,publisher,bookbackimage,bookindeximage,bookfrontimage,price,description,quantity,category,isbnno,publishdate,author,bookid,bookname,date,language,strbookfree,TAG="TAG",toolbarid="e-book",str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;

    public GridAcceptedBookbyAdminAdapter(Context context, ArrayList<AcceptedBooksByAdminResponse.Datum> al_display_myacceptedbooks) {
        this.context=context;
        this.al_display_myacceptedbooks=al_display_myacceptedbooks;

    }

    @Override
    public int getCount() {
        return al_display_myacceptedbooks.size();
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
        tvauthornmae.setText(al_display_myacceptedbooks.get(i).getAuthor());

        textView1.setPaintFlags(textView1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        originalprice= Integer.parseInt(al_display_myacceptedbooks.get(i).getPrice());
        discountprice= Integer.parseInt(al_display_myacceptedbooks.get(i).getDiscount());
        discount = originalprice-discountprice;
        temp= (int) ((discount/originalprice)*100);
        textView3.setText(String.valueOf(temp)+"%");

        textView.setText(al_display_myacceptedbooks.get(i).getBookName());


        textView1.setText("₹"+al_display_myacceptedbooks.get(i).getPrice());
        textView2.setText("₹"+al_display_myacceptedbooks.get(i).getDiscount());
        Picasso
                .get()
                .load(al_display_myacceptedbooks.get(i).getBookImage())
                .into(imageView);

    /*    Picasso
                .with(context)
                .load(al_display_studentebook.get(i).getBookImage())
                .into(imageView);
*/
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookid=al_display_myacceptedbooks.get(i).getId();
                bookname=al_display_myacceptedbooks.get(i).getBookName();
                author=al_display_myacceptedbooks.get(i).getAuthor();
                publishdate=al_display_myacceptedbooks.get(i).getCreateDate();
                isbnno=al_display_myacceptedbooks.get(i).getCopyrightname();
                category=al_display_myacceptedbooks.get(i).getCategoryId();
                quantity=al_display_myacceptedbooks.get(i).getQuantity();
                description=al_display_myacceptedbooks.get(i).getDescription();
                price=al_display_myacceptedbooks.get(i).getPrice();
                strdiscount=al_display_myacceptedbooks.get(i).getDiscount();
                language=al_display_myacceptedbooks.get(i).getLang();
                bookfrontimage=al_display_myacceptedbooks.get(i).getBookImage();
                bookindeximage=al_display_myacceptedbooks.get(i).getBookIndexImage();
                bookbackimage=al_display_myacceptedbooks.get(i).getBookBackImage();
                publisher=al_display_myacceptedbooks.get(i).getPublisher();
                Intent intent=new Intent(context,BookDetailsByPublisherActivity.class);
                intent.putExtra("bookid",bookid);

                intent.putExtra("bookname",bookname);
                intent.putExtra("author",author);
                intent.putExtra("publishdate",publishdate);
                intent.putExtra("isbnno",isbnno);
                intent.putExtra("category",category);
                intent.putExtra("quantity",quantity);
                intent.putExtra("description",description);
                intent.putExtra("price",price);
                intent.putExtra("discountprice",strdiscount);
                intent.putExtra("language",language);
                intent.putExtra("bookfrontimage",bookfrontimage);
                intent.putExtra("bookindeximage",bookindeximage);
                intent.putExtra("bookbackimage",bookbackimage);
                intent.putExtra("publisher",publisher);

                context.startActivity(intent);







            }
        });


        return v;

    }
}
