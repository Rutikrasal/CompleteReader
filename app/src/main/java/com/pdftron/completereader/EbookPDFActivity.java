package com.pdftron.completereader;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pdftron.pdf.PDFViewCtrl;
import com.pdftron.pdf.controls.DocumentActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class EbookPDFActivity extends AppCompatActivity {
    String strbookname, strbokkpdf;
   // WebView pdfView;
    private static final String LOG_TAG = EbookPDFActivity.class.getSimpleName();
    String TAG = "TAG";
    Toolbar toolbar;
    private PDFViewCtrl mPdfViewCtrl;

    ProgressDialog progressDialog;
    Context context;
    private WebView mWebViewPdf;
    private String strUrl,ouput;
private int STORAGE_PERMISSION_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_p_d_f);
        progressDialog = new ProgressDialog(this);
        context = EbookPDFActivity.this;
        //  progressDialog.show();
     //   mWebViewPdf=findViewById(R.id.webview);
        Bundle bundle = getIntent().getExtras();
        strbookname = bundle.getString("bookname");
        strUrl = bundle.getString("bookpdf");
        Log.d(TAG, "onCreate:strbokkpdf " + strUrl);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(strbookname);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        startActivity(new Intent(context,MainActivity.class));
      //  loadWebView();
        isStoragePermissionGranted(EbookPDFActivity.this);
     // DocumentActivity.openDocument(this, R.raw.sample);
        //DocumentActivity.openDocument(this, R.raw.sample);



         // setUI();

    }
public boolean isStoragePermissionGranted(Activity activity)
{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            return false;
        }

    }
    else {
        return true;
    }

}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {

        }
        else if (grantResults[0]== PackageManager.PERMISSION_DENIED)
        {

        }
    }

    private void setUI() {
     //  mWebViewPdf = (WebView) findViewById(R.id.webview);
        loadWebView();

    }

    private void loadWebView() {
        mWebViewPdf.setWebViewClient(new myWebClient());
        mWebViewPdf.getSettings().setJavaScriptEnabled(true);
        mWebViewPdf.getSettings().setLoadWithOverviewMode(true);
        mWebViewPdf.getSettings().setUseWideViewPort(true);
        mWebViewPdf.getSettings().setSupportZoom(true);

        mWebViewPdf.getSettings().setBuiltInZoomControls(true);
        mWebViewPdf.getSettings().setDisplayZoomControls(false);
       // mWebViewPdf.loadData(readTextFromResource(R.raw.samples),"text/html", "utf-8");
/*                "http://docs.google.com/gview?embedded=true&url="
                + strUrl.trim());*/
    }

    private String readTextFromResource(int samples) {
        InputStream raw = getResources().openRawResource(samples);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int i;
        try
        {
            i = raw.read();
            while (i != -1)
            {
                stream.write(i);
                i = raw.read();

            }
            raw.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();

        }
        return stream.toString();

    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        /*@Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }*/

    }

}
