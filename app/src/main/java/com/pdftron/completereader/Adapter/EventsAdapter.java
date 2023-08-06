package com.pdftron.completereader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.EventdetailActivity;
import com.pdftron.completereader.R;
import com.pdftron.completereader.Response.EventResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder>{
    Context context;
    ArrayList<EventResponse.Datum> al_display_events;
    String language,date,TAG="TAG",str_eventid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;
    int originalprice,discountprice,temp;
    double discount;

    public EventsAdapter(Context context, ArrayList<EventResponse.Datum> al_display_events) {
        this.context=context;
        this.al_display_events=al_display_events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.eventdesign, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventViewHolder holder, final int position) {

if (al_display_events!=null)
{
    holder.tveventname.setText(al_display_events.get(position).getEventName());
    if (al_display_events.get(position).getPrice().matches("0"))
    {
        holder.tvprice.setText("Offline:"+"NA");

    }
    else
    {
        holder.tvprice.setText("Offline:"+"₹"+al_display_events.get(position).getPrice());

    }
    if (al_display_events.get(position).getOnprice().matches("0"))
    {
        holder.tvonlineprice.setText("Online:"+"NA");

    }
    else
    {
        holder.tvonlineprice.setText("Online:"+"₹"+al_display_events.get(position).getOnprice());

    }
holder.tvspeakername.setText(al_display_events.get(position).getSpeaker());
holder.tvspecality.setText(al_display_events.get(position).getSpeciality());
   /* originalprice= Integer.parseInt(al_display_magazines.get(position).getPrice());
    discountprice= Integer.parseInt(al_display_magazines.get(position).getDiscount());
    discount = originalprice-discountprice;
    Log.d(TAG, "onBindViewHolder: discount"+discount);
    temp= (int) ((discount/originalprice)*100);
    Log.d(TAG, "onBindViewHolder:temp "+temp);
    holder.tvdiscount.setText(String.valueOf(temp)+"%");
*/
    String path = al_display_events.get(position).getUserfrontfile();


/*
    Picasso
            .with(context)
            .load(path)
            .into(holder.imageevent);
*/
    Picasso
            .get()
            .load(path)
            .into(holder.imageevent);


}
holder.imageevent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        str_eventid=al_display_events.get(position).getId().toString();
        Intent intent=new Intent(context, EventdetailActivity.class);
        intent.putExtra("eventid",str_eventid);
        context.startActivity(intent);

    }
});

    }

    @Override
    public int getItemCount() {
        return al_display_events.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder
{
TextView tveventname,tvprice,tvspeakername,tvspecality,tvonlineprice;
ImageView imageevent;
    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        tveventname=itemView.findViewById(R.id.tv_eventname);
        tvspeakername=itemView.findViewById(R.id.tv_speakername);
        tvprice=itemView.findViewById(R.id.tv_eventprice);
        imageevent=itemView.findViewById(R.id.img_event);
        tvspecality=itemView.findViewById(R.id.tv_spaciality);
        tvonlineprice=itemView.findViewById(R.id.tv_eventonlineprice);
    }
}
}
