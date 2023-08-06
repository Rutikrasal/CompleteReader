package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.pdftron.completereader.Adapter.MagazinesAdapter;
import com.pdftron.completereader.Adapter.RecentBookAdapter;
import com.pdftron.completereader.Adapter.RecenteBookAdapter;
import com.pdftron.completereader.Adapter.StudentTextNotesAdapter;
import com.pdftron.completereader.Adapter.StudentTexteNotesAdapter;
import com.pdftron.completereader.Adapter.StudentsBookAdapter;
import com.pdftron.completereader.Adapter.TopBookAdapter;
import com.pdftron.completereader.Adapter.TopeBookAdapter;
import com.pdftron.completereader.Response.MagazinesResponse;
import com.pdftron.completereader.Response.RecentBookResponse;
import com.pdftron.completereader.Response.RecenteBookResponse;
import com.pdftron.completereader.Response.StudentTextNotesResponse;
import com.pdftron.completereader.Response.StudentTexteNotesResponse;
import com.pdftron.completereader.Response.StudenteBookResponse;
import com.pdftron.completereader.Response.StudentsBookResponse;
import com.pdftron.completereader.Response.TopBookResponse;
import com.pdftron.completereader.Response.TopeBookResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeallActivity extends AppCompatActivity {
    String toolbarname;
    Toolbar toolbar;
    GridView gridView;
    String user_id,topbookaction="top_books",topebookaction="top_ebooks",recentbookaction="books",keyword="all",recentebookaction="ebooks",studentbookaction="student_books",magazinesaction="magazines",studenttextenotesaction="student_text_enotes",studenttextnotesaction="student_text_notes",studentebookaction="student_ebooks";
Context context;
    //Topbook
    TopBookResponse topBookResponse;
    ProgressDialog progressDialog;
    ArrayList<TopBookResponse.Datum> al_display_topbook;
    RecyclerView top_book_recycle;
    TopBookAdapter topBookAdapter;
    TextView tvtopbookseeall;

    //Topebook
    TopeBookResponse topeBookResponse;
    ArrayList<TopeBookResponse.Datum> al_display_topebook;
    TopeBookAdapter topeBookAdapter;


    //RecentBook
    RecentBookResponse recentBookResponse;
    ArrayList<RecentBookResponse.Datum> al_display_recentbook;
    RecentBookAdapter recentBookAdapter;


    //RecenteBook
    RecenteBookResponse recenteBookResponse;
    ArrayList<RecenteBookResponse.Datum> al_display_recentebook;
    RecenteBookAdapter recenteBookAdapter;


    //StudentBook
    StudentsBookResponse studentsBookResponse;
    ArrayList<StudentsBookResponse.Datum> al_display_studentsbook;
    StudentsBookAdapter studentsBookAdapter;


    //Student E-Book
//    StudentsBookResponse studentsBookResponse;
//    ArrayList<StudentsBookResponse.Datum> al_display_studentsbook;
//    RecyclerView students_book_recycle;
//    StudentsBookAdapter studentsBookAdapter;
    TextView tvstudentebookseeall;


    //StudentTextE-Notes
    StudentTexteNotesResponse studentTexteNotesResponse;
    ArrayList<StudentTexteNotesResponse.Datum> al_display_studentTexteNotes;
    StudentTexteNotesAdapter studentTexteNotesAdapter;


    //StudentTextNotes
    StudentTextNotesResponse studentTextNotesResponse;
    ArrayList<StudentTextNotesResponse.Datum> al_display_studentTextNotes;
    StudentTextNotesAdapter studentTextNotesAdapter;

    //Magazines

    MagazinesResponse magazinesResponse;
    ArrayList<MagazinesResponse.Datum> al_display_magazines;
    MagazinesAdapter magazinesAdapter;

    //StudentEBook
    StudenteBookResponse studenteBookResponse;
    ArrayList<StudenteBookResponse.Datum> al_display_studentebook;



    String TAG="TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeall);
        context=SeeallActivity.this;
        progressDialog=new ProgressDialog(this);
        gridView=findViewById(R.id.grid_see_all);
        Bundle bundle = getIntent().getExtras();
        toolbarname = bundle.getString("toolbar");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(toolbarname);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (toolbarname.matches("Top Books"))
        {
            topbook(topbookaction);


        }
        else if (toolbarname.matches("Top E-Books"))
        {
            topebook(topebookaction);

        }
        else if (toolbarname.matches("Recent Books"))
        {
            recentbook(recentbookaction,keyword);

        }
       else if (toolbarname.matches("Recent E-Books"))
        {
            recentebook(recentebookaction,keyword);

        }
        else if (toolbarname.matches("Student Books"))
        {
            studentbook(studentbookaction);

        }
        else if (toolbarname.matches("Student E-Books"))
        {
            studentebook(studentebookaction);

        }
         else if (toolbarname.matches("Student Text Notes"))
        {
            studenttextnotes(studenttextnotesaction);

        }
        else if (toolbarname.matches("Student Text E-Notes"))
        {
            studenttextenotes(studenttextenotesaction);

        }
        else if (toolbarname.matches("Magazines"))
        {
            magazines(magazinesaction);

        }
        //
        /*

*/
    }

    private void studentebook(String studentebookaction) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<StudenteBookResponse> studenteBookResponseCall = apiInterface.studentebook(studentebookaction);

            studenteBookResponseCall.enqueue(new Callback<StudenteBookResponse>() {
                @Override
                public void onResponse(Call<StudenteBookResponse> call, Response<StudenteBookResponse> response) {
                    progressDialog.dismiss();
                    studenteBookResponse = response.body();
                    Log.d(TAG, "onResponse:studenteBookResponse " + response.toString());
                    if (studenteBookResponse != null) {
                        if (studenteBookResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:studenteBookResponse msg " + studenteBookResponse.getResponseMessage());
                            al_display_studentebook = new ArrayList<>();
                            for (int i = 0; i < studenteBookResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: studenteBookResponsesize" + studenteBookResponse.getData().size());
                                if (studenteBookResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_studentebook.add(studenteBookResponse.getData().get(i));
                                    Log.d(TAG, "onResponse:studenteBookResponse al" + al_display_studentebook);
                                    Log.d(TAG, "onResponse: studenteBookResponsealsize" + al_display_studentebook.size());

                                }


                            }
                            GridstudentebookviewAdapter gridstudentebookviewAdapter=new GridstudentebookviewAdapter(context,al_display_studentebook);
                            gridView.setAdapter(gridstudentebookviewAdapter);


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + studenteBookResponse.getResponseMessage());
                            Toast.makeText(context,studenteBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<StudenteBookResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void magazines(String magazinesaction) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<MagazinesResponse> magazinesResponseCall = apiInterface.magazines(magazinesaction);

            magazinesResponseCall.enqueue(new Callback<MagazinesResponse>() {
                @Override
                public void onResponse(Call<MagazinesResponse> call, Response<MagazinesResponse> response) {
                    progressDialog.dismiss();
                    magazinesResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (magazinesResponse != null) {
                        if (magazinesResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + magazinesResponse.getResponseMessage());
                            al_display_magazines = new ArrayList<>();
                            for (int i = 0; i < magazinesResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + magazinesResponse.getData().size());
                                if (magazinesResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_magazines.add(magazinesResponse.getData().get(i));
                                    Log.d(TAG, "onResponse: al" + al_display_magazines);
                                    Log.d(TAG, "onResponse: alsize" + al_display_magazines.size());

                                }


                            }
                            GridmagazineviewAdapter gridmagazineviewAdapter=new GridmagazineviewAdapter(context,al_display_magazines);
                            gridView.setAdapter(gridmagazineviewAdapter);



                        } else {
                            Log.d(TAG, "onResponse:registration msg " + magazinesResponse.getResponseMessage());
                            Toast.makeText(context,magazinesResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<MagazinesResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void studenttextenotes(String studenttextenotesaction) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<StudentTexteNotesResponse> studentTexteNotesResponseCall = apiInterface.studenttextenotes(studenttextenotesaction);

            studentTexteNotesResponseCall.enqueue(new Callback<StudentTexteNotesResponse>() {
                @Override
                public void onResponse(Call<StudentTexteNotesResponse> call, Response<StudentTexteNotesResponse> response) {
                    progressDialog.dismiss();
                    studentTexteNotesResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (studentTexteNotesResponse != null) {
                        if (studentTexteNotesResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + studentTexteNotesResponse.getResponseMessage());
                            al_display_studentTexteNotes = new ArrayList<>();
                            for (int i = 0; i < studentTexteNotesResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + studentTexteNotesResponse.getData().size());
                                if (studentTexteNotesResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_studentTexteNotes.add(studentTexteNotesResponse.getData().get(i));
                                    Log.d(TAG, "onResponse: al" + al_display_studentTexteNotes);
                                    Log.d(TAG, "onResponse: alsize" + al_display_studentTexteNotes.size());

                                }


                            }
                            GridstudenttextenotesviewAdapter gridstudenttextenotesviewAdapter=new GridstudenttextenotesviewAdapter(context,al_display_studentTexteNotes);
                            gridView.setAdapter(gridstudenttextenotesviewAdapter);



                        } else {
                            Log.d(TAG, "onResponse:registration msg " + studentTexteNotesResponse.getResponseMessage());
                            Toast.makeText(context,studentTexteNotesResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<StudentTexteNotesResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void studenttextnotes(String studenttextnotesaction) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<StudentTextNotesResponse> studentTextNotesResponseCall = apiInterface.studenttextnotes(studenttextnotesaction);

            studentTextNotesResponseCall.enqueue(new Callback<StudentTextNotesResponse>() {
                @Override
                public void onResponse(Call<StudentTextNotesResponse> call, Response<StudentTextNotesResponse> response) {
                    progressDialog.dismiss();
                    studentTextNotesResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (studentTextNotesResponse != null) {
                        if (studentTextNotesResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + studentTextNotesResponse.getResponseMessage());
                            al_display_studentTextNotes = new ArrayList<>();
                            for (int i = 0; i < studentTextNotesResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + studentTextNotesResponse.getData().size());
                                if (studentTextNotesResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_studentTextNotes.add(studentTextNotesResponse.getData().get(i));
                                    Log.d(TAG, "onResponse: al" + al_display_studentTextNotes);
                                    Log.d(TAG, "onResponse: alsize" + al_display_studentTextNotes.size());

                                }


                            }
                            GridstudenttextnotesviewAdapter gridstudenttextnotesviewAdapter = new GridstudenttextnotesviewAdapter(context, al_display_studentTextNotes);
                            gridView.setAdapter(gridstudenttextnotesviewAdapter);



                        } else {
                            Log.d(TAG, "onResponse:registration msg " + studentTexteNotesResponse.getResponseMessage());
                            Toast.makeText(context,studentTexteNotesResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<StudentTextNotesResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void studentbook(String studentbookaction) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<StudentsBookResponse> studentsBookResponseCall = apiInterface.studentbook(studentbookaction);

            studentsBookResponseCall.enqueue(new Callback<StudentsBookResponse>() {
                @Override
                public void onResponse(Call<StudentsBookResponse> call, Response<StudentsBookResponse> response) {
                    progressDialog.dismiss();
                    studentsBookResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (studentsBookResponse != null) {
                        if (studentsBookResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + studentsBookResponse.getResponseMessage());
                            al_display_studentsbook = new ArrayList<>();
                            for (int i = 0; i < studentsBookResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + studentsBookResponse.getData().size());
                                if (studentsBookResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_studentsbook.add(studentsBookResponse.getData().get(i));
                                    Log.d(TAG, "onResponse: al" + al_display_studentsbook);
                                    Log.d(TAG, "onResponse: alsize" + al_display_studentsbook.size());

                                }


                            }
                            GridstudentbookviewAdapter gridstudentbookviewAdapter = new GridstudentbookviewAdapter(context, al_display_studentsbook);
                            gridView.setAdapter(gridstudentbookviewAdapter);


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + studentsBookResponse.getResponseMessage());
                            Toast.makeText(context,studentsBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<StudentsBookResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void recentebook(String recentebookaction, String keyword) {
        if (isOnline()) {
            Log.d(TAG, "recentebook: recentebookaction"+recentebookaction);
            Log.d(TAG, "recentebook: keyword"+keyword);
            progressDialog.setMessage("Please Wait...!!");
            Log.d(TAG, "recentebook: ");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RecenteBookResponse> recenteBookResponseCall = apiInterface.recentebook(recentebookaction,keyword);

            recenteBookResponseCall.enqueue(new Callback<RecenteBookResponse>() {
                @Override
                public void onResponse(Call<RecenteBookResponse> call, Response<RecenteBookResponse> response) {
                    progressDialog.dismiss();
                    recenteBookResponse = response.body();
                    Log.d(TAG, "onResponse:recentebook " + response.toString());
                    if (recenteBookResponse != null) {
                        Log.d(TAG, "onResponse:recenteBookResponse ");
                        if (recenteBookResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:recentebook msg " + recenteBookResponse.getResponseMessage());
                            al_display_recentebook = new ArrayList<>();
                            for (int i = 0; i < recenteBookResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + recenteBookResponse.getData().size());
                                if (recenteBookResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_recentebook.add(recenteBookResponse.getData().get(i));
                                    Log.d(TAG, "onResponse: al" + al_display_recentebook);
                                    Log.d(TAG, "onResponse: alsize" + al_display_recentebook.size());


                                }

                            }
                            GridrecentebookviewAdapter gridrecentebookviewAdapter = new GridrecentebookviewAdapter(context, al_display_recentebook);
                            gridView.setAdapter(gridrecentebookviewAdapter);


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + recenteBookResponse.getResponseMessage());
                            Toast.makeText(context,recenteBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<RecenteBookResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void recentbook(String recentbookaction, String keyword) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RecentBookResponse> recentBookResponseCall = apiInterface.recentbook(recentbookaction,keyword);

            recentBookResponseCall.enqueue(new Callback<RecentBookResponse>() {
                @Override
                public void onResponse(Call<RecentBookResponse> call, Response<RecentBookResponse> response) {
                    progressDialog.dismiss();
                    recentBookResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (recentBookResponse != null) {
                        if (recentBookResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + recentBookResponse.getResponseMessage());
                            al_display_recentbook = new ArrayList<>();
                            for (int i = 0; i < recentBookResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + recentBookResponse.getData().size());
                                if (recentBookResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_recentbook.add(recentBookResponse.getData().get(i));
                                    Log.d(TAG, "onResponse: al" + al_display_recentbook);
                                    Log.d(TAG, "onResponse: alsize" + al_display_recentbook.size());

                                }


                            }

                            GridrecentbookviewAdapter gridrecentbookviewAdapter = new GridrecentbookviewAdapter(context, al_display_recentbook);
                            gridView.setAdapter(gridrecentbookviewAdapter);


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + recentBookResponse.getResponseMessage());
                            Toast.makeText(context,recentBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<RecentBookResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void topebook(String topebookaction) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<TopeBookResponse> topeBookResponseCall = apiInterface.topebook(topebookaction);

            topeBookResponseCall.enqueue(new Callback<TopeBookResponse>() {
                @Override
                public void onResponse(Call<TopeBookResponse> call, Response<TopeBookResponse> response) {
                    progressDialog.dismiss();
                    topeBookResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (topeBookResponse != null) {
                        if (topeBookResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:registration msg " + topeBookResponse.getResponseMessage());
                            al_display_topebook = new ArrayList<>();
                            for (int i = 0; i < topeBookResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + topeBookResponse.getData().size());
                                if (topeBookResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_topebook.add(topeBookResponse.getData().get(i));
                                    Log.d(TAG, "onResponse: al" + al_display_topebook);
                                    Log.d(TAG, "onResponse: alsize" + al_display_topebook.size());

                                }


                            }

                            GridtopebookviewAdapter gridtopebookviewAdapter = new GridtopebookviewAdapter(context, al_display_topebook);
                            gridView.setAdapter(gridtopebookviewAdapter);


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + topeBookResponse.getResponseMessage());
                            Toast.makeText(context,topeBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<TopeBookResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void topbook(String topbookaction) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<TopBookResponse> topBookResponseCall = apiInterface.topbook(topbookaction);

            topBookResponseCall.enqueue(new Callback<TopBookResponse>() {
                @Override
                public void onResponse(Call<TopBookResponse> call, Response<TopBookResponse> response) {
                    progressDialog.dismiss();
                    topBookResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (topBookResponse != null) {
                        Log.d(TAG, "onResponse:topBookResponse ");
                        if (topBookResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:book msg " + topBookResponse.getResponseMessage());
                            al_display_topbook = new ArrayList<>();
                            for (int i = 0; i < topBookResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + topBookResponse.getData().size());
                                if (topBookResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_topbook.add(topBookResponse.getData().get(i));
                                    Log.d(TAG, "onResponse: al" + al_display_topbook);
                                    Log.d(TAG, "onResponse: alsize" + al_display_topbook.size());

                                }


                            }

                            GridviewAdapter gridviewAdapter = new GridviewAdapter(context, al_display_topbook);
                            gridView.setAdapter(gridviewAdapter);
                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));

                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + topBookResponse.getResponseMessage());
                            Toast.makeText(context,topBookResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<TopBookResponse> call, Throwable t) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
