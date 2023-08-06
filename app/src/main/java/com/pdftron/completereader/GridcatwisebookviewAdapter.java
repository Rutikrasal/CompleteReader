package com.pdftron.completereader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdftron.completereader.Response.CategorywiseBooksResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class GridcatwisebookviewAdapter extends BaseAdapter {
    ArrayList<CategorywiseBooksResponse.Datum> al_display_catwisebook;
    Context context;
    int originalprice,discountprice,temp;
    double discount;
    String TAG="TAG",toolbarid="book";
    String strbookfree="0",quantity,str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;


    public GridcatwisebookviewAdapter(Context context, ArrayList<CategorywiseBooksResponse.Datum> al_display_catwisebook) {
        this.context=context;
        this.al_display_catwisebook=al_display_catwisebook;

    }

    @Override
    public int getCount() {
        return al_display_catwisebook.size();
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
        final TextView textView2 = v.findViewById(R.id.tv_book_discountprice);
        final TextView textView3=v.findViewById(R.id.tv_discount_percent);
        final TextView tvquantity=v.findViewById(R.id. tv_book_quantity);
        final TextView tvauthor=v.findViewById(R.id. tv_authorname);
        tvquantity.setText(al_display_catwisebook.get(i).getQuantity());
        tvauthor.setText(al_display_catwisebook.get(i).getAuthor());

        textView1.setPaintFlags(textView1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        originalprice= Integer.parseInt(al_display_catwisebook.get(i).getPrice());
        discountprice= Integer.parseInt(al_display_catwisebook.get(i).getDiscount());
        discount = originalprice-discountprice;
        temp= (int) ((discount/originalprice)*100);
        textView3.setText(String.valueOf(temp)+"%");

        textView.setText(al_display_catwisebook.get(i).getBookName());
        textView1.setText("₹"+al_display_catwisebook.get(i).getPrice());
        textView2.setText("₹"+al_display_catwisebook.get(i).getDiscount());

       /* Picasso
                .with(context)
                .load(al_display_catwisebook.get(i).getBookImage())
                .into(imageView);
*/
        Picasso
                .get()
                .load(al_display_catwisebook.get(i).getBookImage())
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_temp=textView3.getText().toString();
                str_bookid=al_display_catwisebook.get(i).getId().toString();
                str_bookname=textView.getText().toString();
                quantity=tvquantity.getText().toString();

                str_authorname=al_display_catwisebook.get(i).getAuthor();
                str_publisher=al_display_catwisebook.get(i).getPublisher();
                str_isbn=al_display_catwisebook.get(i).getCopyrightname();
                str_price=al_display_catwisebook.get(i).getPrice();
                str_discountprice=al_display_catwisebook.get(i).getDiscount();
                str_description=al_display_catwisebook.get(i).getDescription().toString();
                str_frontbookimage=al_display_catwisebook.get(i).getBookImage();
                Log.d(TAG, "onClick:str_frontbookimage "+str_frontbookimage);
                str_indexbookimage=al_display_catwisebook.get(i).getBookIndexImage();
                str_backbookimage=al_display_catwisebook.get(i).getBookBackImage();
                Intent intent=new Intent(context,BookDetailsActivity.class);
                intent.putExtra("toolbarid",toolbarid);
                intent.putExtra("bookid",str_bookid);
                intent.putExtra("bookname",str_bookname);
                Log.d(TAG, "onClick:str_bookname "+str_bookname);
                intent.putExtra("authorname",str_authorname);
                intent.putExtra("publisher",str_publisher);
                intent.putExtra("isbn",str_isbn);
                intent.putExtra("price",str_price);
                intent.putExtra("discount",str_temp);
                intent.putExtra("discountprice",str_discountprice);
                intent.putExtra("description",str_description);
                intent.putExtra("frontbookimage",str_frontbookimage);
                intent.putExtra("indexbookimage",str_indexbookimage);
                intent.putExtra("backbookimage",str_backbookimage);
                intent.putExtra("quantity",quantity);
                intent.putExtra("bookfree",strbookfree);


                context.startActivity(intent);


            }
        });

       return v;

    }
}
