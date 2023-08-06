package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.Adapter.CategoryAdapter;
import com.pdftron.completereader.Response.AllCategoryResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    String categoryaction="category";
    AllCategoryResponse allCategoryResponse;
    ArrayList<AllCategoryResponse.Datum> al_display_category;
    RecyclerView category_recycle;
    CategoryAdapter categoryAdapter;
    String TAG="TAG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        context = CategoryActivity.this;
        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Categories");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        category_recycle=findViewById(R.id.recycler_category);

        category(categoryaction);

    }

    private void category(String categoryaction) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AllCategoryResponse> allCategoryResponseCall = apiInterface.allcategory(categoryaction);

            allCategoryResponseCall.enqueue(new Callback<AllCategoryResponse>() {
                @Override
                public void onResponse(Call<AllCategoryResponse> call, Response<AllCategoryResponse> response) {
                    progressDialog.dismiss();
                    allCategoryResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (allCategoryResponse != null) {
                        if (allCategoryResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + allCategoryResponse.getResponseMessage());
                            al_display_category = new ArrayList<>();
                            for (int i = 0; i < allCategoryResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + allCategoryResponse.getData().size());
                                al_display_category.add(allCategoryResponse.getData().get(i));
                                Log.d(TAG, "onResponse: al" + al_display_category);
                                Log.d(TAG, "onResponse: alsize" + al_display_category.size());


                            }
                            category_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
                            categoryAdapter = new CategoryAdapter(context, al_display_category);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                            category_recycle.setLayoutManager(horizontalLayoutManager);
                            category_recycle.setAdapter(categoryAdapter);

                            category_recycle.addItemDecoration(new DividerItemDecoration(context, 0));



                        } else {
                            Log.d(TAG, "onResponse:registration msg " + allCategoryResponse.getResponseMessage());
                            Toast.makeText(context,allCategoryResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AllCategoryResponse> call, Throwable t) {
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
