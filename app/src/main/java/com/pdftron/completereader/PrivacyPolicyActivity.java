package com.pdftron.completereader;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PrivacyPolicyActivity extends AppCompatActivity {
    TextView tvpp;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Terms And Condition");
        toolbar.setTitleTextColor(0xffffffff);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tvpp=findViewById(R.id.tv_pp);
        tvpp.setText("To carry on in India or elsewhere the business as creator, publishers, printers,DPT operators,proof-readers, binders, cutter, perforators, laminators, designers, authors, writer and editors of publication of all varieties, descriptions, specifications, applications &amp; uses including books, novels, magazines, journals, souvenirs, newsletters, periodicals, bulletins, pamphlets, forms catalogues, diaries, calendars, posters, pictures, stickers, text books, law books, school books college books, notes, guides, reference books, newspapers &amp; other allied publications on any subject related to but not limited to education, cultural and knowledge based subjects whatsoever in print as well as in electronic media and to manage various events, conferences");

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
