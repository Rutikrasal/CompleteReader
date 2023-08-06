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

import com.pdftron.completereader.Response.CategorywiseBooksResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorywiseBookviewActivity extends AppCompatActivity {
    Context context;
    Toolbar toolbar;
    GridView gridViewcatwisebook;
    ProgressDialog progressDialog;
        String strcatname,strcatid,strcatdescription,strcattag,catwisebookaction="book_by_category",TAG="TAG";
    CategorywiseBooksResponse categorywiseBooksResponse;
    ArrayList<CategorywiseBooksResponse.Datum> al_display_catwisebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorywise_bookview);
        context=CategorywiseBookviewActivity.this;
        Bundle bundle = getIntent().getExtras();
        strcatname = bundle.getString("categoryname");
        strcatid = bundle.getString("categoryid");
        strcatdescription=bundle.getString("categorydescription");
        strcattag=bundle.getString("categorytag");



        progressDialog=new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(strcatname);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        gridViewcatwisebook=findViewById(R.id.grid_catwisebook);
       catwisebooks(catwisebookaction,strcatid);

    }

    private void catwisebooks(String catwisebookaction, String strcatid) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<CategorywiseBooksResponse> categorywiseBooksResponseCall = apiInterface.categorywisebooks(catwisebookaction,strcatid);

            categorywiseBooksResponseCall.enqueue(new Callback<CategorywiseBooksResponse>() {
                @Override
                public void onResponse(Call<CategorywiseBooksResponse> call, Response<CategorywiseBooksResponse> response) {
                    progressDialog.dismiss();
                    categorywiseBooksResponse = response.body();
                    Log.d(TAG, "onResponse:studenteBookResponse " + response.toString());
                    if (categorywiseBooksResponse != null) {
                        if (categorywiseBooksResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:studenteBookResponse msg " + categorywiseBooksResponse.getResponseMessage());
                            al_display_catwisebook = new ArrayList<>();
                            for (int i = 0; i < categorywiseBooksResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: studenteBookResponsesize" + categorywiseBooksResponse.getData().size());
                                if (categorywiseBooksResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_catwisebook.add(categorywiseBooksResponse.getData().get(i));
                                    Log.d(TAG, "onResponse:studenteBookResponse al" + al_display_catwisebook);
                                    Log.d(TAG, "onResponse: studenteBookResponsealsize" + al_display_catwisebook.size());

                                }


                            }
                            GridcatwisebookviewAdapter gridcatwisebookviewAdapter=new GridcatwisebookviewAdapter(context,al_display_catwisebook);
                            gridViewcatwisebook.setAdapter(gridcatwisebookviewAdapter);


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + categorywiseBooksResponse.getResponseMessage());
                            Toast.makeText(context,categorywiseBooksResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<CategorywiseBooksResponse> call, Throwable t) {
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
