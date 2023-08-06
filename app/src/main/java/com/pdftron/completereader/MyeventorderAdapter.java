package com.pdftron.completereader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.Response.MyEventOrderResponse;

import java.util.ArrayList;


public class MyeventorderAdapter extends RecyclerView.Adapter<MyeventorderAdapter.TopbookViewHolder>{
    Context context;
    String status,quantity,delstatus,bookname;
    LinearLayout.LayoutParams lparams;
    MyEventOrderResponse myEventOrderResponse;
    String TAG="TAG";
    ArrayList<MyEventOrderResponse.Datum> al_display_myeventorder;

    public MyeventorderAdapter(Context context, ArrayList<MyEventOrderResponse.Datum> al_display_myeventorder) {
       this.context=context;
       this.al_display_myeventorder=al_display_myeventorder;
    }

    @NonNull
    @Override
    public TopbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders, parent, false);
        return new MyeventorderAdapter.TopbookViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TopbookViewHolder holder, int position) {

        if (al_display_myeventorder!=null)
        {
            holder.tvtotalprice.setText("Total:₹"+al_display_myeventorder.get(position).getEventFee());
            status=al_display_myeventorder.get(position).getStatus();
            if (status.matches("0"))
            {
                holder.tvorderstatus.setText("Pending");

            }
            else if (status.matches("1"))
            {
                holder.tvorderstatus.setText("Accepted");

            }


            // holder.tvbookname.append(al_display_mybookorder.get(position).getBookName()+"X"+al_display_mybookorder.get(position).getQuantity()+"\n");
            //  holder.tvquantity.append(al_display_mybookorder.get(position).getQuantity()+"X"+"\n");
            // holder.tvbookname.setText(al_display_mybookorder.get(position).getBookName());

                holder.tvbookname.append(al_display_myeventorder.get(position).getEventName());

            holder.tvdate.setText("Date:"+al_display_myeventorder.get(position).getEventDate());



        }
    }

    @Override
    public int getItemCount() {
        return al_display_myeventorder.size();
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
