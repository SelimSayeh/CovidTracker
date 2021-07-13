package com.example.coronadetector.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;


import com.example.coronadetector.map.Model.Cases;
import com.example.coronadetector.map.Model.CovidModel;
import com.example.coronadetector.map.Model.Deaths;
import com.example.coronadetector.map.Model.ResultModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Service extends JobIntentService {

    final Handler handler= new Handler();
    ArrayList<ResultModel> myResultModel =new ArrayList<>();
    ResultModel resultInfo;
    Intent intent;

    private static  final int JOB_ID=1;
    private static final String TAG="Service";
    public static final String ACTION_FOO = "com.app.action.FOO";
    OkHttpClient client = new OkHttpClient();
    JSONArray Secondarray;
    private  List<ResultModel>myResultArray=new ArrayList<>();
    ResultModel resultModel;
    CovidModel covidModelInfo;
    Cases cases;
    Deaths deaths;
    List<Address> addressList =null;
    Activity mActivity;

    // Step 1 - This interface defines the type of messages I want to communicate to my owner
    public interface MyCustomListener{

        public void OnTaskReady(List<ResultModel> myResultList);
    }

    // Step 2 - This variable represents the listener passed in by the owning object
    // The listener must implement the events interface and passes messages up to the parent.
    private MyCustomListener listener;

    // Constructor where listener events are ignored
    public Service(Activity activity){
        // set null or default listener or accept as argument to constructor
        mActivity=activity;
        this.listener=null;
        getAndTraitCovidList();
    }

    // Assign the listener implementing events interface that will receive the events
    public void setOnTaskReady(MyCustomListener listener){
        this.listener=listener;
    }

    public static void enqueueWork(Context context,Intent intent){
        if(ACTION_FOO.equals(intent.getAction())){
        enqueueWork(context,Service.class,JOB_ID,intent);
    }}

    @Override
    public void onCreate(){
        super.onCreate();
        ShowToast("Job Execution Started");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        getAndTraitCovidList();

    }

    public List<ResultModel> getAndTraitCovidList(){

        Request request  =  new Request.Builder()
                .url("https://covid-193.p.rapidapi.com/statistics")
                .get()
                .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "3dad72c279mshfd033ae13972199p195494jsn66d1d943f13d")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String Response = response.body().string();

            //******************************************************************

            JSONObject root = new JSONObject(Response);
            Secondarray= root.getJSONArray("response");

            for(int i=0;i<Secondarray.length();i++){
                JSONObject json = Secondarray.getJSONObject(i);
                JSONObject json2= json.getJSONObject("cases");
                JSONObject json3=json.getJSONObject("deaths");
                cases=new Cases(json2.getString("new"),json2.getInt("active"),json2.getInt("recovered"),json2.getInt("total"));
                deaths=new Deaths(json3.getString("new"),json3.getInt("total"));
                covidModelInfo =new CovidModel(json.getString("country"),cases,deaths,json.getString("day"),json.getString("time"));
                final Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    //String Name =CkeckName(covidModelInfo.getId());
                    addressList=geocoder.getFromLocationName(covidModelInfo.getId(),1);
                    System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC Country name"+i+" :"+covidModelInfo.getId());
                    if (addressList.size()!=0){
                        final Address address=addressList.get(0);

                        if ( (address.getLatitude()!= 0.0) && address.getLongitude()!=0.0){
                            resultModel = new ResultModel(covidModelInfo,address.getLatitude(),address.getLongitude());
                            myResultArray.add(resultModel);
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }   for (int j=0;j<myResultArray.size();j++) {
                ResultModel test = new ResultModel();
                test = myResultArray.get(j);
                Log.d("my new covide data : ", "my new covide data :" + test.toString());

            }
            //*******************************************************************
            listener.OnTaskReady(myResultArray);
            return  myResultArray;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String CkeckName(String Name){

        switch (Name){
            case "USA":
                Name="United States of America";
                return Name;

            case "S.-Korea":
                Name="Korea (Democratic People's Republic of)";
                return Name;
            case "UK":
                Name="United Kingdom of Great Britain and Northern Irland";
                return Name;
            case "Czechia":
                Name="Czech Republic";
                return Name;
            case "UAE":
                Name="United Arab Emirates";
                return Name;
            case "Faeroe-Islands":
                Name="Faroe Islands";
                return Name;
            case "Brunei-":
                Name="Brunei Darussalam";
                return Name;
            case "Moldova":
                Name="Moldova (Republic of)";
                return Name;
            case "Palestine":
                Name="Palestine, State of";
                return Name;
            case "Tanzania":
                Name="Tanzania, United Republic of";
                return Name;
        }
        if (Name.contains("-")){
            Name=Name.replaceAll("-"," ");
        }

        return Name;
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        ShowToast("Job Execution Finished");
    }


    void ShowToast(final CharSequence text){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Service.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
