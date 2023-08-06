package com.pdftron.completereader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.pdftron.completereader.Response.MagazinesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MagazinesAdapter extends RecyclerView.Adapter<MagazinesAdapter.MagazineViewHolder>{
    Context context;
    ArrayList<MagazinesResponse.Datum> al_display_magazines;
    String toolbarid="magzine",language,date,strbooklink,strbookfree,TAG="TAG",str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;
    int originalprice,discountprice,temp;
    double discount;

    public MagazinesAdapter(Context context, ArrayList<MagazinesResponse.Datum> al_display_magazines) {
        this.context=context;
        this.al_display_magazines=al_display_magazines;
    }

    @NonNull
    @Override
    public MagazineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookdesign, parent, false);
        return new MagazineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MagazineViewHolder holder, final int position) {

if (al_display_magazines!=null)
{

    holder.tvprice.setTextColor(Color.BLACK );
    holder.frameLayout.setVisibility(View.GONE);
holder.tvdiscountprice.setVisibility(View.GONE);
holder.tvauthorname.setText(al_display_magazines.get(position).getAuthor());
    holder.tvbookname.setText(al_display_magazines.get(position).getMagazinesName());
//holder.tvprice.setText("₹"+al_display_magazines.get(position).getPrice());
    holder.tvprice.setVisibility(View.GONE);
   /* originalprice= Integer.parseInt(al_display_magazines.get(position).getPrice());
    discountprice= Integer.parseInt(al_display_magazines.get(position).getDiscount());
    discount = originalprice-discountprice;
    Log.d(TAG, "onBindViewHolder: discount"+discount);
    temp= (int) ((discount/originalprice)*100);
    Log.d(TAG, "onBindViewHolder:temp "+temp);
    holder.tvdiscount.setText(String.valueOf(temp)+"%");
*/
    String path = al_display_magazines.get(position).getBookImage();


   /* Picasso
            .with(context)
            .load(path)
            .into(holder.imagebook);
*/
    Picasso
            .get()
            .load(path)
            .into(holder.imagebook);

}
holder.imagebook.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        str_bookid=al_display_magazines.get(position).getId().toString();
        str_bookname=holder.tvbookname.getText().toString();
        str_authorname=al_display_magazines.get(position).getAuthor();
        str_publisher=al_display_magazines.get(position).getPublisher();
        str_isbn=al_display_magazines.get(position).getCopyrightPerson();
        //str_price=al_display_magazines.get(position).getPrice().toString();
        str_description=al_display_magazines.get(position).getDescription().toString();
        str_frontbookimage=al_display_magazines.get(position).getBookImage();
        str_indexbookimage=al_display_magazines.get(position).getBookIndexPage();
        str_backbookimage=al_display_magazines.get(position).getBookBackImage();
        strbooklink=al_display_magazines.get(position).getBookFile();
        Log.d(TAG, "onClick: strbooklink"+strbooklink);
        strbookfree=al_display_magazines.get(position).getIsFree();
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

    }

    @Override
    public int getItemCount() {
        return al_display_magazines.size();
    }

    public class MagazineViewHolder extends RecyclerView.ViewHolder
{
TextView tvbookname,tvprice,tvdiscount,tvdiscountprice,tvauthorname;
ImageView imagebook;
FrameLayout frameLayout;
    public MagazineViewHolder(@NonNull View itemView) {
        super(itemView);
        tvbookname=itemView.findViewById(R.id.tv_bookname);
        tvprice=itemView.findViewById(R.id.tv_book_price);
        tvdiscountprice=itemView.findViewById(R.id.tv_book_discountprice);
        imagebook=itemView.findViewById(R.id.img_book);
        frameLayout=itemView.findViewById(R.id.counterValuePanel);
        tvauthorname=itemView.findViewById(R.id.tv_authorname);
    }
}
}
