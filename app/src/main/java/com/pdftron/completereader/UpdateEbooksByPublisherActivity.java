package com.pdftron.completereader;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pdftron.completereader.Response.AllCategoryResponse;
import com.pdftron.completereader.Response.UpdateEBookResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEbooksByPublisherActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Toolbar toolbar;
    TextView tvpdfname;

    EditText etbookname,etbookdescription,etauthorname,etisbnnum,etpublishername,etbookprice,etdiscountprice,etbookquantity;
    Spinner spncategory,spnlanguage;
    ImageView ivfrontimage,ivindeximage,ivbackimage;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG",user_id,catrgoryid;
    private Uri filePath,filePathindex,filePathback,filePathpdf;
    String categoryaction="category";
    AllCategoryResponse allCategoryResponse;
    ArrayList<String> arrayListcat = new ArrayList<>();
    Bitmap bitmap,bitmapi,bitmapb;
    ProgressDialog progressDialog;
    String strtype="English",strbooktext="newtext",status="0",updateebookaction="update_ebook",isbn,bookfile,free,publisher,bookid,price,discountprice,strfav="0",language,bookname,author,publishdate,isbnno,category,quantity,description,bookfrontimage,bookindeximage,bookbackimage;

    CheckBox checkBoxfree;
    WebView webViewpdf;
    UpdateEBookResponse updateEBookResponse;
    Button btnupdateebook,btnuploadpdf,btnupdatepdf,btnupdateimages;
    private static final int PICK_IMAGE_REQUEST = 234;

    private static final int PICK_IMAGE_REQUESTI = 235;

    private static final int PICK_IMAGE_REQUESTB = 236;
    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    File file,fileindex,fileback;

   String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ebooks_by_publisher);
        context=UpdateEbooksByPublisherActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressDialog=new ProgressDialog(this);

        Bundle bundle = getIntent().getExtras();
        isbn=bundle.getString("isbn");
        publisher=bundle.getString("publisher");

        bookid=bundle.getString("bookid");
        free=bundle.getString("free");
        bookname=bundle.getString("bookname");
        author=bundle.getString("author");
        publishdate=bundle.getString("publishdate");
        bookfile=bundle.getString("ebookfile");
        category=bundle.getString("category");
        description=bundle.getString("description");
        price=bundle.getString("price");
        discountprice=bundle.getString("discountprice");
        language=bundle.getString("language");
        bookfrontimage=bundle.getString("bookfrontimage");
        bookbackimage=bundle.getString("bookbackimage");
        bookindeximage=bundle.getString("bookindeximage");

        toolbar.setTitle("UPDATE E-BOOKS");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id= sharedPreferences.getString("user_id", "");
        etbookname=findViewById(R.id.et_bookname);
        etbookdescription=findViewById(R.id.et_bookdescription);
        etauthorname=findViewById(R.id.et_authorname);
        etisbnnum=findViewById(R.id.et_isbnnum);
        etpublishername=findViewById(R.id.et_publishername);
        etbookprice=findViewById(R.id.et_bookprice);
        etdiscountprice=findViewById(R.id.et_bookdiscountprice);
        etbookquantity=findViewById(R.id.et_bookquantity);
        ivfrontimage=findViewById(R.id.iv_frontimage);
        ivindeximage=findViewById(R.id.iv_indeximage);
        ivbackimage=findViewById(R.id.iv_backimage);
        spncategory=findViewById(R.id.spn_category);
        checkBoxfree=findViewById(R.id.checkbox_isfree);
        btnupdatepdf=findViewById(R.id.btn_updatepdf);
        btnupdatepdf.setOnClickListener(this);
        btnupdateimages=findViewById(R.id.btn_updateimage);
        btnupdateimages.setOnClickListener(this);
        btnupdateebook=findViewById(R.id.btn_udateebook);
        tvpdfname=findViewById(R.id.tv_pdfname);
        spnlanguage=findViewById(R.id.spn_lang);

        btnupdateebook.setOnClickListener(this);
        btnuploadpdf=findViewById(R.id.btn_uploadpdf);
        btnuploadpdf.setOnClickListener(this);
        ivfrontimage.setOnClickListener(this);
        ivindeximage.setOnClickListener(this);
        ivbackimage.setOnClickListener(this);

        etbookname.setText(bookname);
        etauthorname.setText(author);
        etbookdescription.setText(description);
        etpublishername.setText(publisher);
        etisbnnum.setText(isbn);








        if (free.matches("0"))
        {
            checkBoxfree.setChecked(false);
            etbookprice.setVisibility(View.VISIBLE);
            etdiscountprice.setVisibility(View.VISIBLE);
            etbookprice.setText(price);
            etdiscountprice.setText(discountprice);

        }
        else if (free.matches("1")){
            checkBoxfree.setChecked(true);
            etbookprice.setVisibility(View.GONE);
            etdiscountprice.setVisibility(View.GONE);



        }
        arrayListcat.add("Choose");

        category(categoryaction);

        Picasso
                .get()
                .load(bookfrontimage)
                .into(ivfrontimage);

        Picasso
                .get()
                .load(bookindeximage)
                .into(ivindeximage);


        Picasso
                .get()
                .load(bookbackimage)
                .into(ivbackimage);
/*
        Picasso
                .with(context)
                .load(bookfrontimage)
                .into(ivfrontimage);

        Picasso
                .with(context)
                .load(bookindeximage)
                .into(ivindeximage);


        Picasso
                .with(context)
                .load(bookbackimage)
                .into(ivbackimage);*/
        spnlanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                language=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void category(String categoryaction) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AllCategoryResponse> allCategoryResponseCall = apiInterface.allcategory(categoryaction);

            allCategoryResponseCall.enqueue(new Callback<AllCategoryResponse>() {
                @Override
                public void onResponse(Call<AllCategoryResponse> call, Response<AllCategoryResponse> response) {
                    progressDialog.dismiss();
                    allCategoryResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (allCategoryResponse != null) {
                        if (allCategoryResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + allCategoryResponse.getResponseMessage());
                            for (int i = 0; i < allCategoryResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + allCategoryResponse.getData().size());
                                arrayListcat.add(allCategoryResponse.getData().get(i).getCategory());

                                ArrayAdapter<String> arrayAdaptercat = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, arrayListcat);
                                arrayAdaptercat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spncategory.setAdapter(arrayAdaptercat);
                                spncategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        category = parent.getItemAtPosition(position).toString();
                                        Log.d(TAG, "onItemSelected:category "+category);
                                        for (int j=0;j<allCategoryResponse.getData().size();j++)
                                        {
                                            if (category.matches(allCategoryResponse.getData().get(j).getCategory()))
                                            {
                                                catrgoryid=allCategoryResponse.getData().get(j).getId();
                                                Log.d(TAG, "onItemSelected:catrgoryid "+catrgoryid);

                                            }

                                        }

                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }



                        } else {
                            Log.d(TAG, "onResponse:registration msg " + allCategoryResponse.getResponseMessage());
                            Toast.makeText(context,allCategoryResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AllCategoryResponse> call, Throwable t) {
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
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_updateimage:
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
                byte[] frontimagebyte=byteArrayOutputStream.toByteArray();
                bookfrontimage= Base64.encodeToString(frontimagebyte, Base64.DEFAULT);

                ByteArrayOutputStream byteArrayOutputStreami=new ByteArrayOutputStream();
                bitmapi.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStreami);
                byte[] indeximagebyte=byteArrayOutputStreami.toByteArray();
                bookbackimage= Base64.encodeToString(indeximagebyte, Base64.DEFAULT);

                ByteArrayOutputStream byteArrayOutputStreamb=new ByteArrayOutputStream();
                bitmapb.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStreamb);
                byte[] backimagebyte=byteArrayOutputStreamb.toByteArray();
                bookindeximage= Base64.encodeToString(backimagebyte, Base64.DEFAULT);
                Toast.makeText(context,"Update Images",Toast.LENGTH_SHORT).show();

                break;
            /*case R.id.btn_updatepdf:
                bookfile = FilePath.getPath(this, filePathpdf);
                Log.d(TAG, "onClick: path"+bookfile);

                Toast.makeText(context,"Update PDf",Toast.LENGTH_SHORT).show();

                break;*/
            case R.id.btn_udateebook:
                bookname=etbookname.getText().toString();
                Log.d(TAG, "onClick:strbookname "+bookname);
                description=etbookdescription.getText().toString();
                Log.d(TAG, "onClick:strbookdescription "+description);
                author=etauthorname.getText().toString();
                Log.d(TAG, "onClick:strbookdescription "+author);
                isbn=etisbnnum.getText().toString();
                Log.d(TAG, "onClick:strisbnnum "+isbn);
                publisher=etpublishername.getText().toString();
                Log.d(TAG, "onClick: strpublishername"+publisher);
                quantity=etbookquantity.getText().toString();
                if (checkBoxfree.isChecked())
                {
                    free="1";
                    price="00";
                    discountprice="00";
                }
                else {
                    free="0";
                    price=etbookprice.getText().toString();
                    Log.d(TAG, "onClick:strbookprice "+price);
                    discountprice=etdiscountprice.getText().toString();
                    Log.d(TAG, "onClick:strdiscountprice "+discountprice);

                    if (TextUtils.isEmpty(price))
                    {
                        etbookprice.requestFocus();
                        etbookprice.setError("Please Enter E-Book Price");
                        return;
                    }
                    if (TextUtils.isEmpty(discountprice))
                    {
                        etdiscountprice.requestFocus();
                        etdiscountprice.setError("Please Enter E-Book Discount Price");
                        return;
                    }
                }
                if (TextUtils.isEmpty(bookname))
                {
                    etbookname.requestFocus();
                    etbookname.setError("Please Enter E-Book Name");
                    return;
                }
                if (TextUtils.isEmpty(description))
                {
                    etbookdescription.requestFocus();
                    etbookdescription.setError("Please Enter E-Book Description");
                    return;
                }
                if (TextUtils.isEmpty(author))
                {
                    etauthorname.requestFocus();
                    etauthorname.setError("Please Enter E-Book Author Name");
                    return;
                }
                if (TextUtils.isEmpty(isbn))
                {
                    etisbnnum.requestFocus();
                    etisbnnum.setError("Please Enter E-Book ISBN Number");
                    return;
                }
                if (TextUtils.isEmpty(publisher))
                {
                    etpublishername.requestFocus();
                    etpublishername.setError("Please Enter E-Book Publisher Name");
                    return;
                }


                else
                {
                    Log.d(TAG, "onClick: strbookprice"+price);
                    Log.d(TAG, "onClick: strbookdiscount"+discountprice);
                    Log.d(TAG, "onClick: strcategoryid"+catrgoryid);
                    Log.d(TAG, "onClick: strfree"+free);
                    updateebook(updateebookaction,description,author,publisher,isbn,price,catrgoryid,user_id,status,discountprice,strtype,free,bookname,strbooktext,language,strfav,bookfile,bookfrontimage,bookindeximage,bookbackimage,bookid);
                }

                break;

            case R.id.btn_uploadpdf:
                showFileChooserpdf();

                break;
            case R.id.iv_frontimage:
                showFileChooser();

                break;

            case R.id.iv_indeximage:
                showFileChooseri();

                break;

            case R.id.iv_backimage:
                showFileChooserb();

                break;

        }
    }

    private void updateebook(String updateebookaction, String description, String author, String publisher, String isbn, String price, String catrgoryid, String user_id, String status, String discountprice, String strtype, String free, String bookname, String strbooktext, String language, String strfav, String bookfile, String bookfrontimage, String bookindeximage, String bookbackimage, String bookid) {

        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateEBookResponse> updateEBookResponseCall = apiInterface.updateebook(updateebookaction, description, author, publisher, isbn, price,catrgoryid,user_id,status,discountprice,strtype,free,bookname,strbooktext,language,strfav,bookfile,bookfrontimage,bookindeximage,bookbackimage,bookid);

            updateEBookResponseCall.enqueue(new Callback<UpdateEBookResponse>() {
                @Override
                public void onResponse(Call<UpdateEBookResponse> call, Response<UpdateEBookResponse> response) {
                    progressDialog.dismiss();
                    updateEBookResponse = response.body();
                    Log.d(TAG, "onResponse:addEBookResponse " + response.toString());
                    if (updateEBookResponse != null) {
                        if (updateEBookResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, updateEBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:addEBookResponse msg " + updateEBookResponse.getResponseMessage());
                            startActivity(new Intent(context, PublisherDashboardActivity.class));
                            finish();


                        } else {
                            Log.d(TAG, "onResponse:addEBookResponse msg " + updateEBookResponse.getResponseMessage());
                            Toast.makeText(context,updateEBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<UpdateEBookResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }





    }

    private void showFileChooserpdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }
    private void showFileChooseri() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUESTI);


    }
    private void showFileChooserb() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUESTB);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                ivfrontimage.setImageBitmap(bitmap);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUESTI && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Log.d(TAG, "onActivityResult:filePath " + filePathindex);
            Uri path = data.getData();

            try {
                bitmapi = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                ivindeximage.setImageBitmap(bitmapi);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUESTB && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Log.d(TAG, "onActivityResult:filePath " + filePathback);
            Uri path = data.getData();

            try {
                bitmapb = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                ivbackimage.setImageBitmap(bitmapb);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {


            Uri uripath=data.getData();
            InputStream inputStream= null;
            try {
                inputStream = UpdateEbooksByPublisherActivity.this.getContentResolver().openInputStream(uripath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] pdfinbyte= new byte[0];
            try {
                pdfinbyte = new byte[inputStream.available()];
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.read(pdfinbyte);
                bookfile=Base64.encodeToString(pdfinbyte,Base64.DEFAULT);
                tvpdfname.setText(uripath.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }




      /*      filePathpdf = data.getData();
            tvpdfname.setText(FilePath.getPath(this,filePathpdf).toString());
      */  }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
