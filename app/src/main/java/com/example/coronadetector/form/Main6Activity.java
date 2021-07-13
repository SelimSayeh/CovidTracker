package com.example.coronadetector.form;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;

import com.example.coronadetector.R;
import com.example.coronadetector.map.Activity.MapsMarkerActivity;


public class Main6Activity extends AppCompatActivity {

    Button bt;
    public int cumule;
    HalfGauge hg;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.formulaire6);
        final Intent in = getIntent();
        bt=findViewById(R.id.button7);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main6Activity.this, com.example.coronadetector.MainActivity.class);
                startActivityForResult(i, 0);
            }
        });
        cumule = in.getIntExtra("cumule",0);
        result=findViewById(R.id.result);
        hg = findViewById(R.id.halfGauge);
        Range rg1 =new Range();
        rg1.setFrom(0);
        rg1.setTo(25);
        rg1.setColor(Color.GREEN);
        Range rg2 =new Range();
        rg2.setFrom(25);
        rg2.setTo(50);
        rg2.setColor(Color.YELLOW);
        Range rg3 =new Range();
        rg3.setFrom(50);
        rg3.setTo(100);
        rg3.setColor(Color.RED);
        hg.addRange(rg1);hg.addRange(rg2);hg.addRange(rg3);
        hg.setUseRangeBGColor(true);
        double score = (double)cumule/(double)36;


        if((int)(score*100)<25){
            hg.animate();

            hg.setValue((int)(score*100));


            result.setText("Le résultat de votre test est négatif. Votre chance d'avoir le COVID19 selon ce test est: "+(int)(score*100)+"% alors restez à la maison et soyez en sécurité.");
        }
        else if( (int)(score*100)>=25 && (int)(score*100)<=50){
            hg.animate();
            hg.setValue((int)(score*100));

            result.setText("Le résultat de votre test n'est pas bien connu. Votre chance d'avoir le COVID19 selon ce test est: "+(int)(score*100)+"% vous devez donc faire un test.");
        }
        else {
            hg.animate();
            hg.setValue((int)(score*100));

            result.setText("Le résultat de votre test est positif. Votre chance d'avoir le COVID19 selon ce test est: "+(int)(score*100)+"% vous devez appeler l'urgence.");
        }



    }
}
