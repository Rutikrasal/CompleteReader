package com.pdftron.completereader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>{
    Context context;
    ArrayList<String> uploadcartArrayList;
    String TAG="TAG";
    ArrayList<String> book_id=new ArrayList<>();
    ArrayList<String> book_name=new ArrayList<>();
    ArrayList<String> book_price=new ArrayList<>();
    ArrayList<String> book_quantity=new ArrayList<>();

    ArrayList<String> book_imagelink=new ArrayList<>();
    String bookid,bookname,bookprice,bookquantity,bookimagelink;
    DBcartDataHelper dBcartDataHelper;
    Integer book_idi,bookpricei,bookquantityi,total;

    public CheckoutAdapter(Context context, ArrayList<String> bookid, ArrayList<String> bookname, ArrayList<String> bookprice, ArrayList<String> bookquantity, ArrayList<String> bookimagelink) {
        this.context=context;
        this.book_id=bookid;
        this.book_name=bookname;
        this.book_price=bookprice;
        this.book_quantity=bookquantity;
        this.book_imagelink=bookimagelink;
        dBcartDataHelper=new DBcartDataHelper(context);


    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkoutdesign, parent, false);
        return new CheckoutViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        Picasso
                .get()
                .load(book_imagelink.get(position))
                .into(holder.ivcheckoutbookimage);

     /*   Picasso
                .with(context)
                .load(book_imagelink.get(position))
                .into(holder.ivcheckoutbookimage);
     */   holder.tvbookname.setText(book_name.get(position).toString());
       holder.tvbookquantity.setText(book_quantity.get(position));
        holder.tvbookprice.setText("₹"+book_price.get(position));
        bookquantityi= Integer.valueOf(book_quantity.get(position));
        bookpricei= Integer.valueOf(book_price.get(position));
        total=bookquantityi*bookpricei;
       holder.tvsubtotal.setText("₹"+ Integer.toString(total));
    }

    @Override
    public int getItemCount() {
        return book_name.size();
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder {
        ImageView ivcheckoutbookimage;
        TextView tvbookname,tvbookquantity,tvbookprice,tvsubtotal,tvbookid;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            ivcheckoutbookimage=itemView.findViewById(R.id.image_book);
            tvbookname=itemView.findViewById(R.id.tv_bookname);
            tvbookquantity=itemView.findViewById(R.id.tv_book_quantity);
            tvbookprice=itemView.findViewById(R.id.tv_book_price);
            tvsubtotal=itemView.findViewById(R.id.tv_sub_total);
            tvbookid=itemView.findViewById(R.id.tv_bookid);
        }
    }
}
