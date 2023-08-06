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

import com.pdftron.completereader.Response.PublisherEbooksStatusResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublisherEbookorderActivity extends AppCompatActivity {
    String user_type,user_id;
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG";
    String Myebookorderaction="admin_ebook";
    PublisherEbooksStatusResponse publisherEbooksStatusResponse;
    ArrayList<PublisherEbooksStatusResponse.Datum> al_display_myebookorder;
    GridView gridViewebookorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_ebookorder);
        context=PublisherEbookorderActivity.this;
        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Your EBook Orders");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        gridViewebookorder=findViewById(R.id.grid_ebook_order);

        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);


        user_id= sharedPreferences.getString("user_id", "");
        ebookorder(Myebookorderaction,user_id);

    }
    private void ebookorder(String mybookorderaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<PublisherEbooksStatusResponse> publisherEbooksStatusResponseCall = apiInterface.ebookstatus(mybookorderaction,user_id);

            publisherEbooksStatusResponseCall.enqueue(new Callback<PublisherEbooksStatusResponse>() {
                @Override
                public void onResponse(Call<PublisherEbooksStatusResponse> call, Response<PublisherEbooksStatusResponse> response) {
                    progressDialog.dismiss();
                    publisherEbooksStatusResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (publisherEbooksStatusResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (publisherEbooksStatusResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + publisherEbooksStatusResponse.getResponseMessage());
                            al_display_myebookorder = new ArrayList<>();
                            for (int i = 0; i < publisherEbooksStatusResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + publisherEbooksStatusResponse.getData().size());

                                    al_display_myebookorder.add(publisherEbooksStatusResponse.getData().get(i));

                                    Log.d(TAG, "onResponse: al" + al_display_myebookorder);
                                    Log.d(TAG, "onResponse: alsize" + al_display_myebookorder.size());




                            }
                            GridebookorderviewAdapter gridebookorderviewAdapter=new GridebookorderviewAdapter(context,al_display_myebookorder);
                            gridViewebookorder.setAdapter(gridebookorderviewAdapter);
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
                            Log.d(TAG, "onResponse:registration msg " + publisherEbooksStatusResponse.getResponseMessage());
                            Toast.makeText(context,publisherEbooksStatusResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<PublisherEbooksStatusResponse> call, Throwable t) {
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
