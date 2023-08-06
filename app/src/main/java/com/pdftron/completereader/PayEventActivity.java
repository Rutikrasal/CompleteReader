package com.pdftron.completereader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Response.AddEventorderResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayEventActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {
    String payment,speakerid,eventtype,streventdate,eventaddeventaction="add_event_order",streventid,strname,stremail,strcontact,straddress,streventlocation,strzipcode,strcity,streventtime,strfee;
    TextView tvname,tvemail,tvcontact,tvaddress,tvlacation,tvzipcode,tvcity,tvtime,tvfee,tvdate,tveventtype;
    Button btnpay;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG",user_id;
    Context context;
    ProgressDialog progressDialog;
    AddEventorderResponse addEventorderResponse;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_event);
        context=PayEventActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Your Book Event Detalis");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        progressDialog=new ProgressDialog(this);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid" + user_id);

        Bundle bundle = getIntent().getExtras();
        strname = bundle.getString("name");
        stremail = bundle.getString("email");
        strcontact = bundle.getString("contact");
        straddress = bundle.getString("address");
        streventlocation = bundle.getString("eventlocation");
        strzipcode = bundle.getString("zipcode");
        strcity = bundle.getString("city");
        streventtime = bundle.getString("eventtime");
        strfee = bundle.getString("fee");
        streventid = bundle.getString("eventid");
       streventdate=bundle.getString("date");
        eventtype=bundle.getString("eventtype");
        speakerid=bundle.getString("speakerid");
        tvname=findViewById(R.id.tv_name);
        tvemail=findViewById(R.id.tv_email);
        tvcontact=findViewById(R.id.tv_contact);
        tvaddress=findViewById(R.id.tv_address);
        tvlacation=findViewById(R.id.tv_eventlocation);
        tvzipcode=findViewById(R.id.tv_zipcode);
        tvdate=findViewById(R.id.tv_eventdate);
        btnpay=findViewById(R.id.btn_pymandhan);
        tveventtype=findViewById(R.id.tv_eventtype);
        btnpay.setOnClickListener(this);

        tvcity=findViewById(R.id.tv_city);
        tvtime=findViewById(R.id.tv_eventtime);
        tvfee=findViewById(R.id.tv_eventfee);
        tvname.setText(strname);
        tvemail.setText(stremail);
        tvcontact.setText(strcontact);
        tvcity.setText(strcity);
        tvaddress.setText(straddress);
        tvzipcode.setText(strzipcode);
        tvfee.setText(strfee);
        tvlacation.setText(streventlocation);
        tvtime.setText(streventtime);
        tvdate.setText(streventdate);
        tveventtype.setText(eventtype);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_pymandhan:
              // addeventorder(eventaddeventaction,user_id,strname,stremail,strcontact,straddress,streventlocation,strzipcode,streventdate,streventtime,strspeakerid,strfee,eventtype);
                startPayment(strfee);

                break;
        }
    }

    private void startPayment(String amount) {
        int inamount= Integer.parseInt(amount);
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setKeyID("rzp_live_ee6eKwLbCpNfSm");
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Pustak Market");
            options.put("description", "Event Payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://pustakmarket.com/uploads/PM.png");
            options.put("currency", "INR");
            options.put("amount", inamount*100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", "");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }

    }
    @Override
    public void onPaymentSuccess(String paymentid) {
        Toast.makeText(this,"Payment Successfully", Toast.LENGTH_SHORT).show();
        addeventorder(eventaddeventaction,user_id,strname,stremail,strcontact,straddress,streventlocation,strzipcode,streventdate,streventtime,speakerid,streventid,payment,eventtype,strfee);

    }

    private void addeventorder(String eventaddeventaction, String user_id, String strname, String stremail, String strcontact, String straddress, String streventlocation, String strzipcode, String streventdate, String streventtime, String speakerid, String streventid, String payment, String eventtype, String strfee) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AddEventorderResponse> eventorderResponseCall = apiInterface.addeventorder(eventaddeventaction,user_id,strname,stremail,strcontact,straddress,streventlocation,strzipcode,streventdate,streventtime,speakerid,streventid,payment,eventtype,strfee);

            eventorderResponseCall.enqueue(new Callback<AddEventorderResponse>() {
                @Override
                public void onResponse(Call<AddEventorderResponse> call, Response<AddEventorderResponse> response) {
                    progressDialog.dismiss();
                    addEventorderResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (addEventorderResponse != null) {
                        if (addEventorderResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, addEventorderResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + addEventorderResponse.getResponseMessage());
                            startActivity(new Intent(context, DashboardActivity.class));
                            finish();


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + addEventorderResponse.getResponseMessage());
                            Toast.makeText(context,addEventorderResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AddEventorderResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Payment Cancel", Toast.LENGTH_SHORT).show();

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
