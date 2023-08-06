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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pdftron.completereader.Response.AddEBookResponse;
import com.pdftron.completereader.Response.AllCategoryResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEBooksActivity extends AppCompatActivity implements View.OnClickListener {
    AddEBookResponse addEBookResponse;
    LinearLayout lebookprice,lbookdiscountprice;
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    TextView tvpdfname;
    String MYPREF = "Pustakmatket",TAG="TAG",user_id;
    EditText etbookname,etbookdescription,etauthorname,etisbnnum,etpublishername,etbookprice,etdiscountprice,etbookquantity;
    String strtype="English",strbooktext="newtext",strfree,catrgoryid,category,strboookfront,strboookindex,strboookback,strbookindex,strbookback,strcategoryid,status="0",strlanguage,strfav="0",strbookname,strbookdescription,strauthorname,strisbnnum,strpublishername,strbookprice,strdiscountprice,strbookquantity,addbookaction="add_ebook";
    ImageView ivfrontimage,ivindeximage,ivbackimage;
    Button btnsellbook,btnaddpdf;
    Spinner spncategory,spnlanguage;
    CheckBox checkBoxtc,checkBoxfree;
    private static final int PICK_IMAGE_REQUEST = 234;
    String path;
    private static final int PICK_IMAGE_REQUESTI = 235;

    private static final int PICK_IMAGE_REQUESTB = 236;
    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    File file,fileindex,fileback;

    private Uri filePath,filePathindex,filePathback,filePathpdf;
    String categoryaction="category";
    AllCategoryResponse allCategoryResponse;
    ArrayList<String> arrayListcat = new ArrayList<>();
    Bitmap bitmap,bitmapi,bitmapb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_e_books);
        context = AddEBooksActivity.this;
        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add E-Books");
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
        btnaddpdf=findViewById(R.id.btn_uploadpdf);
        tvpdfname=findViewById(R.id.tv_pdfname);
        btnaddpdf.setOnClickListener(this);
        spncategory=findViewById(R.id.spn_category);
        spnlanguage=findViewById(R.id.spn_lang);

        checkBoxfree=findViewById(R.id.checkbox_isfree);
        lbookdiscountprice=findViewById(R.id.lebookdiscountprice);
        lebookprice=findViewById(R.id.lebookprice);
        arrayListcat.add("Choose");
        checkBoxfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked)
                {
                    lbookdiscountprice.setVisibility(View.GONE);
                    lebookprice.setVisibility(View.GONE);

                }
                else {
                    lbookdiscountprice.setVisibility(View.VISIBLE);
                    lebookprice.setVisibility(View.VISIBLE);

                }
            }
        });


        checkBoxtc=findViewById(R.id.ch_terms);
        ivfrontimage.setOnClickListener(this);
        ivindeximage.setOnClickListener(this);
        ivbackimage.setOnClickListener(this);
        btnsellbook.setOnClickListener(this);

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
            case R.id.btn_sellbook:
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] frontimagebyte=byteArrayOutputStream.toByteArray();
                strboookfront= Base64.encodeToString(frontimagebyte, Base64.DEFAULT);

                ByteArrayOutputStream byteArrayOutputStreami=new ByteArrayOutputStream();
                bitmapi.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStreami);
                byte[] indeximagebyte=byteArrayOutputStreami.toByteArray();
                strboookindex= Base64.encodeToString(indeximagebyte, Base64.DEFAULT);

                ByteArrayOutputStream byteArrayOutputStreamb=new ByteArrayOutputStream();
                bitmapb.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStreamb);
                byte[] backimagebyte=byteArrayOutputStreamb.toByteArray();
                strboookback= Base64.encodeToString(backimagebyte, Base64.DEFAULT);


               /* String path = FilePath.getPath(this, filePathpdf);
                Log.d(TAG, "onClick: path"+path);
*/
                strbookname=etbookname.getText().toString();
                Log.d(TAG, "onClick:strbookname "+strbookname);
                strbookdescription=etbookdescription.getText().toString();
                Log.d(TAG, "onClick:strbookdescription "+strbookdescription);
                strauthorname=etauthorname.getText().toString();
                Log.d(TAG, "onClick:strbookdescription "+strbookdescription);
                strisbnnum=etisbnnum.getText().toString();
                Log.d(TAG, "onClick:strisbnnum "+strisbnnum);
                strpublishername=etpublishername.getText().toString();
                Log.d(TAG, "onClick: strpublishername"+strpublishername);
                strbookquantity=etbookquantity.getText().toString();

                if (checkBoxfree.isChecked())
                {
                    strfree="1";
                    strbookprice="00";
                    strdiscountprice="00";
                                  }
                else {
                    strfree="0";
                    strbookprice=etbookprice.getText().toString();
                    Log.d(TAG, "onClick:strbookprice "+strbookprice);
                    strdiscountprice=etdiscountprice.getText().toString();
                    Log.d(TAG, "onClick:strdiscountprice "+strdiscountprice);

                    if (TextUtils.isEmpty(strbookprice))
                    {
                        etbookprice.requestFocus();
                        etbookprice.setError("Please Enter E-Book Price");
                        return;
                    }
                    if (TextUtils.isEmpty(strdiscountprice))
                    {
                        etdiscountprice.requestFocus();
                        etdiscountprice.setError("Please Enter E-Book Discount Price");
                        return;
                    }
                }
                if (TextUtils.isEmpty(strbookname))
                {
                    etbookname.requestFocus();
                    etbookname.setError("Please Enter E-Book Name");
                    return;
                }
                if (TextUtils.isEmpty(strbookdescription))
                {
                    etbookdescription.requestFocus();
                    etbookdescription.setError("Please Enter E-Book Description");
                    return;
                }
                if (TextUtils.isEmpty(strauthorname))
                {
                    etauthorname.requestFocus();
                    etauthorname.setError("Please Enter E-Book Author Name");
                    return;
                }
                if (TextUtils.isEmpty(strisbnnum))
                {
                    etisbnnum.requestFocus();
                    etisbnnum.setError("Please Enter E-Book ISBN Number");
                    return;
                }
                if (TextUtils.isEmpty(strpublishername))
                {
                    etpublishername.requestFocus();
                    etpublishername.setError("Please Enter E-Book Publisher Name");
                    return;
                }


                else
                {
                    Log.d(TAG, "onClick: strbookprice"+strbookprice);
                    Log.d(TAG, "onClick: strbookdiscount"+strdiscountprice);
                    Log.d(TAG, "onClick: strcategoryid"+catrgoryid);
                    Log.d(TAG, "onClick: strfree"+strfree);
                    addebook(addbookaction,strbookdescription,strauthorname,strpublishername,strisbnnum,strbookprice,catrgoryid,user_id,status,strdiscountprice,strtype,strfree,strbookname,strbooktext,strlanguage,strfav,path,strboookfront,strbookindex,strbookback);
                }
break;
        }


    }

    private void addebook(String addbookaction, String strbookdescription, String strauthorname, String strpublishername, String strisbnnum, String strbookprice, String strcategoryid, String user_id, String status, String strdiscountprice, String strtype, String strfree, String strbookname, String strbooktext, String strlanguage, String strfav, String path, String strboookfront, String strbookindex, String strbookback) {

        if (isOnline()) {
            Log.d(TAG, "addebook: path"+path);
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AddEBookResponse> addEBookResponseCall = apiInterface.addebook(addbookaction, strbookdescription, strauthorname, strpublishername, strisbnnum, strbookprice,strcategoryid,user_id,status,strdiscountprice,strtype,strfree,strbookname,strbooktext,strlanguage,strfav,path,strboookfront,strbookindex,strbookback);

            addEBookResponseCall.enqueue(new Callback<AddEBookResponse>() {
                @Override
                public void onResponse(Call<AddEBookResponse> call, Response<AddEBookResponse> response) {
                    progressDialog.dismiss();
                    addEBookResponse = response.body();
                    Log.d(TAG, "onResponse:addEBookResponse " + response.toString());
                    if (addEBookResponse != null) {
                        if (addEBookResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, addEBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:addEBookResponse msg " + addEBookResponse.getResponseMessage());
                            startActivity(new Intent(context, PublisherDashboardActivity.class));
                            finish();

                        } else {
                            Log.d(TAG, "onResponse:addEBookResponse msg " + addEBookResponse.getResponseMessage());
                            Toast.makeText(context,addEBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AddEBookResponse> call, Throwable t) {
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
                inputStream = AddEBooksActivity.this.getContentResolver().openInputStream(uripath);
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
               path=Base64.encodeToString(pdfinbyte,Base64.DEFAULT);
                Log.d(TAG, "onActivityResult: path"+path);
                tvpdfname.setText(uripath.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            /*filePathpdf = data.getData();
            tvpdfname.setText(FilePath.getPath(this,filePathpdf).toString());*/
        }
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
