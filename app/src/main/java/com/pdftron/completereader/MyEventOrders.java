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

import com.pdftron.completereader.Response.MyEventOrderResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventOrders extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    String TAG="TAG";
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",user_id="48",myeventorderaction="event_order";
    MyEventOrderResponse myEventOrderResponse;
    ArrayList<MyEventOrderResponse.Datum> al_display_myeventorder;
    RecyclerView recyclerViewmyeventorder;
    MyeventorderAdapter myeventorderAdapter;
    LinearLayout linearEmptyCart;
    Button btnShopnow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_orders);
        context = MyEventOrders.this;
        progressDialog = new ProgressDialog(context);
        linearEmptyCart=findViewById(R.id.linear_empty_cart);
        btnShopnow=findViewById(R.id.btn_shopnow);
        btnShopnow.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid"+user_id);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Event Orders");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        recyclerViewmyeventorder=findViewById(R.id.myeventorderrecycler);

        myevents(myeventorderaction,user_id);


    }

    private void myevents(String myeventorderaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "topbook: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<MyEventOrderResponse> myEventOrderResponseCall = apiInterface.eventorder(myeventorderaction,user_id);

            myEventOrderResponseCall.enqueue(new Callback<MyEventOrderResponse>() {
                @Override
                public void onResponse(Call<MyEventOrderResponse> call, Response<MyEventOrderResponse> response) {
                    progressDialog.dismiss();
                    myEventOrderResponse = response.body();
                    Log.d(TAG, "onResponse:topbook: " + response.toString());
                    if (myEventOrderResponse != null) {
                        Log.d(TAG, "onResponse: eventnotnull");
                        recyclerViewmyeventorder.setVisibility(View.VISIBLE);

                        linearEmptyCart.setVisibility(View.GONE);

                        Log.d(TAG, "onResponse:topbook: ");
                        if (myEventOrderResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:book msg " + myEventOrderResponse.getResponseMessage());
                            al_display_myeventorder = new ArrayList<>();
                            for (int i = 0; i < myEventOrderResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: myBookorderResponsesize" + myEventOrderResponse.getData().size());

                                    al_display_myeventorder.add(myEventOrderResponse.getData().get(i));

                                    Log.d(TAG, "onResponse: al" + al_display_myeventorder);
                                    Log.d(TAG, "onResponse: eventorderalsize" + al_display_myeventorder.size());




                            }
                            recyclerViewmyeventorder.addItemDecoration(new DividerItemDecoration(context, 0));

                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                            myeventorderAdapter = new MyeventorderAdapter(context, al_display_myeventorder);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MyEventOrders.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewmyeventorder.setLayoutManager(horizontalLayoutManager);
                            recyclerViewmyeventorder.setAdapter(myeventorderAdapter);

                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + myEventOrderResponse.getResponseMessage());
                            Toast.makeText(context,myEventOrderResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Log.d(TAG, "onResponse: empty");
                        linearEmptyCart.setVisibility(View.VISIBLE);
                        recyclerViewmyeventorder.setVisibility(View.GONE);

                    }


                }

                @Override
                public void onFailure(Call<MyEventOrderResponse> call, Throwable t) {
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(context,DashboardActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(context,DashboardActivity.class));
        finish();

    }
}
