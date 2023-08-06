package com.pdftron.completereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pdftron.completereader.Response.Uploadcart;
import com.rilixtech.shelfview.BookModel;

import java.util.ArrayList;


public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigation;

    Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    String TAG="TAG";
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",user_id;
    RecyclerView recyclerViewcart;
    DBcartDataHelper dBcartDataHelper;
    ArrayList<Uploadcart> uploadcartArrayList=new ArrayList<>();
    String strreview,strbookviewaction="book_view",str_discount,str_bookid,str_bookname,str_author,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_bookfrontimage,str_bookindeximage,str_bookbackimage;
    CartAdapter cartAdapter;

    ArrayList<String> cartlist=new ArrayList<>();

    ArrayList<String> bookid=new ArrayList<>();
    ArrayList<String> bookname=new ArrayList<>();
    ArrayList<String> bookprice=new ArrayList<>();
    ArrayList<String> bookquantity=new ArrayList<>();

    ArrayList<String> bookimagelink=new ArrayList<>();
    ArrayList<String> totalquantity=new ArrayList<>();

    Button btncheckout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        context = CartActivity.this;

        progressDialog = new ProgressDialog(context);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid"+user_id);
        recyclerViewcart=findViewById(R.id.cartrecycler);
        recyclerViewcart.setLayoutManager(new GridLayoutManager(context,1));
        recyclerViewcart.setItemAnimator(new DefaultItemAnimator());
        btncheckout=findViewById(R.id.btn_checkout);
        btncheckout.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cart");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

   dBcartDataHelper=new DBcartDataHelper(this);
        Cursor cartdata= dBcartDataHelper.getcartListContents();
        if (cartdata.getCount()==0){
            btncheckout.setVisibility(View.GONE);
            Toast.makeText(CartActivity.this,"The Cart  empty", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cartdata.moveToNext())
            {
                btncheckout.setVisibility(View.VISIBLE);

                bookid.add(cartdata.getString(1));
                bookname.add(cartdata.getString(2));
                bookprice.add(cartdata.getString(3));
                bookquantity.add(cartdata.getString(4));
                Log.d(TAG, "onCreate: bookquantity"+bookquantity);
                bookimagelink.add(cartdata.getString(5));
                totalquantity.add(cartdata.getString(6));


                CartAdapter cartAdapter=new CartAdapter(context,bookid,bookname,bookprice,bookquantity,bookimagelink,totalquantity);
                recyclerViewcart.setAdapter(cartAdapter);

            }
        }



        bottomNavigation = findViewById(R.id.bottom_navigationcart);
        bottomNavigation.setSelectedItemId(R.id.navigation_cart);
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
                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                        overridePendingTransition(0,0);
                        finish();

                        return true;

                    case R.id.navigation_cart:


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




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(context,DashboardActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        startActivity(new Intent(context,CheckoutActivity.class));

    }

    @Override
    public void onBackPressed() {


    }


}
