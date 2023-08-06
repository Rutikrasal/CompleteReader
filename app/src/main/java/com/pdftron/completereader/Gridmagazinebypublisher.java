package com.pdftron.completereader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdftron.completereader.Response.Magazines_By_PublisherResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Gridmagazinebypublisher extends BaseAdapter {
    ArrayList<Magazines_By_PublisherResponse.Datum> al_display_magazines;
    Context context;
    String isbnno,editor,publisher,bookid,price,discountprice,language,bookname,author,publishdate,ebookfile,category,description,bookfrontimage,bookindeximage,bookbackimage;

    public Gridmagazinebypublisher(Context context, ArrayList<Magazines_By_PublisherResponse.Datum> al_display_magazines) {
        this.context=context;
        this.al_display_magazines=al_display_magazines;
    }

    @Override
    public int getCount() {
        return al_display_magazines.size();
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
        tvbookid.setText("Magazine Id:"+al_display_magazines.get(i).getId());
        tvbookname.setText("Magazine Name:"+al_display_magazines.get(i).getMagazinesName());
        tvpublishername.setText("Publisher Name:"+al_display_magazines.get(i).getPublisher());
        tvprice.setText("Price:free ");
        tvauthorname.setText("Author Name:"+al_display_magazines.get(i).getAuthor());
        tvquantity.setVisibility(View.GONE);
        tvcategory.setText("Category"+al_display_magazines.get(i).getCopyrightPerson());

/*
        Picasso
                .with(context)
                .load(al_display_magazines.get(i).getBookImage())
                .into(imageView);
*/
        Picasso
                .get()
                .load(al_display_magazines.get(i).getBookImage())
                .into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookid=al_display_magazines.get(i).getId();

                bookname=al_display_magazines.get(i).getMagazinesName();
                editor=al_display_magazines.get(i).getAuthor();
/*
        publishdate=al_display_ebooks.get(i).getd();
*/
                ebookfile=al_display_magazines.get(i).getBookFile();
                category=al_display_magazines.get(i).getCategoryId();
                description=al_display_magazines.get(i).getDescription();
                price=al_display_magazines.get(i).getPrice();
                publisher=al_display_magazines.get(i).getPublisher();
                isbnno=al_display_magazines.get(i).getCopyrightPerson();

/*
                discountprice=al_display_magazines.get(i).getDiscount();
*/
/*
                language=al_display_magazines.get(i).getLang();
*/
                bookfrontimage=al_display_magazines.get(i).getBookImage();
                bookindeximage=al_display_magazines.get(i).getBookIndexPage();
                bookbackimage=al_display_magazines.get(i).getBookBackImage();
                Intent intent=new Intent(context,MagazineDetailsByPublisherActivity.class);
                intent.putExtra("bookid",bookid);

                intent.putExtra("bookname",bookname);
                intent.putExtra("editor",editor);
                intent.putExtra("publisher",publisher);
                intent.putExtra("ebookfile",ebookfile);
                intent.putExtra("category",category);
                intent.putExtra("description",description);
                intent.putExtra("price",price);
                intent.putExtra("isbnno",isbnno);
                //intent.putExtra("language",language);
                intent.putExtra("bookfrontimage",bookfrontimage);
                intent.putExtra("bookindeximage",bookindeximage);
                intent.putExtra("bookbackimage",bookbackimage);
                context.startActivity(intent);



            }
        });





        return v;
    }
}
