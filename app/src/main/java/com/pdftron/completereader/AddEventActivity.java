package com.pdftron.completereader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pdftron.completereader.Response.AddEBookResponse;
import com.pdftron.completereader.Response.AddEventResponse;
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

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {
    AddEventResponse addEventResponse;
    LinearLayout lebookprice,lbookdiscountprice;
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    TextView tvpdfname;
    String strstatus="0",strpaystatus="unpaid",streventonlineprice,streventoflineprice, MYPREF = "Pustakmatket",TAG="TAG",user_id,strrole,streventname,streventdescription,streventubject,streventspeaciality,streventbusiness;
    EditText eteventname,eteventdescription,eteventonlneprice,eteventoflineprice,eteventsubject,eteventspeaciality,eteventbusiness;
    String strfree,stryourphoto,status="0",strfav="0",addeventaction="add_event";
    ImageView ivyourphoto;
    Button btnregisterevent,btnaddpdf;
    Spinner spnrole;
    CheckBox checkBoxtc,checkBoxfree;
    private static final int PICK_IMAGE_REQUEST = 234;

    private static final int PICK_IMAGE_REQUESTI = 235;

    private static final int PICK_IMAGE_REQUESTB = 236;
    //Pdf request code
    private int PICK_PDF_REQUEST = 1;
    String path;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    File file;

    private Uri filePath,filePathpdf;

    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        context = AddEventActivity.this;
        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Event");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id= sharedPreferences.getString("user_id", "");
        eteventname=findViewById(R.id.et_eventname);
        eteventdescription=findViewById(R.id.et_eventdescription);
        eteventsubject=findViewById(R.id.et_eventsubject);
        eteventspeaciality=findViewById(R.id.et_yourspeciality);
        eteventbusiness=findViewById(R.id.et_yourbusiness);
        eteventoflineprice=findViewById(R.id.et_eventofflineprice);
        eteventonlneprice=findViewById(R.id.et_eventonlineprice);
        spnrole=findViewById(R.id.spn_lang);
        btnregisterevent=findViewById(R.id.btn_sellbook);
        btnaddpdf=findViewById(R.id.btn_uploadpdf);
        spnrole=findViewById(R.id.spn_role);
        tvpdfname=findViewById(R.id.tv_pdfname);

        checkBoxfree=findViewById(R.id.checkbox_isfree);
        lbookdiscountprice=findViewById(R.id.lebookdiscountprice);
        lebookprice=findViewById(R.id.lebookprice);
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
        ivyourphoto=findViewById(R.id.iv_frontimage);
        ivyourphoto.setOnClickListener(this);
        btnregisterevent=findViewById(R.id.btn_registerevent);
        btnregisterevent.setOnClickListener(this);
        btnaddpdf.setOnClickListener(this);


        spnrole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strrole=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_uploadpdf:
                showFileChooserpdf();

                break;
            case R.id.iv_frontimage:
                showFileChooser();

                break;
            case R.id.btn_registerevent:

                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] frontimagebyte=byteArrayOutputStream.toByteArray();
                stryourphoto= Base64.encodeToString(frontimagebyte, Base64.DEFAULT);
              /*  String path = FilePath.getPath(this, filePathpdf);
                Log.d(TAG, "onClick: path"+path);
              */  streventname=eteventname.getText().toString();
                Log.d(TAG, "onClick:strbookname "+streventname);
                streventdescription=eteventdescription.getText().toString();
                Log.d(TAG, "onClick:strbookdescription "+streventdescription);
                streventubject=eteventsubject.getText().toString();
                Log.d(TAG, "onClick:strbookdescription "+streventubject);
                streventspeaciality=eteventspeaciality.getText().toString();
                Log.d(TAG, "onClick:strisbnnum "+streventspeaciality);
                streventbusiness=eteventbusiness.getText().toString();
                if (checkBoxfree.isChecked())
                {
                    strfree="1";
                    streventonlineprice="00";
                    streventoflineprice="00";
                }
                else {
                    strfree="0";
                    streventonlineprice=eteventonlneprice.getText().toString();
                    Log.d(TAG, "onClick:strbookprice "+streventonlineprice);
                    streventoflineprice=eteventoflineprice.getText().toString();
                    Log.d(TAG, "onClick:strdiscountprice "+streventoflineprice);

                    if (TextUtils.isEmpty(streventonlineprice))
                    {
                        eteventonlneprice.requestFocus();
                        eteventonlneprice.setError("Please Enter Event Online- Price");
                        return;
                    }
                    if (TextUtils.isEmpty(streventoflineprice))
                    {
                        eteventoflineprice.requestFocus();
                        eteventoflineprice.setError("Please Enter Event ofline  Price");
                        return;
                    }
                }
                if (TextUtils.isEmpty(streventname))
                {
                    eteventname.requestFocus();
                    eteventname.setError("Please Enter Event Name");
                    return;
                }
                if (TextUtils.isEmpty(streventdescription))
                {
                    eteventdescription.requestFocus();
                    eteventdescription.setError("Please Enter Event Description");
                    return;
                }
                if (TextUtils.isEmpty(streventbusiness))
                {
                    eteventbusiness.requestFocus();
                    eteventbusiness.setError("Please Enter Business");
                    return;
                }
                if (TextUtils.isEmpty(streventubject))
                {
                    eteventsubject.requestFocus();
                    eteventsubject.setError("Please Enter Subject");
                    return;
                }
                if (TextUtils.isEmpty(streventspeaciality))
                {
                    eteventspeaciality.requestFocus();
                    eteventspeaciality.setError("Please Enter Speciality");
                    return;
                }
                else
                {
                    Log.d(TAG, "onClick: strbookprice"+streventoflineprice);
                    Log.d(TAG, "onClick: strbookdiscount"+streventonlineprice);
                    Log.d(TAG, "onClick: strfree"+strfree);
                    addevent(addeventaction,streventname,streventubject,streventspeaciality,streventbusiness,strfree,streventoflineprice,streventonlineprice,streventdescription,user_id,strstatus,strrole,strpaystatus,strfav,path,stryourphoto);
                }
                break;


        }

        }

    private void addevent(String addeventaction, String streventname, String streventubject, String streventspeaciality, String streventbusiness, String strfree, String streventoflineprice, String streventonlineprice, String streventdescription, String user_id, String strstatus, String strrole, String strpaystatus, String strfav, String path, String stryourphoto) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AddEventResponse> addEventResponseCall = apiInterface.addevent( addeventaction,  streventname,  streventubject,  streventspeaciality,  streventbusiness,  strfree,  streventoflineprice,  streventonlineprice,  streventdescription,  user_id,  strstatus,  strrole,  strpaystatus,  strfav,  path,  stryourphoto);

            addEventResponseCall.enqueue(new Callback<AddEventResponse>() {
                @Override
                public void onResponse(Call<AddEventResponse> call, Response<AddEventResponse> response) {
                    progressDialog.dismiss();
                    addEventResponse = response.body();
                    Log.d(TAG, "onResponse:addEBookResponse " + response.toString());
                    if (addEventResponse != null) {
                        if (addEventResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, addEventResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:addEBookResponse msg " + addEventResponse.getResponseMessage());
                            startActivity(new Intent(context, EventDashboardActivity.class));
                            finish();

                        } else {
                            Log.d(TAG, "onResponse:addEBookResponse msg " + addEventResponse.getResponseMessage());
                            Toast.makeText(context,addEventResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AddEventResponse> call, Throwable t) {
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

    private void showFileChooserpdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                ivyourphoto.setImageBitmap(bitmap);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uripath=data.getData();
            InputStream inputStream= null;
            try {
                inputStream = AddEventActivity.this.getContentResolver().openInputStream(uripath);
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



         /*   filePathpdf = data.getData();
            tvpdfname.setText(FilePath.getPath(this,filePathpdf).toString());
*/
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
