package com.example.coronadetector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {

    Animation topAnim,bottomAnim;
    ImageView logo;
    TextView slogan;
    private  static  int SPLASH_SCREEN=5000;

    /*  @Override
      protected void onStart() {
          super.onStart();
          if(!SharedPrefManager.getInstance(this).isLoggedIn()){
              Intent intent=new Intent(this,Profile.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(intent);
          }
      }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainn);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bot_animation);

        logo=findViewById(R.id.logo);
        slogan=findViewById(R.id.slogan);
        logo.setAnimation(topAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Main.this,Home.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

    }


}