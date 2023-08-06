package com.pdftron.completereader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class SplashscreenActivity extends AppCompatActivity {
    LinearLayout l1;
    DrawerLayout drawerLayout;
    public static int SPLASH_TIME_OUT = 3000;
    Context mContext;
    Animation uptodown,downtoup;
    String TAG="TAG";
    SharedPreferences sharedPreferences;
    String MYPREF = "Pustakmatket";
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        mContext= SplashscreenActivity.this;
        sharedPreferences=getSharedPreferences(MYPREF,MODE_PRIVATE);
        user_id=sharedPreferences.getString("user_id","");
        Log.d(TAG, "onCreate:user_id "+user_id);

        l1 = (LinearLayout) findViewById(R.id.l1);
        //l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user_id.isEmpty()) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(mContext, DashboardActivity.class));
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }


}
