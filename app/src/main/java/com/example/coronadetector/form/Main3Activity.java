package com.example.coronadetector.form;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronadetector.R;

public class Main3Activity extends AppCompatActivity {
    public Button next;
    private CheckBox breath;
    private CheckBox pressure;
    private CheckBox sleep;
    private CheckBox blue;
    CheckBox no1;
    CheckBox no2;
    CheckBox no3;
    CheckBox no4;
    public int score;
    public int cumule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulaire3);
        next = findViewById(R.id.next);
        breath = findViewById(R.id.breath);
        pressure = findViewById(R.id.press);
        sleep=findViewById(R.id.sleep);
        blue=findViewById(R.id.blue);
        no1 = findViewById(R.id.no1);
        no2 = findViewById(R.id.no2);
        no3 = findViewById(R.id.no3);
        no4 = findViewById(R.id.no4);
        breath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no1.setChecked(false);
            }
        });
        no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breath.setChecked(false);
            }
        });
        pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no2.setChecked(false);
            }
        });
        no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressure.setChecked(false);
            }
        });
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no3.setChecked(false);
            }
        });
        no3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleep.setChecked(false);
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no4.setChecked(false);
            }
        });
        no4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blue.setChecked(false);
            }
        });

        final Intent i = getIntent();
        cumule = i.getIntExtra("cumule",0);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s=0;
                Intent i = new Intent(Main3Activity.this, Main4Activity.class);
                score=0;
                if(breath.isChecked())score=score+3;
                if(pressure.isChecked())score=score+3;
                if(sleep.isChecked())score++;
                if(blue.isChecked())score++;

                System.out.println(score);
                i.putExtra("cumule",cumule+score);
                if(breath.isChecked()||no1.isChecked())s++;
                if(pressure.isChecked()||no2.isChecked())s++;
                if(sleep.isChecked()||no3.isChecked())s++;
                if(blue.isChecked()||no4.isChecked())s++;
                if(s==4) {
                    startActivityForResult(i, 0);
                }
                else{
                    Toast toast=Toast.makeText(Main3Activity.this,"You have to answer all questions",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
