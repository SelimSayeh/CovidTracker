package com.example.coronadetector.stat.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronadetector.R;
import com.example.coronadetector.stat.Model.TableItem;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class StatDetailsActivity extends AppCompatActivity {
    TextView country;
    TextView totalCase;
    TextView newCases;
    TextView totalDeath;
    TextView newDeath;
    TextView totalRecov;
    TextView activeCases;
    TextView critic;

    List<TableItem> tableItems;
    List<TableItem>listItems;


    int pos;
    int pos1;
    String c;
    String pays;
    int tcs;
    int ncs;
    int tds;
    int nds;
    int trd;
    int acs;
    int cts;


    String pos5;

    public static final String SHARED_FILE_NAME = "favoriteCountries";
    SharedPreferences data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_details);
        country=findViewById(R.id.country1);
        totalCase=findViewById(R.id.totalCases1);
        newCases=findViewById(R.id.NewCases1);
        totalDeath=findViewById(R.id.totalDeath1);
        newDeath=findViewById(R.id.newDeath1);
        totalRecov=findViewById(R.id.totalRecov1);
        activeCases=findViewById(R.id.activeCases1);
        critic=findViewById(R.id.critic1);

        Intent i=getIntent();
        c= i.getStringExtra("country");




        tableItems=new ArrayList<>();

        listItems=new ArrayList<>();


        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10,TimeUnit.SECONDS);
        client.setWriteTimeout(10,TimeUnit.SECONDS);
        client.setReadTimeout(30,TimeUnit.SECONDS);


        final Request request = new Request.Builder()
                .url("https://nepalcorona.info/api/v1/data/world")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("Chart", "Network error");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String body = response.body().string();
                try {
                    JSONArray jRes = new JSONArray(body);
                    for (int i=0; i < jRes.length(); i++) {
                        JSONObject obj = jRes.getJSONObject(i);
                        TableItem tableItem=new TableItem(obj.getString("country"),obj.getInt("totalCases")
                                ,obj.getInt("newCases")
                                ,obj.getInt("totalDeaths"),obj.getInt("newDeaths")
                                ,obj.getInt("totalRecovered"),obj.getInt("activeCases")
                                ,obj.getInt("criticalCases"));
                        listItems.add(tableItem);

                    }
                    for (int j = 0; j <listItems.size() ; j++) {
                        if (listItems.get(j).getCountry().equals(c)){
                            pays=listItems.get(j).getCountry();
                            tcs=listItems.get(j).getTotalCases();
                            ncs=listItems.get(j).getNewCases();
                            tds=listItems.get(j).getTotalDeath();
                            nds=listItems.get(j).getNewDeath();
                            trd=listItems.get(j).getTotalRecovered();
                            acs=listItems.get(j).getActiveCases();
                            cts=listItems.get(j).getCriticCases();
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    country.setText(pays);
                                    totalCase.setText(""+tcs);
                                    newCases.setText(""+ncs);
                                    totalDeath.setText(""+tds);
                                    newDeath.setText(""+nds);
                                    totalRecov.setText(""+trd);
                                    activeCases.setText(""+acs);
                                    critic.setText(""+cts);
                                }
                            });


                        break;

                        }
                    }


                }
                catch(JSONException jse) {
                    jse.printStackTrace();
                }



            }
        });



        updateCountry();




    }

    public void updateCountry()
    {

        final Handler handler =new Handler();
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        OkHttpClient client = new OkHttpClient();
                        client.setConnectTimeout(10,TimeUnit.SECONDS);
                        client.setWriteTimeout(10,TimeUnit.SECONDS);
                        client.setReadTimeout(30,TimeUnit.SECONDS);


                        final Request request = new Request.Builder()
                                .url("https://nepalcorona.info/api/v1/data/world")
                                .method("GET", null)
                                .addHeader("Content-Type", "application/json")
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                Log.d("Chart", "Network error");
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                String body = response.body().string();
                                try {
                                    JSONArray jRes = new JSONArray(body);
                                    for (int i=0; i < jRes.length(); i++) {
                                        JSONObject obj = jRes.getJSONObject(i);
                                        TableItem tableItem=new TableItem(obj.getString("country"),obj.getInt("totalCases")
                                                ,obj.getInt("newCases")
                                                ,obj.getInt("totalDeaths"),obj.getInt("newDeaths")
                                                ,obj.getInt("totalRecovered"),obj.getInt("activeCases")
                                                ,obj.getInt("criticalCases"));
                                        listItems.add(tableItem);

                                    }
                                    for (int j = 0; j <listItems.size() ; j++) {
                                        if (listItems.get(j).getCountry().equals(c)){
                                            pays=listItems.get(j).getCountry();
                                            tcs=listItems.get(j).getTotalCases();
                                            ncs=listItems.get(j).getNewCases();
                                            tds=listItems.get(j).getTotalDeath();
                                            nds=listItems.get(j).getNewDeath();
                                            trd=listItems.get(j).getTotalRecovered();
                                            acs=listItems.get(j).getActiveCases();
                                            cts=listItems.get(j).getCriticCases();
                                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    country.setText(pays);
                                                    totalCase.setText(""+tcs);
                                                    newCases.setText(""+ncs);
                                                    totalDeath.setText(""+tds);
                                                    newDeath.setText(""+nds);
                                                    totalRecov.setText(""+trd);
                                                    activeCases.setText(""+acs);
                                                    critic.setText(""+cts);
                                                }
                                            });


                                            break;

                                        }
                                    }


                                }
                                catch(JSONException jse) {
                                    jse.printStackTrace();
                                }



                            }
                        });

                    }
                });
            }
        };
        timer.schedule(timerTask,0,50000);




    }

    public void showDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Country already added to favorite list ! Do you want to remove it ?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                SharedPreferences.Editor editor = data.edit();

                                editor.remove(country.getText().toString());
                                editor.apply();
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void showFavDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Country added to favorite list !");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        alertDialogBuilder.setCancelable(false);


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.favoris_menu,menu);

        MenuItem favorisItem=menu.findItem(R.id.allFavoris);
        favorisItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
              //  Intent intent=new Intent(getApplicationContext(),AllFavorisActivity.class);
              //  startActivity(intent);

                data = getSharedPreferences(SHARED_FILE_NAME,MODE_PRIVATE);

                String c = data.getString(country.getText().toString(),"");

                if(c.equals(""))
                {
                    SharedPreferences.Editor editor = data.edit();

                    editor.putString(country.getText().toString(),totalCase.getText().toString());
                    editor.apply();
                    showFavDialog();

                }
                else
                {
                    showDialog();
                }


                return true;
            }
        });
        return true;
    }
}
