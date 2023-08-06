package com.pdftron.completereader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.pdftron.completereader.Response.BookedEventResponse;
import com.pdftron.completereader.Response.MyEventResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedEventActivity extends AppCompatActivity {
    String user_type,user_id;
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG";
    GridView gridView;
    String Mybookedeventaction="my_events";
    BookedEventResponse bookedEventResponse;
    ArrayList<BookedEventResponse.Datum> al_display_bookedevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_event);
        context=BookedEventActivity.this;
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);


        user_id= sharedPreferences.getString("user_id", "");
        gridView=findViewById(R.id.grid_view);

        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Events");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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




                            }
                            BookedEventAdapter gridBookedEventAdapter=new BookedEventAdapter(context,al_display_bookedevent);
                            gridView.setAdapter(gridBookedEventAdapter);
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
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
