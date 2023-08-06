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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Response.UpdateProfileResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etusername,etmobilenum,etemail,etpassword,etconfirmpassword,etaddress,etcity;
    Spinner spnusertype;
    String user_type,str_action="edit",str_usertype,str_username,str_mobilenum,str_email,str_password,str_confirmpassword,str_address,str_city,TAG="TAG",type="U";
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket";
    String user_id;
    Context context;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    Button btnupdate;
    String username,useraddress,usermobilenum,useremail,usertype,usercity;
    UpdateProfileResponse updateProfileResponse;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        context = UpdateProfileActivity.this;
        sharedPreferences=getSharedPreferences(MYPREF,MODE_PRIVATE);
        user_id=sharedPreferences.getString("user_id","");
        Log.d(TAG, "onCreate:user_id "+user_id);

        progressDialog = new ProgressDialog(context);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Update Your Account");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etusername=findViewById(R.id.et_username);
        etmobilenum=findViewById(R.id.et_phonenumber);
        etemail=findViewById(R.id.et_email);
        etpassword=findViewById(R.id.et_password);
        etaddress=findViewById(R.id.et_address);
        etcity=findViewById(R.id.et_city);
        spnusertype=findViewById(R.id.spn_usertype);
        btnupdate=findViewById(R.id.btn_update);
        btnupdate.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        username=bundle.getString("username");
        useraddress = bundle.getString("useraddress");
        usermobilenum = bundle.getString("usermobilenum");
        useremail=bundle.getString("useremail");
        usercity=bundle.getString("usercity");
        user_type=bundle.getString("usertype");

        etusername.setText(username);
        etaddress.setText(useraddress);
        etcity.setText(usercity);
        etmobilenum.setText(usermobilenum);
        etemail.setText(useremail);






        spnusertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_usertype=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        str_username=etusername.getText().toString();
        Log.d(TAG, "onClick:str_username "+str_username);
        str_mobilenum=etmobilenum.getText().toString();
        Log.d(TAG, "onClick:str_mobilenum "+str_mobilenum);
        str_email=etemail.getText().toString();
        Log.d(TAG, "onClick:str_email "+str_email);
        str_password=etpassword.getText().toString();
        Log.d(TAG, "onClick: str_password"+str_password);
        Log.d(TAG, "onClick:str_password "+str_password);
        str_address=etaddress.getText().toString();
        Log.d(TAG, "onClick:str_address "+str_address);
        Log.d(TAG, "onClick: userid"+user_id);
        Log.d(TAG, "onClick: usertype"+user_type);
        str_city=etcity.getText().toString();
        if (TextUtils.isEmpty(str_username))
        {
            etusername.setError("Please Enter user Name");
            etusername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(str_city))
        {
            etcity.setError("Please Enter City Name");
            etcity.requestFocus();
            return;
        }



        if (TextUtils.isEmpty(str_password))
        {
            etpassword.setError("Please Enter Password");
            etpassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(str_address))
        {
            etaddress.setError("Please Enter Address");
            etaddress.requestFocus();
            return;
        }


else {
            update(str_action,user_id,str_username,usermobilenum,useremail,str_password,str_address,str_city,user_type,type);

        }

    }

    private void update(String str_action, String user_id, String str_username, String str_mobilenum, String str_email, String str_password, String str_address,String str_city,String str_usertype, String type) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateProfileResponse> updateProfileResponseCall = apiInterface.updateprofile(str_action,user_id,str_username, str_mobilenum, str_email, str_password, str_address,str_city,str_usertype,type);

            updateProfileResponseCall.enqueue(new Callback<UpdateProfileResponse>() {
                @Override
                public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                    progressDialog.dismiss();
                    updateProfileResponse = response.body();
                    Log.d(TAG, "onResponse:UpdateProfileResponse " + response.toString());
                    if (updateProfileResponse != null) {
                        if (updateProfileResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, updateProfileResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:UpdateProfileResponse msg " + updateProfileResponse.getResponseMessage());
                            startActivity(new Intent(context, Account.class));
                            finish();


                        } else {
                            Log.d(TAG, "onResponse:UpdateProfileResponse msg " + updateProfileResponse.getResponseMessage());
                            Toast.makeText(context,updateProfileResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
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
