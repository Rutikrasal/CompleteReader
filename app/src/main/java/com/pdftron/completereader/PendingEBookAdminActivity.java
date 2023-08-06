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

import com.pdftron.completereader.Response.PendingEBooksByAdminResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingEBookAdminActivity extends AppCompatActivity {
    String user_type,user_id;
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG";
    GridView gridView;

    String Pendingebookaction="pending_ebooks";
    PendingEBooksByAdminResponse pendingeBooksByAdminResponse;
    ArrayList<PendingEBooksByAdminResponse.Datum> al_display_mypendingebooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_e_book_admin);

        context=PendingEBookAdminActivity.this;
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);


        user_id= sharedPreferences.getString("user_id", "");
        gridView=findViewById(R.id.grid_view);

        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pending E-Books");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        pendingebooks(Pendingebookaction,user_id);

    }
    private void pendingebooks(String pendingebookaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<PendingEBooksByAdminResponse> pendingEBooksByAdminResponseCall = apiInterface.pendingebooks(pendingebookaction,user_id);

            pendingEBooksByAdminResponseCall.enqueue(new Callback<PendingEBooksByAdminResponse>() {
                @Override
                public void onResponse(Call<PendingEBooksByAdminResponse> call, Response<PendingEBooksByAdminResponse> response) {
                    progressDialog.dismiss();
                    pendingeBooksByAdminResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (pendingeBooksByAdminResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (pendingeBooksByAdminResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + pendingeBooksByAdminResponse.getResponseMessage());
                            al_display_mypendingebooks = new ArrayList<>();
                            for (int i = 0; i < pendingeBooksByAdminResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + pendingeBooksByAdminResponse.getData().size());

                                al_display_mypendingebooks.add(pendingeBooksByAdminResponse.getData().get(i));

                                Log.d(TAG, "onResponse: al" + al_display_mypendingebooks);
                                Log.d(TAG, "onResponse: alsize" + al_display_mypendingebooks.size());




                            }
                            GridPendingEBookbyAdminAdapter gridPendingEBookbyAdminAdapter=new GridPendingEBookbyAdminAdapter(context,al_display_mypendingebooks);
                            gridView.setAdapter(gridPendingEBookbyAdminAdapter);
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
                            Log.d(TAG, "onResponse:registration msg " + pendingeBooksByAdminResponse.getResponseMessage());
                            Toast.makeText(context,pendingeBooksByAdminResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<PendingEBooksByAdminResponse> call, Throwable t) {
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
