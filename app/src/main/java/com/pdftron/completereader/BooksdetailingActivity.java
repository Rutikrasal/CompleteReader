package com.pdftron.completereader;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BooksdetailingActivity extends AppCompatActivity {
    String bookname,language,author,publisher,date,isbn;
    TextView tvbookname,tvlanguage,tvauthor,tvpublisher,tvdate,tvisbn;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booksdetailing);
        tvbookname=findViewById(R.id.tv_bookname);
        tvlanguage=findViewById(R.id.tv_booklanguage);
        tvauthor=findViewById(R.id.tv_bookauthor);
        tvpublisher=findViewById(R.id.tv_publication);
        tvdate=findViewById(R.id.tv_date);
        tvisbn=findViewById(R.id.tv_isbn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Book Detail");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getExtras();
        date=bundle.getString("date");
        language = bundle.getString("language");
        bookname = bundle.getString("bookname");
        author=bundle.getString("authorname");
        publisher=bundle.getString("publisher");
        isbn=bundle.getString("isbn");

        tvbookname.setText(bookname);
        tvauthor.setText(author);
        tvpublisher.setText(publisher);
        tvlanguage.setText(language);
        tvdate.setText(date);
        tvisbn.setText(isbn);

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
