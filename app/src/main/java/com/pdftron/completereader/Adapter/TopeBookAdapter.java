package com.pdftron.completereader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.BookDetailsActivity;
import com.pdftron.completereader.R;
import com.pdftron.completereader.Response.TopeBookResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopeBookAdapter extends RecyclerView.Adapter<TopeBookAdapter.TopebookViewHolder>{
    Context context;
    ArrayList<TopeBookResponse.Datum> al_display_topebook;
    String language,date,toolbarid="e-book",TAG="TAG",strbooklink,str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;
    int originalprice,discountprice,temp;
    double discount;
    String status,strbookfree;

    public TopeBookAdapter(Context context, ArrayList<TopeBookResponse.Datum> al_display_topebook) {
        this.context=context;
        this.al_display_topebook=al_display_topebook;
    }

    @NonNull
    @Override
    public TopebookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookdesign, parent, false);
        return new TopebookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TopebookViewHolder holder, final int position) {

if (al_display_topebook!=null)
{





    holder.tvbookname.setText(al_display_topebook.get(position).getEbookName());
holder.tvauthorname.setText(al_display_topebook.get(position).getAuthor());
if (al_display_topebook.get(position).getIsFree().matches("1"))
{
    holder.tvprice.setVisibility(View.GONE);
    holder.tvdiscountprice.setVisibility(View.GONE);
    holder.counter_ValuePanel.setVisibility(View.GONE);

    holder.tvbookfree.setVisibility(View.VISIBLE);

}
else if (al_display_topebook.get(position).getIsFree().matches("0"))
{
    holder.tvprice.setPaintFlags(holder.tvprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


    holder.tvprice.setText("₹"+al_display_topebook.get(position).getPrice());
    holder.tvdiscountprice.setText("₹"+al_display_topebook.get(position).getDiscount());


    originalprice= Integer.parseInt(al_display_topebook.get(position).getPrice());
    discountprice= Integer.parseInt(al_display_topebook.get(position).getDiscount());
    discount = originalprice-discountprice;
    Log.d(TAG, "onBindViewHolder: discount"+discount);
    temp= (int) ((discount/originalprice)*100);
    Log.d(TAG, "onBindViewHolder:temp "+temp);
    holder.tvdiscount.setText(String.valueOf(temp)+"%");
    holder.tvbookfree.setVisibility(View.GONE);

    holder.tvprice.setVisibility(View.VISIBLE);
    holder.tvdiscountprice.setVisibility(View.VISIBLE);
    holder.counter_ValuePanel.setVisibility(View.VISIBLE);


}

    String path = al_display_topebook.get(position).getBookImage();
    Log.d(TAG, "onBindViewHolder: path"+path);

    Picasso
            .get()
            .load(path)
            .into(holder.imagebook);

 /*   Picasso
            .with(context)
            .load(path)
            .into(holder.imagebook);*/
}
holder.imagebook.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String str_temp=holder.tvdiscount.getText().toString();
        strbooklink=al_display_topebook.get(position).getBookFile();
        language=al_display_topebook.get(position).getLang();


        str_bookid=al_display_topebook.get(position).getId().toString();
        str_bookname=holder.tvbookname.getText().toString();
        str_authorname=al_display_topebook.get(position).getAuthor();
        str_publisher=al_display_topebook.get(position).getPublisher();
        str_isbn=al_display_topebook.get(position).getCopyrightPerson();
        str_price=al_display_topebook.get(position).getPrice();
        str_discountprice=al_display_topebook.get(position).getDiscount();
        str_description=al_display_topebook.get(position).getDescription().toString();
        str_frontbookimage=al_display_topebook.get(position).getBookImage();
        str_indexbookimage=al_display_topebook.get(position).getBookIndexPage();
        str_backbookimage=al_display_topebook.get(position).getBookBackImage();
        strbookfree=al_display_topebook.get(position).getIsFree();
        Intent intent=new Intent(context, BookDetailsActivity.class);
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

    }

    @Override
    public int getItemCount() {
        return al_display_topebook.size();
    }

    public class TopebookViewHolder extends RecyclerView.ViewHolder
{
TextView tvbookname,tvprice,tvdiscount,tvdiscountprice,tvbookfree,tvauthorname;
ImageView imagebook;
FrameLayout counter_ValuePanel;
    public TopebookViewHolder(@NonNull View itemView) {
        super(itemView);
        tvbookname=itemView.findViewById(R.id.tv_bookname);
        tvprice=itemView.findViewById(R.id.tv_book_price);
        tvdiscount=itemView.findViewById(R.id.tv_discount_percent);
        tvdiscountprice=itemView.findViewById(R.id.tv_book_discountprice);
        imagebook=itemView.findViewById(R.id.img_book);
        tvbookfree=itemView.findViewById(R.id.tv_book_free);
        counter_ValuePanel=itemView.findViewById(R.id.counterValuePanel);
        tvauthorname=itemView.findViewById(R.id.tv_authorname);
    }
}
}
