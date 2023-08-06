package com.pdftron.completereader;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pdftron.completereader.Response.ProfileViewResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Account extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigation;
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG";
    TextView tvuname,tvuemail;
    String user_id,username,useraddress,usermobilenum,useremail,usertype,usercity,user_type,userid;
    CardView cardViewpp,cardViewtc,cardViewlogin,cardViewpendingorder,cardViewcart,cardViewcategory,cardViewebooks,cardViewfeedback,cardViewlogout,cardViewmydashboard,cardViewmytopebooks,cardViewmybookorder,cardViewmyeventorders,cardViewbooks,cardViewmyeventdashbord;
    ImageView ivshare,ivcontactus,ivrateus;
    String appPackageName,profileaction="user_view";
    Button btnedit;
    ProfileViewResponse profileViewResponse;
    String strtoolbarname;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        context = Account.this;
        builder = new AlertDialog.Builder(context);

        cardViewfeedback=findViewById(R.id.card_feedback);
        cardViewlogout=findViewById(R.id.card_logout);
        cardViewfeedback.setOnClickListener(this);
        cardViewlogout.setOnClickListener(this);
        cardViewmydashboard=findViewById(R.id.card_my_publisher_dashboard);
        cardViewmydashboard.setOnClickListener(this);
        cardViewmytopebooks=findViewById(R.id.card_my_top_ebook);
        cardViewmytopebooks.setOnClickListener(this);
        cardViewmybookorder=findViewById(R.id.card_my_orders);
        cardViewmyeventorders=findViewById(R.id.card_my_eventorders);
        cardViewmyeventorders.setOnClickListener(this);
        cardViewmybookorder.setOnClickListener(this);
        cardViewbooks=findViewById(R.id.card_books);
        cardViewbooks.setOnClickListener(this);
        cardViewebooks=findViewById(R.id.card_e_books);
        cardViewebooks.setOnClickListener(this);
        cardViewcategory=findViewById(R.id.card_category);
        cardViewcategory.setOnClickListener(this);
        cardViewcart=findViewById(R.id.card_cart);
        cardViewcart.setOnClickListener(this);
        cardViewpendingorder=findViewById(R.id.card_my_pendingorders);
        cardViewpendingorder.setOnClickListener(this);
        cardViewlogin=findViewById(R.id.card_login);
        cardViewlogin.setOnClickListener(this);
        cardViewtc=findViewById(R.id.card_terms_condition);
        cardViewtc.setOnClickListener(this);
        cardViewpp=findViewById(R.id.card_privacy);
        cardViewpp.setOnClickListener(this);
        ivcontactus=findViewById(R.id.iv_contactus);
        ivcontactus.setOnClickListener(this);
        ivshare=findViewById(R.id.iv_share);
        ivshare.setOnClickListener(this);
        ivrateus=findViewById(R.id.iv_rate);
        ivrateus.setOnClickListener(this);
        btnedit=findViewById(R.id.btn_edit);
        btnedit.setOnClickListener(this);
        cardViewmyeventdashbord=findViewById(R.id.card_my_Event_dashboard);
        cardViewmyeventdashbord.setOnClickListener(this);
        appPackageName = context.getPackageName();

        tvuname=findViewById(R.id.tv_uname);
        tvuemail=findViewById(R.id.tv_uemail);
        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Account");
        toolbar.setTitleTextColor(0xffffffff);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);


        user_id= sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: profileuserid"+user_id);
        Log.d(TAG, "onCreate:profileaction "+profileaction);
          if (user_id.matches("0"))
        {
            cardViewmydashboard.setVisibility(View.GONE);
            cardViewmybookorder.setVisibility(View.GONE);
            cardViewmyeventorders.setVisibility(View.GONE);
            cardViewcart.setVisibility(View.GONE);
            cardViewlogout.setVisibility(View.GONE);
            cardViewmytopebooks.setVisibility(View.GONE);
            cardViewlogin.setVisibility(View.VISIBLE);
            cardViewmyeventdashbord.setVisibility(View.GONE);
            btnedit.setVisibility(View.GONE);

        }
          else
          {
              cardViewmydashboard.setVisibility(View.VISIBLE);
              cardViewmybookorder.setVisibility(View.VISIBLE);
              cardViewmyeventorders.setVisibility(View.VISIBLE);
              cardViewcart.setVisibility(View.VISIBLE);
              cardViewlogout.setVisibility(View.VISIBLE);
              cardViewmytopebooks.setVisibility(View.VISIBLE);
              cardViewlogin.setVisibility(View.GONE);
              btnedit.setVisibility(View.VISIBLE);




          }
     /*   username= sharedPreferences.getString("username", "");
        useraddress=sharedPreferences.getString("useraddress", "");
        usermobilenum= sharedPreferences.getString("usermobilenum", "");
        useremail= sharedPreferences.getString("useremail", "");
        user_type = sharedPreferences.getString("user_type", "");
     */   profile(profileaction,user_id);

        bottomNavigation = findViewById(R.id.bottom_navigationaccount);
        bottomNavigation.setSelectedItemId(R.id.navigation_account);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_search:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0,0);
                        finish();

                        return true;
                    case R.id.navigation_books:
                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                        overridePendingTransition(0,0);
                        finish();


                        return true;

                    case R.id.navigation_cart:

                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        finish();

                        return true;

                    case R.id.navigation_account:


                        return true;

                }
                return false;
            }
        });


    }

    private void profile(String profileaction, String user_id) {
        if (isOnline()) {
            progressDialog.show();
            progressDialog.setMessage(" Please Wait..!! ");
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class)    ;
            Call<ProfileViewResponse> profileViewResponseCall = apiInterface.viewprofile(profileaction, user_id);

            profileViewResponseCall.enqueue(new Callback<ProfileViewResponse>() {
                @Override
                public void onResponse(Call<ProfileViewResponse> call, Response<ProfileViewResponse> response) {
                    progressDialog.dismiss();
                    profileViewResponse = response.body();
                    Log.d(TAG, "onResponse:profileViewResponse " + response.toString());
                    if (profileViewResponse != null) {
                        if (profileViewResponse.getResponseCode().matches("0")) {
                           // Toast.makeText(context, profileViewResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "onResponse:profileViewResponsemsg " + profileViewResponse.getResponseMessage());
                            for (int i=0;i<profileViewResponse.getData().size();i++)
                            {
                                 username=profileViewResponse.getData().get(i).getName();
                                Log.d(TAG, "onResponse: username"+username);
                                 useraddress=profileViewResponse.getData().get(i).getAddress();
                                 usermobilenum=profileViewResponse.getData().get(i).getContact();
                                 useremail=profileViewResponse.getData().get(i).getEmail();
                                usertype=profileViewResponse.getData().get(i).getUserType();
                                Log.d(TAG, "onResponse:usertype "+usertype);
                                 usercity=profileViewResponse.getData().get(i).getCity();
                                 userid=profileViewResponse.getData().get(i).getId();
                                user_type=profileViewResponse.getData().get(i).getType();
                                tvuname.setText(username);
                                tvuemail.setText(useremail);

                                if (usertype.matches("Publisher")/*|| profileViewResponse.getData().get(i).getType().matches("Author")*/)
                                {
                                    cardViewmydashboard.setVisibility(View.VISIBLE);
                                    cardViewmyeventdashbord.setVisibility(View.GONE);



                                }
                                else if(usertype.matches("User")){
                                    cardViewmydashboard.setVisibility(View.GONE);
                                    cardViewmyeventdashbord.setVisibility(View.GONE);


                                }
                                else if(usertype.matches("Author")){
                                    cardViewmydashboard.setVisibility(View.VISIBLE);
                                    cardViewmyeventdashbord.setVisibility(View.GONE);


                                }
                                else if(usertype.matches("ES")){

                                    cardViewmydashboard.setVisibility(View.GONE);
                                    cardViewmyeventdashbord.setVisibility(View.VISIBLE);

                                }


                            }

                           } else if(profileViewResponse.getResponseCode().equals(1))
                        {
                            Toast.makeText(context, profileViewResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "loginonResponse: msg"+profileViewResponse.getResponseMessage());
                        }
                    }


                }

                @Override
                public void onFailure(Call<ProfileViewResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               startActivity(new Intent(context,DashboardActivity.class));
                finish();                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.card_feedback:
                Intent intent=new Intent(context, FeedbackActivty.class);
                intent.putExtra("name",username);
                intent.putExtra("email",useremail);
                startActivity(intent);
                break;
            case  R.id.card_my_top_ebook:
                startActivity(new Intent(context,MyEBookActivity.class));
                break;
            case R.id.card_logout:
                builder.setMessage("Are you sure").setTitle("Pustak Market");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to Log out ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                user_id = null;

                                Intent intentl = new Intent(context, LoginActivity.class);
                                intentl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_id", user_id);
                                editor.commit();
                                editor.apply();

                                startActivity(intentl);



/*
                                Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                        Toast.LENGTH_SHORT).show();
*/
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                              /*  Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();*/
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Alert");
                alert.show();


                break;
            case R.id.iv_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out Pustak Market app at: https://play.google.com/store/apps/details?id=" + appPackageName);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;
            case R.id.card_my_orders:
                startActivity(new Intent(context,MyBookOrderActivity.class));
                break;
            case R.id.card_my_publisher_dashboard:
                Intent intentd=new Intent(context, PublisherDashboardActivity.class);
                intentd.putExtra("user_type",usertype);
                startActivity(intentd);

                break;
            case R.id.btn_edit:
                Intent intentp=new Intent(context, UpdateProfileActivity.class);
                intentp.putExtra("username",username);
                intentp.putExtra("useraddress",useraddress);
                intentp.putExtra("usermobilenum",usermobilenum);
                intentp.putExtra("useremail",useremail);
                intentp.putExtra("usercity",usercity);
                intentp.putExtra("usertype",usertype);

                startActivity(intentp);

                break;
            case R.id.iv_rate:
                Uri uri = Uri.parse("market://details?id=" + appPackageName);
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, " unable to find  app", Toast.LENGTH_LONG).show();
                }

                break;
            case  R.id.card_my_eventorders:
                startActivity(new Intent(context,MyEventOrders.class));

                break;
            case  R.id.card_books:
                 strtoolbarname="Top Books";
                Intent intent0=new Intent(context, SeeallActivity.class);
                intent0.putExtra("toolbar",strtoolbarname);
                context.startActivity(intent0);
                break;
            case R.id.card_e_books:
            strtoolbarname="Top E-Books";
            Intent intente=new Intent(context, SeeallActivity.class);
            intente.putExtra("toolbar",strtoolbarname);
            context.startActivity(intente);
            break;
            case R.id.card_category:
                startActivity(new Intent(context,CategoryActivity.class));
                break;

            case R.id.card_cart:
                startActivity(new Intent(context,CartActivity.class));
                finish();
                break;
            case R.id.card_my_pendingorders:
                break;
            case R.id.card_login:
                startActivity(new Intent(context,LoginActivity.class));
                break;
            case R.id.iv_contactus:
                startActivity(new Intent(context,ContactusActivity.class));
                break;
            case R.id.card_terms_condition:
                Intent intentt = new Intent(Intent.ACTION_VIEW);
                intentt.setData(Uri.parse("https://pustakmarket.com/users/terms"));
                context.startActivity(intentt)    ;
                break;

            case R.id.card_privacy:
                startActivity(new Intent(context,PrivacyPolicyActivity.class));
                break;
            case R.id.card_my_Event_dashboard:
                startActivity(new Intent(context,EventDashboardActivity.class));
                break;


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
    public void onBackPressed() {

    }
}
