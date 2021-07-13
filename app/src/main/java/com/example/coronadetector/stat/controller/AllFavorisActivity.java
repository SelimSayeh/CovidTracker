package com.example.coronadetector.stat.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.coronadetector.R;
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

public class AllFavorisActivity extends AppCompatActivity implements TableAdapter.OnItemClickListener  {

    RecyclerView recyclerViewF;
    public static final String SHARED_FILE_NAME = "favoriteCountries";
    SharedPreferences data;

    List<TableItem> listItems=new ArrayList<>();
    List<TableItem> tableItems=new ArrayList<>();
    TableAdapter tableAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_favoris);


        recyclerViewF=findViewById(R.id.recylerViewF);
        tableAdapter=new TableAdapter(tableItems,getApplicationContext());
        recyclerViewF.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewF.setAdapter(tableAdapter);
        tableAdapter.setOnItemClickListener(AllFavorisActivity.this);


        init();

    }


    public void init()
    {
        final  Handler handler1 = new Handler();
        Timer timer1 = new Timer();
        TimerTask doAsynchronousTask1=new TimerTask() {
            @Override
            public void run() {
                handler1.post(new Runnable() {
                    @Override
                    public void run() {


                        OkHttpClient client = new OkHttpClient();
                        client.setConnectTimeout(10, TimeUnit.SECONDS);
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
                                    listItems=new ArrayList<>();

                                    for (int i=0; i < jRes.length(); i++) {
                                        JSONObject obj = jRes.getJSONObject(i);
                                        TableItem tableItem=new TableItem(obj.getString("country"),obj.getInt("totalCases")
                                                ,obj.getInt("newCases")
                                                ,obj.getInt("totalDeaths"),obj.getInt("newDeaths")
                                                ,obj.getInt("totalRecovered"),obj.getInt("activeCases")
                                                ,obj.getInt("criticalCases"));
                                        listItems.add(tableItem);

                                    }
                                    data = getSharedPreferences(SHARED_FILE_NAME,MODE_PRIVATE);
                                    int sharedCount=data.getAll().size();
                                    tableItems=new ArrayList<>();
                                    for (int j = 0; j <listItems.size() ; j++) {


                                        String c = data.getString(listItems.get(j).getCountry(),"");


                                        if(tableItems.size()==sharedCount)
                                            break;


                                        if (!c.equals("")){
                                            String pays=listItems.get(j).getCountry();

                                            int tcs=listItems.get(j).getTotalCases();
                                            int ncs=listItems.get(j).getNewCases();
                                            int  tds=listItems.get(j).getTotalDeath();
                                            int  nds=listItems.get(j).getNewDeath();
                                            int  trd=listItems.get(j).getTotalRecovered();
                                            int   acs=listItems.get(j).getActiveCases();
                                            int    cts=listItems.get(j).getCriticCases();


                                            TableItem tableItem = new TableItem(pays,tcs
                                                    ,ncs,tds
                                                    ,nds,acs,trd,cts);
                                            tableItems.add(tableItem);
                                        }
                                    }

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tableAdapter=new TableAdapter(tableItems,getApplicationContext());
                                            recyclerViewF.setAdapter(tableAdapter);
                                            tableAdapter.setOnItemClickListener(AllFavorisActivity.this);

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
        timer1.schedule(doAsynchronousTask1, 0, 120000);



    }

    @Override
    protected void onResume() {
        init();
        super.onResume();
    }

    @Override
    public void onItemClick(int position) {
        Intent i=new Intent(AllFavorisActivity.this,StatDetailsActivity.class);
        TableItem tableItem=tableItems.get(position);
        i.putExtra("country",tableItem.getCountry());
        i.putExtra("totalCases",tableItem.getTotalCases());
        i.putExtra("newCases",tableItem.getNewCases());
        i.putExtra("totalDeath",tableItem.getTotalDeath());
        i.putExtra("newDeath",tableItem.getNewDeath());
        i.putExtra("totalRecov",tableItem.getTotalRecovered());
        i.putExtra("activeCases",tableItem.getActiveCases());
        i.putExtra("critic",tableItem.getCriticCases());


        startActivity(i);
    }
}
