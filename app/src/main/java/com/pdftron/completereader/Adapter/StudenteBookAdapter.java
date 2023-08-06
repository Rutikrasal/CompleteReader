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
import com.pdftron.completereader.Response.StudenteBookResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StudenteBookAdapter extends RecyclerView.Adapter<StudenteBookAdapter.StudentebookViewHolder>{
    Context context;
    ArrayList<StudenteBookResponse.Datum> al_display_studentebook;
    String language,date,strbooklink,strbookfree,toolbarid="e-book",TAG="TAG",str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;
   int originalprice,discountprice,temp;
    double discount;

    public StudenteBookAdapter(Context context, ArrayList<StudenteBookResponse.Datum> al_display_studentebook) {
        this.context=context;
        this.al_display_studentebook=al_display_studentebook;
    }

    @NonNull
    @Override
    public StudentebookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookdesign, parent, false);
        return new StudentebookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentebookViewHolder holder, final int position) {

if (al_display_studentebook!=null)
{
    holder.tvprice.setPaintFlags(holder.tvprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    holder.tvbookname.setText(al_display_studentebook.get(position).getEbookName());
    holder.tvauthorname.setText(al_display_studentebook.get(position).getAuthor());

holder.tvprice.setText("₹"+al_display_studentebook.get(position).getPrice());
holder.tvdiscountprice.setText("₹"+al_display_studentebook.get(position).getDiscount());
    originalprice= Integer.parseInt(al_display_studentebook.get(position).getPrice());
    discountprice= Integer.parseInt(al_display_studentebook.get(position).getDiscount());
    discount = originalprice-discountprice;
    Log.d(TAG, "onBindViewHolder: discount"+discount);
    temp= (int) ((discount/originalprice)*100);
    Log.d(TAG, "onBindViewHolder:temp "+temp);
    holder.tvdiscount.setText(String.valueOf(temp)+"%");
    String path = al_display_studentebook.get(position).getBookImage();
    Log.d(TAG, "onBindViewHolder: enote"+al_display_studentebook.get(position).getIsFree());
    if (al_display_studentebook.get(position).getIsFree().matches("1"))
    {
        holder.tvprice.setVisibility(View.GONE);
        holder.tvdiscountprice.setVisibility(View.GONE);
        holder.counter_ValuePanel.setVisibility(View.GONE);

        holder.tvbookfree.setVisibility(View.VISIBLE);

    }
    else if (al_display_studentebook.get(position).getIsFree().matches("0"))
    {
        holder.tvbookfree.setVisibility(View.GONE);

        holder.tvprice.setVisibility(View.VISIBLE);
        holder.tvdiscountprice.setVisibility(View.VISIBLE);
        holder.counter_ValuePanel.setVisibility(View.VISIBLE);

    }
/*
    Picasso
            .with(context)
            .load(path)
            .into(holder.imagebook);*/

    Picasso
            .get()
            .load(path)
            .into(holder.imagebook);

}
holder.imagebook.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String str_temp=holder.tvdiscount.getText().toString();
        language=al_display_studentebook.get(position).getLang();


        str_bookid=al_display_studentebook.get(position).getId().toString();
        str_bookname=holder.tvbookname.getText().toString();
        str_authorname=al_display_studentebook.get(position).getAuthor();
        str_publisher=al_display_studentebook.get(position).getPublisher();
        str_isbn=al_display_studentebook.get(position).getCopyrightPerson();
        str_price=al_display_studentebook.get(position).getPrice();
        str_discountprice=al_display_studentebook.get(position).getDiscount();
        str_description=al_display_studentebook.get(position).getDescription().toString();
        str_frontbookimage=al_display_studentebook.get(position).getBookImage();
        str_indexbookimage=al_display_studentebook.get(position).getBookIndexPage();
        str_backbookimage=al_display_studentebook.get(position).getBookBackImage();
        strbookfree=al_display_studentebook.get(position).getIsFree();
        strbooklink=al_display_studentebook.get(position).getBookFile();
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
        intent.putExtra("language",language);


        context.startActivity(intent);

    }
});

    }

    @Override
    public int getItemCount() {
        return al_display_studentebook.size();
    }

    public class StudentebookViewHolder extends RecyclerView.ViewHolder
{
TextView tvbookname,tvprice,tvdiscount,tvdiscountprice,tvauthorname,tvbookfree;
ImageView imagebook;
FrameLayout counter_ValuePanel;
    public StudentebookViewHolder(@NonNull View itemView) {
        super(itemView);
        tvbookname=itemView.findViewById(R.id.tv_bookname);

        tvprice=itemView.findViewById(R.id.tv_book_price);
        tvdiscount=itemView.findViewById(R.id.tv_discount_percent);
        tvdiscountprice=itemView.findViewById(R.id.tv_book_discountprice);
        imagebook=itemView.findViewById(R.id.img_book);
        counter_ValuePanel=itemView.findViewById(R.id.counterValuePanel);
        tvauthorname=itemView.findViewById(R.id.tv_authorname);
        tvbookfree=itemView.findViewById(R.id.tv_book_free);

    }
}
}
