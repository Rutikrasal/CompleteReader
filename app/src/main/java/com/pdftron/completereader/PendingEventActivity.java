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

import com.pdftron.completereader.Response.AcceptedEventResponse;
import com.pdftron.completereader.Response.PendingEventResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingEventActivity extends AppCompatActivity {
    String user_type,user_id;
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG";
    GridView gridView;
    String Pendingeventaction="pending_events";
    PendingEventResponse pendingEventResponse;
    ArrayList<PendingEventResponse.Datum> al_display_mypendingevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_event);
        context=PendingEventActivity.this;
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);


        user_id= sharedPreferences.getString("user_id", "");
        gridView=findViewById(R.id.grid_pending_event);

        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pending Events");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        pendingevent(Pendingeventaction,user_id);

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




                            }
                            PendingEventAdapter gridPendingEventAdapter=new PendingEventAdapter(context,al_display_mypendingevent);
                            gridView.setAdapter(gridPendingEventAdapter);
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
