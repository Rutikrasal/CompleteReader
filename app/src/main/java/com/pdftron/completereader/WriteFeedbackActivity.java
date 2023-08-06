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

import com.pdftron.completereader.Response.AddfeedbackResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteFeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etfeedback;
    Button btnsendfeedback;
    Context context;
    Toolbar toolbar;
    String name,email,strfeedback,feedbackaction="feedback",TAG="TAG";
    ProgressDialog progressDialog;
    AddfeedbackResponse addfeedbackResponse;

 String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_feedback);

        context=WriteFeedbackActivity.this;
        progressDialog=new ProgressDialog(this);
        Log.d(TAG, "onCreate: uuuserid"+user_id);
        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("Write Feedback");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        etfeedback=findViewById(R.id.et_feedback);
        btnsendfeedback=findViewById(R.id.btn_addfedback);



        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        email = bundle.getString("email");
        user_id = bundle.getString("userid");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_addfedback:
                strfeedback=etfeedback.getText().toString();
                if (TextUtils.isEmpty(strfeedback))
                {
                    etfeedback.setError("Enter The Feedback");
                    etfeedback.requestFocus();
                    return;
                }
                else {
                    addfeedback(name,email,strfeedback);

                }
                break;
        }
    }
    private void addfeedback(String name, String email, String strfeedback) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AddfeedbackResponse> addfeedbackResponseCall = apiInterface.addfeedback(name, email, strfeedback);

            addfeedbackResponseCall.enqueue(new Callback<AddfeedbackResponse>() {
                @Override
                public void onResponse(Call<AddfeedbackResponse> call, Response<AddfeedbackResponse> response) {
                    progressDialog.dismiss();
                    addfeedbackResponse = response.body();
                    Log.d(TAG, "onResponse:addfeedbackResponse " + response.toString());
                    if (addfeedbackResponse != null) {
                        if (addfeedbackResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, addfeedbackResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:addfeedbackResponse msg " + addfeedbackResponse.getResponseMessage());


                        } else {
                            Log.d(TAG, "onResponse:addfeedbackResponse msg " + addfeedbackResponse.getResponseMessage());
                            Toast.makeText(context,addfeedbackResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AddfeedbackResponse> call, Throwable t) {
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
