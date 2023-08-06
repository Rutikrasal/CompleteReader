package com.pdftron.completereader.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.R;
import com.pdftron.completereader.Response.MyBookorderResponse;

import java.util.ArrayList;

public class MybookorderAdapter extends RecyclerView.Adapter<MybookorderAdapter.TopbookViewHolder>{
    Context context;
String status,quantity,delstatus,bookname;
    LinearLayout.LayoutParams lparams;
    MyBookorderResponse myBookorderResponse;
    String TAG="TAG";

    ArrayList<MyBookorderResponse.Datum> al_display_mybookorder;
    public MybookorderAdapter(Context context, ArrayList<MyBookorderResponse.Datum> al_display_mybookorder) {
        this.context=context;
        this.al_display_mybookorder=al_display_mybookorder;
    }



    @NonNull
    @Override
    public TopbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders, parent, false);
        return new TopbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TopbookViewHolder holder, final int position) {

if (al_display_mybookorder!=null)
{
    holder.tvtotalprice.setText("Total:₹"+al_display_mybookorder.get(position).getTotalPrice());
    status=al_display_mybookorder.get(position).getStatus();
    if (status.matches("0"))
    {
        holder.tvorderstatus.setText("Pending");

    }
    else if (status.matches("1"))
    {
        holder.tvorderstatus.setText("Accepted");

    }
    delstatus=al_display_mybookorder.get(position).getDelStatus();
    if (delstatus.matches("1"))
    {
        holder.tvorderstatus.setVisibility(View.GONE);
        holder.tvdelorderstatus.setVisibility(View.VISIBLE);
        holder.tvdelorderstatus.setText("Delivered");
    }
    else if (delstatus.matches("0"))
    {
        holder.tvdelorderstatus.setVisibility(View.GONE);

        holder.tvorderstatus.setVisibility(View.VISIBLE);

    }


       // holder.tvbookname.append(al_display_mybookorder.get(position).getBookName()+"X"+al_display_mybookorder.get(position).getQuantity()+"\n");
  //  holder.tvquantity.append(al_display_mybookorder.get(position).getQuantity()+"X"+"\n");
   // holder.tvbookname.setText(al_display_mybookorder.get(position).getBookName());
    Log.d(TAG, "onBindViewHolder:books "+al_display_mybookorder.get(position).getBookName());
    quantity=al_display_mybookorder.get(position).getQuantity();
    Log.d(TAG, "onBindViewHolder:quantity "+quantity);
    String[] res = quantity.split(",");
    Log.d(TAG, "onBindViewHolder:res "+res);
    for(String myStr: res) {
holder.tvquantity.append(myStr+"\n");
        Log.d(TAG, "onBindViewHolder: myStr"+myStr);
    }
    bookname=al_display_mybookorder.get(position).getBookName();
    Log.d(TAG, "onBindViewHolder:quantity "+quantity);
    String[] booknames = bookname.split(",");
    Log.d(TAG, "onBindViewHolder:res "+booknames);
    /*for(String mybooknames: booknames) {
        holder.tvbookname.append(mybooknames+"\n");

        Log.d(TAG, "onBindViewHolder: myStr"+mybooknames);
    }*/

    for(String mybooknames: booknames) {
        holder.tvbookname.append(mybooknames+"\n");
        Log.d(TAG, "onBindViewHolder: myStr"+mybooknames);
    }
    holder.tvdate.setText("Date:"+al_display_mybookorder.get(position).getDateTime());



}
else
{
    Toast.makeText(context,"You Dont Have any Order",Toast.LENGTH_SHORT).show();
}
    }

    @Override
    public int getItemCount() {
        return al_display_mybookorder.size();
    }

    public class TopbookViewHolder extends RecyclerView.ViewHolder
{
TextView tvbookname,tvtotalprice,tvorderstatus,tvdelorderstatus,tvquantity,tvdate;
    public TopbookViewHolder(@NonNull View itemView) {
        super(itemView);
        tvbookname=itemView.findViewById(R.id.tv_bookname);
        tvquantity=itemView.findViewById(R.id.tv_itemcount);
        tvdate=itemView.findViewById(R.id.tv_date);

        tvtotalprice=itemView.findViewById(R.id.tv_total);
        tvorderstatus=itemView.findViewById(R.id.tv_order_status);
        tvdelorderstatus=itemView.findViewById(R.id.tv_order_delstatus);
          }
}
}
