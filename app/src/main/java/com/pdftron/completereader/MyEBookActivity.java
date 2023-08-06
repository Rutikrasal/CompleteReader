package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.multidex.MultiDex;

import com.pdftron.completereader.Response.ViewTopebookResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEBookActivity extends AppCompatActivity {


    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    String TAG="TAG";
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",user_id,viewtopebookaction="top_ebooks";
    ViewTopebookResponse viewTopebookResponse;
    ArrayList<ViewTopebookResponse.Datum> al_display_viewebooks ;
    String strbookname,strbookpdf;
    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_e_book);
        MultiDex.install(this);

        context = MyEBookActivity.this;
        progressDialog = new ProgressDialog(context);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuseridviewtopebook"+user_id);
        Log.d(TAG, "onCreate:viewtopebookaction "+viewtopebookaction);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My  E-Book Orders");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        gridView=findViewById(R.id.grid_ebook);




        viewtopebook(viewtopebookaction,user_id);
     /*   ArrayList<BookModel> models = new ArrayList<>();

        models.add(new BookModel("http://eurodroid.com/pics/beginning_android_book.jpg", "1", "Beginning Android"));

        shelfView.loadData(models, ShelfView.BOOK_SOURCE_URL);
*/
    }

    private void viewtopebook(String viewtopebookaction, String user_id) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ViewTopebookResponse> viewTopebookResponseCall = apiInterface.viewtopebook(viewtopebookaction,user_id);

            viewTopebookResponseCall.enqueue(new Callback<ViewTopebookResponse>() {
                @Override
                public void onResponse(Call<ViewTopebookResponse> call, Response<ViewTopebookResponse> response) {
                    progressDialog.dismiss();
                    viewTopebookResponse = response.body();
                    Log.d(TAG, "onResponse:viewTopebookResponse " + response.toString());
                    if (viewTopebookResponse != null) {
                        Log.d(TAG, "onResponse: viewTopebookResponse notnull");
                        if (viewTopebookResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:viewTopebookResponse msg " + viewTopebookResponse.getResponseMessage());
                            al_display_viewebooks = new ArrayList<>();
                            for (int i = 0; i < viewTopebookResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: viewTopebookResponse" + viewTopebookResponse.getData().size());
                                if (viewTopebookResponse.getData().get(i).getPaymentStatus().matches("Yes")||viewTopebookResponse.getData().get(i).getIsFree().matches("1"))
                                {
                                    al_display_viewebooks.add(viewTopebookResponse.getData().get(i));
                                    Log.d(TAG, "onResponse:viewTopebookResponse al" + al_display_viewebooks);
                                    Log.d(TAG, "onResponse: viewTopebookResponse" + al_display_viewebooks.size());


                                }


                            }

                            GridyourEbookorderviewAdapter gridyourEbookorderviewAdapter = new GridyourEbookorderviewAdapter(context, al_display_viewebooks);
                            gridView.setAdapter(gridyourEbookorderviewAdapter);


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + viewTopebookResponse.getResponseMessage());
                            Toast.makeText(context,viewTopebookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<ViewTopebookResponse> call, Throwable t) {
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
