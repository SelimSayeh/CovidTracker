package com.example.coronadetector.stat.controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

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
import org.tensorflow.lite.Interpreter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;



public class ExampleService extends Service {
    Intent notificationIntent;
   // List<TableItem> tableItems=new ArrayList<>();
    List<TableItem>listItems=new ArrayList<>();
    public static final String CHANNEL_ID="serviceChannel";

    public static final String SHARED_FILE_NAME = "favoriteCountries";
    SharedPreferences data;
    @Override
    public void onCreate() {



        super.onCreate();


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel serviceChannel=new NotificationChannel(
                    CHANNEL_ID,
                    "Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT

            );
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

    }



    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        lookForUpdate();

        Log.d("Notifs Service"," started");

        return START_NOT_STICKY;


    }


    public void lookForUpdate()
    {
        final Handler handler1 = new Handler();
        Timer timer1 = new Timer();
        TimerTask doAsynchronousTask1=new TimerTask() {
            @Override
            public void run() {
                handler1.post(new Runnable() {
                    @Override
                    public void run() {
                        data = getSharedPreferences(SHARED_FILE_NAME,MODE_PRIVATE);
                        int sharedCount=data.getAll().size();

                        if(sharedCount>0)
                        {
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

                                        int currCount=0;

                                        for (int j = 0; j <listItems.size() ; j++) {


                                            String c = data.getString(listItems.get(j).getCountry(),"");


                                            if(currCount==sharedCount)
                                                break;


                                            if (!c.equals("")){

                                                currCount++;



                                                int tcs=listItems.get(j).getTotalCases();
                                                int oldCs= Integer.parseInt(c);

                                                if(tcs!=oldCs)
                                                {
                                                    makeNotif(listItems.get(j));
                                                    SharedPreferences.Editor editor = data.edit();

                                                    editor.putString(listItems.get(j).getCountry(),tcs+"");
                                                    editor.apply();
                                                }



                                            }
                                        }



                                    }
                                    catch(JSONException jse) {
                                        jse.printStackTrace();
                                    }



                                }
                            });


                        }



                    }
                });
            }
        };
        timer1.schedule(doAsynchronousTask1, 0, 120000);



    }

    public void makeNotif(TableItem tableItem)
    {
        int tcs=tableItem.getTotalCases();
        String c=tableItem.getCountry();

        int ncs=tableItem.getNewCases();
        int  tds=tableItem.getTotalDeath();
        int  nds=tableItem.getNewDeath();
        int  trd=tableItem.getTotalRecovered();
        int   acs=tableItem.getActiveCases();
        int    cts=tableItem.getCriticCases();

        String notTxt="TotalCases : "+tcs+", "+"New Cases : "+ncs+"\n"+"Total Deaths : "+tds+", "+"New Deaths : "+nds+"\n"+"Total Recovered : "+trd+", "+"Active Cases :"+acs+ "\n"+"Critic Cases : "+cts;

        notificationIntent=new Intent(getApplicationContext(),StatDetailsActivity.class);

        notificationIntent.putExtra("country",c);
        notificationIntent.putExtra("totalCases",tcs);
        notificationIntent.putExtra("newCases",ncs);
        notificationIntent.putExtra("totalDeath",tds);
        notificationIntent.putExtra("newDeath",nds);
        notificationIntent.putExtra("totalRecov",trd);
        notificationIntent.putExtra("activeCases",acs);
        notificationIntent.putExtra("critic",cts);

        PendingIntent pendingIntent= PendingIntent.getActivity(getApplicationContext(),0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        String title=c+" Cases Update";
      /*  final   Notification notification=new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.corona_detector_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.mipmap.corona_detector_launcher))
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notTxt))
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        startForeground(1,notification);*/


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.roundednewlogo))
                .setContentTitle(title)
                .setContentText(notTxt)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);



        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.trans_blue_logo_new);
            notificationBuilder.setColor(getResources().getColor(R.color.newIconGreen));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.transnewlogo);
        }

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
