package com.example.coronadetector.form;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronadetector.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    CheckBox male;
    CheckBox female;
    private EditText date;
    EditText pays;
    private Button skip;
    DatePickerDialog.OnDateSetListener dateSetListener1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulaire1);

        pays=findViewById(R.id.editText3);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        date = findViewById(R.id.editText4);
        skip = findViewById(R.id.next);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setChecked(false);
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setChecked(false);
            }
        });
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        MainActivity.this,dateSetListener1,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });
        dateSetListener1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String dates=day + "-" +month+ "-" +year;
                date.setText(dates);
            }
        };

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                int s=0;
                if(male.isChecked()||female.isChecked())s++;
                if(!date.getText().toString().isEmpty())s++;
                if(!pays.getText().toString().isEmpty())s++;
                if(s==3) {
                    startActivityForResult(i, 0);
                }
                else{
                    Toast toast=Toast.makeText(MainActivity.this,"You have to answer all questions",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }
}
