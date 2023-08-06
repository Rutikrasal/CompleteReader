package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Response.AllCategoryResponse;
import com.pdftron.completereader.Response.UpdateBookBypublisherResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBoooksBypublisherActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Toolbar toolbar;
    EditText etbookname,etbookdescription,etauthorname,etisbnnum,etpublishername,etbookprice,etdiscountprice,etbookquantity;
    Spinner spncategory,spnlanguage;
    ImageView ivfrontimage,ivindeximage,ivbackimage;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG",user_id,catrgoryid;
    private Uri filePath,filePathindex,filePathback;
    String categoryaction="category",updatebookaction="update_book";
    AllCategoryResponse allCategoryResponse;
    ArrayList<String> arrayListcat = new ArrayList<>();
    Bitmap bitmap,bitmapi,bitmapb;
    ProgressDialog progressDialog;
    Button btnupdatebook,btnupdateimage;
    UpdateBookBypublisherResponse updateBookBypublisherResponse;

    private static final int PICK_IMAGE_REQUEST = 234;
    File file,fileindex,fileback;

    private static final int PICK_IMAGE_REQUESTI = 235;

    private static final int PICK_IMAGE_REQUESTB = 236;
    String strfav="0",status="0",publisher,bookid,price,discountprice,language,bookname,author,publishdate,isbnno,category,quantity,description,bookfrontimage,bookindeximage,bookbackimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_boooks_bypublisher);
        context=UpdateBoooksBypublisherActivity.this;
        progressDialog = new ProgressDialog(context);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();
        bookid=bundle.getString("bookid");
        bookname=bundle.getString("bookname");
        author=bundle.getString("author");
        publishdate=bundle.getString("publishdate");
        isbnno=bundle.getString("isbnno");
        category=bundle.getString("category");
        quantity=bundle.getString("quantity");
        description=bundle.getString("description");
        price=bundle.getString("price");
        discountprice=bundle.getString("discountprice");
        language=bundle.getString("language");
        bookfrontimage=bundle.getString("bookfrontimage");
        bookbackimage=bundle.getString("bookbackimage");
        bookindeximage=bundle.getString("bookindeximage");
        publisher=bundle.getString("publisher");


        toolbar.setTitle("UPDATE BOOKS");
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
        btnupdatebook=findViewById(R.id.btn_updatebook);
        btnupdateimage=findViewById(R.id.btn_updateimage);
        btnupdatebook.setOnClickListener(this);
        btnupdateimage.setOnClickListener(this);
        spnlanguage=findViewById(R.id.spn_lang);
        spncategory=findViewById(R.id.spn_category);


        etbookname.setText(bookname);
        etauthorname.setText(author);
        etbookdescription.setText(description);
        etbookquantity.setText(quantity);
        etisbnnum.setText(isbnno);
        etpublishername.setText(publisher);
        etbookprice.setText(price);
        etdiscountprice.setText(discountprice);

        ivfrontimage.setOnClickListener(this);
        ivindeximage.setOnClickListener(this);
        ivbackimage.setOnClickListener(this);
        arrayListcat.add("Choose");

        category(categoryaction);
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
            case R.id.btn_updatebook:
                bookname=etbookname.getText().toString();
                Log.d(TAG, "onClick:strbookname "+bookname);
                description=etbookdescription.getText().toString();
                Log.d(TAG, "onClick:strbookdescription "+description);
                author=etauthorname.getText().toString();
                Log.d(TAG, "onClick:strbookdescription "+author);
                isbnno=etisbnnum.getText().toString();
                Log.d(TAG, "onClick:strisbnnum "+isbnno);
                publisher=etpublishername.getText().toString();
                Log.d(TAG, "onClick: strpublishername"+publisher);
                quantity=etbookquantity.getText().toString();
                price=etbookprice.getText().toString();
                Log.d(TAG, "onClick:price "+price);
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
        if (TextUtils.isEmpty(isbnno))
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
            updatebook(updatebookaction,description,author,publisher,isbnno,price,catrgoryid,user_id,status,discountprice,bookname,strfav,bookfrontimage,bookindeximage,bookbackimage,bookid,quantity,language);

        }
                break;
        }

    }

    private void updatebook(String updatebookaction, String description, String author, String publisher, String isbnno, String price, String catrgoryid, String user_id, String status, String discountprice, String bookname, String strfav, String bookfrontimage, String bookindeximage, String bookbackimage, String bookid, String quantity, String language) {

        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateBookBypublisherResponse> updateBookBypublisherResponseCall = apiInterface.updatebooks(updatebookaction,description,author,publisher,isbnno,price,catrgoryid,user_id, status,discountprice,bookname,strfav, bookfrontimage, bookindeximage,bookbackimage, bookid,quantity,language);

            updateBookBypublisherResponseCall.enqueue(new Callback<UpdateBookBypublisherResponse>() {
                @Override
                public void onResponse(Call<UpdateBookBypublisherResponse> call, Response<UpdateBookBypublisherResponse> response) {
                    progressDialog.dismiss();
                    updateBookBypublisherResponse = response.body();
                    Log.d(TAG, "onResponse:addEBookResponse " + response.toString());
                    if (updateBookBypublisherResponse != null) {
                        if (updateBookBypublisherResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, updateBookBypublisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:addEBookResponse msg " + updateBookBypublisherResponse.getResponseMessage());
                            startActivity(new Intent(context, PublisherDashboardActivity.class));
                            finish();


                        } else {
                            Log.d(TAG, "onResponse:addEBookResponse msg " + updateBookBypublisherResponse.getResponseMessage());
                            Toast.makeText(context,updateBookBypublisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<UpdateBookBypublisherResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

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
}
