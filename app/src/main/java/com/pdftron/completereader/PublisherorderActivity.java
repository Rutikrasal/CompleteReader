package com.pdftron.completereader;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.Adapter.MybookorderAdapter;
import com.pdftron.completereader.Response.MyBookorderResponse;
import com.pdftron.completereader.Response.PublisherAcceptedBooksResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublisherorderActivity extends AppCompatActivity {
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    String TAG="TAG";
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",user_id;

    PublisherAcceptedBooksResponse publisherAcceptedBooksResponse;
    ArrayList<PublisherAcceptedBooksResponse.Datum> al_display_mybookorder;
    MybookorderAdapter mybookorderAdapter;

    GridView gridView;

    String Mybookorderaction="admin_book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisherorder);
        context = PublisherorderActivity.this;
        progressDialog = new ProgressDialog(context);
        gridView=findViewById(R.id.gridbookorders);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid"+user_id);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Your Books Order");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        bookorder(Mybookorderaction,user_id);

    }


    private void bookorder(String mybookorderaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<PublisherAcceptedBooksResponse> publisherAcceptedBooksResponseCall = apiInterface.bookstatus(mybookorderaction,user_id);

            publisherAcceptedBooksResponseCall.enqueue(new Callback<PublisherAcceptedBooksResponse>() {
                @Override
                public void onResponse(Call<PublisherAcceptedBooksResponse> call, Response<PublisherAcceptedBooksResponse> response) {
                    progressDialog.dismiss();
                    publisherAcceptedBooksResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (publisherAcceptedBooksResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (publisherAcceptedBooksResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + publisherAcceptedBooksResponse.getResponseMessage());
                            al_display_mybookorder = new ArrayList<>();
                            for (int i = 0; i < publisherAcceptedBooksResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + publisherAcceptedBooksResponse.getData().size());

                                    al_display_mybookorder.add(publisherAcceptedBooksResponse.getData().get(i));

                                    Log.d(TAG, "onResponse: al" + al_display_mybookorder);
                                    Log.d(TAG, "onResponse: alsize" + al_display_mybookorder.size());




                            }
                            GridPendingbookviewAdapter gridpendingbookviewAdapter=new GridPendingbookviewAdapter(context,al_display_mybookorder);
                            gridView.setAdapter(gridpendingbookviewAdapter);

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
                            Log.d(TAG, "onResponse:registration msg " + publisherAcceptedBooksResponse.getResponseMessage());
                            Toast.makeText(context,publisherAcceptedBooksResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<PublisherAcceptedBooksResponse> call, Throwable t) {
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
}
