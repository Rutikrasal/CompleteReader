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

import com.pdftron.completereader.Response.DeleteEBookBypublisherResponse;
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

public class EBookDetailsByPublisherActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    ProgressDialog progressDialog;
    HashMap<String, String> Hash_file_maps ;
    Toolbar toolbar;
    ImageView imageView;
    String deleteebookaction="delete_ebook",isbn,publisher,free,bookid,price,discountprice,language,bookname,author,publishdate,bookfile,category,description,bookfrontimage,bookindeximage,bookbackimage;
    TextView tvbookname,tvauthor,tvpublishdate,tvebookfile,tvcategory,tvbookdescription;
    Button btnupdate,btndelete;
    DeleteEBookBypublisherResponse deleteEBookBypublisherResponse;
    String TAG="TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_book_details_by_publisher);
        context=EBookDetailsByPublisherActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressDialog=new ProgressDialog(this);

        tvbookname=findViewById(R.id.tv_bookdetail_name);
        tvauthor=findViewById(R.id.tv_bookAuthorname);
        tvpublishdate=findViewById(R.id.tv_publishdates);
        tvebookfile=findViewById(R.id.tv_bookfiles);
        tvcategory=findViewById(R.id.tv_categoryname);
        tvbookdescription=findViewById(R.id.tv_description);
        btnupdate=findViewById(R.id.btn_update);
        btndelete=findViewById(R.id.btn_delete);
        btnupdate.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        imageView=findViewById(R.id.slider);


        Hash_file_maps = new HashMap<String, String>();
        Bundle bundle = getIntent().getExtras();
        bookid=bundle.getString("bookid");
        free=bundle.getString("free");
        isbn=bundle.getString("isbn");
                publisher=bundle.getString("publisher");
        bookname=bundle.getString("bookname");
        author=bundle.getString("author");
        publishdate=bundle.getString("publishdate");
        bookfile=bundle.getString("ebookfile");
        category=bundle.getString("category");
        description=bundle.getString("description");
        price=bundle.getString("price");
        discountprice=bundle.getString("discountprice");
        language=bundle.getString("language");
        bookfrontimage=bundle.getString("bookfrontimage");
        bookbackimage=bundle.getString("bookbackimage");
        bookindeximage=bundle.getString("bookindeximage");

        toolbar.setTitle(bookname);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tvbookname.setText(bookname);
        tvauthor.setText(author);
        tvpublishdate.setText(publishdate);
        tvebookfile.setText(bookfile);
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
            case R.id.btn_update:
                Intent intent=new Intent(context,UpdateEbooksByPublisherActivity.class);
                intent.putExtra("bookid",bookid);
                intent.putExtra("free",free);
                intent.putExtra("bookname",bookname);
                intent.putExtra("author",author);
                intent.putExtra("publishdate",publishdate);
                intent.putExtra("ebookfile",bookfile);
                intent.putExtra("category",category);
                intent.putExtra("description",description);
                intent.putExtra("price",price);
                intent.putExtra("discountprice",discountprice);
                intent.putExtra("language",language);
                intent.putExtra("bookfrontimage",bookfrontimage);
                intent.putExtra("bookindeximage",bookindeximage);
                intent.putExtra("bookbackimage",bookbackimage);
                intent.putExtra("isbn",isbn);
                intent.putExtra("publisher",publisher);


                context.startActivity(intent);

                break;
            case R.id.btn_delete:
                deleteebook(deleteebookaction,bookid);

                break;
        }

    }

    private void deleteebook(String deleteebookaction, String bookid) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<DeleteEBookBypublisherResponse> deleteeBookBypublisherResponseCall = apiInterface.deleteebook(deleteebookaction, bookid);

            deleteeBookBypublisherResponseCall.enqueue(new Callback<DeleteEBookBypublisherResponse>() {
                @Override
                public void onResponse(Call<DeleteEBookBypublisherResponse> call, Response<DeleteEBookBypublisherResponse> response) {
                    progressDialog.dismiss();
                    deleteEBookBypublisherResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (deleteEBookBypublisherResponse != null) {
                        if (deleteEBookBypublisherResponse.getResponseCode().matches("0")) {
                            Toast.makeText(context, deleteEBookBypublisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + deleteEBookBypublisherResponse.getResponseMessage());

                            startActivity(new Intent(context,PublisherDashboardActivity.class));
                            finish();

                        } else {
                            Log.d(TAG, "onResponse:registration msg " + deleteEBookBypublisherResponse.getResponseMessage());
                            Toast.makeText(context,deleteEBookBypublisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<DeleteEBookBypublisherResponse> call, Throwable t) {
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
