package com.pdftron.completereader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.Response.AddBookResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    int totalprice;
    Integer subtotal;
    String TAG = "TAG",actionbookorder="add_book_order",ship_name,email,contact,address,city,zipcode,paymentcheck;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket", user_id;
    RecyclerView recyclerViewcheckout;
    DBcartDataHelper dBcartDataHelper;
    ArrayList<String> bookid = new ArrayList<>();
    ArrayList<String> bookname = new ArrayList<>();
    ArrayList<String> bookprice = new ArrayList<>();
    ArrayList<String> bookquantity = new ArrayList<>();
    ArrayList<Integer> bookpriceint = new ArrayList<>();
    CheckBox checkBox;
    EditText etshipname,etemail,etcontact,etaddress,etzipcode,etcity;
AddBookResponse addBookResponse;


    ArrayList<String> bookimagelink = new ArrayList<>();
    TextView tvtotalbookprice, tvdelverycharges,tvtotal;
    int sum=0;
    Button btnplaceorder;
    String bookids,bookquantitys,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        context = CheckoutActivity.this;
        progressDialog = new ProgressDialog(context);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid" + user_id);
        checkBox=findViewById(R.id.ch_paymentgateway);
etshipname=findViewById(R.id.et_name);
        etemail=findViewById(R.id.et_email);
        etcontact=findViewById(R.id.et_phonenum);
        etaddress=findViewById(R.id.et_address);
        etzipcode=findViewById(R.id.et_zipcode);
        etcity=findViewById(R.id.et_city);
        recyclerViewcheckout = findViewById(R.id.recyclercheckout);
        recyclerViewcheckout.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerViewcheckout.setItemAnimator(new DefaultItemAnimator());
        tvtotalbookprice = findViewById(R.id.tv_totalbookprice);
        tvdelverycharges = findViewById(R.id.tv_shippingcost);
        tvtotal=findViewById(R.id.tv_totalcost);
        btnplaceorder=findViewById(R.id.btn_placeorder);
        btnplaceorder.setOnClickListener(this);
/*
        tvdelverycharges.setText("₹ 50");
*/


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Check Out");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        dBcartDataHelper = new DBcartDataHelper(this);
        Cursor cartdata = dBcartDataHelper.getcartListContents();
        if (cartdata.getCount() == 0) {
            Toast.makeText(CheckoutActivity.this, "The Cart  empty", Toast.LENGTH_SHORT).show();
        } else {
            while (cartdata.moveToNext()) {
                bookid.add(cartdata.getString(1));
                bookname.add(cartdata.getString(2));
                bookprice.add(cartdata.getString(3));
                bookquantity.add(cartdata.getString(4));
                Log.d(TAG, "onCreate: bookquantity" + bookquantity);
                bookimagelink.add(cartdata.getString(5));
                bookids= TextUtils.join(",",bookid);
                Log.d(TAG, "onCreate:bookids "+bookids);
                bookquantitys= TextUtils.join(",",bookquantity);
                Log.d(TAG, "onCreate:bookquantitys "+bookquantitys);

                int result=0;
                for (int i=0;i<bookprice.size();i++)
                {
                    int num1= Integer.parseInt(bookprice.get(i));
                    int num2= Integer.parseInt(bookquantity.get(i));
                    result+=num1*num2;

                }
                Log.d(TAG, "onCreate:result "+result);
                tvtotalbookprice.setText("₹"+ Integer.toString(result));
                tvdelverycharges.setText("₹ 50");
                tvtotal.setText("₹"+ Integer.toString(result+50));
                amount= Integer.toString(result+50);


                CheckoutAdapter checkoutAdapter = new CheckoutAdapter(context, bookid, bookname, bookprice, bookquantity, bookimagelink);
                recyclerViewcheckout.setAdapter(checkoutAdapter);

            }

        }
    }

    @Override
    public void onClick(View view) {
        ship_name=etshipname.getText().toString();
        email=etemail.getText().toString();
        contact=etcontact.getText().toString();
        address=etaddress.getText().toString();
        zipcode=etzipcode.getText().toString();
        city=etcity.getText().toString();
        if (TextUtils.isEmpty(ship_name))
        {
            etshipname.requestFocus();
            etshipname.setError("Enter the Name");
            return;
        }
        if (TextUtils.isEmpty(email))
        {
            etemail.requestFocus();
            etemail.setError("Enter the Email Id");

            return;
        }
        if (TextUtils.isEmpty(contact))
        {
            etcontact.requestFocus();
            etcontact.setError("Enter the Contact Number");

            return;
        }
        if (TextUtils.isEmpty(address))
        {
            etaddress.requestFocus();
            etaddress.setError("Enter the Address");

            return;
        }
        if (TextUtils.isEmpty(zipcode))
        {
            etzipcode.requestFocus();
            etzipcode.setError("Enter the Zipcode");

            return;
        }
        if (TextUtils.isEmpty(city))
        {
            etcity.requestFocus();
            etcity.setError("Enter the City");

            return;
        }
        bookids= TextUtils.join(",",bookid);
        Log.d(TAG, "onCreate:bookids "+bookids);
        bookquantitys= TextUtils.join(",",bookquantity);
        Log.d(TAG, "onCreate:bookquantitys "+bookquantitys);

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
            options.put("description", "Book Payment");
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
        addbookorder(actionbookorder,user_id,ship_name,email,contact,address,city,zipcode,amount,paymentcheck,bookids,bookquantitys,paymentid);

    }

    private void addbookorder(String actionbookorder, String user_id, String ship_name, String email, String contact, String address, String city, String zipcode, String amount, String paymentcheck, String bookids, String bookquantitys, String paymentid) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AddBookResponse> addBookResponseCall = apiInterface.addbookoreder(actionbookorder, user_id, ship_name, email, contact, address,city,zipcode,amount,paymentcheck,bookids,bookquantitys,paymentid);

            addBookResponseCall.enqueue(new Callback<AddBookResponse>() {
                @Override
                public void onResponse(Call<AddBookResponse> call, Response<AddBookResponse> response) {
                    progressDialog.dismiss();
                    addBookResponse = response.body();
                    Log.d(TAG, "onResponse:addENoteOrderResponse " + response.toString());
                    if (addBookResponse != null) {
                        if (addBookResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, addBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:addENoteOrderResponse msg " + addBookResponse.getResponseMessage());
                            startActivity(new Intent(context,MainActivity.class));


                        } else {
                            Log.d(TAG, "onResponse:addEBookOrderResponse msg " + addBookResponse.getResponseMessage());
                            Toast.makeText(context,addBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AddBookResponse> call, Throwable t) {
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
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Payment Cancel", Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();   break;
        }
        return super.onOptionsItemSelected(item);
    }
}

