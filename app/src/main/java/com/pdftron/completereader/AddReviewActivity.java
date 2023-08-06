package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Response.AddBookReviewResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReviewActivity extends AppCompatActivity implements View.OnClickListener {
    String str_bookid,userid,review;
    EditText etreview;
    Button btnaddreview;
    AddBookReviewResponse addBookReviewResponse;
    ProgressDialog progressDialog;
    Toolbar toolbar;

    Context context;
String TAG="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        progressDialog=new ProgressDialog(this);
        context=AddReviewActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Add Review");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getExtras();
        str_bookid = bundle.getString("bookid");
        userid = bundle.getString("userid");
        etreview=findViewById(R.id.et_review);
        btnaddreview=findViewById(R.id.btn_addreview);
        btnaddreview.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_addreview:
                review=etreview.getText().toString();
                if (TextUtils.isEmpty(review))
                {
                    etreview.requestFocus();
                    etreview.setError("Enter the comments first");
                    return;
                }
                else{
                    addbookreview(review,str_bookid,userid);

                }
                break;
        }
    }

    private void addbookreview(String strreview, String str_bookid, String user_id) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AddBookReviewResponse> addBookReviewResponseCall = apiInterface.addreview(strreview, str_bookid, user_id);

            addBookReviewResponseCall.enqueue(new Callback<AddBookReviewResponse>() {
                @Override
                public void onResponse(Call<AddBookReviewResponse> call, Response<AddBookReviewResponse> response) {
                    progressDialog.dismiss();
                    addBookReviewResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (addBookReviewResponse != null) {
                        if (addBookReviewResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, addBookReviewResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + addBookReviewResponse.getResponseMessage());



                        } else {
                            Log.d(TAG, "onResponse:registration msg " + addBookReviewResponse.getResponseMessage());
                            Toast.makeText(context,addBookReviewResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AddBookReviewResponse> call, Throwable t) {
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
