package com.pdftron.completereader.Adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.pdftron.completereader.R;

import java.util.ArrayList;
import java.util.Objects;


public class ViewPagerAdapter extends PagerAdapter {

    Context mContext;
String TAG="TAG";
    private ArrayList<Integer> IMAGES;
    LayoutInflater mLayoutInflater;



    public ViewPagerAdapter(Context context, ArrayList<Integer> IMAGES) {
        this.mContext = context;
        this.IMAGES=IMAGES;
        Log.d(TAG, "ViewPagerAdapter: "+IMAGES);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return IMAGES.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_layout, container, false);

        // referencing the image view from the item.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView_slider);

        // setting the image in the imageView
       imageView.setImageResource(IMAGES.get(position));


        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        return itemView;   }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }



}
