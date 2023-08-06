package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

public class MagazineDownloadActivity extends AppCompatActivity {
    WebView pdf;
    String boolink;
    Context context;
    String TAG="TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_download);
        context=MagazineDownloadActivity.this;

      //  pdf=findViewById(R.id.pdfview);
    /*    ProgressBar progressBar =findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    */    Bundle bundle = getIntent().getExtras();
        boolink = bundle.getString("bookpdf");
        Intent intentUrl = new Intent(Intent.ACTION_VIEW);
        intentUrl.setDataAndType(Uri.parse(boolink), "application/pdf");
        intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intentUrl);

    }

}
