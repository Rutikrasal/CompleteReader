package com.pdftron.completereader;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EventDetailingActivity extends AppCompatActivity {
    String str_eventname,subject,str_speciality,str_eventspeakername,str_role,strbusiness;
    TextView tvbookname,tvlanguage,tvauthor,tvpublisher,tvdate,tvisbn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detailing);
        tvbookname=findViewById(R.id.tv_bookname);
        tvlanguage=findViewById(R.id.tv_booklanguage);
        tvauthor=findViewById(R.id.tv_bookauthor);
        tvpublisher=findViewById(R.id.tv_publication);
        tvdate=findViewById(R.id.tv_date);
        tvisbn=findViewById(R.id.tv_isbn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Event Detail");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        Bundle bundle = getIntent().getExtras();

        str_eventname = bundle.getString("eventname");
        subject= bundle.getString("subject");
        str_speciality=bundle.getString("speciality");
        str_eventspeakername = bundle.getString("speaker");
        str_role= bundle.getString("role");
        strbusiness= bundle.getString("business");
        tvbookname.setText(str_eventname);
        tvauthor.setText(subject);
        tvpublisher.setText(str_speciality);
        tvlanguage.setText(str_eventspeakername);
        tvdate.setText(str_role);
        tvisbn.setText(strbusiness);

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
