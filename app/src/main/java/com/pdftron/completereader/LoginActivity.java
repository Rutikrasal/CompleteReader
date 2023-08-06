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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Response.LoginResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    EditText etemail,etpassword;
    TextView tvforgetpassword,tvregistration;
    Button btnlogin,btnskip;
    String str_email,str_password, user_id="0";
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG",action="login";
    LoginResponse loginResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
        progressDialog = new ProgressDialog(context);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);


        etemail=findViewById(R.id.et_username);
        etpassword=findViewById(R.id.et_password);
        tvforgetpassword=findViewById(R.id.tv_forgot_pass);
        tvregistration=findViewById(R.id.tv_new_account);
        btnlogin=findViewById(R.id.btn_login);
        btnskip=findViewById(R.id.btn_skip);
        tvforgetpassword.setOnClickListener(this);
        btnlogin.setOnClickListener(this);
        btnskip.setOnClickListener(this);
        tvregistration.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_forgot_pass:
                break;
            case R.id.btn_login:
                str_email=etemail.getText().toString();
                str_password=etpassword.getText().toString();
                if (TextUtils.isEmpty(str_email))
                {
                    etemail.requestFocus();
                    etemail.setError("Enter the Email");
                    return;
                }
                if (TextUtils.isEmpty(str_password))
                {
                    etpassword.requestFocus();
                    etpassword.setError("Enter the Password");
                    return;
                }

                loginuser(action, str_email, str_password);

                break;
            case R.id.tv_new_account:
                startActivity(new Intent(context, RegistrationActivity.class));

                break;
            case R.id.btn_skip:
                Log.d(TAG, "onClick:user_id "+user_id);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user_id", user_id);


                editor.commit();
                editor.apply();


                Intent intent = new Intent(context, DashboardActivity.class);

                startActivity(intent);
                finish();

                break;
        }

    }

    private void loginuser(String action, String str_email, String str_password) {
        if (isOnline()) {
            progressDialog.show();
            progressDialog.setMessage("Login, Please Wait..!! ");
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class)    ;
            Call<LoginResponse> loginResponseCall = apiInterface.login(action, str_email, str_password);

            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    progressDialog.dismiss();
                    loginResponse = response.body();
                    Log.d(TAG, "onResponse:login " + response.toString());
                    if (loginResponse != null) {
                        if (loginResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, loginResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "onResponse:msg " + loginResponse.getResponseMessage());
                            Log.d(TAG, "onResponse:msg " + loginResponse.getData().getId());
                            user_id =loginResponse.getData().getId().toString();
                            String username=loginResponse.getData().getName();
                            String useraddress=loginResponse.getData().getCity()+loginResponse.getData().getAddress();
                            String usermobilenum=loginResponse.getData().getContact();
                            String useremail=loginResponse.getData().getEmail();
                            String usertype=loginResponse.getData().getUserType();
                            String usercity=loginResponse.getData().getCity();

                            Log.d(TAG, "onResponse:str id " + user_id);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_id", user_id);
                            editor.putString("username", username);
                            editor.putString("useraddress", useraddress);
                            editor.putString("usermobilenum", usermobilenum);
                            editor.putString("useremail", useremail);
                            editor.putString("usertype", usertype);
                            editor.putString("usercity", usercity);





                            editor.commit();
                            editor.apply();


                            Intent intent = new Intent(context, DashboardActivity.class);
                            /*intent.putExtra("id", user_id);
                            intent.putExtra("apikey",str_api);*/
                            startActivity(intent);
                            finish();
                        }
                        else if(loginResponse.getResponseCode().equals(1))
                        {
                            Toast.makeText(context, loginResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "loginonResponse: msg"+loginResponse.getResponseMessage());
                        }
                    }


                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
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

}
