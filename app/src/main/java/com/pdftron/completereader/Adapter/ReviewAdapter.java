package com.pdftron.completereader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.R;
import com.pdftron.completereader.Response.ViewBookReviewResponse;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.RecentbookViewHolder>{
    Context context;
    ArrayList<ViewBookReviewResponse.Datum> al_display_viewbookreview;
    String TAG="TAG",str_bookid,str_bookname,str_authorname,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;
    int originalprice,discountprice,temp;
    double discount;

    public ReviewAdapter(Context context, ArrayList<ViewBookReviewResponse.Datum> al_display_viewbookreview) {
        this.context=context;
        this.al_display_viewbookreview=al_display_viewbookreview;
    }

    @NonNull
    @Override
    public RecentbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reviewdesign, parent, false);
        return new RecentbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecentbookViewHolder holder, final int position) {

if (al_display_viewbookreview!=null)
{

    holder.tvreview.setText(al_display_viewbookreview.get(position).getReview().toString());
    holder.tvdate.setText(al_display_viewbookreview.get(position).getDateTime());


}


    }

    @Override
    public int getItemCount() {
        return al_display_viewbookreview.size();
    }

    public class RecentbookViewHolder extends RecyclerView.ViewHolder
{
TextView tvreview,tvdate;
    public RecentbookViewHolder(@NonNull View itemView) {
        super(itemView);

        tvreview=itemView.findViewById(R.id.tv_viewreview);
        tvdate=itemView.findViewById(R.id.tv_reviewdate);

    }
}
}
