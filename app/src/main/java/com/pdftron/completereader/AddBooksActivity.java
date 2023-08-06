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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Response.AddBookByAuthorResponse;
import com.pdftron.completereader.Response.AllCategoryResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBooksActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG",user_id;
    EditText etbookname,etbookdescription,etauthorname,etisbnnum,etpublishername,etbookprice,etdiscountprice,etbookquantity;
    String catrgoryid,category,strboookfront,strboookindex,strboookback,strbookindex,strbookback,strcategoryid,status="0",strlanguage,strfav="0",strbookname,strbookdescription,strauthorname,strisbnnum,strpublishername,strbookprice,strdiscountprice,strbookquantity,addbookaction="add_book";
    ImageView ivfrontimage,ivindeximage,ivbackimage;
    Button btnsellbook;
    Spinner spncategory,spnlanguage;
    CheckBox checkBoxtc;
    private static final int PICK_IMAGE_REQUEST = 234;
    File file,fileindex,fileback;

    private static final int PICK_IMAGE_REQUESTI = 235;

    private static final int PICK_IMAGE_REQUESTB = 236;
    AddBookByAuthorResponse addBookByAuthorResponse;



    private Uri filePath,filePathindex,filePathback;
    String categoryaction="category";
    AllCategoryResponse allCategoryResponse;
    ArrayList<String> arrayListcat = new ArrayList<>();
    Bitmap bitmap,bitmapi,bitmapb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);
        context = AddBooksActivity.this;
        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Books");
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
        btnsellbook=findViewById(R.id.btn_sellbook);
        spncategory=findViewById(R.id.spn_category);
        spnlanguage=findViewById(R.id.spn_lang);
        checkBoxtc=findViewById(R.id.ch_terms);
        ivfrontimage.setOnClickListener(this);
        ivindeximage.setOnClickListener(this);
        ivbackimage.setOnClickListener(this);
        btnsellbook.setOnClickListener(this);
        arrayListcat.add("Choose");

        category(categoryaction);

        spnlanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strlanguage=adapterView.getItemAtPosition(i).toString();

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
            case R.id.iv_frontimage:
               showFileChooser();
/*

                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                Log.d(TAG, "choosePhotoFromGallary: "+"Inside");
*/
                break;

            case R.id.iv_indeximage:
                showFileChooseri();

                break;

            case R.id.iv_backimage:
                showFileChooserb();

                break;
            case R.id.btn_sellbook:
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] frontimagebyte=byteArrayOutputStream.toByteArray();
                Log.d(TAG, "onClick:fb "+frontimagebyte);
                strboookfront= Base64.encodeToString(frontimagebyte, Base64.DEFAULT);
                Log.d(TAG, "onClick:strboookfront "+strboookfront);
              /*  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] b = baos.toByteArray();
                String encImage = Base64.encodeToString(b, Base64.DEFAULT);
                Log.d(TAG, "onClick: encImage"+encImage);
*/
                ByteArrayOutputStream byteArrayOutputStreami=new ByteArrayOutputStream();
                bitmapi.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStreami);
                byte[] indeximagebyte=byteArrayOutputStreami.toByteArray();
                strboookindex= Base64.encodeToString(indeximagebyte, Base64.DEFAULT);

                ByteArrayOutputStream byteArrayOutputStreamb=new ByteArrayOutputStream();
                bitmapb.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStreamb);
                byte[] backimagebyte=byteArrayOutputStreamb.toByteArray();
                strboookback= Base64.encodeToString(backimagebyte, Base64.DEFAULT);

                strbookname=etbookname.getText().toString();
                strbookdescription=etbookdescription.getText().toString();
                strauthorname=etauthorname.getText().toString();
                strisbnnum=etisbnnum.getText().toString();
                strpublishername=etpublishername.getText().toString();
                strbookprice=etbookprice.getText().toString();
                strdiscountprice=etdiscountprice.getText().toString();
                strbookquantity=etbookquantity.getText().toString();
                if (TextUtils.isEmpty(strbookname))
                {
                    etbookname.requestFocus();
                    etbookname.setError("Please Enter Book Name");
                    return;
                }
                if (TextUtils.isEmpty(strbookdescription))
                {
                    etbookdescription.requestFocus();
                    etbookdescription.setError("Please Enter Book Description");
                    return;
                }
                if (TextUtils.isEmpty(strauthorname))
                {
                    etauthorname.requestFocus();
                    etauthorname.setError("Please Enter Book Author Name");
                    return;
                }
                if (TextUtils.isEmpty(strisbnnum))
                {
                    etisbnnum.requestFocus();
                    etisbnnum.setError("Please Enter Book ISBN Number");
                    return;
                }
                if (TextUtils.isEmpty(strpublishername))
                {
                    etpublishername.requestFocus();
                    etpublishername.setError("Please Enter Book Publisher Name");
                    return;
                }
                if (TextUtils.isEmpty(strbookprice))
                {
                    etbookprice.requestFocus();
                    etbookprice.setError("Please Enter Book Price");
                    return;
                }
                if (TextUtils.isEmpty(strdiscountprice))
                {
                    etdiscountprice.requestFocus();
                    etdiscountprice.setError("Please Enter Book Discount Price");
                    return;
                }
                if (TextUtils.isEmpty(strbookquantity))
                {
                    etbookquantity.requestFocus();
                    etbookquantity.setError("Please Enter Book Quantity");
                    return;
                }
                else
                {
                    addbook(addbookaction,strbookname,strbookdescription,strauthorname,strpublishername,strisbnnum,strbookprice,strbookquantity,catrgoryid,strboookfront,strboookindex,strboookback,user_id,status,strdiscountprice,strlanguage,strfav);

                }
                break;
        }

    }


    private void addbook(String addbookaction, String strbookname, String strbookdescription, String strauthorname, String strpublishername, String strisbnnum, String strbookprice, String strbookquantity, String catrgoryid, String strboookfront, String strboookindex, String strboookback, String user_id, String status, String strdiscountprice, String strlanguage, String strfav) {

        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AddBookByAuthorResponse> addBookByAuthorResponseCall = apiInterface.addbooks(addbookaction, strbookname, strbookdescription, strauthorname, strpublishername, strisbnnum,strbookprice,strbookquantity,catrgoryid,strboookfront,strboookindex,strboookback,user_id,status,strdiscountprice,strlanguage,strfav);

            addBookByAuthorResponseCall.enqueue(new Callback<AddBookByAuthorResponse>() {
                @Override
                public void onResponse(Call<AddBookByAuthorResponse> call, Response<AddBookByAuthorResponse> response) {
                    progressDialog.dismiss();
                    addBookByAuthorResponse = response.body();
                    Log.d(TAG, "onResponse:addEBookResponse " + response.toString());
                    if (addBookByAuthorResponse != null) {
                        if (addBookByAuthorResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, addBookByAuthorResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:addEBookResponse msg " + addBookByAuthorResponse.getResponseMessage());
                            startActivity(new Intent(context, PublisherDashboardActivity.class));
                            finish();



                        } else {
                            Log.d(TAG, "onResponse:addEBookResponse msg " + addBookByAuthorResponse.getResponseMessage());
                            Toast.makeText(context,addBookByAuthorResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AddBookByAuthorResponse> call, Throwable t) {
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
                        Log.d(TAG, "onActivityResult:bitmap "+bitmap);
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
