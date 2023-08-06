package com.pdftron.completereader;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Response.AddEventorderResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class EventorderActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;

    Context context;
    EditText etname,etemail,etphonenum,etaddress,etzipcode,etcity,eteventlocation,eteventdate,eteventtime;
    Button btnbookevent;
    TextView tveventtc;
    ArrayList<String> eventtype= new ArrayList<>();
    String speakerid,strfee,onlineprice,eventprice,streventtype,streventid,user_id,eventaddeventaction="add_event_order",strpayment="0",strname,stremail,strcontact,straddress,strzipcode,strcity,streventlocation,streventtime,streventdate;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG";
    AddEventorderResponse addEventorderRespons;
    ProgressDialog progressDialog;
    Spinner spneventtype;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener date;
    String[] type ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventorder);
        context=EventorderActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Book Event");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        spneventtype=findViewById(R.id.spn_eventtype);
        etname=findViewById(R.id.et_name);
        etemail=findViewById(R.id.et_email);
        etphonenum=findViewById(R.id.et_phonenum);
        etaddress=findViewById(R.id.et_address);
        etzipcode=findViewById(R.id.et_zipcode);
        etcity=findViewById(R.id.et_city);
        eteventlocation=findViewById(R.id.et_eventlocation);
        eteventdate=findViewById(R.id.et_eventdate);
        eteventtime=findViewById(R.id.et_eventtime);
        tveventtc=findViewById(R.id.tv_eventtc);
        btnbookevent=findViewById(R.id.btn_bookevent);
        eteventdate.setOnClickListener(this);
        eteventtime.setOnClickListener(this);
        tveventtc.setOnClickListener(this);
        btnbookevent.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid"+user_id);
        Bundle bundle = getIntent().getExtras();
        streventid = bundle.getString("eventid");
        eventprice = bundle.getString("eventprice");
        onlineprice = bundle.getString("onlineprice");
        speakerid = bundle.getString("speakerid");

        if (onlineprice.matches("0"))
        {
            type = new String[]{"Offline Event"};

          //  eventtype.add(" Offline Event");

        }
         else if (eventprice.matches("0"))
        {
            //eventtype.add(" Online Event");
             type = new String[]{"Online Event"};

        }
         else
         {
             //eventtype.add(" Online Event");
             //eventtype.add(" Offline Event");
              type = new String[]{"Online Event", "Offline Event"};


         }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, type);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spneventtype.setAdapter(arrayAdapter);
        spneventtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                streventtype=adapterView.getItemAtPosition(i).toString();

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
            case R.id.et_eventdate:
                openDatePicker();
                break;
            case R.id.et_eventtime:
                openTimePicker();
                Log.d(TAG, "onClick: openTimePicker");

                break;
                case R.id.tv_eventtc:
                    break;
            case R.id.btn_bookevent:
                strname=etname.getText().toString();
                stremail=etemail.getText().toString();
                strcontact=etphonenum.getText().toString();
                straddress=etaddress.getText().toString();
                strzipcode=etzipcode.getText().toString();
                strcity=etcity.getText().toString();
                streventlocation=eteventlocation.getText().toString();
                streventdate=eteventdate.getText().toString();
                streventtime=eteventtime.getText().toString();
                if (streventtype.matches("Online Event"))
                {
                    strfee=onlineprice;
                }
                else if (streventtype.matches("Offline Event"))
                {
                    strfee=eventprice;

                }
                if (TextUtils.isEmpty(strname))
                {
                    etname.requestFocus();
                    etname.setError("Please Enter Your Name");
                    return;
                }
                if (TextUtils.isEmpty(stremail))
                {
                    etemail.requestFocus();
                    etemail.setError("Please Enter Your Email Id");

                    return;
                }

                if (TextUtils.isEmpty(strcontact))
                {
                    etphonenum.requestFocus();
                    etphonenum.setError("Please Enter Your Contact Number");
                    return;
                }
                if (TextUtils.isEmpty(straddress))
                {
                    etaddress.requestFocus();
                    etaddress.setError("Please Enter Event Address ");

                    return;
                }
                if (TextUtils.isEmpty(strzipcode))
                {
                    etzipcode.requestFocus();
                    etzipcode.setError("Please Enter Event Zip Code ");

                    return;
                }
                if (TextUtils.isEmpty(strcity))
                {
                    etcity.requestFocus();
                    etcity.setError("Please Enter Event City");

                    return;
                }
                if (TextUtils.isEmpty(streventlocation))
                {
                    eteventlocation.requestFocus();
                    eteventlocation.setError("Please Enter Event Location");

                    return;
                }
                if (TextUtils.isEmpty(streventdate))
                {
                    eteventdate.requestFocus();
                    eteventdate.setError("Please Enter Event Date");

                    return;
                }
                if (TextUtils.isEmpty(streventtime))
                {
                    eteventtime.requestFocus();
                    eteventtime.setError("Please Enter Event Time");

                    return;
                }
else
                {

                    Intent intent=new Intent(context, PayEventActivity.class);
                    intent.putExtra("name",strname);
                    intent.putExtra("email",stremail);
                    intent.putExtra("contact",strcontact);
                    intent.putExtra("address",straddress);
                    intent.putExtra("eventlocation",streventlocation);
                    intent.putExtra("zipcode",strzipcode);
                    intent.putExtra("city",strcity);
                    intent.putExtra("eventtime",streventtime);
                    intent.putExtra("fee",strfee);
                    intent.putExtra("eventid",streventid);
                    intent.putExtra("date",streventdate);
                    intent.putExtra("eventtype",streventtype);
                    intent.putExtra("speakerid",speakerid);

                    context.startActivity(intent);




                   // addeventorder(eventaddeventaction,user_id,strname,stremail,strcontact,straddress,streventlocation,strzipcode,streventdate,streventtime,strspeakerid,strpayment);

                }


                break;
        }
    }

    private void openTimePicker() {
        Log.d(TAG, "openTimePicker: ");
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(EventorderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String AM_PM ;
                if(selectedHour < 12) {
                    AM_PM = "AM";
                } else {
                    AM_PM = "PM";
                }
                eteventtime.setText( selectedHour + ":" + selectedMinute+ " " + AM_PM );
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void openDatePicker() {
        Log.d(TAG, "openDatePicker: ");
        calendar=calendar.getInstance();
        date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                setDate();
            }

            private void setDate() {
                String myformat="dd-MM-yyyy";
                SimpleDateFormat sdf=new SimpleDateFormat(myformat, Locale.US);
                String selectdate = sdf.format(calendar.getTime());
                eteventdate.setText(selectdate);            }
        };
        new DatePickerDialog(this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();


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
