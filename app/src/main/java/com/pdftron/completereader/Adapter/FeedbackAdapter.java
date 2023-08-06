package com.pdftron.completereader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.R;
import com.pdftron.completereader.Response.ViewfeedbackResponse;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>{
    Context context;
    ArrayList<ViewfeedbackResponse.Datum> al_display_feedback;
    String TAG="TAG",str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;
    int originalprice,discountprice,temp;
    double discount;

    public FeedbackAdapter(Context context, ArrayList<ViewfeedbackResponse.Datum> al_display_feedback) {
        this.context=context;
        this.al_display_feedback=al_display_feedback;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feedbackviewdesign, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeedbackViewHolder holder, final int position) {

if (al_display_feedback!=null)
{
   holder.tvname.setText(al_display_feedback.get(position).getName().toString());
    holder.tvemail.setText("Email ID:"+al_display_feedback.get(position).getEmail());
    holder.tvfeedback.setText(al_display_feedback.get(position).getMessage());
    holder.tvdate.setText("Posted On:"+al_display_feedback.get(position).getDateT());

}
    }

    @Override
    public int getItemCount() {
        return al_display_feedback.size();
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder
{
TextView tvname,tvemail,tvfeedback,tvdate;
    public FeedbackViewHolder(@NonNull View itemView) {
        super(itemView);
        tvname=itemView.findViewById(R.id.tv_username);
        tvemail=itemView.findViewById(R.id.tv_useremail);
        tvfeedback=itemView.findViewById(R.id.tv_feedback);
        tvdate=itemView.findViewById(R.id.tv_feedbackdate);
            }
}
}
