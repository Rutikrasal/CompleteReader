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

import com.pdftron.completereader.Response.Books_By_PublisherResponse;
import com.pdftron.completereader.Response.EBooks_By_PublisherResponse;
import com.pdftron.completereader.Response.Magazines_By_PublisherResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBooksActivity extends AppCompatActivity {
    Context context;
    Toolbar toolbar;
    GridView gridViewmybooks;
    ProgressDialog progressDialog;
    String user_id,MyBooksaction="book_by_publisher",MyEBooksaction="ebook_by_publisher",MyMagazineaction="magazine_by_publisher",toolbarname;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket";
    Books_By_PublisherResponse books_by_publisherResponse;
    ArrayList<Books_By_PublisherResponse.Datum> al_display_books;
    String TAG="TAG";


    EBooks_By_PublisherResponse ebooks_by_publisherResponse;
    ArrayList<EBooks_By_PublisherResponse.Datum> al_display_ebooks;

    Magazines_By_PublisherResponse magazines_by_publisherResponse;
    ArrayList<Magazines_By_PublisherResponse.Datum> al_display_magazines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        context=MyBooksActivity.this;
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);

        progressDialog=new ProgressDialog(this);
        gridViewmybooks=findViewById(R.id.grid_my_books);
        Bundle bundle = getIntent().getExtras();
        toolbarname = bundle.getString("toolbar");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(toolbarname);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        user_id = sharedPreferences.getString("user_id", "");
        if (toolbarname.matches("My Book List"))
        {
            mybooklist(MyBooksaction,user_id);


        }
        else if (toolbarname.matches("My E-Book List"))
        {
            myebooklist(MyEBooksaction,user_id);

        }
        else if (toolbarname.matches("My Magazine List"))
        {
            mymagazinelist(MyMagazineaction,user_id);

        }



    }

    private void mymagazinelist(String myMagazineaction, String user_id) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Magazines_By_PublisherResponse> magazines_by_publisherResponseCall = apiInterface.magazines(myMagazineaction,user_id);

            magazines_by_publisherResponseCall.enqueue(new Callback<Magazines_By_PublisherResponse>() {
                @Override
                public void onResponse(Call<Magazines_By_PublisherResponse> call, Response<Magazines_By_PublisherResponse> response) {
                    progressDialog.dismiss();
                    magazines_by_publisherResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (magazines_by_publisherResponse != null) {
                        Log.d(TAG, "onResponse:topBookResponse ");
                        if (magazines_by_publisherResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:book msg " + magazines_by_publisherResponse.getResponseMessage());
                            al_display_magazines = new ArrayList<>();
                            for (int i = 0; i < magazines_by_publisherResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + magazines_by_publisherResponse.getData().size());

                                al_display_magazines.add(magazines_by_publisherResponse.getData().get(i));

                                Log.d(TAG, "onResponse: al" + al_display_magazines);
                                Log.d(TAG, "onResponse: alsize" + al_display_magazines.size());




                            }
                            Gridmagazinebypublisher gridmagazinebypublisher = new Gridmagazinebypublisher(context, al_display_magazines);
                            gridViewmybooks.setAdapter(gridmagazinebypublisher);


                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + magazines_by_publisherResponse.getResponseMessage());
                            Toast.makeText(context,magazines_by_publisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<Magazines_By_PublisherResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void myebooklist(String myEBooksaction, String user_id) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<EBooks_By_PublisherResponse> ebooks_by_publisherResponseCall = apiInterface.ebooks(myEBooksaction,user_id);

            ebooks_by_publisherResponseCall.enqueue(new Callback<EBooks_By_PublisherResponse>() {
                @Override
                public void onResponse(Call<EBooks_By_PublisherResponse> call, Response<EBooks_By_PublisherResponse> response) {
                    progressDialog.dismiss();
                    ebooks_by_publisherResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (ebooks_by_publisherResponse != null) {
                        Log.d(TAG, "onResponse:topBookResponse ");
                        if (ebooks_by_publisherResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:book msg " + ebooks_by_publisherResponse.getResponseMessage());
                            al_display_ebooks = new ArrayList<>();
                            for (int i = 0; i < ebooks_by_publisherResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + ebooks_by_publisherResponse.getData().size());

                                al_display_ebooks.add(ebooks_by_publisherResponse.getData().get(i));

                                Log.d(TAG, "onResponse: al" + al_display_books);
                                Log.d(TAG, "onResponse: alsize" + al_display_ebooks.size());




                            }
                            Gridebooksbypublisher gridebooksbypublisher = new Gridebooksbypublisher(context, al_display_ebooks);
                            gridViewmybooks.setAdapter(gridebooksbypublisher);


                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + ebooks_by_publisherResponse.getResponseMessage());
                            Toast.makeText(context,ebooks_by_publisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<EBooks_By_PublisherResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void mybooklist(String myBooksaction, String user_id) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Books_By_PublisherResponse> books_by_publisherResponseCall = apiInterface.books(myBooksaction,user_id);

            books_by_publisherResponseCall.enqueue(new Callback<Books_By_PublisherResponse>() {
                @Override
                public void onResponse(Call<Books_By_PublisherResponse> call, Response<Books_By_PublisherResponse> response) {
                    progressDialog.dismiss();
                    books_by_publisherResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (books_by_publisherResponse != null) {
                        Log.d(TAG, "onResponse:topBookResponse ");
                        if (books_by_publisherResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:book msg " + books_by_publisherResponse.getResponseMessage());
                            al_display_books = new ArrayList<>();
                            for (int i = 0; i < books_by_publisherResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + books_by_publisherResponse.getData().size());

                                    al_display_books.add(books_by_publisherResponse.getData().get(i));

                                    Log.d(TAG, "onResponse: al" + al_display_books);
                                    Log.d(TAG, "onResponse: alsize" + al_display_books.size());




                            }
                            Gridbooksbypublisher gridbooksbypublisher = new Gridbooksbypublisher(context, al_display_books);
                            gridViewmybooks.setAdapter(gridbooksbypublisher);

                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + books_by_publisherResponse.getResponseMessage());
                            Toast.makeText(context,books_by_publisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<Books_By_PublisherResponse> call, Throwable t) {
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
