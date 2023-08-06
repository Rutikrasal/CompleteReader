package com.pdftron.completereader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdftron.completereader.Response.AcceptedEventResponse;
import com.pdftron.completereader.Response.PendingEventResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PendingEventAdapter extends BaseAdapter {
    ArrayList<PendingEventResponse.Datum> al_display_mypendingevent;
    Context context;
    String eventid;
    public PendingEventAdapter(Context context, ArrayList<PendingEventResponse.Datum> al_display_mypendingevent) {
        this.context=context;
        this.al_display_mypendingevent=al_display_mypendingevent;
    }

    @Override
    public int getCount() {
        return al_display_mypendingevent.size();
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
            v = inflater.inflate(R.layout.eventdesign, null);
        }
        else {
            v = (View) convertView;
        }
        ImageView imageView = v.findViewById(R.id.img_event);
        TextView textView = v.findViewById(R.id.tv_eventname);
        TextView textView1 = v.findViewById(R.id.tv_speakername);
        TextView textView2 = v.findViewById(R.id.tv_spaciality);
        TextView textView3=v.findViewById(R.id.tv_eventprice);
        TextView textView4=v.findViewById(R.id.tv_eventonlineprice);
        if (al_display_mypendingevent.get(i).getPrice().matches("0"))
        {
            textView3.setText("Offline:"+"NA");

        }
        else
        {
            textView3.setText("Offline"+"₹"+al_display_mypendingevent.get(i).getPrice());

        }
        if (al_display_mypendingevent.get(i).getOnprice().matches("0"))
        {
            textView4.setText("Online:"+"NA");

        }
        else
        {
            textView4.setText("Online"+"₹"+al_display_mypendingevent.get(i).getOnprice());

        }
        textView.setText(al_display_mypendingevent.get(i).getEventName());
       // textView1.setText(al_display_mypendingevent.get(i).getSpeaker());
        textView2.setText(al_display_mypendingevent.get(i).getSpeciality());


        //textView2.setText(al_display_magazines.get(i).getDiscount());
        Picasso.get()

                .load(al_display_mypendingevent.get(i).getUserfrontfile())

                .into(imageView);
       /* Picasso
                .with(context)
                .load(al_display_events.get(i).getUserfrontfile())
                .into(imageView);
       */ imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventid=al_display_mypendingevent.get(i).getId();
                Intent intent=new Intent(context, MyEventDetailActivity.class);
                intent.putExtra("eventid",eventid);
                context.startActivity(intent );
            }
        });

        return v;
    }
}
