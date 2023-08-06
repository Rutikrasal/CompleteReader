package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Response.RegisterResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Context context;
    EditText etusername,etmobilenum,etemail,etpassword,etaddress,etcity;
    Spinner spnusertype;
    String str_action="register",str_usertype,str_username,str_mobilenum,str_email,str_password,str_confirmpassword,str_address,str_city,user_id,TAG="TAG",type="U";
    CheckBox chterm;
    TextView tv_alreadyregister;
    Button btnsignup;
    ProgressDialog progressDialog;
    RegisterResponse registerResponse;
    SharedPreferences sharedPreferences;
    String MYPREF = "pustakmarket";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = RegistrationActivity.this;
        progressDialog = new ProgressDialog(context);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);

        etusername=findViewById(R.id.et_username);
        etmobilenum=findViewById(R.id.et_phonenumber);
        etemail=findViewById(R.id.et_email);
        etpassword=findViewById(R.id.et_password);
        etaddress=findViewById(R.id.et_address);
        etcity=findViewById(R.id.et_city);
        spnusertype=findViewById(R.id.spn_usertype);
        chterm=findViewById(R.id.ch_terms);
        tv_alreadyregister=findViewById(R.id.tv_alreadyregister);
        btnsignup=findViewById(R.id.btn_signup);
        btnsignup.setOnClickListener(this);
        tv_alreadyregister.setOnClickListener(this);
        spnusertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().matches("Event Speaker"))
                {
                    str_usertype="ES";
                }
                else
                {
                    str_usertype=adapterView.getItemAtPosition(i).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_signup:
                Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                str_username=etusername.getText().toString();
                str_mobilenum=etmobilenum.getText().toString();
                str_email=etemail.getText().toString();
                str_confirmpassword=etpassword.getText().toString();
                str_address=etaddress.getText().toString();
                str_city=etcity.getText().toString();
                if (TextUtils.isEmpty(str_username))
                {
                    etusername.setError("Please Enter user Name");
                    etusername.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(str_mobilenum))
                {
                    etmobilenum.setError("Please Enter Mobile Number");
                    etmobilenum.requestFocus();
                    return;
                }
                if (str_mobilenum.length() <= 9 || regex.matcher(str_mobilenum).find()) {
                    etmobilenum.requestFocus();
                    etmobilenum.setError("Enter Valid Mobile Number");
                    return;
                }
                if (TextUtils.isEmpty(str_email))
                {
                    etemail.setError("Please Enter Email Id");
                    etemail.requestFocus();
                    return;
                }
                if (!str_email.matches(emailPattern)) {
                    etemail.requestFocus();
                    etemail.setError("Enter Valid Email-Id");
                    return;
                }
                if (TextUtils.isEmpty(str_confirmpassword))
                {
                    etpassword.setError("Please Enter Password");
                    etpassword.requestFocus();
                    return;
                }

                Log.d(TAG, "onClick: str_password"+str_password);
                Log.d(TAG, "onClick:str_confirmpassword "+str_confirmpassword);

                if (TextUtils.isEmpty(str_address))
                {
                    etaddress.setError("Please Enter Address");
                    etaddress.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(str_city))
                {
                    etcity.setError("Please Enter City");
                    etcity.requestFocus();
                    return;
                }



                if (str_usertype.matches("Select User Type"))
                {
                    spnusertype.requestFocus();
                    Toast.makeText(context,"Please Select User Type", Toast.LENGTH_SHORT).show();
                }

                    else {

                        registration(str_action,str_username,str_mobilenum,str_email,str_confirmpassword,str_address,str_city,str_usertype,type);

                    }




                break;
            case R.id.tv_alreadyregister:
                startActivity(new Intent(context,LoginActivity.class));
                finish();
                break;
        }

    }

    private void registration(String str_action, String str_username, String str_mobilenum, String str_email, String str_confirmpassword, String str_address, String str_city, String spnusertype, String type) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RegisterResponse> registrationResponseCall = apiInterface.register(str_action, str_username, str_mobilenum, str_email, str_confirmpassword, str_address,str_city,spnusertype,type);

            registrationResponseCall.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    progressDialog.dismiss();
                    registerResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (registerResponse != null) {
                        if (registerResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, registerResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + registerResponse.getResponseMessage());
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + registerResponse.getResponseMessage());
                            Toast.makeText(context,registerResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
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
