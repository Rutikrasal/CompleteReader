package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.Adapter.MybookorderAdapter;
import com.pdftron.completereader.Response.MyBookorderResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookOrderActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    String TAG="TAG";
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",user_id;
    LinearLayout linearEmptyCart;

    MyBookorderResponse myBookorderResponse;
    ArrayList<MyBookorderResponse.Datum> al_display_mybookorder;
    MybookorderAdapter mybookorderAdapter;
    Button btnShopnow;

    RecyclerView recyclerViewmybookorder;
    String Mybookorderaction="book_order";
   // String id="107";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_order);
        context = MyBookOrderActivity.this;
        progressDialog = new ProgressDialog(context);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        linearEmptyCart=findViewById(R.id.linear_empty_cart);
        btnShopnow=findViewById(R.id.btn_shopnow);
        btnShopnow.setOnClickListener(this);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid"+user_id);
        recyclerViewmybookorder=findViewById(R.id.mybookorderrecycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Book Order");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        bookorder(Mybookorderaction,user_id);


    }

    private void bookorder(String mybookorderaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "topbook: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<MyBookorderResponse> myBookorderResponseCall = apiInterface.mybookorder(mybookorderaction,user_id);

            myBookorderResponseCall.enqueue(new Callback<MyBookorderResponse>() {
                @Override
                public void onResponse(Call<MyBookorderResponse> call, Response<MyBookorderResponse> response) {
                    progressDialog.dismiss();
                    myBookorderResponse = response.body();
                    Log.d(TAG, "onResponse:topbook: " + response.toString());
                    if (myBookorderResponse != null) {
                        recyclerViewmybookorder.setVisibility(View.VISIBLE);

                        linearEmptyCart.setVisibility(View.GONE);
                        Log.d(TAG, "onResponse:topbook: ");
                        if (myBookorderResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:book msg " + myBookorderResponse.getResponseMessage());
                            al_display_mybookorder = new ArrayList<>();
                            for (int i = 0; i < myBookorderResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: myBookorderResponsesize" + myBookorderResponse.getData().size());

                                    al_display_mybookorder.add(myBookorderResponse.getData().get(i));

                                    Log.d(TAG, "onResponse: al" + al_display_mybookorder);
                                    Log.d(TAG, "onResponse: alsize" + al_display_mybookorder.size());




                            }
                            recyclerViewmybookorder.addItemDecoration(new DividerItemDecoration(context, 0));

                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                           mybookorderAdapter = new MybookorderAdapter(context, al_display_mybookorder);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MyBookOrderActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewmybookorder.setLayoutManager(horizontalLayoutManager);
                           recyclerViewmybookorder.setAdapter(mybookorderAdapter);

                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + myBookorderResponse.getResponseMessage());
                            Toast.makeText(context,myBookorderResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        linearEmptyCart.setVisibility(View.VISIBLE);
                        recyclerViewmybookorder.setVisibility(View.GONE);

                    }


                }

                @Override
                public void onFailure(Call<MyBookorderResponse> call, Throwable t) {
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
    public void onClick(View v) {
        startActivity(new Intent(context,DashboardActivity.class));
        finish();

    }
}
