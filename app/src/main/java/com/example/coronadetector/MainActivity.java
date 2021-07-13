package com.example.coronadetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coronadetector.map.Activity.MapsMarkerActivity;

public class MainActivity extends AppCompatActivity {
    Button stat;
    Button maps;
    Button form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        form=findViewById(R.id.button3);
        maps=findViewById(R.id.button4);
        stat=findViewById(R.id.button);
        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.example.coronadetector.form.MainActivity.class);
                startActivityForResult(i, 0);
            }
        });
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsMarkerActivity.class);
                startActivityForResult(i, 0);
            }
        });
        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.example.coronadetector.stat.controller.MainActivity.class);
                startActivityForResult(i, 0);
            }
        });
    }
}