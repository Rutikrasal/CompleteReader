package com.pdftron.completereader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pdftron.completereader.Response.DeleteBookBypublisherResponse;
import com.pdftron.completereader.Response.DeleteEventResponse;
import com.pdftron.completereader.Response.EventDetailResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    String str_resume,str_isfav,str_paystatus,str_status,str_free,strspeakerid,strbusiness,subject,TAG="TAG",eventaction="event_view",strimageurl,eventid,str_eventspeakername,str_eventprice,str_role,str_speciality,str_description,str_eventname,str_onlineprice;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",user_id,deleteeventaction="delete_event";
    ProgressDialog progressDialog;
    Toolbar toolbar;
    ImageView eventimage;
    TextView tveventspeakername,tveventprice,tvspeakerrole,tvspeciality,tveventonlineprice;
    Button btnupdateevent,btndeleteevent;
    EventDetailResponse eventDetailResponse;
    ArrayList<EventDetailResponse.Datum> al_display_eventdetails;
    CardView cardVieweventdetail,cardViewpreview;
    DeleteEventResponse deleteEventResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_detail);
        context=MyEventDetailActivity.this;
        progressDialog=new ProgressDialog(this);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid"+user_id);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tveventspeakername=findViewById(R.id.tv_eventspeaker_name);
        tveventprice=findViewById(R.id.tv_eventprice);
        tvspeakerrole=findViewById(R.id.tv_speakerrole);
        tvspeciality=findViewById(R.id.tv_speakerspeciality);
        eventimage=findViewById(R.id.event_image);
        btnupdateevent=findViewById(R.id.btn_update);
        btnupdateevent.setOnClickListener(this);
        btndeleteevent=findViewById(R.id.btn_delete);
        btndeleteevent.setOnClickListener(this);
        tveventonlineprice=findViewById(R.id.tv_eventonlineprice);
        cardVieweventdetail=findViewById(R.id.card_eventdetails);
        cardVieweventdetail.setOnClickListener(this);
        cardViewpreview=findViewById(R.id.card_preview);
        cardViewpreview.setOnClickListener(this);


        Bundle bundle = getIntent().getExtras();
        eventid = bundle.getString("eventid");
        Log.d(TAG, "onCreate: eventid"+eventid);
        viewevent(eventaction,eventid);



    }
    private void viewevent(String eventaction, String eventid) {
        if (isOnline()) {
            Log.d(TAG, "viewevent: ");
            Log.d(TAG, "viewevent: eventaction"+eventaction);
            Log.d(TAG, "viewevent: eventid"+eventid);
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<EventDetailResponse> eventDetailResponseCall = apiInterface.eventdetail(eventaction,eventid);

            eventDetailResponseCall.enqueue(new Callback<EventDetailResponse>() {
                @Override
                public void onResponse(Call<EventDetailResponse> call, Response<EventDetailResponse> response) {
                    progressDialog.dismiss();
                    eventDetailResponse = response.body();
                    Log.d(TAG, "onResponse:event " + response.toString());
                    if (eventDetailResponse != null) {
                        Log.d(TAG, "onResponse:eventDetailResponse ");
                        if (eventDetailResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:eventDetailResponse msg " + eventDetailResponse.getResponseMessage());
                            al_display_eventdetails = new ArrayList<>();
                            Log.d(TAG, "onResponse: eventfor");

                            Log.d(TAG, "onResponse: eventsize"+eventDetailResponse.getData().size());


                            for (int i = 0; i < eventDetailResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: infor");
                                Log.d(TAG, "onResponse: eventDetailResponsesize" + eventDetailResponse.getData().size());
                                al_display_eventdetails.add(eventDetailResponse.getData().get(i));
                                Log.d(TAG, "onResponse: eventDetailResponseal" + al_display_eventdetails);
                                Log.d(TAG, "onResponse:eventDetailResponse alsize" + al_display_eventdetails.size());
                                str_eventspeakername=eventDetailResponse.getData().get(i).getSpeaker();
                                Log.d(TAG, "onResponse: str_eventspeakername"+str_eventspeakername);
                                str_eventprice=eventDetailResponse.getData().get(i).getPrice();
                                str_role=eventDetailResponse.getData().get(i).getEventRole();
                                str_speciality=eventDetailResponse.getData().get(i).getSpeciality();
                                str_description=eventDetailResponse.getData().get(i).getDescription();
                                str_eventname=eventDetailResponse.getData().get(i).getEventName();
                                str_onlineprice=eventDetailResponse.getData().get(i).getOnprice();
                                strbusiness=eventDetailResponse.getData().get(i).getBusiness();
                                subject=eventDetailResponse.getData().get(i).getEventSubject();
                                strspeakerid=eventDetailResponse.getData().get(i).getSpeakerId();
                                str_free=eventDetailResponse.getData().get(i).getIsFree();
                                str_status=eventDetailResponse.getData().get(i).getStatus();
                                str_paystatus=eventDetailResponse.getData().get(i).getPayStatus();
                                str_isfav=eventDetailResponse.getData().get(i).getIsFav();
                                str_resume=eventDetailResponse.getData().get(i).getResume();


                                strimageurl=eventDetailResponse.getData().get(i).getUserfrontfile();

/*
                                Picasso
                                        .with(context)
                                        .load(strimageurl)
                                        .into(eventimage);
*/
                                Picasso
                                        .get()
                                        .load(strimageurl)
                                        .into(eventimage);


                                tveventspeakername.setText(str_eventspeakername);
                                Log.d(TAG, "onResponse: str_onlinepice"+str_onlineprice);
                                if (eventDetailResponse.getData().get(i).getOnprice().matches("0"))
                                {
                                    tveventonlineprice.setText("Not Available For Online Event" );

                                }
                                else
                                {
                                    tveventonlineprice.setText("Online:"+"₹"+eventDetailResponse.getData().get(i).getOnprice());

                                }
                                if (eventDetailResponse.getData().get(i).getPrice().matches("0"))
                                {
                                    tveventprice.setText("Not Available For Offline Event ");

                                }
                                else
                                {
                                    tveventprice.setText("Offline:"+"₹"+eventDetailResponse.getData().get(i).getPrice());

                                }

                                tvspeakerrole.setText(str_role);
                                tvspeciality.setText(str_speciality);
                                toolbar.setTitle(str_eventname);
                                toolbar.setTitleTextColor(0xffffffff);
                                setSupportActionBar(toolbar);
                                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                                getSupportActionBar().setHomeButtonEnabled(true);


                            }

                        } else {
                            Log.d(TAG, "onResponse:eventDetailResponse msg " + eventDetailResponse.getResponseMessage());
                            Toast.makeText(context,eventDetailResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<EventDetailResponse> call, Throwable t) {
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
            case R.id.btn_update:
                Intent intentu=new Intent(context,UpdateEventActivity.class);
                intentu.putExtra("eventid",eventid);
                intentu.putExtra("eventname",str_eventname);
                intentu.putExtra("eventsubject",subject);
                intentu.putExtra("speciality",str_speciality);
                intentu.putExtra("business",strbusiness);
                intentu.putExtra("free",str_free);
                intentu.putExtra("offlineprice",str_eventprice);
                intentu.putExtra("onlineprice",str_onlineprice);
                intentu.putExtra("description",str_description);
                intentu.putExtra("userid",user_id);
                intentu.putExtra("status",str_status);
                intentu.putExtra("role",str_role);
                intentu.putExtra("paystatus",str_paystatus);
                intentu.putExtra("isfav",str_isfav);
                intentu.putExtra("resume",str_resume);
                intentu.putExtra("yourphoto",strimageurl);


                context.startActivity(intentu);

                break;
            case R.id.btn_delete:
                deleteevent(deleteeventaction,eventid);

                break;

            case R.id.card_eventdetails:
                Intent intent=new Intent(context, EventDetailingActivity.class);
                intent.putExtra("eventname",str_eventname);
                intent.putExtra("subject",subject);
                intent.putExtra("speciality",str_speciality);
                intent.putExtra("speaker",str_eventspeakername);
                intent.putExtra("role",str_role);
                intent.putExtra("business",strbusiness);


                context.startActivity(intent);

                break;
            case R.id.card_preview:
                Intent intentd=new Intent(context, EventPreviewActivity.class);
                intentd.putExtra("description",str_description);
                context.startActivity(intentd);

                break;
        }


    }

    private void deleteevent(String deleteeventaction, String eventid) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<DeleteEventResponse> deleteEventResponseCall = apiInterface.deleteevent(deleteeventaction, eventid);

            deleteEventResponseCall.enqueue(new Callback<DeleteEventResponse>() {
                @Override
                public void onResponse(Call<DeleteEventResponse> call, Response<DeleteEventResponse> response) {
                    progressDialog.dismiss();
                    deleteEventResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (deleteEventResponse != null) {
                        if (deleteEventResponse.getResponseCode().matches("0")) {
                            Toast.makeText(context, deleteEventResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + deleteEventResponse.getResponseMessage());
                            startActivity(new Intent(context, EventDashboardActivity.class));
                            finish();


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + deleteEventResponse.getResponseMessage());
                            Toast.makeText(context,deleteEventResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<DeleteEventResponse> call, Throwable t) {
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
