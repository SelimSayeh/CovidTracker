package com.example.coronadetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.coronadetector.fragments.Feed;
import com.example.coronadetector.fragments.Form;
import com.example.coronadetector.fragments.Maps;
import com.example.coronadetector.fragments.Profilee;
import com.example.coronadetector.fragments.Stat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView  val= findViewById(R.id.bottomNavigationView);
//        View x= findViewById(R.id.fragment);
        val.setSelectedItemId(R.id.feed);
        loadFragment(new Feed());
        val.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.feed:
                       loadFragment(new Feed());
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        loadFragment(new Profilee());
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.maps:
                        loadFragment(new Maps());
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.stat:
                        loadFragment(new Stat());
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.form:
                        loadFragment(new Form());
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }
    private void loadFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
}