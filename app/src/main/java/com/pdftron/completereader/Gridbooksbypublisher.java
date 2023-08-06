package com.pdftron.completereader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdftron.completereader.Response.Books_By_PublisherResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Gridbooksbypublisher extends BaseAdapter {
    ArrayList<Books_By_PublisherResponse.Datum> al_display_books;
    Context context;
    String publisher,bookid,price,discountprice,language,bookname,author,publishdate,isbnno,category,quantity,description,bookfrontimage,bookindeximage,bookbackimage;

    public Gridbooksbypublisher(Context context, ArrayList<Books_By_PublisherResponse.Datum> al_display_books) {
        this.context=context;
        this.al_display_books=al_display_books;
    }

    @Override
    public int getCount() {
        return al_display_books.size();
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
            v = inflater.inflate(R.layout.publisherbookdesign, null);
        }
        else {
            v = (View) convertView;
        }
        ImageView imageView = v.findViewById(R.id.img_book);
        final TextView tvbookid = v.findViewById(R.id.tv_bookid);
        TextView tvbookname = v.findViewById(R.id.tv_bookname);
        final TextView tvauthorname = v.findViewById(R.id.tv_authorname);
        final TextView tvpublishername = v.findViewById(R.id.tv_publishername);
        final TextView tvprice = v.findViewById(R.id.tv_price);
        final TextView tvquantity = v.findViewById(R.id.tv_quantity);
        final TextView tvcategory = v.findViewById(R.id.tv_category);
        tvbookid.setText("Book Id:"+al_display_books.get(i).getId());
        tvbookname.setText("Book Name:"+al_display_books.get(i).getBookName());
        tvpublishername.setText("Publisher Name:"+al_display_books.get(i).getPublisher());
        tvprice.setText("Price:₹"+al_display_books.get(i).getPrice());
        tvauthorname.setText("Author Name:"+al_display_books.get(i).getAuthor());
        tvquantity.setText("Quantity:"+al_display_books.get(i).getQuantity());
        tvcategory.setText("Category"+al_display_books.get(i).getCopyrightname());

/*
        Picasso
                .with(context)
                .load(al_display_books.get(i).getBookImage())
                .into(imageView);
*/
        Picasso
                .get()
                .load(al_display_books.get(i).getBookImage())
                .into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookid=al_display_books.get(i).getId();
                bookname=al_display_books.get(i).getBookName();
                author=al_display_books.get(i).getAuthor();
                publishdate=al_display_books.get(i).getCreateDate();
                isbnno=al_display_books.get(i).getCopyrightname();
                category=al_display_books.get(i).getCategoryId();
                quantity=al_display_books.get(i).getQuantity();
                description=al_display_books.get(i).getDescription();
                price=al_display_books.get(i).getPrice();
                discountprice=al_display_books.get(i).getDiscount();
                language=al_display_books.get(i).getLang();
                bookfrontimage=al_display_books.get(i).getBookImage();
                bookindeximage=al_display_books.get(i).getBookIndexImage();
                bookbackimage=al_display_books.get(i).getBookBackImage();
                publisher=al_display_books.get(i).getPublisher();
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
                intent.putExtra("discountprice",discountprice);
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
