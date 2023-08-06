package com.pdftron.completereader.Adapter;

import android.app.Activity;
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
import com.pdftron.completereader.Response.TopBookResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopBookAdapter extends RecyclerView.Adapter<TopBookAdapter.TopbookViewHolder>{
    Context context;
int originalprice,discountprice,temp;
double discount;
    String language;
String status,quantity,strbookfree="0",date;
    ArrayList<TopBookResponse.Datum> al_display_topbook;
String strbooklink="link",toolbarid="book",TAG="TAG",str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;
    public TopBookAdapter(Context context, ArrayList<TopBookResponse.Datum> al_display_topbook) {
        this.context=context;
        this.al_display_topbook=al_display_topbook;
    }

    @NonNull
    @Override
    public TopbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookdesign, parent, false);
        return new TopbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TopbookViewHolder holder, final int position) {

if (al_display_topbook!=null)
{
/*
    status=al_display_topbook.get(position).getStatus();
    if (status.matches("1"))
    {
*/


    holder.tvprice.setPaintFlags(holder.tvprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    holder.tvbookname.setText(al_display_topbook.get(position).getBookName());
    Log.d(TAG, "onBindViewHolder: ");
holder.tvprice.setText("₹"+al_display_topbook.get(position).getPrice());
holder.tvdiscountprice.setText("₹"+al_display_topbook.get(position).getDiscount());
originalprice= Integer.parseInt(al_display_topbook.get(position).getPrice());
discountprice= Integer.parseInt(al_display_topbook.get(position).getDiscount());
    discount = originalprice-discountprice;
    Log.d(TAG, "onBindViewHolder: discount"+discount);
temp= (int) ((discount/originalprice)*100);
    Log.d(TAG, "onBindViewHolder:temp "+temp);
    holder.tvdiscount.setText(String.valueOf(temp)+"%");
    holder.tvquantity.setText(al_display_topbook.get(position).getQuantity());
    holder.tvauthorname.setText(al_display_topbook.get(position).getAuthor());

    String path =al_display_topbook.get(position).getBookImage();
    Log.d(TAG, "onBindViewHolder: bookimage"+path);


/*
    Picasso
            .with(context)
            .load(path)
            .into(holder.imagebook);
*/
    Picasso
            .get()
            .load(path)
            .into(holder.imagebook);


    /*}*/
}
holder.imagebook.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String str_temp=holder.tvdiscount.getText().toString();
        str_bookid=al_display_topbook.get(position).getId().toString();
        str_bookname=holder.tvbookname.getText().toString();
        str_authorname=al_display_topbook.get(position).getAuthor();
        str_publisher=al_display_topbook.get(position).getPublisher();
        str_isbn=al_display_topbook.get(position).getCopyrightname();
        str_price=al_display_topbook.get(position).getPrice();
        str_discountprice=al_display_topbook.get(position).getDiscount();
        str_description=al_display_topbook.get(position).getDescription().toString();
        str_frontbookimage=al_display_topbook.get(position).getBookImage();
        str_indexbookimage=al_display_topbook.get(position).getBookIndexImage();
        str_backbookimage=al_display_topbook.get(position).getBookBackImage();
        date=al_display_topbook.get(position).getCreateDate();
        quantity=al_display_topbook.get(position).getQuantity();
        Log.d(TAG, "onClick:quantity "+quantity);
        language=al_display_topbook.get(position).getLang();

        Intent intent=new Intent(context, BookDetailsActivity.class);
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
        intent.putExtra("booklink",strbooklink);
        intent.putExtra("date",date);
        intent.putExtra("language",language);




        context.startActivity(intent);
        ((Activity)context).finish();









    }
});

    }

    @Override
    public int getItemCount() {
        return al_display_topbook.size();
    }

    public class TopbookViewHolder extends RecyclerView.ViewHolder
{
TextView tvbookname,tvprice,tvdiscount,tvdiscountprice,tvquantity,tvauthorname;
ImageView imagebook;
FrameLayout counter_ValuePanel;
    public TopbookViewHolder(@NonNull View itemView) {
        super(itemView);
        tvbookname=itemView.findViewById(R.id.tv_bookname);
        tvquantity=itemView.findViewById(R.id.tv_book_quantity);
        tvauthorname=itemView.findViewById(R.id.tv_authorname);

        tvprice=itemView.findViewById(R.id.tv_book_price);
        tvdiscount=itemView.findViewById(R.id.tv_discount_percent);
        tvdiscountprice=itemView.findViewById(R.id.tv_book_discountprice);
        imagebook=itemView.findViewById(R.id.img_book);
        counter_ValuePanel=itemView.findViewById(R.id.counterValuePanel);
    }
}
}
