package com.pdftron.completereader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ContactusActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Context context;
    ImageView ivcal,ivmail,ivwebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        context = ContactusActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Contact Us");
        toolbar.setTitleTextColor(0xffffffff);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ivcal=findViewById(R.id.ivcall);
        ivmail=findViewById(R.id.ivmail);
        ivcal.setOnClickListener(this);
        ivmail.setOnClickListener(this);
        ivwebsite=findViewById(R.id.ivwebsite);
        ivwebsite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.ivcall:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:07385486832"));
                startActivity(intent);    break;
            case R.id.ivmail:
                Intent sendEmail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+"pustakmarket2020@gmail.com")); // mention an email id here
                sendEmail.putExtra(Intent.EXTRA_SUBJECT, "subject"); //subject of the email
                sendEmail.putExtra(Intent.EXTRA_TEXT, "body"); //body of the email
                startActivity(Intent.createChooser(sendEmail, "Choose an email client from..."));
                break;
            case R.id.ivwebsite:
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("http://www.pustakmarket.com/"));
                startActivity(viewIntent);
                break;
        }

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
