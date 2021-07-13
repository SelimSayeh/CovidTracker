package com.example.coronadetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronadetector.Models.User;
import com.example.coronadetector.RecyclerView.newsfeed;
import com.example.coronadetector.sharedPreferences.SharedPrefManager;

public class Profile extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    TextView username1;
    TextView email1;
    TextView phone1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username1 = findViewById(R.id.username);
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.settings).setOnClickListener(this);

        email1 = findViewById(R.id.email);
        phone1 = findViewById(R.id.numtel);
        User user = SharedPrefManager.getInstance(this).getUser();

       /* System.out.println(user);
        System.out.println(user.getName());
        System.out.println(user.getId());
        System.out.println(user.getEmail());*/

        username1.setText(user.getName());
        email1.setText(user.getEmail());
        phone1.setText(String.valueOf(user.getPhone()));


    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }*/

    /* @Override
         protected void onStart() {
             super.onStart();

             if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
                 Intent intent = new Intent(this, MainActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 startActivity(intent);
             }
         }*/
    private void userLogOut() {
        SharedPrefManager.getInstance(Profile.this).clear();
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:

                userLogOut();
                break;
            case R.id.username:
                break;

            case R.id.settings:

                PopupMenu popup = new PopupMenu(this, v);
                popup.setOnMenuItemClickListener(this);
                popup.inflate(R.menu.menu);
                popup.show();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newsfeed:
                Intent intent = new Intent(this, newsfeed.class);

                startActivity(intent);

                return true;

            case R.id.addpublication:
                Intent intenttest = new Intent(this, addPublication.class);

                startActivity(intenttest);
                return true;
            case R.id.profile:


                //  Intent intent2=new Intent(this,Profile.class);
                //  startActivity(intent2);
                return true;
            case R.id.logoutitem:
                userLogOut();
                return true;
            case R.id.tracker:
                Intent intentTracker = new Intent(this, Maps.class);

                startActivity(intentTracker);
                return true;
            case R.id.showtracks:
                Intent intentshowtracks = new Intent(this, showCases.class);

                startActivity(intentshowtracks);
                return true;



            default:
                return false;
        }
    }
}