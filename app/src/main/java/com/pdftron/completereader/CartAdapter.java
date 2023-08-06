package com.pdftron.completereader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
*/

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    ArrayList<String> uploadcartArrayList;
    String TAG="TAG";
    ArrayList<String> book_id=new ArrayList<>();
    ArrayList<String> book_name=new ArrayList<>();
    ArrayList<String> book_price=new ArrayList<>();
    ArrayList<String> book_quantity=new ArrayList<>();

    ArrayList<String> book_imagelink=new ArrayList<>();
    ArrayList<String> total_quantity=new ArrayList<>();

    String bookid,bookname,bookprice,bookquantity,bookimagelink,totalbookquantity;
    DBcartDataHelper dBcartDataHelper;
    Integer book_idi,bookquantitys,bokkq;

    public CartAdapter(Context context, ArrayList<String> bookid, ArrayList<String> bookname, ArrayList<String> bookprice, ArrayList<String> bookquantity, ArrayList<String> bookimagelink, ArrayList<String> totalquantity) {
        this.context=context;
        this.book_id=bookid;
        this.book_name=bookname;
        this.book_price=bookprice;
        this.book_quantity=bookquantity;
        this.book_imagelink=bookimagelink;
        this.total_quantity=totalquantity;
        dBcartDataHelper=new DBcartDataHelper(context);
        Log.d(TAG, "CartAdapter:uploadcartArrayList "+uploadcartArrayList);

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cartdesign, parent, false);

        return new CartViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        holder.tvbookprice.setText("₹"+book_price.get(position));
        holder.tvbookname.setText(book_name.get(position));
        holder.tvbookid.setText(book_id.get(position));
        holder.etquantity.setText(book_quantity.get(position));
        holder.tvbookimagelink.setText(book_imagelink.get(position));
        holder.tvbookquantity.setText(total_quantity.get(position));
        Log.d(TAG, "onBindViewHolder: quan"+holder.tvbookquantity.getText().toString());

        String path = book_imagelink.get(position);
/*
        Picasso
                .with(context)
                .load(path)

                .into(holder.imagebook);*/

        Picasso
                .get()
                .load(path)

                .into(holder.imagebook);
holder.imageButtonremove.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        bookid=holder.tvbookid.getText().toString();
        book_idi= Integer.parseInt(bookid);
        Log.d(TAG, "onClick:bookid "+bookid);
        deletecartitem(book_idi);
    }
});
holder.btnupdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        totalbookquantity=holder.tvbookquantity.getText().toString();
        bookquantitys= Integer.valueOf(totalbookquantity);
        bookid=holder.tvbookid.getText().toString();
        bookname=holder.tvbookname.getText().toString();
        bookprice=book_price.get(position);
        bookquantity=holder.etquantity.getText().toString();
        bokkq= Integer.valueOf(bookquantity);
        bookimagelink=holder.tvbookimagelink.getText().toString();
        book_idi= Integer.parseInt(bookid);
        if (bookquantity.matches("0") )
        {
            Toast.makeText(context,"Please dont Enter Zero Quantity",Toast.LENGTH_SHORT).show();
        }
        else {
            if (  bokkq > bookquantitys)
            {
                Toast.makeText(context,"please enter quantity less than"+bookquantitys,Toast.LENGTH_SHORT).show();

            }
            else {
                updatecart(bookid,bookname,bookprice,bookquantity,bookimagelink);

            }

        }

    }
});


    }

    private void updatecart(String bookid, String bookname, String bookprice, String bookquantity, String bookimagelink) {
        boolean isupdated=dBcartDataHelper.updatecart(bookid,bookname,bookprice,bookquantity,bookimagelink);
        if (isupdated==true)
        {
            Toast.makeText(context,"Update quantity sucessfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, CartActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((Activity) context).finish();



        }
        else
        {
            Log.d(TAG, "deletfromrx: No");
            //Toast.makeText(this,"Data is not inserted",Toast.LENGTH_SHORT).show();
        }

    }


    private void deletecartitem(Integer bookid) {
        boolean isdeleted=dBcartDataHelper.deletecartdata(bookid);
        if (isdeleted==true)
        {
            Toast.makeText(context,"Delete Item", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, CartActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((Activity) context).finish();


        }
        else
        {
            Log.d(TAG, "deletfromrx: No");
            //Toast.makeText(this,"Data is not inserted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }
    public class CartViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvbookname,tvbookprice,tvbookid,tvbookimagelink,tvbookquantity;
        ImageView imagebook;
        ImageButton imageButtonremove;
        EditText etquantity;
        Button btnupdate;
      //  ElegantNumberButton elegantNumberButton;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvbookname=itemView.findViewById(R.id.tv_bookname);
            tvbookprice=itemView.findViewById(R.id.tv_book_price);
            imagebook=itemView.findViewById(R.id.image_book);
            tvbookid=itemView.findViewById(R.id.tv_bookid);
            imageButtonremove=itemView.findViewById(R.id.imgbtn_remove);
            etquantity=itemView.findViewById(R.id.etquantity);
            btnupdate=itemView.findViewById(R.id.btn_update);
            tvbookimagelink=itemView.findViewById(R.id.tv_bookimagelink);
            tvbookquantity=itemView.findViewById(R.id.tv_book_quantity);
           // elegantNumberButton=itemView.findViewById(R.id.elegant_btn);
        }
    }
}
