package com.pdftron.completereader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.CategorywiseBookviewActivity;
import com.pdftron.completereader.R;
import com.pdftron.completereader.Response.AllCategoryResponse;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    Context context;
    ArrayList<AllCategoryResponse.Datum> al_display_category;
    String toolbarid="book",TAG="TAG",str_categoryid,str_categoryname,str_categorydescription,str_categorytag,str_isbn,str_category,str_price,str_discountprice,str_description,str_frontbookimage,str_indexbookimage,str_backbookimage;
    int originalprice,discountprice,temp;
    double discount;



    public CategoryAdapter(Context context, ArrayList<AllCategoryResponse.Datum> al_display_category) {
        this.context=context;
        this.al_display_category=al_display_category;

    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_design, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, final int position) {

if (al_display_category!=null)
{

    holder.tvcategoryname.setText(al_display_category.get(position).getCategory());
    holder.tvcategoryid.setText(al_display_category.get(position).getId());
    holder.tvcategorydescription.setText(al_display_category.get(position).getDescription());
    holder.tvcategorytag.setText(al_display_category.get(position).getTag());


}
holder.tvcategoryname.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        str_categoryname=holder.tvcategoryname.getText().toString();
        str_categoryid=holder.tvcategoryid.getText().toString();
        str_categorydescription=holder.tvcategorydescription.getText().toString();
        str_categorytag=holder.tvcategorytag.getText().toString();
        Intent intent=new Intent(context, CategorywiseBookviewActivity.class);

        intent.putExtra("categoryid",str_categoryid);
        intent.putExtra("categoryname",str_categoryname);
        intent.putExtra("categorydescription",str_categorydescription);
        intent.putExtra("categorytag",str_categorytag);
        context.startActivity(intent);


    }
});
    }

    @Override
    public int getItemCount() {
        return al_display_category.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder
{
TextView tvcategoryname,tvcategoryid,tvcategorydescription,tvcategorytag,tvauthor;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvcategoryname=itemView.findViewById(R.id.tv_categoryname);
        tvcategoryid=itemView.findViewById(R.id.tv_categoryid);
        tvcategorydescription=itemView.findViewById(R.id.tv_categorydescription);
        tvcategorytag=itemView.findViewById(R.id.tv_categorytag);

          }
}
}
