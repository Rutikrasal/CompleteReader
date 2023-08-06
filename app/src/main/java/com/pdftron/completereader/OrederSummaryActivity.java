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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Response.AddEBookOrderResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrederSummaryActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {
    AddEBookOrderResponse addEBookOrderResponse;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    Button btnpay;
    Context context;
    String amount,addebookaction="add_ebook_order",TAG="TAG";
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",user_id;
    String str_bookid,str_bookname,paymentcheck,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_bookfrontimage,str_bookindeximage,str_bookbackimage;
CheckBox checkBox;
TextView tvbookname,tvbookprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        context=OrederSummaryActivity.this;
        progressDialog=new ProgressDialog(this);
        checkBox=findViewById(R.id.ch_paymentgateway);
        tvbookname=findViewById(R.id.tv_ebookname);
        tvbookprice=findViewById(R.id.tv_totalbookprice);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Order Summary");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid"+user_id);
        Bundle bundle = getIntent().getExtras();
        str_bookid = bundle.getString("bookid");
        str_bookname = bundle.getString("bookname");
        str_discountprice=bundle.getString("discountprice");
        tvbookname.setText(str_bookname);
        tvbookprice.setText("₹"+str_discountprice);

        btnpay=findViewById(R.id.btn_pay);
        btnpay.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Bundle bundle = getIntent().getExtras();
        amount=bundle.getString("discountprice");
        if (checkBox.isChecked())
        {
            paymentcheck="1";
            startPayment(amount);


        }
        else {
            paymentcheck="0";
            Toast.makeText(context,"please select payment method", Toast.LENGTH_SHORT).show();
           // checkBox.setError("please select payment method");
            checkBox.requestFocus();
            return;

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
            options.put("description", "E-Book Payment");
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
            Toast.makeText(activity, "Error in payment " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String payment_id) {
        Toast.makeText(this,"Payment Successfully", Toast.LENGTH_SHORT).show();
        addebookorder(addebookaction,user_id,str_discountprice,paymentcheck,str_bookid,payment_id);

    }

    private void addebookorder(String addebookaction, String user_id, String str_discountprice, String paymentcheck, String str_bookid, String payment_id) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AddEBookOrderResponse> addEBookOrderResponseCall = apiInterface.addebookorder(addebookaction, user_id, str_discountprice, paymentcheck, str_bookid, payment_id);

            addEBookOrderResponseCall.enqueue(new Callback<AddEBookOrderResponse>() {
                @Override
                public void onResponse(Call<AddEBookOrderResponse> call, Response<AddEBookOrderResponse> response) {
                    progressDialog.dismiss();
                    addEBookOrderResponse = response.body();
                    Log.d(TAG, "onResponse:addEBookOrderResponse " + response.toString());
                    if (addEBookOrderResponse != null) {
                        if (addEBookOrderResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, addEBookOrderResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:addEBookOrderResponse msg " + addEBookOrderResponse.getResponseMessage());
                            startActivity(new Intent(context,DashboardActivity.class));


                        } else {
                            Log.d(TAG, "onResponse:addEBookOrderResponse msg " + addEBookOrderResponse.getResponseMessage());
                            Toast.makeText(context,addEBookOrderResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AddEBookOrderResponse> call, Throwable t) {
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

}
