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

import com.pdftron.completereader.Response.StudentTexteNotesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class GridstudenttextenotesviewAdapter extends BaseAdapter {
    ArrayList<StudentTexteNotesResponse.Datum> al_display_studentTexteNotes;
    Context context;
    int originalprice,discountprice,temp;
    double discount;
    String strbooklink,date,language,strbookfree,TAG="TAG",toolbarid="e-note",str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;

    public GridstudenttextenotesviewAdapter(Context context, ArrayList<StudentTexteNotesResponse.Datum> al_display_studentTexteNotes) {
        this.context=context;
        this.al_display_studentTexteNotes=al_display_studentTexteNotes;

    }

    @Override
    public int getCount() {
        return al_display_studentTexteNotes.size();
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
        tvauthornmae.setText(al_display_studentTexteNotes.get(i).getAuthor());



        textView.setText(al_display_studentTexteNotes.get(i).getEbookName());
        if (al_display_studentTexteNotes.get(i).getIsFree().matches("1"))
        {
            counter_ValuePanel.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            textView4.setVisibility(View.VISIBLE);
        }
        else if (al_display_studentTexteNotes.get(i).getIsFree().matches("0"))
        {
            textView1.setPaintFlags(textView1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            textView4.setVisibility(View.GONE);

            textView1.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            counter_ValuePanel.setVisibility(View.VISIBLE);
            originalprice= Integer.parseInt(al_display_studentTexteNotes.get(i).getPrice());
            discountprice= Integer.parseInt(al_display_studentTexteNotes.get(i).getDiscount());
            discount = originalprice-discountprice;
            temp= (int) ((discount/originalprice)*100);
            textView3.setText(String.valueOf(temp)+"%");

            textView1.setText("₹"+al_display_studentTexteNotes.get(i).getPrice());
            textView2.setText("₹"+al_display_studentTexteNotes.get(i).getDiscount());
        }


      /*  Picasso
                .with(context)
                .load(al_display_studentTexteNotes.get(i).getBookImage())
                .into(imageView);
*/
        Picasso
                .get()
                .load(al_display_studentTexteNotes.get(i).getBookImage())
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_temp=textView3.getText().toString();
                str_bookid=al_display_studentTexteNotes.get(i).getId().toString();
                str_bookname=textView.getText().toString();
                language=al_display_studentTexteNotes.get(i).getLang();
                str_authorname=al_display_studentTexteNotes.get(i).getAuthor();
                str_publisher=al_display_studentTexteNotes.get(i).getPublisher();
                str_isbn=al_display_studentTexteNotes.get(i).getCopyrightPerson();
                str_price=al_display_studentTexteNotes.get(i).getPrice();
                str_discountprice=al_display_studentTexteNotes.get(i).getDiscount();
                str_description=al_display_studentTexteNotes.get(i).getDescription().toString();
                str_frontbookimage=al_display_studentTexteNotes.get(i).getBookImage();
                str_indexbookimage=al_display_studentTexteNotes.get(i).getBookIndexPage();
                str_backbookimage=al_display_studentTexteNotes.get(i).getBookBackImage();
                strbookfree=al_display_studentTexteNotes.get(i).getIsFree();
                strbooklink=al_display_studentTexteNotes.get(i).getBookFile();
                Intent intent=new Intent(context,BookDetailsActivity.class);
                intent.putExtra("toolbarid",toolbarid);

                intent.putExtra("bookid",str_bookid);
                intent.putExtra("bookname",str_bookname);
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
                intent.putExtra("bookfree",strbookfree);
                intent.putExtra("booklink",strbooklink);
                Log.d(TAG, "onClick:strbooklink "+strbooklink);
                intent.putExtra("language",language);

                context.startActivity(intent);

            }
        });

       return v;

    }
}
