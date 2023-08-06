package com.pdftron.completereader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pdftron.completereader.Adapter.CategoryAdapter;
import com.pdftron.completereader.Adapter.EventsAdapter;
import com.pdftron.completereader.Adapter.MagazinesAdapter;
import com.pdftron.completereader.Adapter.RecentBookAdapter;
import com.pdftron.completereader.Adapter.RecenteBookAdapter;
import com.pdftron.completereader.Adapter.StudentTextNotesAdapter;
import com.pdftron.completereader.Adapter.StudentTexteNotesAdapter;
import com.pdftron.completereader.Adapter.StudenteBookAdapter;
import com.pdftron.completereader.Adapter.StudentsBookAdapter;
import com.pdftron.completereader.Adapter.TopBookAdapter;
import com.pdftron.completereader.Adapter.TopeBookAdapter;
import com.pdftron.completereader.Adapter.ViewPagerAdapter;
import com.pdftron.completereader.Response.AllCategoryResponse;
import com.pdftron.completereader.Response.EventResponse;
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

public class DashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

     BottomNavigationView bottomNavigation;
    Toolbar toolbar1;
    Context context;
    private long backPressedTime;
    private Toast backToast;
    ImageView iconBadge;
    String whatsappnum,TAG="TAG";
    String strtoolbarname,user_id,user_type,topbookaction="top_books",topebookaction="top_ebooks",recentbookaction="books",keyword,recentebookaction="ebooks",studentbookaction="student_books",studentebookaction="student_ebooks",magazinesaction="magazines",studenttextenotesaction="student_text_enotes",studenttextnotesaction="student_text_notes",eventaction="events";
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket";
    ViewPager viewPager;
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
    RecyclerView top_ebook_recycle;
    TopeBookAdapter topeBookAdapter;
    TextView tvtopebookseeall;


    //RecentBook
    RecentBookResponse recentBookResponse;
    ArrayList<RecentBookResponse.Datum> al_display_recentbook;
    RecyclerView recent_book_recycle;
    RecentBookAdapter recentBookAdapter;
    TextView tvrecentbookseeall;


    //RecenteBook
    RecenteBookResponse recenteBookResponse;
    ArrayList<RecenteBookResponse.Datum> al_display_recentebook;
    RecyclerView recent_ebook_recycle;
    RecenteBookAdapter recenteBookAdapter;
    TextView tvrecentebookseeall;


    //StudentBook
    StudentsBookResponse studentsBookResponse;
    ArrayList<StudentsBookResponse.Datum> al_display_studentsbook;
    RecyclerView students_book_recycle;
    StudentsBookAdapter studentsBookAdapter;
    TextView tvstudentbookseeall;


    //Student E-Book
    StudenteBookResponse studenteBookResponse;
    ArrayList<StudenteBookResponse.Datum> al_display_studentebook;
    RecyclerView student_ebook_recycle;
    StudenteBookAdapter studenteBookAdapter;
    TextView tvstudentebookseeall;


    //StudentTextE-Notes
    StudentTexteNotesResponse studentTexteNotesResponse;
    ArrayList<StudentTexteNotesResponse.Datum> al_display_studentTexteNotes;
    RecyclerView studentTexteNotes_recycle;
    StudentTexteNotesAdapter studentTexteNotesAdapter;
    TextView tvstudenttextenotesseeall;


    //StudentTextNotes
    StudentTextNotesResponse studentTextNotesResponse;
    ArrayList<StudentTextNotesResponse.Datum> al_display_studentTextNotes;
    RecyclerView studentTextNotes_recycle;
    StudentTextNotesAdapter studentTextNotesAdapter;
    TextView tvstudenttextnotesseeall;

    //Magazines

    MagazinesResponse magazinesResponse;
    ArrayList<MagazinesResponse.Datum> al_display_magazines;
    RecyclerView magazines_recycle;
    MagazinesAdapter magazinesAdapter;
    TextView tvmagazineseeall;


    //Events

    EventResponse eventResponse;
    ArrayList<EventResponse.Datum> al_display_events;
    RecyclerView events_recycle;
    EventsAdapter eventsAdapter;
    TextView tveventsseeall;

    //Category
    String categoryaction="category";
    AllCategoryResponse allCategoryResponse;
    ArrayList<AllCategoryResponse.Datum> al_display_category;
    RecyclerView category_recycle;
    CategoryAdapter categoryAdapter;

    Integer[] images = {R.drawable.a /*R.drawable.sliderimage_three, R.drawable.sliderimage_four*/};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        context=DashboardActivity.this;
        tvtopbookseeall=findViewById(R.id.tv_seealltopbook);
        tvtopbookseeall.setOnClickListener(this);

        tvtopebookseeall=findViewById(R.id.tv_seealltopebook);
        tvtopebookseeall.setOnClickListener(this);

        tvrecentbookseeall=findViewById(R.id.tv_seeallrecentbook);
        tvrecentbookseeall.setOnClickListener(this);

        tvrecentebookseeall=findViewById(R.id.tv_seeallrecentebook);
        tvrecentebookseeall.setOnClickListener(this);

        tvstudentbookseeall=findViewById(R.id.tv_seeallstudentbook);
        tvstudentbookseeall.setOnClickListener(this);

        tvstudenttextnotesseeall=findViewById(R.id.tv_seeallstudenttextnote);
        tvstudenttextnotesseeall.setOnClickListener(this);

        tvstudenttextenotesseeall=findViewById(R.id.tv_seeallstudenttextenote);
        tvstudenttextenotesseeall.setOnClickListener(this);

        tvmagazineseeall=findViewById(R.id.tv_seeallmagazine);
        tvmagazineseeall.setOnClickListener(this);

        tveventsseeall=findViewById(R.id.tv_seeallevent);
        tveventsseeall.setOnClickListener(this);

        tvstudentebookseeall=findViewById(R.id.tv_seeallstudentebook);
        tvstudentebookseeall.setOnClickListener(this);






        progressDialog=new ProgressDialog(context);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);

        top_book_recycle=findViewById(R.id.recyle_topbook);

        top_ebook_recycle=findViewById(R.id.recyle_top_ebook);

        recent_book_recycle=findViewById(R.id.recyle_recentbook);

        recent_ebook_recycle=findViewById(R.id.recyle_recentebook);

        students_book_recycle=findViewById(R.id.recyle_studentbook);

        student_ebook_recycle=findViewById(R.id.recyle_studentebook);

        studentTexteNotes_recycle=findViewById(R.id.recyle_studenttextenote);

        studentTextNotes_recycle=findViewById(R.id.recyle_studenttextnote);

        magazines_recycle=findViewById(R.id.recyle_magazine);

        events_recycle=findViewById(R.id.recyle_event);

        category_recycle=findViewById(R.id.recyle_cat);



        user_id = sharedPreferences.getString("user_id", "");
        user_type = sharedPreferences.getString("user_type", "");

        Log.d(TAG, "onCreate: uuuserid"+user_id);

        iconBadge=findViewById(R.id.icon_badge);
        iconBadge.setOnClickListener(this);
        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().getThemedContext();
        toolbar1.setTitleTextColor(Color.BLACK);

        toolbar1.setTitle("");
        getSupportActionBar().setLogo(R.drawable.p);
        toolbar1.setNavigationIcon(R.mipmap.ic_more);

        getSupportActionBar().setDisplayUseLogoEnabled(true);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.navigation_books);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_search:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0,0);

                        finish();
                        return true;
                    case R.id.navigation_books:
                        return true;

                    case R.id.navigation_cart:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
finish();
                        return true;

                    case R.id.navigation_account:
                        startActivity(new Intent(getApplicationContext(),Account.class));
                        overridePendingTransition(0,0);
                        finish();


                        return true;

                }
                return false;
            }
        });







        //slider_image();
        topbook(topbookaction);
        topebook(topebookaction);
        recentbook(recentbookaction,keyword);
        recentebook(recentebookaction,keyword);
        studentbook(studentbookaction);
        studentebook(studentebookaction);
        studenttextenotes(studenttextenotesaction);
        studenttextnotes(studenttextnotesaction);
        magazines(magazinesaction);
        events(eventaction);

        category(categoryaction);

        init();

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
                            al_display_category = new ArrayList<>();
                            for (int i = 0; i < allCategoryResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + allCategoryResponse.getData().size());
                                al_display_category.add(allCategoryResponse.getData().get(i));
                                Log.d(TAG, "onResponse: al" + al_display_category);
                                Log.d(TAG, "onResponse: alsize" + al_display_category.size());


                            }
                            category_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
                            categoryAdapter = new CategoryAdapter(context, al_display_category);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                            category_recycle.setLayoutManager(horizontalLayoutManager);
                            category_recycle.setAdapter(categoryAdapter);

                            category_recycle.addItemDecoration(new DividerItemDecoration(context, 0));



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


    private void events(String eventaction) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<EventResponse> eventResponseCall = apiInterface.events(eventaction);

            eventResponseCall.enqueue(new Callback<EventResponse>() {
                @Override
                public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                    progressDialog.dismiss();
                    eventResponse = response.body();
                    Log.d(TAG, "onResponse:eventResponse " + response.toString());
                    if (eventResponse != null) {
                        if (eventResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:studenteBookResponse msg " + eventResponse.getResponseMessage());
                            al_display_events = new ArrayList<>();
                            for (int i = 0; i < eventResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: studenteBookResponsesize" + eventResponse.getData().size());
                                if (eventResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    al_display_events.add(eventResponse.getData().get(i));
                                    Log.d(TAG, "onResponse:studenteBookResponse al" + al_display_events);
                                    Log.d(TAG, "onResponse: studenteBookResponsealsize" + al_display_events.size());


                                }


                            }
                            eventsAdapter = new EventsAdapter(context, al_display_events);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            events_recycle.setLayoutManager(horizontalLayoutManager);
                            events_recycle.setAdapter(eventsAdapter);




                        } else {
                            Log.d(TAG, "onResponse:registration msg " + eventResponse.getResponseMessage());
                            Toast.makeText(context,eventResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<EventResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

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
                            studenteBookAdapter = new StudenteBookAdapter(context, al_display_studentebook);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            student_ebook_recycle.setLayoutManager(horizontalLayoutManager);
                            student_ebook_recycle.setAdapter(studenteBookAdapter);




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
                            studentTextNotesAdapter = new StudentTextNotesAdapter(context, al_display_studentTextNotes);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            studentTextNotes_recycle.setLayoutManager(horizontalLayoutManager);
                            studentTextNotes_recycle.setAdapter(studentTextNotesAdapter);




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
                            studentTexteNotesAdapter = new StudentTexteNotesAdapter(context, al_display_studentTexteNotes);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            studentTexteNotes_recycle.setLayoutManager(horizontalLayoutManager);
                            studentTexteNotes_recycle.setAdapter(studentTexteNotesAdapter);




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
                                if (magazinesResponse.getData().get(i).getStatus().matches("1"))
                                {
                                    Log.d(TAG, "onResponse: size" + magazinesResponse.getData().size());
                                    al_display_magazines.add(magazinesResponse.getData().get(i));
                                    Log.d(TAG, "onResponse: al" + al_display_magazines);
                                    Log.d(TAG, "onResponse: alsize" + al_display_magazines.size());

                                }


                            }
                            magazinesAdapter = new MagazinesAdapter(context, al_display_magazines);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            magazines_recycle.setLayoutManager(horizontalLayoutManager);
                            magazines_recycle.setAdapter(magazinesAdapter);




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
                            studentsBookAdapter = new StudentsBookAdapter(context, al_display_studentsbook);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            students_book_recycle.setLayoutManager(horizontalLayoutManager);
                            students_book_recycle.setAdapter(studentsBookAdapter);




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

    private void init() {
        for (int i = 0; i < images.length; i++)
            ImagesArray.add(images[i]);

        viewPager=findViewById(R.id.viewpagerslid);
        ViewPagerAdapter adapterView = new ViewPagerAdapter(context,ImagesArray);
        viewPager.setAdapter(adapterView);


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
                            recenteBookAdapter = new RecenteBookAdapter(context, al_display_recentebook);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            recent_ebook_recycle.setLayoutManager(horizontalLayoutManager);
                            recent_ebook_recycle.setAdapter(recenteBookAdapter);




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
                            recentBookAdapter = new RecentBookAdapter(context, al_display_recentbook);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            recent_book_recycle.setLayoutManager(horizontalLayoutManager);
                            recent_book_recycle.setAdapter(recentBookAdapter);




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
                            // top_ebook_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                            topeBookAdapter = new TopeBookAdapter(context, al_display_topebook);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            top_ebook_recycle.setLayoutManager(horizontalLayoutManager);
                            top_ebook_recycle.setAdapter(topeBookAdapter);




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
                            //top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));

                            topBookAdapter = new TopBookAdapter(context, al_display_topbook);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            top_book_recycle.setLayoutManager(horizontalLayoutManager);
                            top_book_recycle.setAdapter(topBookAdapter);



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.navigation_search) {
            Log.d(TAG, "onNavigationItemSelected: search");
            startActivity(new Intent(context,SearchActivity.class));
        }
        if (id == R.id.navigation_category) {
            Log.d(TAG, "onNavigationItemSelected: search");
            startActivity(new Intent(context,CategoryActivity.class));

        }
        return true;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Toast.makeText(context,"Click down",Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                //onBackPressed();
                // onbackprace();
                startActivity(new Intent(context,CategoryActivity.class));

                break;
            case R.id.navigation_search:
                startActivity(new Intent(context,SearchActivity.class));
                finish();

                break;

            case R.id.navigation_category:
                startActivity(new Intent(context,CategoryActivity.class));
                finish();

                break;



        }


        return super.onOptionsItemSelected(item);
    }

    private void onbackprace() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_seealltopbook:
                strtoolbarname="Top Books";
                Intent intent0=new Intent(context, SeeallActivity.class);
                intent0.putExtra("toolbar",strtoolbarname);
                context.startActivity(intent0);


                break;
            case R.id.tv_seealltopebook:
                strtoolbarname="Top E-Books";
                Intent intent=new Intent(context, SeeallActivity.class);
                intent.putExtra("toolbar",strtoolbarname);
                context.startActivity(intent);


                break;
            case R.id.tv_seeallrecentbook:
                strtoolbarname="Recent Books";
                Intent intent1=new Intent(context, SeeallActivity.class);
                intent1.putExtra("toolbar",strtoolbarname);
                context.startActivity(intent1);


                break;
            case R.id.tv_seeallrecentebook:
                strtoolbarname="Recent E-Books";
                Intent intent2=new Intent(context, SeeallActivity.class);
                intent2.putExtra("toolbar",strtoolbarname);
                context.startActivity(intent2);


                break;
            case R.id.tv_seeallstudentbook:
                strtoolbarname="Student Books";
                Intent intent3=new Intent(context, SeeallActivity.class);
                intent3.putExtra("toolbar",strtoolbarname);
                context.startActivity(intent3);


                break;
            case R.id.tv_seeallstudentebook:
                strtoolbarname="Student E-Books";
                Intent intent7=new Intent(context, SeeallActivity.class);
                intent7.putExtra("toolbar",strtoolbarname);
                context.startActivity(intent7);

                break;
            case R.id.tv_seeallstudenttextnote:
                strtoolbarname="Student Text Notes";
                Intent intent4=new Intent(context, SeeallActivity.class);
                intent4.putExtra("toolbar",strtoolbarname);
                context.startActivity(intent4);


                break;
            case R.id.tv_seeallstudenttextenote:
                strtoolbarname="Student Text E-Notes";
                Intent intent5=new Intent(context, SeeallActivity.class);
                intent5.putExtra("toolbar",strtoolbarname);
                context.startActivity(intent5);


                break;
            case R.id.tv_seeallmagazine:
                strtoolbarname="Magazines";
                Intent intent6=new Intent(context, SeeallActivity.class);
                intent6.putExtra("toolbar",strtoolbarname);
                context.startActivity(intent6);


                break;
            case R.id.tv_seeallevent:
                startActivity(new Intent(context,EventActivity.class));
                break;
            case R.id.icon_badge:
                whatsappnum = "+917385486832";
                Log.d(TAG, "onViewClicked: whatsapp" + whatsappnum);

                String url = "https://api.whatsapp.com/send?phone=" + whatsappnum;
                try {
                    PackageManager pm = context.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(context, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
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
