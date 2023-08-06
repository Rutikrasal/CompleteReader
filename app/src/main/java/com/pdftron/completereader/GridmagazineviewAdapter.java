package com.pdftron.completereader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdftron.completereader.Response.MagazinesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class GridmagazineviewAdapter extends BaseAdapter {
    ArrayList<MagazinesResponse.Datum> al_display_magazines;
    Context context;
    int originalprice,discountprice,temp;
    double discount;
    String toolbarid="magzine",language,date,strbooklink,strbookfree,TAG="TAG",str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;

    public GridmagazineviewAdapter(Context context, ArrayList<MagazinesResponse.Datum> al_display_magazines) {
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
            v = inflater.inflate(R.layout.gridbookdesign, null);
        }
        else {
            v = (View) convertView;
        }
        ImageView imageView = v.findViewById(R.id.img_book);
        TextView textView = v.findViewById(R.id.tv_bookname);
        TextView textView1 = v.findViewById(R.id.tv_book_price);
        TextView textView2 = v.findViewById(R.id.tv_book_discountprice);
        TextView textView3=v.findViewById(R.id.tv_discount_percent);
        FrameLayout frameLayout=v.findViewById(R.id.counterValuePanel);
        frameLayout.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
/*
        textView1.setPaintFlags(textView1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
*/
        final TextView tvauthornmae=v.findViewById(R.id. tv_authorname);
        tvauthornmae.setText(al_display_magazines.get(i).getAuthor());

        textView.setText(al_display_magazines.get(i).getMagazinesName());
        textView1.setText("₹"+al_display_magazines.get(i).getPrice());
        //textView2.setText(al_display_magazines.get(i).getDiscount());
        Picasso
                .get()
                .load(al_display_magazines.get(i).getBookImage())
                .into(imageView);

    /*    Picasso
                .with(context)
                .load(al_display_magazines.get(i).getBookImage())
                .into(imageView);
    */    imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_bookid=al_display_magazines.get(i).getId().toString();
                str_bookname=al_display_magazines.get(i).getMagazinesName();
                str_authorname=al_display_magazines.get(i).getAuthor();
                str_publisher=al_display_magazines.get(i).getPublisher();
                str_isbn=al_display_magazines.get(i).getCopyrightPerson();
                //str_price=al_display_magazines.get(i).getPrice().toString();
                str_description=al_display_magazines.get(i).getDescription().toString();
                str_frontbookimage=al_display_magazines.get(i).getBookImage();
                str_indexbookimage=al_display_magazines.get(i).getBookIndexPage();
                str_backbookimage=al_display_magazines.get(i).getBookBackImage();
                strbooklink=al_display_magazines.get(i).getBookFile();
                strbookfree=al_display_magazines.get(i).getIsFree();
                Intent intent=new Intent(context, BookDetailsActivity.class);
                intent.putExtra("toolbarid",toolbarid);

                intent.putExtra("bookid",str_bookid);
                intent.putExtra("bookname",str_bookname);
                intent.putExtra("authorname",str_authorname);
                intent.putExtra("publisher",str_publisher);
                intent.putExtra("isbn",str_isbn);
                // intent.putExtra("price",str_price);
                intent.putExtra("description",str_description);
                intent.putExtra("frontbookimage",str_frontbookimage);
                intent.putExtra("indexbookimage",str_indexbookimage);
                intent.putExtra("backbookimage",str_backbookimage);
                intent.putExtra("bookfree",strbookfree);
                intent.putExtra("booklink",strbooklink);

                context.startActivity(intent);

            }
        });
       return v;

    }
}
