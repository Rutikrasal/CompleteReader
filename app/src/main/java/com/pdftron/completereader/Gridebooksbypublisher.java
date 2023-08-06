package com.pdftron.completereader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdftron.completereader.Response.EBooks_By_PublisherResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Gridebooksbypublisher extends BaseAdapter {
    ArrayList<EBooks_By_PublisherResponse.Datum> al_display_ebooks;
    Context context;
    String isbn,publisher,free,bookid,price,discountprice,language,bookname,author,publishdate,ebookfile,category,description,bookfrontimage,bookindeximage,bookbackimage;

    public Gridebooksbypublisher(Context context, ArrayList<EBooks_By_PublisherResponse.Datum> al_display_ebooks) {
        this.context=context;
        this.al_display_ebooks=al_display_ebooks;
    }

    @Override
    public int getCount() {
        return al_display_ebooks.size();
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
        tvbookid.setText("E-Book Id:"+al_display_ebooks.get(i).getId());
        tvbookname.setText("E-Book Name:"+al_display_ebooks.get(i).getEbookName());
        tvpublishername.setText("Publisher Name:"+al_display_ebooks.get(i).getPublisher());
        if (al_display_ebooks.get(i).getIsFree().matches("1"))
        {
            tvprice.setText("Price:Free");

        }
        else if (al_display_ebooks.get(i).getIsFree().matches("0"))
        {
            tvprice.setText("Price:₹"+al_display_ebooks.get(i).getPrice());

        }
        tvauthorname.setText("Author Name:"+al_display_ebooks.get(i).getAuthor());
        tvquantity.setVisibility(View.GONE);
        tvcategory.setText("Category"+al_display_ebooks.get(i).getCopyrightPerson());
/*
        Picasso
                .with(context)
                .load(al_display_ebooks.get(i).getBookImage())
                .into(imageView);*/

        Picasso
                .get()
                .load(al_display_ebooks.get(i).getBookImage())
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        bookid=al_display_ebooks.get(i).getId();

        bookname=al_display_ebooks.get(i).getEbookName();
        author=al_display_ebooks.get(i).getAuthor();
/*
        publishdate=al_display_ebooks.get(i).getd();
*/
isbn=al_display_ebooks.get(i).getCopyrightPerson();
publisher=al_display_ebooks.get(i).getPublisher();
        ebookfile=al_display_ebooks.get(i).getBookFile();
        category=al_display_ebooks.get(i).getCategoryId();
        description=al_display_ebooks.get(i).getDescription();
        price=al_display_ebooks.get(i).getPrice();
        discountprice=al_display_ebooks.get(i).getDiscount();
        language=al_display_ebooks.get(i).getLang();
        bookfrontimage=al_display_ebooks.get(i).getBookImage();
        bookindeximage=al_display_ebooks.get(i).getBookIndexPage();
        bookbackimage=al_display_ebooks.get(i).getBookBackImage();
        free=al_display_ebooks.get(i).getIsFree();
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
        intent.putExtra("discountprice",discountprice);
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
