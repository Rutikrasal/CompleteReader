package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pdftron.completereader.Adapter.SearchAdapter;
import com.pdftron.completereader.Response.SearchResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigation;
    EditText etsearch;
    Button btnsearch;
    RecyclerView searchrecycler;
    Context context;
    String str_search,searchaction="search_book",TAG="TAG";
    SearchResponse searchResponse;
    ArrayList<SearchResponse.Datum> al_display_searchbook;
    SearchAdapter searchAdapter;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    TextView tvbooksebooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context=SearchActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tvbooksebooks=findViewById(R.id.tv_booksebooks);
        tvbooksebooks.setOnClickListener(this);

        etsearch=findViewById(R.id.et_search);
        btnsearch=findViewById(R.id.btn_search);
        searchrecycler=findViewById(R.id.recycle_searchbook);
        btnsearch.setOnClickListener(this);
        progressDialog=new ProgressDialog(this);
        bottomNavigation = findViewById(R.id.bottom_navigationsearch);
        bottomNavigation.setSelectedItemId(R.id.navigation_search);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_search:
                        return true;
                    case R.id.navigation_books:
                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.navigation_cart:

                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.navigation_account:
                        startActivity(new Intent(getApplicationContext(),Account.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_search:
                str_search=etsearch.getText().toString();
                search(searchaction,str_search);
                break;


            case R.id.tv_booksebooks:
                startActivity(new Intent(context,SearchActivity.class));

                break;

        }

    }

    private void search(String searchaction, String str_search) {

        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<SearchResponse> searchResponseCall = apiInterface.searchbook(searchaction,str_search);

            searchResponseCall.enqueue(new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                    progressDialog.dismiss();
                    searchResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (searchResponse != null) {
                        if (searchResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + searchResponse.getResponseMessage());
                            al_display_searchbook = new ArrayList<>();
                            for (int i = 0; i < searchResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + searchResponse.getData().size());
                                al_display_searchbook.add(searchResponse.getData().get(i));
                                Log.d(TAG, "onResponse: al" + al_display_searchbook);
                                Log.d(TAG, "onResponse: alsize" + al_display_searchbook.size());


                            }
                            searchrecycler.addItemDecoration(new DividerItemDecoration(SearchActivity.this, LinearLayoutManager.VERTICAL));
                            searchAdapter = new SearchAdapter(context, al_display_searchbook);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
                            searchrecycler.setLayoutManager(horizontalLayoutManager);
                            searchrecycler.setAdapter(searchAdapter);

                            searchrecycler.addItemDecoration(new DividerItemDecoration(context, 0));



                        } else {
                            Log.d(TAG, "onResponse:registration msg " + searchResponse.getResponseMessage());
                            Toast.makeText(context,searchResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
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
                startActivity(new Intent(context,DashboardActivity.class));
                finish();                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }
}
