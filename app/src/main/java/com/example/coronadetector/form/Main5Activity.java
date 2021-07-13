package com.example.coronadetector.form;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronadetector.R;

public class Main5Activity extends AppCompatActivity {
    private CheckBox abroad;
    private CheckBox contact;
    private CheckBox contact2;
    private CheckBox contact3;
    CheckBox no1;
    CheckBox no2;
    CheckBox no3;
    CheckBox no4;
    public int score;
    public int cumule;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulaire5);
        abroad = findViewById(R.id.abroad);
        contact=findViewById(R.id.contact);
        contact2=findViewById(R.id.contact2);
        contact3=findViewById(R.id.contact3);

        no1 = findViewById(R.id.no1);
        no2 = findViewById(R.id.no2);
        no3 = findViewById(R.id.no3);
        no4 = findViewById(R.id.no4);

        abroad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no1.setChecked(false);
            }
        });
        no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abroad.setChecked(false);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no2.setChecked(false);
            }
        });
        no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.setChecked(false);
            }
        });
        contact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no3.setChecked(false);
            }
        });
        no3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact2.setChecked(false);
            }
        });
        contact3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no4.setChecked(false);
            }
        });
        no4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact3.setChecked(false);
            }
        });



        submit = findViewById(R.id.next);
        final Intent i = getIntent();
        cumule = i.getIntExtra("cumule",0);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main5Activity.this, Main6Activity.class);
                score=0;
                if(abroad.isChecked())score=score+2;
                if(contact.isChecked())score=score+2;
                if(contact2.isChecked())score=score+2;
                if(contact3.isChecked())score=score+2;
                i.putExtra("cumule",cumule+score);
                int s=0;
                if(abroad.isChecked()||no1.isChecked())s++;
                if(contact.isChecked()||no2.isChecked())s++;
                if(contact2.isChecked()||no3.isChecked())s++;
                if(contact3.isChecked()||no4.isChecked())s++;
                if(s==4) {
                    startActivityForResult(i, 0);
                }
                else{
                    Toast toast=Toast.makeText(Main5Activity.this,"You have to answer all questions",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }
}
