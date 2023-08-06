package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Response.DeleteMagazineBypublisherResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
*/

public class MagazineDetailsByPublisherActivity extends AppCompatActivity implements  View.OnClickListener{
    Context context;
    HashMap<String, String> Hash_file_maps ;
    Toolbar toolbar;
    String deletemagazineaction="delete_magazine",bookid,price,bookname,editor,publisher,bookfile,category,description,bookfrontimage,bookindeximage,bookbackimage,isbnno;
    TextView tvbookname,tvauthor,tvpublisher,tvisbnno,tvcategory,tvbookdescription;
    Button btnupdate,btndelete;
    ImageView imageView;
    DeleteMagazineBypublisherResponse deleteMagazineBypublisherResponse;
    String TAG="TAG";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_details_by_publisher);
        progressDialog=new ProgressDialog(this);

        context=MagazineDetailsByPublisherActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tvbookname=findViewById(R.id.tv_bookdetail_name);
        tvauthor=findViewById(R.id.tv_bookAuthorname);
        tvpublisher=findViewById(R.id.tv_publishername);
       // tvebookfile=findViewById(R.id.tv_bookfiles);
        tvcategory=findViewById(R.id.tv_categoryname);
        tvbookdescription=findViewById(R.id.tv_description);
        tvisbnno=findViewById(R.id.tv_bookisnbnno);
        btnupdate=findViewById(R.id.btn_update);
        btndelete=findViewById(R.id.btn_delete);
        btnupdate.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        imageView=findViewById(R.id.slider);


        Hash_file_maps = new HashMap<String, String>();
        Bundle bundle = getIntent().getExtras();
        bookid=bundle.getString("bookid");

        bookname=bundle.getString("bookname");
        editor=bundle.getString("editor");
        publisher=bundle.getString("publisher");
        bookfile=bundle.getString("ebookfile");
        category=bundle.getString("category");
        description=bundle.getString("description");
        price=bundle.getString("price");
       /* discountprice=bundle.getString("discountprice");
        language=bundle.getString("language");
       */ bookfrontimage=bundle.getString("bookfrontimage");
        bookbackimage=bundle.getString("bookbackimage");
        bookindeximage=bundle.getString("bookindeximage");
        isbnno=bundle.getString("isbnno");

        toolbar.setTitle(bookname);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tvbookname.setText(bookname);
        tvauthor.setText(editor);
        tvpublisher.setText(publisher);
        tvisbnno.setText(isbnno);
        tvcategory.setText(category);
        tvbookdescription.setText(description);
/*
        Picasso.with(context).load(bookfrontimage).into(imageView);
*/

        Picasso.get().load(bookfrontimage).into(imageView);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.btn_delete:
                deletemagazine(deletemagazineaction,bookid);
                break;
        }

    }

    private void deletemagazine(String deletemagazineaction, String bookid) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<DeleteMagazineBypublisherResponse> deleteMagazineBypublisherResponseCall = apiInterface.deletemagazin(deletemagazineaction, bookid);

            deleteMagazineBypublisherResponseCall.enqueue(new Callback<DeleteMagazineBypublisherResponse>() {
                @Override
                public void onResponse(Call<DeleteMagazineBypublisherResponse> call, Response<DeleteMagazineBypublisherResponse> response) {
                    progressDialog.dismiss();
                    deleteMagazineBypublisherResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (deleteMagazineBypublisherResponse != null) {
                        if (deleteMagazineBypublisherResponse.getResponseCode().matches("0")) {
                            Toast.makeText(context, deleteMagazineBypublisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + deleteMagazineBypublisherResponse.getResponseMessage());
                            startActivity(new Intent(context,PublisherDashboardActivity.class));
                            finish();


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + deleteMagazineBypublisherResponse.getResponseMessage());
                            Toast.makeText(context,deleteMagazineBypublisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<DeleteMagazineBypublisherResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    public boolean isOnline() {

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
