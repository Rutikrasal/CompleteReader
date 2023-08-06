package com.pdftron.completereader;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.Adapter.FeedbackAdapter;
import com.pdftron.completereader.Response.AddfeedbackResponse;
import com.pdftron.completereader.Response.ViewfeedbackResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivty extends AppCompatActivity implements View.OnClickListener {
    Button btnsendfeedback;
    RecyclerView recyclerViewfeedback;
    Context context;
    Toolbar toolbar;
    String name,email,strfeedback,feedbackaction="feedback",TAG="TAG";
    ProgressDialog progressDialog;
    AddfeedbackResponse addfeedbackResponse;

    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",user_id;
    AlertDialog.Builder builder;

    ArrayList<ViewfeedbackResponse.Datum> al_display_feedback;
    ViewfeedbackResponse viewfeedbackResponse;
FeedbackAdapter feedbackAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_activty);
        context=FeedbackActivty.this;
        builder = new AlertDialog.Builder(context);

        progressDialog=new ProgressDialog(this);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid"+user_id);
        recyclerViewfeedback=findViewById(R.id.recycle_feedback);
        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("Feedback");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        btnsendfeedback=findViewById(R.id.btn_feddback);


        btnsendfeedback.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        email = bundle.getString("email");
viewfeedback(feedbackaction);

    }

    private void viewfeedback(String feedbackaction) {
        if (isOnline()) {
            progressDialog.show();
            progressDialog.setMessage(" Please Wait..!! ");
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class)    ;
            Call<ViewfeedbackResponse> viewfeedbackResponseCall = apiInterface.viewfeedback(feedbackaction);

            viewfeedbackResponseCall.enqueue(new Callback<ViewfeedbackResponse>() {
                @Override
                public void onResponse(Call<ViewfeedbackResponse> call, Response<ViewfeedbackResponse> response) {
                    progressDialog.dismiss();
                    viewfeedbackResponse = response.body();
                    Log.d(TAG, "onResponse:login " + response.toString());
                    if (viewfeedbackResponse != null) {
                        if (viewfeedbackResponse.getResponseCode().matches("0")) {
                            al_display_feedback = new ArrayList<>();
                            for (int i = 0; i < viewfeedbackResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + viewfeedbackResponse.getData().size());
                                al_display_feedback.add(viewfeedbackResponse.getData().get(i));
                                Log.d(TAG, "onResponse: al" + al_display_feedback);
                                Log.d(TAG, "onResponse: alsize" + al_display_feedback.size());


                            }
/*
                            recyclerViewfeedback.addItemDecoration(new DividerItemDecoration(context, 0));
*/
                            recyclerViewfeedback.addItemDecoration(new DividerItemDecoration(FeedbackActivty.this, LinearLayoutManager.VERTICAL));

                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                            feedbackAdapter = new FeedbackAdapter(context, al_display_feedback);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(FeedbackActivty.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewfeedback.setLayoutManager(horizontalLayoutManager);
                            recyclerViewfeedback.setAdapter(feedbackAdapter);


                        } else {
                            Toast.makeText(context, viewfeedbackResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: msg"+viewfeedbackResponse.getResponseMessage());
                        }
                    }


                }

                @Override
                public void onFailure(Call<ViewfeedbackResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    @Override
    public void onClick(View view) {
      switch (view.getId())
      {
          case R.id.btn_feddback:
              if (user_id.matches("0"))
              {
                  builder.setMessage("Please Login First To Write Feedback").setTitle("Pustak Market");
                  //Setting message manually and performing action on button click
                  builder.setMessage("Please Login First To Write Feedback")
                          .setCancelable(false)
                          .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                                  startActivity(new Intent(context,LoginActivity.class));


                              }
                          })
                          .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
                  alert.setTitle("Pustak Market");
                  alert.show();


              }
              else
              {
                  Intent intente=new Intent(context,WriteFeedbackActivity.class);
                  intente.putExtra("name",name);
                  intente.putExtra("userid",user_id);
                  intente.putExtra("email",email);
                  context.startActivity(intente);

              }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
