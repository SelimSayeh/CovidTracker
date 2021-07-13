package com.example.coronadetector.form;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronadetector.R;

public class Main4Activity extends AppCompatActivity {
    public Button next;
    private CheckBox diarr;
    private CheckBox head;
    private CheckBox smell;
    private CheckBox app;
    CheckBox no1;
    CheckBox no2;
    CheckBox no3;
    CheckBox no4;
    public int cumule;
    public int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulaire4);
         next = findViewById(R.id.next);
         diarr = findViewById(R.id.diarr);
         head = findViewById(R.id.head);
         smell = findViewById(R.id.smell);
         app = findViewById(R.id.app);
         no1 = findViewById(R.id.no1);
         no2 = findViewById(R.id.no2);
         no3 = findViewById(R.id.no3);
         no4 = findViewById(R.id.no4);

        diarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no1.setChecked(false);
            }
        });
        no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diarr.setChecked(false);
            }
        });
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no2.setChecked(false);
            }
        });
        no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                head.setChecked(false);
            }
        });
        smell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no3.setChecked(false);
            }
        });
        no3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smell.setChecked(false);
            }
        });
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no4.setChecked(false);
            }
        });
        no4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setChecked(false);
            }
        });

         final Intent i = getIntent();
         cumule = i.getIntExtra("cumule",0);
         next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main4Activity.this, Main5Activity.class);
                score=0;
                int s=0;
                if(diarr.isChecked())score++;
                if(head.isChecked())score++;
                if(smell.isChecked())score++;
                if(app.isChecked())score++;
                i.putExtra("cumule",cumule+score);
                if(diarr.isChecked()||no1.isChecked())s++;
                if(head.isChecked()||no2.isChecked())s++;
                if(smell.isChecked()||no3.isChecked())s++;
                if(app.isChecked()||no4.isChecked())s++;
                if(s==4) {
                    startActivityForResult(i, 0);
                }
                else{
                    Toast toast=Toast.makeText(Main4Activity.this,"You have to answer all questions",Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });
    }
}
