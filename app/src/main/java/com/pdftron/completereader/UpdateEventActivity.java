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

import com.pdftron.completereader.Response.AddEventResponse;
import com.pdftron.completereader.Response.UpdateEBookResponse;
import com.pdftron.completereader.Response.UpdateEventResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEventActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Toolbar toolbar;
    TextView tvpdfname;
    AddEventResponse addEventResponse;
    LinearLayout lebookprice,lbookdiscountprice;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    EditText eteventname,eteventdescription,eteventonlneprice,eteventoflineprice,eteventsubject,eteventspeaciality,eteventbusiness;
    ImageView ivyourphoto;
    Button btnupdateevent,btnupdateimages,btnupdatepdf;
    Spinner spnrole;
    CheckBox checkBoxtc,checkBoxfree;
    private static final int PICK_IMAGE_REQUEST = 234;

    private static final int PICK_IMAGE_REQUESTI = 235;

    private static final int PICK_IMAGE_REQUESTB = 236;
    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    File file;

    private Uri filePath,filePathpdf;

    Bitmap bitmap;
    UpdateEventResponse updateEventResponse;


    String updateeventaction="update_event",MYPREF = "Pustakmatket",TAG="TAG",eventid,str_eventname,subject,str_speciality,strbusiness,str_free,str_eventprice,str_onlineprice,str_description,user_id,str_status,str_role,str_paystatus,str_isfav,str_resume,strimageurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);
        context = UpdateEventActivity.this;
        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Update Event");
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
        btnupdateevent=findViewById(R.id.btn_updateevent);
        btnupdateevent.setOnClickListener(this);
        btnupdatepdf=findViewById(R.id.btn_updatepdf);
        btnupdatepdf.setOnClickListener(this);
        btnupdateimages=findViewById(R.id.btn_updateimage);
        btnupdateimages.setOnClickListener(this);

        spnrole=findViewById(R.id.spn_role);

        checkBoxfree=findViewById(R.id.checkbox_isfree);
        lbookdiscountprice=findViewById(R.id.lebookdiscountprice);
        lebookprice=findViewById(R.id.lebookprice);
        checkBoxtc=findViewById(R.id.ch_terms);
        ivyourphoto=findViewById(R.id.iv_frontimage);
        ivyourphoto.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        eventid=bundle.getString("eventid");
        str_eventname=bundle.getString("eventname");
        subject=bundle.getString("eventsubject");
        str_speciality=bundle.getString("speciality");
        strbusiness=bundle.getString("business");
        str_free=bundle.getString("free");
        str_eventprice=bundle.getString("offlineprice");
        str_onlineprice=bundle.getString("onlineprice");
        str_description=bundle.getString("description");
        user_id=bundle.getString("userid");
        str_status=bundle.getString("status");
        str_role=bundle.getString("role");
        str_paystatus=bundle.getString("paystatus");
        str_isfav=bundle.getString("isfav");
        str_resume=bundle.getString("resume");
        strimageurl=bundle.getString("yourphoto");

        if (str_free.matches("0"))
        {
            checkBoxfree.setChecked(false);
            eteventoflineprice.setVisibility(View.VISIBLE);
            eteventonlneprice.setVisibility(View.VISIBLE);
            eteventoflineprice.setText(str_eventprice);
            eteventonlneprice.setText(str_onlineprice);

        }
        else if (str_free.matches("1")){
            checkBoxfree.setChecked(true);
            eteventoflineprice.setVisibility(View.GONE);
            eteventonlneprice.setVisibility(View.GONE);



        }
        Picasso
                .get()
                .load(strimageurl)
                .into(ivyourphoto);
        spnrole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_role=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_updateimage:
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
                byte[] frontimagebyte=byteArrayOutputStream.toByteArray();
                strimageurl= Base64.encodeToString(frontimagebyte, Base64.DEFAULT);
                Toast.makeText(context,"Update Imge",Toast.LENGTH_SHORT).show();

                break;/*
            case R.id.btn_updatepdf:
                str_resume = FilePath.getPath(this, filePathpdf);
                Log.d(TAG, "onClick: path"+str_resume);
                Toast.makeText(context,"Update PDf",Toast.LENGTH_SHORT).show();

                break;*/

            case R.id.btn_uploadpdf:
                showFileChooserpdf();

                break;

            case R.id.iv_frontimage:
                showFileChooser();

                break;
            case R.id.btn_updateevent:
                str_eventname=eteventname.getText().toString();
                Log.d(TAG, "onClick:str_eventname "+str_eventname);
                str_description=eteventdescription.getText().toString();
                Log.d(TAG, "onClick:str_description "+str_description);
                subject=eteventsubject.getText().toString();
                Log.d(TAG, "onClick:subject "+subject);
                str_speciality=eteventspeaciality.getText().toString();
                Log.d(TAG, "onClick:str_speciality "+str_speciality);
                strbusiness=eteventbusiness.getText().toString();
                if (checkBoxfree.isChecked())
                {
                    str_free="1";
                    str_eventprice="00";
                    str_onlineprice="00";
                }
                else {
                    str_free="0";
                    str_onlineprice=eteventonlneprice.getText().toString();
                    Log.d(TAG, "onClick:str_onlineprice "+str_onlineprice);
                    str_eventprice=eteventoflineprice.getText().toString();
                    Log.d(TAG, "onClick:str_eventprice "+str_eventprice);

                    if (TextUtils.isEmpty(str_onlineprice))
                    {
                        eteventonlneprice.requestFocus();
                        eteventonlneprice.setError("Please Enter Event Online- Price");
                        return;
                    }
                    if (TextUtils.isEmpty(str_eventprice))
                    {
                        eteventoflineprice.requestFocus();
                        eteventoflineprice.setError("Please Enter Event ofline  Price");
                        return;
                    }
                }
                if (TextUtils.isEmpty(str_eventname))
                {
                    eteventname.requestFocus();
                    eteventname.setError("Please Enter Event Name");
                    return;
                }
                if (TextUtils.isEmpty(str_description))
                {
                    eteventdescription.requestFocus();
                    eteventdescription.setError("Please Enter Event Description");
                    return;
                }
                if (TextUtils.isEmpty(strbusiness))
                {
                    eteventbusiness.requestFocus();
                    eteventbusiness.setError("Please Enter Business");
                    return;
                }
                if (TextUtils.isEmpty(subject))
                {
                    eteventsubject.requestFocus();
                    eteventsubject.setError("Please Enter Subject");
                    return;
                }
                if (TextUtils.isEmpty(str_speciality))
                {
                    eteventspeaciality.requestFocus();
                    eteventspeaciality.setError("Please Enter Speciality");
                    return;
                }
                else
                {
                    Log.d(TAG, "onClick: str_eventprice"+str_eventprice);
                    Log.d(TAG, "onClick: str_onlineprice"+str_onlineprice);
                    Log.d(TAG, "onClick: strfree"+str_free);
                    updateevent(updateeventaction,eventid,str_eventname,subject,str_speciality,strbusiness,str_free,str_eventprice,str_onlineprice,str_description,user_id,str_status,str_role,str_paystatus,str_isfav,str_resume,strimageurl);
                }

                break;


        }


    }

    private void updateevent(String updateeventaction, String eventid, String str_eventname, String subject, String str_speciality, String strbusiness, String str_free, String str_eventprice, String str_onlineprice, String str_description, String user_id, String str_status, String str_role, String str_paystatus, String str_isfav, String str_resume, String strimageurl) {

        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateEventResponse> updateEventResponseCall = apiInterface.updateevent( updateeventaction,  eventid,  str_eventname,  subject,  str_speciality,  strbusiness,  str_free,  str_eventprice,  str_onlineprice,  str_description,  user_id,  str_status,  str_role,  str_paystatus,  str_isfav,  str_resume, strimageurl);

            updateEventResponseCall.enqueue(new Callback<UpdateEventResponse>() {
                @Override
                public void onResponse(Call<UpdateEventResponse> call, Response<UpdateEventResponse> response) {
                    progressDialog.dismiss();
                    updateEventResponse = response.body();
                    Log.d(TAG, "onResponse:addEBookResponse " + response.toString());
                    if (updateEventResponse != null) {
                        if (updateEventResponse.getResponseCode().equals(0)) {
                            Toast.makeText(context, updateEventResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:addEBookResponse msg " + updateEventResponse.getResponseMessage());
                            startActivity(new Intent(context, EventDashboardActivity.class));
                            finish();

                        } else {
                            Log.d(TAG, "onResponse:addEBookResponse msg " + updateEventResponse.getResponseMessage());
                            Toast.makeText(context,updateEventResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<UpdateEventResponse> call, Throwable t) {
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
                inputStream = UpdateEventActivity.this.getContentResolver().openInputStream(uripath);
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
                str_resume=Base64.encodeToString(pdfinbyte,Base64.DEFAULT);
                tvpdfname.setText(uripath.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }



          /*  filePathpdf = data.getData();
            tvpdfname.setText(FilePath.getPath(this,filePathpdf).toString());
*/
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
