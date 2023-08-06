package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pdftron.completereader.Adapter.MybookorderAdapter;
import com.pdftron.completereader.Response.AcceptedBooksByAdminResponse;
import com.pdftron.completereader.Response.AcceptedEBooksByAdminResponse;
import com.pdftron.completereader.Response.Books_By_PublisherResponse;
import com.pdftron.completereader.Response.EBooks_By_PublisherResponse;
import com.pdftron.completereader.Response.Magazines_By_PublisherResponse;
import com.pdftron.completereader.Response.MyBookorderResponse;
import com.pdftron.completereader.Response.PendingBooksByAdminResponse;
import com.pdftron.completereader.Response.PendingEBooksByAdminResponse;
import com.pdftron.completereader.Response.PublisherAcceptedBooksResponse;
import com.pdftron.completereader.Response.PublisherEbooksStatusResponse;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublisherDashboardActivity extends AppCompatActivity implements View.OnClickListener {
    String user_type,user_id;
    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",TAG="TAG";
    String sizepending="0",sizeaccepted="0";
    String MyBooksaction="book_by_publisher",MyEBooksaction="ebook_by_publisher",MyMagazineaction="magazine_by_publisher",toolbarname;
    TextView tvbooksize,tvebooksize,tvmagazinesize,tvbookordersize,tvebookordersize,tvacceptedbooksize,tvpendingbooksize,tvacceptedebooksize,tvpendingebooksize;

    RelativeLayout rl_1,rl_2,rl_3,rl_4,rl_5,rl_6,rl_7,rl_8,rl_9,rl_10,rl_11,rl_12;
    Books_By_PublisherResponse books_by_publisherResponse;
    ArrayList<Books_By_PublisherResponse.Datum> al_display_books;


    EBooks_By_PublisherResponse ebooks_by_publisherResponse;
    ArrayList<EBooks_By_PublisherResponse.Datum> al_display_ebooks;

    Magazines_By_PublisherResponse magazines_by_publisherResponse;
    ArrayList<Magazines_By_PublisherResponse.Datum> al_display_magazines;

    String Mybookorderaction="admin_book";
    PublisherAcceptedBooksResponse publisherAcceptedBooksResponse;
    ArrayList<PublisherAcceptedBooksResponse.Datum> al_display_mybookorder;


    String Myebookorderaction="admin_ebook";
    PublisherEbooksStatusResponse publisherEbooksStatusResponse;
    ArrayList<PublisherEbooksStatusResponse.Datum> al_display_myebookorder;


    String Acceptedbookaction="accepted_books";
    AcceptedBooksByAdminResponse acceptedBooksByAdminResponse;
    ArrayList<AcceptedBooksByAdminResponse.Datum> al_display_myacceptedbooks;

    String Pendingbookaction="pending_books";
    PendingBooksByAdminResponse pendingBooksByAdminResponse;
    ArrayList<PendingBooksByAdminResponse.Datum> al_display_mypendingbooks;

    String Acceptedebookaction="accepted_ebooks";
    AcceptedEBooksByAdminResponse acceptedeBooksByAdminResponse;
    ArrayList<AcceptedEBooksByAdminResponse.Datum> al_display_myacceptedebooks;

    String Pendingebookaction="pending_ebooks";
    PendingEBooksByAdminResponse pendingeBooksByAdminResponse;
    ArrayList<PendingEBooksByAdminResponse.Datum> al_display_mypendingebooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_dashboard);
        context=PublisherDashboardActivity.this;
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);


        user_id= sharedPreferences.getString("user_id", "");

        progressDialog = new ProgressDialog(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Your Dashboard");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        rl_1=findViewById(R.id.rl1);
        rl_1.setOnClickListener(this);
        rl_2=findViewById(R.id.rl2);
        rl_2.setOnClickListener(this);
        rl_3=findViewById(R.id.rl3);
        rl_3.setOnClickListener(this);
        rl_4=findViewById(R.id.rl4);
        rl_4.setOnClickListener(this);
        rl_5=findViewById(R.id.rl5);
        rl_5.setOnClickListener(this);
        rl_6=findViewById(R.id.rl6);
        rl_6.setOnClickListener(this);
        rl_7=findViewById(R.id.rl7);
        rl_7.setOnClickListener(this);
        rl_8=findViewById(R.id.rl8);
        rl_8.setOnClickListener(this);
        rl_9=findViewById(R.id.rl9);
        rl_9.setOnClickListener(this);

        rl_10=findViewById(R.id.rl10);
        rl_10.setOnClickListener(this);

        rl_11=findViewById(R.id.rl11);
        rl_11.setOnClickListener(this);

        rl_12=findViewById(R.id.rl12);
        rl_12.setOnClickListener(this);

        tvbooksize=findViewById(R.id.tv_booksize);
        tvebooksize=findViewById(R.id.tv_ebooksize);
        tvmagazinesize=findViewById(R.id.tv_magazinesize);
        tvbookordersize=findViewById(R.id.tv_bookordersize);
        tvebookordersize=findViewById(R.id.tv_ebookordersize);
        tvacceptedbooksize=findViewById(R.id.tv_acceptedbooksize);
        tvpendingbooksize=findViewById(R.id.tv_pendingbooksize);
        tvacceptedebooksize=findViewById(R.id.tv_acceptedebooksize);
        tvpendingebooksize=findViewById(R.id.tv_pendingebooksize);

        mybooklist(MyBooksaction,user_id);
        myebooklist(MyEBooksaction,user_id);
        mymagazinelist(MyMagazineaction,user_id);

        bookorder(Mybookorderaction,user_id);
        ebookorder(Myebookorderaction,user_id);
        acceptedbooks(Acceptedbookaction,user_id);
        pendingbooks(Pendingbookaction,user_id);
        acceptedebooks(Acceptedebookaction,user_id);
        pendingebooks(Pendingebookaction,user_id);





    }

    private void pendingebooks(String pendingebookaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<PendingEBooksByAdminResponse> pendingEBooksByAdminResponseCall = apiInterface.pendingebooks(pendingebookaction,user_id);

            pendingEBooksByAdminResponseCall.enqueue(new Callback<PendingEBooksByAdminResponse>() {
                @Override
                public void onResponse(Call<PendingEBooksByAdminResponse> call, Response<PendingEBooksByAdminResponse> response) {
                    progressDialog.dismiss();
                    pendingeBooksByAdminResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (pendingeBooksByAdminResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (pendingeBooksByAdminResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + pendingeBooksByAdminResponse.getResponseMessage());
                            al_display_mypendingebooks = new ArrayList<>();
                            for (int i = 0; i < pendingeBooksByAdminResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + pendingeBooksByAdminResponse.getData().size());

                                    al_display_mypendingebooks.add(pendingeBooksByAdminResponse.getData().get(i));
                                    tvpendingebooksize.setText(String.valueOf(al_display_mypendingebooks.size()));

                                    Log.d(TAG, "onResponse: al" + al_display_mypendingebooks);
                                    Log.d(TAG, "onResponse: alsize" + al_display_mypendingebooks.size());




                            }
                          /*  GridPendingbookviewAdapter gridpendingbookviewAdapter=new GridPendingbookviewAdapter(context,al_display_mybookorder);
                            gridView.setAdapter(gridpendingbookviewAdapter);
*/
                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                        /*    mybookorderAdapter = new MybookorderAdapter(context, al_display_mybookorder);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(PublisherPendingorderActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewmybookorder.setLayoutManager(horizontalLayoutManager);
                            recyclerViewmybookorder.setAdapter(mybookorderAdapter);
*/
                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + pendingeBooksByAdminResponse.getResponseMessage());
                            Toast.makeText(context,pendingeBooksByAdminResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<PendingEBooksByAdminResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void acceptedebooks(String acceptedebookaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AcceptedEBooksByAdminResponse> acceptedEBooksByAdminResponseCall = apiInterface.acceptedebooks(acceptedebookaction,user_id);

            acceptedEBooksByAdminResponseCall.enqueue(new Callback<AcceptedEBooksByAdminResponse>() {
                @Override
                public void onResponse(Call<AcceptedEBooksByAdminResponse> call, Response<AcceptedEBooksByAdminResponse> response) {
                    progressDialog.dismiss();
                    acceptedeBooksByAdminResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (acceptedeBooksByAdminResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (acceptedeBooksByAdminResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + acceptedeBooksByAdminResponse.getResponseMessage());
                            al_display_myacceptedebooks = new ArrayList<>();
                            for (int i = 0; i < acceptedeBooksByAdminResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + acceptedeBooksByAdminResponse.getData().size());

                                    al_display_myacceptedebooks.add(acceptedeBooksByAdminResponse.getData().get(i));
                                    tvacceptedebooksize.setText(String.valueOf(al_display_myacceptedebooks.size()));

                                    Log.d(TAG, "onResponse: al" + al_display_myacceptedebooks);
                                    Log.d(TAG, "onResponse: alsize" + al_display_myacceptedebooks.size());




                            }
                          /*  GridPendingbookviewAdapter gridpendingbookviewAdapter=new GridPendingbookviewAdapter(context,al_display_mybookorder);
                            gridView.setAdapter(gridpendingbookviewAdapter);
*/
                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                        /*    mybookorderAdapter = new MybookorderAdapter(context, al_display_mybookorder);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(PublisherPendingorderActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewmybookorder.setLayoutManager(horizontalLayoutManager);
                            recyclerViewmybookorder.setAdapter(mybookorderAdapter);
*/
                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + acceptedeBooksByAdminResponse.getResponseMessage());
                            Toast.makeText(context,acceptedeBooksByAdminResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AcceptedEBooksByAdminResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void pendingbooks(String pendingbookaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<PendingBooksByAdminResponse> pendingBooksByAdminResponseCall = apiInterface.pendingbooks(pendingbookaction,user_id);

            pendingBooksByAdminResponseCall.enqueue(new Callback<PendingBooksByAdminResponse>() {
                @Override
                public void onResponse(Call<PendingBooksByAdminResponse> call, Response<PendingBooksByAdminResponse> response) {
                    progressDialog.dismiss();
                    pendingBooksByAdminResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (pendingBooksByAdminResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (pendingBooksByAdminResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + pendingBooksByAdminResponse.getResponseMessage());
                            al_display_mypendingbooks = new ArrayList<>();
                            for (int i = 0; i < pendingBooksByAdminResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + pendingBooksByAdminResponse.getData().size());

                                    al_display_mypendingbooks.add(pendingBooksByAdminResponse.getData().get(i));
                                    tvpendingbooksize.setText(String.valueOf(al_display_mypendingbooks.size()));

                                    Log.d(TAG, "onResponse: al" + al_display_mypendingbooks);
                                    Log.d(TAG, "onResponse: alsize" + al_display_mypendingbooks.size());




                            }
                          /*  GridPendingbookviewAdapter gridpendingbookviewAdapter=new GridPendingbookviewAdapter(context,al_display_mybookorder);
                            gridView.setAdapter(gridpendingbookviewAdapter);
*/
                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                        /*    mybookorderAdapter = new MybookorderAdapter(context, al_display_mybookorder);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(PublisherPendingorderActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewmybookorder.setLayoutManager(horizontalLayoutManager);
                            recyclerViewmybookorder.setAdapter(mybookorderAdapter);
*/
                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + pendingBooksByAdminResponse.getResponseMessage());
                            Toast.makeText(context,pendingBooksByAdminResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<PendingBooksByAdminResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void acceptedbooks(String acceptedbookaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AcceptedBooksByAdminResponse> acceptedBooksByAdminResponseCall = apiInterface.acceptedbooks(acceptedbookaction,user_id);

            acceptedBooksByAdminResponseCall.enqueue(new Callback<AcceptedBooksByAdminResponse>() {
                @Override
                public void onResponse(Call<AcceptedBooksByAdminResponse> call, Response<AcceptedBooksByAdminResponse> response) {
                    progressDialog.dismiss();
                    acceptedBooksByAdminResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (acceptedBooksByAdminResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (acceptedBooksByAdminResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + acceptedBooksByAdminResponse.getResponseMessage());
                            al_display_myacceptedbooks = new ArrayList<>();
                            for (int i = 0; i < acceptedBooksByAdminResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + acceptedBooksByAdminResponse.getData().size());

                                    al_display_myacceptedbooks.add(acceptedBooksByAdminResponse.getData().get(i));
                                    tvacceptedbooksize.setText(String.valueOf(al_display_myacceptedbooks.size()));

                                    Log.d(TAG, "onResponse: al" + al_display_myacceptedbooks);
                                    Log.d(TAG, "onResponse: alsize" + al_display_myacceptedbooks.size());




                            }
                          /*  GridPendingbookviewAdapter gridpendingbookviewAdapter=new GridPendingbookviewAdapter(context,al_display_mybookorder);
                            gridView.setAdapter(gridpendingbookviewAdapter);
*/
                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                        /*    mybookorderAdapter = new MybookorderAdapter(context, al_display_mybookorder);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(PublisherPendingorderActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewmybookorder.setLayoutManager(horizontalLayoutManager);
                            recyclerViewmybookorder.setAdapter(mybookorderAdapter);
*/
                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + acceptedBooksByAdminResponse.getResponseMessage());
                            Toast.makeText(context,acceptedBooksByAdminResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<AcceptedBooksByAdminResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void ebookorder(String mybookorderaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<PublisherEbooksStatusResponse> publisherEbooksStatusResponseCall = apiInterface.ebookstatus(mybookorderaction,user_id);

            publisherEbooksStatusResponseCall.enqueue(new Callback<PublisherEbooksStatusResponse>() {
                @Override
                public void onResponse(Call<PublisherEbooksStatusResponse> call, Response<PublisherEbooksStatusResponse> response) {
                    progressDialog.dismiss();
                    publisherEbooksStatusResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (publisherEbooksStatusResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (publisherEbooksStatusResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + publisherEbooksStatusResponse.getResponseMessage());
                            al_display_myebookorder = new ArrayList<>();
                            for (int i = 0; i < publisherEbooksStatusResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + publisherEbooksStatusResponse.getData().size());

                                    al_display_myebookorder.add(publisherEbooksStatusResponse.getData().get(i));
                                    tvebookordersize.setText(String.valueOf(al_display_myebookorder.size()));

                                    Log.d(TAG, "onResponse: al" + al_display_myebookorder);
                                    Log.d(TAG, "onResponse: alsize" + al_display_myebookorder.size());




                            }
                          /*  GridPendingbookviewAdapter gridpendingbookviewAdapter=new GridPendingbookviewAdapter(context,al_display_mybookorder);
                            gridView.setAdapter(gridpendingbookviewAdapter);
*/
                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                        /*    mybookorderAdapter = new MybookorderAdapter(context, al_display_mybookorder);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(PublisherPendingorderActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewmybookorder.setLayoutManager(horizontalLayoutManager);
                            recyclerViewmybookorder.setAdapter(mybookorderAdapter);
*/
                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + publisherEbooksStatusResponse.getResponseMessage());
                            Toast.makeText(context,publisherEbooksStatusResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<PublisherEbooksStatusResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void mymagazinelist(String myMagazineaction, String user_id) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Magazines_By_PublisherResponse> magazines_by_publisherResponseCall = apiInterface.magazines(myMagazineaction,user_id);

            magazines_by_publisherResponseCall.enqueue(new Callback<Magazines_By_PublisherResponse>() {
                @Override
                public void onResponse(Call<Magazines_By_PublisherResponse> call, Response<Magazines_By_PublisherResponse> response) {
                    progressDialog.dismiss();
                    magazines_by_publisherResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (magazines_by_publisherResponse != null) {
                        Log.d(TAG, "onResponse:topBookResponse ");
                        if (magazines_by_publisherResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:book msg " + magazines_by_publisherResponse.getResponseMessage());
                            al_display_magazines = new ArrayList<>();
                            tvmagazinesize.setText(Integer.toString(magazines_by_publisherResponse.getData().size()));
                            for (int i = 0; i < magazines_by_publisherResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + magazines_by_publisherResponse.getData().size());

                                al_display_magazines.add(magazines_by_publisherResponse.getData().get(i));

                                Log.d(TAG, "onResponse: al" + al_display_magazines);
                                Log.d(TAG, "onResponse: alsize" + al_display_magazines.size());




                            }

                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + magazines_by_publisherResponse.getResponseMessage());
                            Toast.makeText(context,magazines_by_publisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<Magazines_By_PublisherResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void myebooklist(String myEBooksaction, String user_id) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<EBooks_By_PublisherResponse> ebooks_by_publisherResponseCall = apiInterface.ebooks(myEBooksaction,user_id);

            ebooks_by_publisherResponseCall.enqueue(new Callback<EBooks_By_PublisherResponse>() {
                @Override
                public void onResponse(Call<EBooks_By_PublisherResponse> call, Response<EBooks_By_PublisherResponse> response) {
                    progressDialog.dismiss();
                    ebooks_by_publisherResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (ebooks_by_publisherResponse != null) {
                        Log.d(TAG, "onResponse:topBookResponse ");
                        if (ebooks_by_publisherResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:book msg " + ebooks_by_publisherResponse.getResponseMessage());
                            al_display_ebooks = new ArrayList<>();
                            tvebooksize.setText(Integer.toString(ebooks_by_publisherResponse.getData().size()));
                            for (int i = 0; i < ebooks_by_publisherResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + ebooks_by_publisherResponse.getData().size());

                                al_display_ebooks.add(ebooks_by_publisherResponse.getData().get(i));

                                Log.d(TAG, "onResponse: al" + al_display_books);
                                Log.d(TAG, "onResponse: alsize" + al_display_ebooks.size());




                            }

                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + ebooks_by_publisherResponse.getResponseMessage());
                            Toast.makeText(context,ebooks_by_publisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<EBooks_By_PublisherResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }
    private void bookorder(String mybookorderaction, String user_id) {
        if (isOnline()) {
            Log.d(TAG, "pending: ");
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<PublisherAcceptedBooksResponse> publisherAcceptedBooksResponseCall = apiInterface.bookstatus(mybookorderaction,user_id);

            publisherAcceptedBooksResponseCall.enqueue(new Callback<PublisherAcceptedBooksResponse>() {
                @Override
                public void onResponse(Call<PublisherAcceptedBooksResponse> call, Response<PublisherAcceptedBooksResponse> response) {
                    progressDialog.dismiss();
                    publisherAcceptedBooksResponse = response.body();
                    Log.d(TAG, "onResponse:pending: " + response.toString());
                    if (publisherAcceptedBooksResponse != null) {
                        Log.d(TAG, "onResponse:pending: ");
                        if (publisherAcceptedBooksResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:pending msg " + publisherAcceptedBooksResponse.getResponseMessage());
                            al_display_mybookorder = new ArrayList<>();
                            for (int i = 0; i < publisherAcceptedBooksResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: pendingsize" + publisherAcceptedBooksResponse.getData().size());

                                    al_display_mybookorder.add(publisherAcceptedBooksResponse.getData().get(i));
                                    tvbookordersize.setText(String.valueOf(al_display_mybookorder.size()));

                                    Log.d(TAG, "onResponse: al" + al_display_mybookorder);
                                    Log.d(TAG, "onResponse: alsize" + al_display_mybookorder.size());




                            }
                          /*  GridPendingbookviewAdapter gridpendingbookviewAdapter=new GridPendingbookviewAdapter(context,al_display_mybookorder);
                            gridView.setAdapter(gridpendingbookviewAdapter);
*/
                            // top_book_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                        /*    mybookorderAdapter = new MybookorderAdapter(context, al_display_mybookorder);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(PublisherPendingorderActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerViewmybookorder.setLayoutManager(horizontalLayoutManager);
                            recyclerViewmybookorder.setAdapter(mybookorderAdapter);
*/
                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + publisherAcceptedBooksResponse.getResponseMessage());
                            Toast.makeText(context,publisherAcceptedBooksResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<PublisherAcceptedBooksResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        }

    }

    private void mybooklist(String myBooksaction, String user_id) {
        if (isOnline()) {
            progressDialog.setMessage("Please Wait...!!");
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Books_By_PublisherResponse> books_by_publisherResponseCall = apiInterface.books(myBooksaction,user_id);

            books_by_publisherResponseCall.enqueue(new Callback<Books_By_PublisherResponse>() {
                @Override
                public void onResponse(Call<Books_By_PublisherResponse> call, Response<Books_By_PublisherResponse> response) {
                    progressDialog.dismiss();
                    books_by_publisherResponse = response.body();
                    Log.d(TAG, "onResponse:registration " + response.toString());
                    if (books_by_publisherResponse != null) {
                        Log.d(TAG, "onResponse:topBookResponse ");
                        if (books_by_publisherResponse.getResponseCode().matches("0")) {
                            // Toast.makeText(context, registerResponse.getResponseMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:book msg " + books_by_publisherResponse.getResponseMessage());
                            al_display_books = new ArrayList<>();
                            tvbooksize.setText(Integer.toString(books_by_publisherResponse.getData().size()));
                            for (int i = 0; i < books_by_publisherResponse.getData().size(); i++) {
                                Log.d(TAG, "onResponse: size" + books_by_publisherResponse.getData().size());

                                al_display_books.add(books_by_publisherResponse.getData().get(i));

                                Log.d(TAG, "onResponse: al" + al_display_books);
                                Log.d(TAG, "onResponse: alsize" + al_display_books.size());




                            }

                            //   top_book_recycle.addItemDecoration(new DividerItemDecoration(context, 0));
/*
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();*/


                        } else {
                            Log.d(TAG, "onResponse:registration msg " + books_by_publisherResponse.getResponseMessage());
                            Toast.makeText(context,books_by_publisherResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<Books_By_PublisherResponse> call, Throwable t) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl1:
                String toolbarname="My Book List";
                Intent intent=new Intent(context,MyBooksActivity.class);
                intent.putExtra("toolbar",toolbarname);
                context.startActivity(intent);

                break;
            case R.id.rl2:
                String toolbarnamee="My E-Book List";
                Intent intente=new Intent(context,MyBooksActivity.class);
                intente.putExtra("toolbar",toolbarnamee);
                context.startActivity(intente);

                break;
            case R.id.rl3:
                String toolbarnamem="My Magazine List";
                Intent intentm=new Intent(context,MyBooksActivity.class);
                intentm.putExtra("toolbar",toolbarnamem);
                context.startActivity(intentm);

                break;
            case R.id.rl4:
                startActivity(new Intent(context,PublisherorderActivity.class));

                break;
            case R.id.rl5:
                startActivity(new Intent(context,PublisherEbookorderActivity.class));

                break;
            case R.id.rl6:
                startActivity(new Intent(context,AcceptedBookAdminActivity.class));

                break;
            case R.id.rl7:
                startActivity(new Intent(context,PendingBookAdminActivity.class));

                break;
            case R.id.rl8:
                startActivity(new Intent(context,AcceptedEBookAdminActivity.class));

                break;
            case R.id.rl9:
                startActivity(new Intent(context,PendingEBookAdminActivity.class));

                break;

            case R.id.rl10:
                context.startActivity(new Intent(context,AddBooksActivity.class));

                break;

            case R.id.rl11:
                context.startActivity(new Intent(context,AddEBooksActivity.class));

                break;

            case R.id.rl12:
                context.startActivity(new Intent(context,AddMagazinesActivity.class));

                break;
        }
    }

}
