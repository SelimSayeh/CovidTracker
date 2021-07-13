package com.example.coronadetector.stat.controller;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.coronadetector.R;
import com.example.coronadetector.map.Activity.MapsMarkerActivity;
import com.example.coronadetector.stat.Model.TableItem;
import com.example.coronadetector.stat.view.TableAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements TableAdapter.OnItemClickListener {
   static int total;
   static int recover;
   static  int dece;
   static int active;
   static int critic;
   static int recC;
   static int death;
   static int rec;
     TextView ttotal;
     TextView tdece;
     TextView trecover;
     TextView tactive;
     TextView tcritic;
     TextView tactcrit;
     TextView tactcritpourcent;
     TextView tcritpourcent;
     TextView trecovClo;
     TextView trecov;
     TextView tdeath;
     TextView trecpourcent;
     TextView tdpourcent;
     RecyclerView recyclerView;
     List<TableItem>tableItems;
    TableAdapter tableAdapter;
    ArrayList<Integer> results;
    Activity thisAct=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main_stat);
        ttotal=findViewById(R.id.total);
        tdece=findViewById(R.id.dece);
        trecover=findViewById(R.id.recovered);
        tactive=findViewById(R.id.active);
        tcritic=findViewById(R.id.critic);
        tactcrit=findViewById(R.id.active_critic);
        tactcritpourcent=findViewById(R.id.actcrit_pourcent);
        tcritpourcent=findViewById(R.id.critic_pourcent);
        trecovClo=findViewById(R.id.recClosed);
        tdeath=findViewById(R.id.death);
        trecov=findViewById(R.id.rec);
        trecpourcent=findViewById(R.id.recpourcent);
        tdpourcent=findViewById(R.id.deathpourcent);
        recyclerView=findViewById(R.id.recylerView);
        tableItems=new ArrayList<>();


        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10,TimeUnit.SECONDS);
        client.setWriteTimeout(10,TimeUnit.SECONDS);
        client.setReadTimeout(30,TimeUnit.SECONDS);



        final  Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask=new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        results = new ArrayList<>();
                        // ArrayList<>lists=new ArrayList<>();
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
                                        JSONObject obj = jRes.getJSONObject(0);
                                        total=  obj.getInt("totalCases");
                                        recover=obj.getInt("totalRecovered");
                                        dece=obj.getInt("totalDeaths");
                                        active=obj.getInt("activeCases");
                                        critic=obj.getInt("criticalCases");





                                    final int s=active-critic;
                                    final int recC=recover+dece;

                               new Handler(Looper.getMainLooper()).post(new Runnable() {
                                   @Override
                                   public void run() {
                                       ttotal.setText(""+total);
                                       trecover.setText(""+recover);
                                       tdece.setText(""+dece);
                                       tactive.setText(""+active);
                                       tcritic.setText(""+critic);
                                       tactcrit.setText(""+s);
                                       int actcri=(s*100)/active;
                                       int crit=(critic*100)/active;
                                       tcritpourcent.setText("("+crit+"%)");
                                       tactcritpourcent.setText("("+actcri+"%)");
                                       trecovClo.setText(""+recC);
                                       tdeath.setText(""+dece);
                                       trecov.setText(""+recover);
                                       int recP=(recover*100)/recC;
                                       int dP=(dece*100)/recC;
                                       trecpourcent.setText("("+recP+"%)");
                                       tdpourcent.setText("("+dP+"%)");


                                   }
                               });

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

// Debut Thread RecylerView
        final  Handler handler1 = new Handler();
        Timer timer1 = new Timer();
        TimerTask doAsynchronousTask1=new TimerTask() {
            @Override
            public void run() {
                handler1.post(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client1 = new OkHttpClient();
                        client1.setConnectTimeout(10,TimeUnit.SECONDS);
                        client1.setWriteTimeout(10,TimeUnit.SECONDS);
                        client1.setReadTimeout(30,TimeUnit.SECONDS);


                        final Request request1 = new Request.Builder()
                                .url("https://nepalcorona.info/api/v1/data/world")
                                .method("GET", null)
                                .addHeader("Content-Type", "application/json")
                                .build();
                        client.newCall(request1).enqueue(new Callback() {
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
                                    tableItems=new ArrayList<>();
                                    for (int i = 0; i <jRes.length() ; i++) {
                                        JSONObject obj1=jRes.getJSONObject(i);


                                        TableItem tableItem=new TableItem(obj1.getString("country"),obj1.getInt("totalCases")
                                                ,obj1.getInt("newCases"),obj1.getInt("totalDeaths")
                                                ,obj1.getInt("newDeaths"),obj1.getInt("activeCases"),obj1.getInt("totalRecovered"),obj1.getInt("criticalCases"));
                                       if(!obj1.getString("country").equals("")){
                                        tableItems.add(tableItem);

                                       }



                                    }


                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {


                                            tableAdapter=new TableAdapter(tableItems,getApplicationContext());
                                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                            recyclerView.setAdapter(tableAdapter);
                                            tableAdapter.setOnItemClickListener(MainActivity.this);
                                        }
                                    });

                                }
                                catch(JSONException jse) {
                                    jse.printStackTrace();
                                }

                            }
                        });
                        /////////////////Fin Thread////
                    }
                });
            }
        };

        if(isNetworkAvailable(getApplicationContext()))
        {
            timer.schedule(doAsynchronousTask, 0, 120000);


            timer1.schedule(doAsynchronousTask1, 0, 120000);

        }
        else
        {
            showNoInternetDialog();
        }



       }

    public void showNoInternetDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Cannot load Statistics. Please check your Internet connection !");
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
    public static boolean isNetworkAvailable(Context context) {
        if(context == null)  return false;


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                        return true;
                    }
                }
            }

            else {

                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        Log.i("update_statut", "Network is available : true");
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("update_statut", "" + e.getMessage());
                }
            }
        }
        Log.i("update_statut","Network is available : FALSE ");
        return false;
    }
//Debut recherche
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.table_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                tableAdapter.getFilter().filter(newText);

                return false;
            }
        });

        MenuItem favorisItem=menu.findItem(R.id.action_favoris);
        favorisItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                startActivity(new Intent(getApplicationContext(),AllFavorisActivity.class));

                return false;
            }
        });

        return true;
    }
///fin recherche
    @Override
    public void onItemClick(int position) {
        Intent i=new Intent(MainActivity.this,StatDetailsActivity.class);
        TableItem tableItem=tableItems.get(position);
        i.putExtra("country",tableItem.getCountry());
        i.putExtra("totalCases",tableItem.getTotalCases());
        i.putExtra("newCases",tableItem.getNewCases());
        i.putExtra("totalDeath",tableItem.getTotalDeath());
        i.putExtra("newDeath",tableItem.getNewDeath());
        i.putExtra("totalRecov",tableItem.getTotalRecovered());
        i.putExtra("activeCases",tableItem.getActiveCases());
        i.putExtra("critic",tableItem.getCriticCases());
        i.putExtra("position",position);


        startActivity(i);
    }




}


