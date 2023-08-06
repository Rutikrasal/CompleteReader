package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Adapter.EventAdapter;
import com.pdftron.completereader.Response.EventResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {
    Context context;
    Toolbar toolbar;
    GridView gridViewevent;
    EventResponse eventResponse;
    ArrayList<EventResponse.Datum> al_display_events;
    String eventaction="events",TAG="TAG";
    ProgressDialog progressDialog;
    EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        context=EventActivity.this;
        progressDialog=new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Events");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        gridViewevent=findViewById(R.id.grid_events);
        events(eventaction);

    }

    private void events(String eventaction) {
        if (isOnline()) {
            progressDialog.show();
            progressDialog.setMessage(" Please Wait..!! ");
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class)    ;
            Call<EventResponse> eventResponseCall = apiInterface.events(eventaction);

            eventResponseCall.enqueue(new Callback<EventResponse>() {
                @Override
                public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                    progressDialog.dismiss();
                    eventResponse = response.body();
                    Log.d(TAG, "onResponse:login " + response.toString());
                    if (eventResponse != null) {
                        if (eventResponse.getResponseCode().matches("0")) {
                            al_display_events = new ArrayList<>();
                            for (int i = 0; i < eventResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + eventResponse.getData().size());
                                if (eventResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_events.add(eventResponse.getData().get(i));
                                    Log.d(TAG, "onResponse: al" + al_display_events);
                                    Log.d(TAG, "onResponse: alsize" + al_display_events.size());

                                }


                            }

                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                            eventAdapter = new EventAdapter(context, al_display_events);
                              gridViewevent.setAdapter(eventAdapter);


                        } else {
                            Toast.makeText(context, eventResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: msg"+eventResponse.getResponseMessage());
                        }
                    }


                }

                @Override
                public void onFailure(Call<EventResponse> call, Throwable t) {
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
