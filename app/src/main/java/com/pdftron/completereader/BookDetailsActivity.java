package com.pdftron.completereader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.pdftron.completereader.Response.AddEBookOrderResponse;
import com.pdftron.completereader.Response.BookViewResponse;
import com.pdftron.completereader.Response.Uploadcart;
import com.pdftron.completereader.WebService.ApiClient;
import com.pdftron.completereader.WebService.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;*/

public class BookDetailsActivity extends AppCompatActivity implements  View.OnClickListener {
    String language,date,strbooklink,strbookfree,strreview,strbookviewaction="book_view",str_discount,str_bookid,str_bookname,str_author,str_publisher,str_isbn,str_category,str_price,str_discountprice,str_description,str_bookfrontimage,str_bookindeximage,str_bookbackimage;
TextView tv_priceoffer,tvbookdetailname,tvbookname,tvauthor,tvpublisher,tvisbn,tvprice,tvdiscountprice,tvdescription;
Button btndownloadmagazine, btnaddcart,btnbuyebook,btnbuyenote,btnlogin,btnblock,btnread,btngotocart;
CardView cardViewbookdetails,cardViewreview,cardViewbookpreview,cardViewship,cardViewdel;
TextView tvcartcount;
    ArrayList<String> bookid=new ArrayList<>();
    ArrayList<String> bookname=new ArrayList<>();
    ArrayList<String> bookprice=new ArrayList<>();
    ArrayList<String> bookquantity=new ArrayList<>();

    ArrayList<String> bookimagelink=new ArrayList<>();
    RelativeLayout rlcart;

Context context;
    DBcartDataHelper dBcartDataHelper;
    LinearLayout ldiscount,lprice;
    ViewPager viewPager;

    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    String description,TAG="TAG",reviewaction="review",toolbarid,paymentcheck,payment_id,quantity;
EditText etreview;
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket",user_id;
    TextView tvreview,tvreviewcount,tvbookfree;
LinearLayout linearLayoutreview;
    ArrayList<String> imagearray=new ArrayList<String>();
    ProgressDialog progressDialog;

    Toolbar toolbar;

    BookViewResponse bookViewResponse;
    ArrayList<BookViewResponse.Datum> al_display_viewbook;



    int originalprice,discountprice,temp,userid;
    double discount;
String str_bookquantity="1",str_totalbookquantity;
    List<Uploadcart> bookList;
    //AddEbookorder
    AddEBookOrderResponse addEBookOrderResponse;

    ImageView ivbookimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        context=BookDetailsActivity.this;
        progressDialog=new ProgressDialog(this);
        sharedPreferences = getSharedPreferences(MYPREF, MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        Log.d(TAG, "onCreate: uuuserid"+user_id);
dBcartDataHelper=new DBcartDataHelper(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bookList=new ArrayList<>();
        btnbuyenote=findViewById(R.id.btn_buyenote);
btnbuyebook=findViewById(R.id.btn_buyebook);
btnblock=findViewById(R.id.btn_notavailable);
lprice=findViewById(R.id.lprice);
ldiscount=findViewById(R.id.ldiscount);
        tvbookname=findViewById(R.id.tv_bookdetail_name);
        tvauthor=findViewById(R.id.tv_bookAuthorname);
        tvpublisher=findViewById(R.id.tv_bookPublishername);
        tvisbn=findViewById(R.id.tv_bookisnbnno);
        tvprice=findViewById(R.id.tv_book_price);
        tvdiscountprice=findViewById(R.id.tv_book_discountprice);
        tv_priceoffer=findViewById(R.id.tv_book_priceoffer);
        tvdescription=findViewById(R.id.tv_description);
        btnaddcart=findViewById(R.id.btn_addtocart);
        tvbookfree=findViewById(R.id.tv_book_free);
        btnaddcart.setOnClickListener(this);
        btnbuyebook.setOnClickListener(this);
        btnbuyenote.setOnClickListener(this);
        etreview=findViewById(R.id.et_review);
        btnread=findViewById(R.id.btn_readbook);
        btnread.setOnClickListener(this);
        btngotocart=findViewById(R.id.btn_gotocart);
        btngotocart.setOnClickListener(this);
        ivbookimage=findViewById(R.id.slider);
        cardViewbookdetails=findViewById(R.id.card_bookdetails);
        cardViewbookdetails.setOnClickListener(this);
        cardViewreview=findViewById(R.id.card_bookreview);
        cardViewreview.setOnClickListener(this);
        cardViewbookpreview=findViewById(R.id.card_preview);
        cardViewbookpreview.setOnClickListener(this);
        cardViewship=findViewById(R.id.card_ship);
        cardViewdel=findViewById(R.id.card_del);
        btndownloadmagazine=findViewById(R.id.btn_downloadmagazine);
        btndownloadmagazine.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        toolbarid=bundle.getString("toolbarid");
        str_bookid = bundle.getString("bookid");
        str_bookname = bundle.getString("bookname");
        str_author=bundle.getString("authorname");
        str_publisher=bundle.getString("publisher");
        str_isbn=bundle.getString("isbn");
        str_price=bundle.getString("price");
        str_discount=bundle.getString("discount");
        Log.d(TAG, "onCreate: str_discount"+str_discount);
        str_discountprice=bundle.getString("discountprice");
        str_description=bundle.getString("description");
        str_bookfrontimage=bundle.getString("frontbookimage");
        str_bookindeximage=bundle.getString("indexbookimage");
        str_bookbackimage=bundle.getString("backbookimage");
        strbookfree=bundle.getString("bookfree");
        strbooklink=bundle.getString("booklink");
        Log.d(TAG, "onCreate:strbooklinkc "+strbooklink);
        date=bundle.getString("date");
        Log.d(TAG, "onCreate:date "+date);
       language= bundle.getString("language");
        quantity=bundle.getString("quantity");
        Log.d(TAG, "onCreate: quantity"+quantity);
        if (toolbarid.matches("e-book"))
        {
            btngotocart.setVisibility(View.GONE);

            btnaddcart.setVisibility(View.GONE);
            btnbuyenote.setVisibility(View.GONE);
            btnbuyebook.setVisibility(View.VISIBLE);
            cardViewship.setVisibility(View.GONE);
            cardViewdel.setVisibility(View.GONE);
            if (strbookfree.matches("1"))
            {
                ldiscount.setVisibility(View.GONE);
                lprice.setVisibility(View.GONE);
                tvbookfree.setVisibility(View.VISIBLE);

                btnbuyebook.setVisibility(View.GONE);

                btnread.setVisibility(View.VISIBLE);

            }
            else if(strbookfree.matches("0"))
                {
                tvbookfree.setVisibility(View.GONE);

                tv_priceoffer.setVisibility(View.VISIBLE);
                ldiscount.setVisibility(View.VISIBLE);
                lprice.setVisibility(View.VISIBLE);

                btnread.setVisibility(View.GONE);

                btnbuyebook.setVisibility(View.VISIBLE);

            }
        }
        else if (toolbarid.matches("book"))
        {
            btnbuyebook.setVisibility(View.GONE);
            btnbuyenote.setVisibility(View.GONE);
            btnaddcart.setVisibility(View.VISIBLE);
            btngotocart.setVisibility(View.VISIBLE);

            cardViewdel.setVisibility(View.VISIBLE);
            cardViewship.setVisibility(View.VISIBLE);

            if (quantity.matches("0"))
            {
                btnaddcart.setVisibility(View.GONE);
                btngotocart.setVisibility(View.GONE);

                btnblock.setVisibility(View.VISIBLE);

            }
            else
            {
                btnblock.setVisibility(View.GONE);
                btnaddcart.setVisibility(View.VISIBLE);
                btngotocart.setVisibility(View.VISIBLE);


            }


        }
         if (toolbarid.matches("e-note"))
        {
            btnbuyebook.setVisibility(View.GONE);
            btnbuyenote.setVisibility(View.VISIBLE);
            btnaddcart.setVisibility(View.GONE);
            btngotocart.setVisibility(View.GONE);

            cardViewship.setVisibility(View.GONE);
            cardViewdel.setVisibility(View.GONE);
            if (strbookfree.matches("1"))
            {
                ldiscount.setVisibility(View.GONE);
                lprice.setVisibility(View.GONE);
                tvbookfree.setVisibility(View.VISIBLE);

                btnbuyebook.setVisibility(View.GONE);

                btnread.setVisibility(View.VISIBLE);

            }
            else if(strbookfree.matches("0"))
            {
                tvbookfree.setVisibility(View.GONE);

                tv_priceoffer.setVisibility(View.VISIBLE);
                ldiscount.setVisibility(View.VISIBLE);
                lprice.setVisibility(View.VISIBLE);

                btnread.setVisibility(View.GONE);

                btnbuyebook.setVisibility(View.VISIBLE);

            }



        }
       else if (toolbarid.matches("magzine"))
        {

            btnbuyebook.setVisibility(View.GONE);
            btnbuyenote.setVisibility(View.GONE);
            btnaddcart.setVisibility(View.GONE);
            btngotocart.setVisibility(View.GONE);
            cardViewship.setVisibility(View.GONE);
            cardViewdel.setVisibility(View.GONE);
            if (strbookfree.matches("1"))
            {
                ldiscount.setVisibility(View.GONE);
                lprice.setVisibility(View.GONE);
                tvbookfree.setVisibility(View.VISIBLE);

                btnbuyebook.setVisibility(View.GONE);

                btnread.setVisibility(View.GONE);
                btndownloadmagazine.setVisibility(View.VISIBLE);

            }
            else if(strbookfree.matches("0"))
            {
                tvbookfree.setVisibility(View.GONE);

                tv_priceoffer.setVisibility(View.VISIBLE);
                ldiscount.setVisibility(View.VISIBLE);
                lprice.setVisibility(View.VISIBLE);

                btnread.setVisibility(View.GONE);

                btnbuyebook.setVisibility(View.VISIBLE);

            }



        }
     /*   if (strbookfree.matches("1"))
        {
            ldiscount.setVisibility(View.GONE);
            lprice.setVisibility(View.GONE);
            tvbookfree.setVisibility(View.VISIBLE);
            btnbuyenote.setVisibility(View.GONE);

            btnread.setVisibility(View.VISIBLE);


        }
        else if (strbookfree.matches("0"))
        {
            tvbookfree.setVisibility(View.GONE);

            tv_priceoffer.setVisibility(View.VISIBLE);
            ldiscount.setVisibility(View.VISIBLE);
            lprice.setVisibility(View.VISIBLE);
            btnbuyenote.setVisibility(View.VISIBLE);

            btnread.setVisibility(View.GONE);

        }
*/
        tvbookname.setText(str_bookname);
        tvisbn.setText(str_isbn);
        tvauthor.setText(str_author);
        tvpublisher.setText(str_publisher);
        tvprice.setPaintFlags(tvprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Log.d(TAG, "onCreate: str_bookfrontimage"+str_bookfrontimage);
        toolbar.setTitle(str_bookname);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tvprice.setText("₹"+str_price);
        tv_priceoffer.setText(str_discount+"OFF");
        tvdiscountprice.setText("₹"+str_discountprice);
      /*  Picasso
                .with(context)
                .load(str_bookfrontimage)
                .into(ivbookimage);*/
        Picasso
                .get()
                .load(str_bookfrontimage)
                .into(ivbookimage);


        //bookview(strbookviewaction,str_bookid);

        dBcartDataHelper=new DBcartDataHelper(this);
       /* Cursor cartdata= dBcartDataHelper.getcartListContents();
        if (cartdata.getCount()==0){
            //  Toast.makeText(BookDetailsActivity.this,"The Cart  empty", Toast.LENGTH_SHORT).show();
            tvcartcount.setText("0");

        }
        else{
            while (cartdata.moveToNext())
            {

                bookid.add(cartdata.getString(1));
                bookname.add(cartdata.getString(2));
                bookprice.add(cartdata.getString(3));
                bookquantity.add(cartdata.getString(4));
                Log.d(TAG, "onCreate: bookquantity"+bookquantity);
                bookimagelink.add(cartdata.getString(5));
                tvcartcount.setText(String.valueOf(cartdata.getCount()));


            }
        }*/

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(context,DashboardActivity.class));
                finish();                     break;
        }
        return super.onOptionsItemSelected(item);
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
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.btn_buyebook:
                if (user_id.matches("0"))
                {
                    Toast.makeText(context,"Please Login First", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, LoginActivity.class));
                }
                else {
                    Intent intent=new Intent(context,OrederSummaryActivity.class);
                    intent.putExtra("bookid",str_bookid);
                    intent.putExtra("bookname",str_bookname);
                    Log.d(TAG, "onClick:str_bookname "+str_bookname);
                    intent.putExtra("discountprice",str_discountprice);
                    context.startActivity(intent);

                }
               /* Intent intent=new Intent(context,OrederSummaryActivity.class);
                intent.putExtra("bookid",str_bookid);
                intent.putExtra("bookname",str_bookname);
                Log.d(TAG, "onClick:str_bookname "+str_bookname);
                intent.putExtra("discountprice",str_discountprice);
                context.startActivity(intent);

*/
                //addebookorder(addebookaction,user_id,str_discountprice,paymentcheck,str_bookid,payment_id);

                break;
            case R.id.btn_buyenote:
                if (user_id.matches("0"))
                {
                    Toast.makeText(context,"Please Login First", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, LoginActivity.class));
                }
                else {
                    Intent intente=new Intent(context,OrderSummeryENoteActivity.class);
                    intente.putExtra("bookid",str_bookid);
                    intente.putExtra("bookname",str_bookname);
                    Log.d(TAG, "onClick:str_bookname "+str_bookname);
                    intente.putExtra("discountprice",str_discountprice);
                    context.startActivity(intente);


                }

                break;
            case R.id.btn_addtocart:
               /* if (user_id.matches("0"))
                {
                    Toast.makeText(context,"Please Login First",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, LoginActivity.class));
                }
                else
                {
                    Uploadcart uploadcart =new Uploadcart(str_bookid,str_bookname,str_discountprice,str_bookfrontimage);

                }*/
            /*    Intent intentb=new Intent(context,CartActivity.class);
                intentb.putExtra("bookid",str_bookid);
                intentb.putExtra("bookname",str_bookname);
                Log.d(TAG, "onClick:str_bookname "+str_bookname);
                intentb.putExtra("discountprice",str_discountprice);
                intentb.putExtra("frontbookimage",str_bookfrontimage);
                context.startActivity(intentb);

                Log.d(TAG, "onClick:btn_addtocart ");
                Log.d(TAG, "onClick:str_bookfrontimage "+str_bookfrontimage);
            */    /* bookList.add(new Uploadcart(str_bookid,str_bookname,str_discountprice,str_bookfrontimage));
                 Toast.makeText(context,"Added",Toast.LENGTH_SHORT).show();
*/
                dBcartDataHelper=new DBcartDataHelper(this);
                Cursor cartdata= dBcartDataHelper.getcartListContents();
                if (cartdata.getCount()==0){
                   }
                else{
                    while (cartdata.moveToNext())
                    {

                        bookid.add(cartdata.getString(1));
                        bookname.add(cartdata.getString(2));
                        bookprice.add(cartdata.getString(3));
                        bookquantity.add(cartdata.getString(4));
                        Log.d(TAG, "onCreate: bookquantity"+bookquantity);
                        bookimagelink.add(cartdata.getString(5));



                    }
                }

                if (user_id.matches("0"))
                {
                    Toast.makeText(context,"Please Login First", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, LoginActivity.class));
                }
                if (bookid.contains(str_bookid))
                {
                    Toast.makeText(context,"This book is Already in Your Cart", Toast.LENGTH_SHORT).show();

                }
                else {
                    insert(str_bookid, str_bookname, str_discountprice, str_bookquantity, str_bookfrontimage,quantity);
                }

                break;
            case R.id.btn_readbook:
                Intent intentb=new Intent(context,MainActivity.class);
                intentb.putExtra("bookpdf",strbooklink);
                Log.d(TAG, "onClick:strbooklink "+strbooklink);
                 context.startActivity(intentb);
                 break;
            case R.id.card_bookdetails:
                Intent intent=new Intent(context,BooksdetailingActivity.class);
                intent.putExtra("bookname",str_bookname);
                Log.d(TAG, "onClick:str_bookname "+str_bookname);
                intent.putExtra("authorname",str_author);
                intent.putExtra("publisher",str_publisher);
                intent.putExtra("isbn",str_isbn);
                intent.putExtra("date",date);
                intent.putExtra("language",language);
                context.startActivity(intent);

                break;
            case R.id.card_bookreview:
                Intent intentr=new Intent(context,ReviewActivity.class);
                intentr.putExtra("bookid",str_bookid);
                context.startActivity(intentr);
                break;

            case R.id.card_preview:

                Intent intentp=new Intent(context,BookPreviewActivity.class);
                intentp.putExtra("description",str_description);
                context.startActivity(intentp);
                break;
            case R.id.btn_downloadmagazine:
                Intent intentm=new Intent(context,MagazineDownloadActivity.class);
                intentm.putExtra("bookpdf",strbooklink);
                Log.d(TAG, "onClick:strbooklink "+strbooklink);
                context.startActivity(intentm);

                break;

            case R.id.btn_gotocart:
                startActivity(new Intent(context,CartActivity.class));
                finish();
                break;



        }


        /*userid=Integer.parseInt(user_id);
        if (userid==0)
        {
            Toast.makeText(context,"Please Login First",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(context, LoginActivity.class));
        }
        else {
                   *//* Log.d(TAG, "onViewClicked: valueee"+value);
                    if (value.matches("0"))
                    {

                        Toast.makeText(context,"Zero Items not accept ",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {*//*
            startActivity(new Intent(context, DeliveryCheckout.class));
            finish();
        }*/

    }

    private void insert(String str_bookid, String str_bookname, String str_discountprice, String str_bookquantity, String str_bookfrontimage,String quantity) {
        boolean isinserted=dBcartDataHelper.insertdata(str_bookid,str_bookname,str_discountprice,str_bookquantity,str_bookfrontimage,quantity);
        if (isinserted==true)
        {
            Toast.makeText(context,"Book added to Cart", Toast.LENGTH_SHORT).show();



        }
        else
        {
            Log.d(TAG, "insertdata: No");
            //Toast.makeText(this,"Data is not inserted",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,DashboardActivity.class));
        finish();


    }
}


