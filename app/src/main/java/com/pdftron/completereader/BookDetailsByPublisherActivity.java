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

import com.pdftron.completereader.Response.DeleteBookBypublisherResponse;
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
import com.daimajia.slider.library.Tricks.ViewPagerEx;*/

public class BookDetailsByPublisherActivity extends AppCompatActivity implements  View.OnClickListener{
    ProgressDialog progressDialog;
    Context context;
    HashMap<String, String> Hash_file_maps ;
    Toolbar toolbar;
    ImageView imageView;
    String TAG="TAG",deletebookaction="delete_book";
    String publisher,bookid,price,discountprice,language,bookname,author,publishdate,isbnno,category,quantity,description,bookfrontimage,bookindeximage,bookbackimage;
TextView tvbookname,tvauthor,tvpublishdate,tvisbnnum,tvcategory,tvquantity,tvbookdescription;
Button btnupdate,btndelete;
DeleteBookBypublisherResponse deleteBookBypublisherResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details_by_publisher);
        context=BookDetailsByPublisherActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressDialog=new ProgressDialog(this);

        tvbookname=findViewById(R.id.tv_bookdetail_name);
        tvauthor=findViewById(R.id.tv_bookAuthorname);
        tvpublishdate=findViewById(R.id.tv_publishdates);
        tvisbnnum=findViewById(R.id.tv_bookisnbnno);
       tvcategory=findViewById(R.id.tv_categoryname);
       tvquantity=findViewById(R.id.tv_quantitys);
       tvbookdescription=findViewById(R.id.tv_description);
       btnupdate=findViewById(R.id.btn_update);
       btndelete=findViewById(R.id.btn_delete);
       btnupdate.setOnClickListener(this);
       btndelete.setOnClickListener(this);
       imageView=findViewById(R.id.slider);


        Hash_file_maps = new HashMap<String, String>();
        Bundle bundle = getIntent().getExtras();
        bookid=bundle.getString("bookid");
        bookname=bundle.getString("bookname");
        author=bundle.getString("author");
        publishdate=bundle.getString("publishdate");
        isbnno=bundle.getString("isbnno");
        category=bundle.getString("category");
        quantity=bundle.getString("quantity");
        description=bundle.getString("description");
        price=bundle.getString("price");
        discountprice=bundle.getString("discountprice");
        language=bundle.getString("language");
        bookfrontimage=bundle.getString("bookfrontimage");
        bookbackimage=bundle.getString("bookbackimage");
        bookindeximage=bundle.getString("bookindeximage");
        publisher=bundle.getString("publisher");

        toolbar.setTitle(bookname);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);





tvbookname.setText(bookname);
tvauthor.setText(author);
tvpublishdate.setText(publishdate);
tvisbnnum.setText(isbnno);
tvcategory.setText(category);
tvquantity.setText(quantity);
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
            case R.id.btn_update:
                Intent intent=new Intent(context,UpdateBoooksBypublisherActivity.class);
                intent.putExtra("bookid",bookid);

                intent.putExtra("bookname",bookname);
                intent.putExtra("author",author);
                intent.putExtra("publishdate",publishdate);
                intent.putExtra("isbnno",isbnno);
                intent.putExtra("category",category);
                intent.putExtra("quantity",quantity);
                intent.putExtra("description",description);
                Log.d(TAG, "onClick:price "+price);
                intent.putExtra("price",price);
                intent.putExtra("discountprice",discountprice);
                intent.putExtra("language",language);
                intent.putExtra("bookfrontimage",bookfrontimage);
                intent.putExtra("bookindeximage",bookindeximage);
                intent.putExtra("bookbackimage",bookbackimage);
                intent.putExtra("publisher",publisher);


                context.startActivity(intent);


                break;

            case R.id.btn_delete:
                deletebook(deletebookaction,bookid);
                break;
        }

    }

    private void deletebook(String deletebookaction, String bookid) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<DeleteBookBypublisherResponse> deleteBookBypublisherResponseCall = apiInterface.deletebook(deletebookaction, bookid);

            deleteBookBypublisherResponseCall.enqueue(new Callback<DeleteBookBypublisherResponse>() {
                @Override
                public void onResponse(Call<DeleteBookBypublisherResponse> call, Response<DeleteBookBypublisherResponse> response) {
                    progressDialog.dismiss();
                    deleteBookBypublisherResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (deleteBookBypublisherResponse != null) {
                        if (deleteBookBypublisherResponse.getResponseCode().matches("0")) {
                            Toast.makeText(context, deleteBookBypublisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + deleteBookBypublisherResponse.getResponseMessage());
                            startActivity(new Intent(context,PublisherDashboardActivity.class));
                            finish();


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + deleteBookBypublisherResponse.getResponseMessage());
                            Toast.makeText(context,deleteBookBypublisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<DeleteBookBypublisherResponse> call, Throwable t) {
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
