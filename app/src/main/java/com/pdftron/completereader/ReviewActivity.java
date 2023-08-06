package com.pdftron.completereader;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.Adapter.ReviewAdapter;
import com.pdftron.completereader.Response.ViewBookReviewResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView reviewrecycle;
    ViewBookReviewResponse viewBookReviewResponse;
    ArrayList<ViewBookReviewResponse.Datum> al_display_viewbookreview;
    ReviewAdapter reviewAdapter;
    Context context;
    String TAG="TAG",reviewaction="review",str_bookid;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    Button btnaddreview;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",user_id;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        context=ReviewActivity.this;
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid"+user_id);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Review");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        builder = new AlertDialog.Builder(context);

        progressDialog=new ProgressDialog(this);
        btnaddreview=findViewById(R.id.btn_addreview);
        btnaddreview.setOnClickListener(this);

        reviewrecycle=findViewById(R.id.review_recycle);
        Bundle bundle = getIntent().getExtras();
        str_bookid = bundle.getString("bookid");

        viewreview(reviewaction,str_bookid);


    }
    private void viewreview(String reviewaction, String str_bookid) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ViewBookReviewResponse> bookViewResponseCall = apiInterface.viewreview(reviewaction,str_bookid);

            bookViewResponseCall.enqueue(new Callback<ViewBookReviewResponse>() {
                @Override
                public void onResponse(Call<ViewBookReviewResponse> call, Response<ViewBookReviewResponse> response) {
                    progressDialog.dismiss();
                    viewBookReviewResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (viewBookReviewResponse != null) {
                        Log.d(TAG, "onResponse:topBookResponse ");
                        if (viewBookReviewResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:book msg " + viewBookReviewResponse.getResponseMessage());
                            al_display_viewbookreview = new ArrayList<>();
                            for (int i = 0; i < viewBookReviewResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + viewBookReviewResponse.getData().size());
                                al_display_viewbookreview.add(viewBookReviewResponse.getData().get(i));
                                Log.d(TAG, "onResponse: al" + al_display_viewbookreview);
                                Log.d(TAG, "onResponse: alsize" + al_display_viewbookreview.size());

                            }
//                            tvreviewcount.setText(al_display_viewbookreview.size());
                            reviewrecycle.addItemDecoration(new DividerItemDecoration(context, 0));
                            reviewAdapter = new ReviewAdapter(context, al_display_viewbookreview);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

                            reviewrecycle.setLayoutManager(horizontalLayoutManager);
                            reviewrecycle.setAdapter(reviewAdapter);

                            reviewrecycle.addItemDecoration(new DividerItemDecoration(context, 0));



                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + viewBookReviewResponse.getResponseMessage());
                            Toast.makeText(context,viewBookReviewResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<ViewBookReviewResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

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
    public boolean isOnline() {

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_addreview:
                if (user_id.matches("0"))
                {
                    builder.setMessage("Please Login First To Write Review").setTitle("Pustak Market");
                    //Setting message manually and performing action on button click
                    builder.setMessage("Please Login First To Write Review")
                            .setCancelable(false)
                            .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(context,LoginActivity.class));


                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                              /*  Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();*/
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Pustak Market");
                    alert.show();


                }
                else
                {
                    Intent intente=new Intent(context,AddReviewActivity.class);
                    intente.putExtra("bookid",str_bookid);
                    intente.putExtra("userid",user_id);
                    context.startActivity(intente);

                }
                break;
        }

    }
}
