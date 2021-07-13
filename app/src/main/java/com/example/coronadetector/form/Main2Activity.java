package com.example.coronadetector.form;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronadetector.R;

public class Main2Activity extends AppCompatActivity {
    public Button next;
    private CheckBox temperature;
    private CheckBox cough;
    private CheckBox breath;
    TextView tx;
    CheckBox no1;
    CheckBox no2;
    CheckBox no3;
    private EditText tmp;
    public int score=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.formulaire2);
        next = findViewById(R.id.next);
        temperature =findViewById(R.id.tmpyes);
        tmp =findViewById(R.id.tmp);
        cough =findViewById(R.id.cough);
        breath =findViewById(R.id.breath);
        no1 = findViewById(R.id.no1);
        no2 = findViewById(R.id.no2);
        no3 = findViewById(R.id.no3);
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp.setVisibility(View.VISIBLE);
                no1.setChecked(false);
            }
        });
        no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp.setVisibility(View.INVISIBLE);
                temperature.setChecked(false);
            }
        });
        cough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no2.setChecked(false);
            }
        });
        no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cough.setChecked(false);
            }
        });

        breath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no3.setChecked(false);
            }
        });
        no3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breath.setChecked(false);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s=0;
                Intent i = new Intent(Main2Activity.this, Main3Activity.class);
                score=0;
                if(temperature.isChecked())score++;
                if(!tmp.getText().toString().isEmpty()){ if(Float.parseFloat(tmp.getText().toString())>=37.8) score = score +5;}
                if(cough.isChecked())score=score+5;
                if(breath.isChecked())score=score+5;
                i.putExtra("cumule",score);
                if(temperature.isChecked()||no1.isChecked())s++;
                if(cough.isChecked()||no2.isChecked())s++;
                if(breath.isChecked()||no3.isChecked())s++;
                if(s==3) {
                    startActivityForResult(i, 0);
                }
                else{
                    Toast toast=Toast.makeText(Main2Activity.this,"You have to answer all questions",Toast.LENGTH_LONG);
                    toast.show();
                }

                System.out.println(score);
            }
        });
    }
}
