package com.pdftron.completereader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdftron.completereader.Response.AcceptedEventResponse;
import com.pdftron.completereader.Response.BookedEventResponse;
import com.pdftron.completereader.Response.PendingEventResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDashboardActivity extends AppCompatActivity implements View.OnClickListener {
    String user_type,user_id;
    Toolbar toolbar;
    Context context;
    TextView tvacceptedeventcount,tvpendingeventcount,tvbookeventcount;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG";
    RelativeLayout rl_1,rl_2,rl_3,rl_4,rl_5;
    String Acceptedeventaction="accepted_events";
    AcceptedEventResponse acceptedEventResponse;
    ArrayList<AcceptedEventResponse.Datum> al_display_myacceptedevent;

    String Pendingeventaction="pending_events";
    PendingEventResponse pendingEventResponse;
    ArrayList<PendingEventResponse.Datum> al_display_mypendingevent;

    String Mybookedeventaction="my_events";
    BookedEventResponse bookedEventResponse;
    ArrayList<BookedEventResponse.Datum> al_display_bookedevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dashboard);
        context=EventDashboardActivity.this;
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);


        user_id= sharedPreferences.getString("user_id", "");

        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Event Dashboard");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tvacceptedeventcount=findViewById(R.id.tv_acceptedeventsize);
        tvpendingeventcount=findViewById(R.id.tv_pendingeventsize);
        tvbookeventcount=findViewById(R.id.tv_bookedeventsize);
        rl_1=findViewById(R.id.rl1);
        rl_1.setOnClickListener(this);
        rl_2=findViewById(R.id.rl2);
        rl_2.setOnClickListener(this);
        rl_3=findViewById(R.id.rl3);
        rl_3.setOnClickListener(this);
        rl_4=findViewById(R.id.rl4);
        rl_4.setOnClickListener(this);
        rl_5=findViewById(R.id.rl5);
        rl_5.setOnClickListener(this);
        acceptedevent(Acceptedeventaction,user_id);
        pendingevent(Pendingeventaction,user_id);
        bookedevent(Mybookedeventaction,user_id);


    }
    private void bookedevent(String mybookedeventaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<BookedEventResponse> bookedEventResponseCall = apiInterface.bookedevent(mybookedeventaction,user_id);

            bookedEventResponseCall.enqueue(new Callback<BookedEventResponse>() {
                @Override
                public void onResponse(Call<BookedEventResponse> call, Response<BookedEventResponse> response) {
                    progressDialog.dismiss();
                    bookedEventResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (bookedEventResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (bookedEventResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + bookedEventResponse.getResponseMessage());
                            al_display_bookedevent = new ArrayList<>();
                            for (int i = 0; i < bookedEventResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + bookedEventResponse.getData().size());

                                al_display_bookedevent.add(bookedEventResponse.getData().get(i));

                                Log.d(TAG, "onResponse: al" + al_display_bookedevent);
                                Log.d(TAG, "onResponse: alsize" + al_display_bookedevent.size());
                                tvbookeventcount.setText(String.valueOf(al_display_bookedevent.size()));




                            }
                         /*   BookedEventAdapter gridBookedEventAdapter=new BookedEventAdapter(context,al_display_bookedevent);
                            gridView.setAdapter(gridBookedEventAdapter);
                         */   // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                        /*    mybookorderAdapter = new MybookorderAdapter(context, al_display_mybookorder);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(PublisherPendingorderActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewmybookorder.setLayoutManager(horizontalLayoutManager);
                            recyclerViewmybookorder.setAdapter(mybookorderAdapter);
*/
                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + bookedEventResponse.getResponseMessage());
                            Toast.makeText(context,bookedEventResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<BookedEventResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void pendingevent(String pendingeventaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<PendingEventResponse> pendingEventResponseCall = apiInterface.pendingevent(pendingeventaction,user_id);

            pendingEventResponseCall.enqueue(new Callback<PendingEventResponse>() {
                @Override
                public void onResponse(Call<PendingEventResponse> call, Response<PendingEventResponse> response) {
                    progressDialog.dismiss();
                    pendingEventResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (pendingEventResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (pendingEventResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + pendingEventResponse.getResponseMessage());
                            al_display_mypendingevent = new ArrayList<>();
                            for (int i = 0; i < pendingEventResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + pendingEventResponse.getData().size());

                                al_display_mypendingevent.add(pendingEventResponse.getData().get(i));

                                Log.d(TAG, "onResponse: al" + al_display_mypendingevent);
                                Log.d(TAG, "onResponse: alsize" + al_display_mypendingevent.size());
                                tvpendingeventcount.setText(String.valueOf(al_display_mypendingevent.size()));




                            }
                          /*  PendingEventAdapter gridPendingEventAdapter=new PendingEventAdapter(context,al_display_mypendingevent);
                            gridView.setAdapter(gridPendingEventAdapter);
                          */  // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                        /*    mybookorderAdapter = new MybookorderAdapter(context, al_display_mybookorder);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(PublisherPendingorderActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewmybookorder.setLayoutManager(horizontalLayoutManager);
                            recyclerViewmybookorder.setAdapter(mybookorderAdapter);
*/
                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + pendingEventResponse.getResponseMessage());
                            Toast.makeText(context,pendingEventResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<PendingEventResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void acceptedevent(String acceptedeventaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AcceptedEventResponse> acceptedEventResponseCall = apiInterface.acceptedevent(acceptedeventaction,user_id);

            acceptedEventResponseCall.enqueue(new Callback<AcceptedEventResponse>() {
                @Override
                public void onResponse(Call<AcceptedEventResponse> call, Response<AcceptedEventResponse> response) {
                    progressDialog.dismiss();
                    acceptedEventResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (acceptedEventResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (acceptedEventResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + acceptedEventResponse.getResponseMessage());
                            al_display_myacceptedevent = new ArrayList<>();
                            for (int i = 0; i < acceptedEventResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + acceptedEventResponse.getData().size());

                                al_display_myacceptedevent.add(acceptedEventResponse.getData().get(i));

                                Log.d(TAG, "onResponse: al" + al_display_myacceptedevent);
                                Log.d(TAG, "onResponse: alsize" + al_display_myacceptedevent.size());
                                tvacceptedeventcount.setText(String.valueOf(al_display_myacceptedevent.size()));




                            }
                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                        /*    mybookorderAdapter = new MybookorderAdapter(context, al_display_mybookorder);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(PublisherPendingorderActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewmybookorder.setLayoutManager(horizontalLayoutManager);
                            recyclerViewmybookorder.setAdapter(mybookorderAdapter);
*/
                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + acceptedEventResponse.getResponseMessage());
                            Toast.makeText(context,acceptedEventResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AcceptedEventResponse> call, Throwable t) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl1:
                startActivity(new Intent(context, AddEventActivity.class));

                break;
            case R.id.rl2:
                startActivity(new Intent(context, MyEventActivity.class));

                break;
            case R.id.rl3:
                startActivity(new Intent(context, BookedEventActivity.class));

                break;
            case R.id.rl4:
                startActivity(new Intent(context, AcceptedEventActivity.class));

                break;
            case R.id.rl5:
                startActivity(new Intent(context, PendingEventActivity.class));

                break;
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
}
