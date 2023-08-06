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

import com.pdftron.completereader.Response.AcceptedEBooksByAdminResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptedEBookAdminActivity extends AppCompatActivity {
    String user_type,user_id;
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG";
    GridView gridView;

    String Acceptedebookaction="accepted_ebooks";
    AcceptedEBooksByAdminResponse acceptedeBooksByAdminResponse;
    ArrayList<AcceptedEBooksByAdminResponse.Datum> al_display_myacceptedebooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_e_book_admin);

        context=AcceptedEBookAdminActivity.this;
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);


        user_id= sharedPreferences.getString("user_id", "");
        gridView=findViewById(R.id.grid_view);

        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Accepted E-Books By Admin");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




        acceptedebooks(Acceptedebookaction,user_id);

    }
    private void acceptedebooks(String acceptedebookaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AcceptedEBooksByAdminResponse> acceptedEBooksByAdminResponseCall = apiInterface.acceptedebooks(acceptedebookaction,user_id);

            acceptedEBooksByAdminResponseCall.enqueue(new Callback<AcceptedEBooksByAdminResponse>() {
                @Override
                public void onResponse(Call<AcceptedEBooksByAdminResponse> call, Response<AcceptedEBooksByAdminResponse> response) {
                    progressDialog.dismiss();
                    acceptedeBooksByAdminResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (acceptedeBooksByAdminResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (acceptedeBooksByAdminResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + acceptedeBooksByAdminResponse.getResponseMessage());
                            al_display_myacceptedebooks = new ArrayList<>();
                            for (int i = 0; i < acceptedeBooksByAdminResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + acceptedeBooksByAdminResponse.getData().size());

                                al_display_myacceptedebooks.add(acceptedeBooksByAdminResponse.getData().get(i));

                                Log.d(TAG, "onResponse: al" + al_display_myacceptedebooks);
                                Log.d(TAG, "onResponse: alsize" + al_display_myacceptedebooks.size());




                            }
                            GridAcceptedEBookbyAdminAdapter gridAcceptedEBookbyAdminAdapter=new GridAcceptedEBookbyAdminAdapter(context,al_display_myacceptedebooks);
                            gridView.setAdapter(gridAcceptedEBookbyAdminAdapter);
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
                            Log.d(TAG, "onResponse:registration msg " + acceptedeBooksByAdminResponse.getResponseMessage());
                            Toast.makeText(context,acceptedeBooksByAdminResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AcceptedEBooksByAdminResponse> call, Throwable t) {
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
