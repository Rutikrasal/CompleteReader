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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pdftron.completereader.Response.AddMagazineResponse;
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

public class AddMagazinesActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    TextView tvpdfname;
    String path;
    String MYPREF = "Pustakmatket",TAG="TAG",user_id;
    EditText etbookname,etbookdescription,etauthorname,etisbnnum,etpublishername,etbookprice,etdiscountprice,etbookquantity;
    String strfree="1",catrgoryid,category,strboookfront,strboookindex,strboookback,status="0",strfav="0",strbookname,strbookdescription,strauthorname,strisbnnum="000",strpublishername,strbookprice="oo",addmagazineaction="add_magazine";
    ImageView ivfrontimage,ivindeximage,ivbackimage;
    Button btnsellbook,btnaddpdf;
    Spinner spncategory;
    CheckBox checkBoxtc,checkBoxfree;
    private static final int PICK_IMAGE_REQUEST = 234;

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

AddMagazineResponse addMagazineResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_magazines);
        context = AddMagazinesActivity.this;
        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Magazine");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id= sharedPreferences.getString("user_id", "");
        etbookname=findViewById(R.id.et_bookname);
        etbookdescription=findViewById(R.id.et_bookdescription);
        etauthorname=findViewById(R.id.et_authorname);
        etpublishername=findViewById(R.id.et_publishername);
               ivfrontimage=findViewById(R.id.iv_frontimage);
        ivindeximage=findViewById(R.id.iv_indeximage);
        ivbackimage=findViewById(R.id.iv_backimage);
        btnsellbook=findViewById(R.id.btn_sellbook);
        btnaddpdf=findViewById(R.id.btn_uploadpdf);
        tvpdfname=findViewById(R.id.tv_pdfname);
        btnaddpdf.setOnClickListener(this);
        spncategory=findViewById(R.id.spn_category);
        checkBoxfree=findViewById(R.id.checkbox_isfree);
        arrayListcat.add("Choose");
        checkBoxtc=findViewById(R.id.ch_terms);
        ivfrontimage.setOnClickListener(this);
        ivindeximage.setOnClickListener(this);
        ivbackimage.setOnClickListener(this);
        btnsellbook.setOnClickListener(this);

        category(categoryaction);

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
              //  String path = FilePath.getPath(this, filePathpdf);

                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
                byte[] frontimagebyte=byteArrayOutputStream.toByteArray();
                strboookfront= Base64.encodeToString(frontimagebyte, Base64.DEFAULT);

                ByteArrayOutputStream byteArrayOutputStreami=new ByteArrayOutputStream();
                bitmapi.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStreami);
                byte[] indeximagebyte=byteArrayOutputStreami.toByteArray();
                strboookindex= Base64.encodeToString(indeximagebyte, Base64.DEFAULT);

                ByteArrayOutputStream byteArrayOutputStreamb=new ByteArrayOutputStream();
                bitmapb.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStreamb);
                byte[] backimagebyte=byteArrayOutputStreamb.toByteArray();
                strboookback= Base64.encodeToString(backimagebyte, Base64.DEFAULT);


                strbookname=etbookname.getText().toString();
            strbookdescription=etbookdescription.getText().toString();
            strauthorname=etauthorname.getText().toString();
            strpublishername=etpublishername.getText().toString();
            if (TextUtils.isEmpty(strbookname))
            {
                etbookname.requestFocus();
                etbookname.setError("Please Magazine Book Name");
                return;
            }
            if (TextUtils.isEmpty(strbookdescription))
            {
                etbookdescription.requestFocus();
                etbookdescription.setError("Please Magazine Description");
                return;
            }
            if (TextUtils.isEmpty(strauthorname))
            {
                etauthorname.requestFocus();
                etauthorname.setError("Please Enter Magazine Editor Name");
                return;
            }

            if (TextUtils.isEmpty(strpublishername))
            {
                etpublishername.requestFocus();
                etpublishername.setError("Please Enter  Publisher Name");
                return;
            }
            else {
                addmagazines(addmagazineaction,strbookdescription,strauthorname,strpublishername,strisbnnum,strbookprice,catrgoryid,user_id,status,strfree,strbookname,path,strboookfront,strboookindex,strboookback);
            }


break;
        }

    }

    private void addmagazines(String addmagazineaction, String strbookdescription, String strauthorname, String strpublishername, String strisbnnum, String strbookprice, String catrgoryid, String user_id, String status, String strfree, String strbookname, String path, String strboookfront, String strboookindex, String strboookback) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AddMagazineResponse> addMagazineResponseCall = apiInterface.addmagazine(addmagazineaction, strbookdescription, strauthorname, strpublishername, strisbnnum, strbookprice,catrgoryid,user_id,status,strfree,strbookname,path,strboookfront,strboookindex,strboookback);

            addMagazineResponseCall.enqueue(new Callback<AddMagazineResponse>() {
                @Override
                public void onResponse(Call<AddMagazineResponse> call, Response<AddMagazineResponse> response) {
                    progressDialog.dismiss();
                    addMagazineResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (addMagazineResponse != null) {
                        if (addMagazineResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, addMagazineResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + addMagazineResponse.getResponseMessage());
                            startActivity(new Intent(context, PublisherDashboardActivity.class));
                            finish();

                        } else {
                            Log.d(TAG, "onResponse:registration msg " + addMagazineResponse.getResponseMessage());
                            Toast.makeText(context,addMagazineResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AddMagazineResponse> call, Throwable t) {
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
              inputStream = AddMagazinesActivity.this.getContentResolver().openInputStream(uripath);
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
              tvpdfname.setText(uripath.toString());

          } catch (IOException e) {
              e.printStackTrace();
          }




        /*    filePathpdf = data.getData();
            tvpdfname.setText(FilePath.getPath(this,filePathpdf).toString());
*/
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
