package com.example.coronadetector.map.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.coronadetector.map.Model.Cases;
import com.example.coronadetector.map.Model.CovidModel;
import com.example.coronadetector.map.Model.Deaths;
import com.example.coronadetector.map.Model.ResultModel;
import com.example.coronadetector.R;
import com.example.coronadetector.map.Utils.OnEventListener;
import com.example.coronadetector.map.Utils.PermissionUtils;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapsMarkerActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private final static String SHARED_PREFERENCES_REULT_LIST="MyResultData";
    private final static String SHARED_PREFERENCES_KEY_REULT_LIST="MyResultListKey";
    private final static String RESULT_LIST="RESULT_LIST_TAG";

    private boolean mPermissionDenied = false;
    private GoogleMap Mmap;
    double myLongitude,myLatitude;
    String resp;
    CircularProgressView progressView;
    FrameLayout ProgressCircleContainer;
    List<ResultModel> ResultListArray;
    TextView testview;
    Gson gson;
    LinearLayout myInfoView;
    Boolean isUp;
    TextView TotalCases;
    TextView CountryName;
    TextView RecovNumber;
    TextView RecovPerc;
    TextView DeathNumber;
    TextView DeathPerc;
    ImageView StateColor;
    Drawable ColoredCircle;
    final DecimalFormat df = new DecimalFormat("0.0");
    List<ResultModel>myResultList;
    GetCovid_19Api  getCovid_19Api;
    Activity thisAct=this;

    private OkHttpClient client;
    Button seeMoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new OkHttpClient();

        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.

        progressView=findViewById(R.id.progress_view);
        progressView.setVisibility(View.VISIBLE);
        progressView.startAnimation();
        ProgressCircleContainer = findViewById(R.id.progressContainer);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        RetreiveSharedPreferences();

        TotalCases =findViewById(R.id.totalCases);
        RecovNumber=findViewById(R.id.recoveredNumber);
        RecovPerc=findViewById(R.id.recoveredPercent);
        DeathNumber=findViewById(R.id.deathsNumber);
        DeathPerc=findViewById(R.id.deathsPercent);
        CountryName=findViewById(R.id.countryName);
        myInfoView=findViewById(R.id.InfoSlider);
        myInfoView.setVisibility(View.INVISIBLE);
        isUp =false;

         ColoredCircle = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.circle_shape);

        ResultListArray=new ArrayList<>();

        seeMoreBtn=findViewById(R.id.seeMoreBtn);

        seeMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsMarkerActivity.this, com.example.coronadetector.stat.controller.MainActivity.class));

            }
        });


    }




    public class GetCovid_19Api extends AsyncTask<String,String,List<ResultModel>> {
        OkHttpClient client = new OkHttpClient();
        JSONArray Secondarray;
        private  List<ResultModel>myResultArray=new ArrayList<>();
        ResultModel resultModel;
        CovidModel covidModelInfo;
        Cases cases;
        Deaths deaths;
        List<Address> addressList =null;
        private OnEventListener<List<ResultModel>> mCallback;
        Context mContext;

        public GetCovid_19Api(Context context, OnEventListener callback){
            mContext = context;
            mCallback=callback;

        }

        protected void onPreExecute(){

            progressView.setVisibility(View.VISIBLE);
            progressView.bringToFront();
            progressView.startAnimation();
        }

        @Override
        protected List<ResultModel> doInBackground(String... strings)   {
            Request request = new Request.Builder()
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

                    if(isCancelled())
                        break;

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
                        if (addressList.size()!=0){
                            final Address address=addressList.get(0);

                            if ( (address.getLatitude()!= 0.0) && address.getLongitude()!=0.0){
                                resultModel = new ResultModel(covidModelInfo,address.getLatitude(),address.getLongitude());
                              //  myResultArray.add(resultModel);

                                ResultListArray.add(resultModel);
                                addMarkerCircleToMap(resultModel);
                            }

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

                return  myResultArray;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<ResultModel> s){
            super.onPostExecute(s);

            if (mCallback!=null){
                mCallback.onListReady(s);
                progressView.stopAnimation();
                ((ViewGroup)ProgressCircleContainer.getParent()).removeView(ProgressCircleContainer);


            }

        }
    }








    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
            Mmap = googleMap;
    /*    int height = 130;
        int width = 130;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.transparent_circle);
        Bitmap b = bitmapdraw.getBitmap();
       final Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
*/
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){

            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsMarkerActivity.this,R.raw.style_json));




            Mmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            Mmap.setMinZoomPreference(2.0f);
            Mmap.setMaxZoomPreference(20.0f);
            Mmap.setOnMyLocationButtonClickListener(this);
            Mmap.setOnMyLocationClickListener(this);
            enableMyLocation();


            getCovid_19Api = new GetCovid_19Api(getApplicationContext(), new OnEventListener() {

                @Override
                public void onListReady(List ResultList) {
                 //   myResultList = ResultList;
                  //  RemoveSharedPreferences();
                 //  SaveSharedPreferences(ResultList);




                }



            }) ;


            if(isNetworkAvailable(getApplicationContext()))
            {
                getCovid_19Api.execute();

                Mmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {



                        return getModelFromMarker(marker);
                    }
                });
            }
            else
            {
                showNoInternetDialog();
            }





        } else {
           Log.d("in the else","in the else ");
            Mmap.setMinZoomPreference(3.0f);
            Mmap.setMaxZoomPreference(20.0f);
            Mmap.setOnMyLocationButtonClickListener(this);
            Mmap.setOnMyLocationClickListener(this);
            enableMyLocation();

        }


    }
    public void showNoInternetDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Cannot load Map Data. Please check your Internet connection !");
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
    public Boolean getModelFromMarker(Marker marker)
    {

        for(int i=0;i<ResultListArray.size();i++)
        {
            ResultModel resultModel=ResultListArray.get(i);

            if((marker.getPosition().latitude== resultModel.getLat())&&(marker.getPosition().longitude== resultModel.getLng())){
                double deathPerc = (( (double)resultModel.getCovidModel().getDeaths().getTotal() / (double)resultModel.getCovidModel().getCases().getTotal())*100);
                double  percR= (((double) resultModel.getCovidModel().getCases().getRecovered() / (double)resultModel.getCovidModel().getCases().getTotal())*100) ;

                CountryName.setText(resultModel.getCovidModel().getId());
                TotalCases.setText(resultModel.getCovidModel().getCases().getTotal()+"");
                DeathNumber.setText(resultModel.getCovidModel().getDeaths().getTotal()+"");
                DeathPerc.setText("("+df.format(deathPerc) +"%)");
                RecovNumber.setText(resultModel.getCovidModel().getCases().getRecovered()+"");
                RecovPerc.setText("("+df.format(percR)+"%)");


                if (isUp) {
                    slideDown(myInfoView);
                } else {
                    slideUp(myInfoView);
                }
                isUp = !isUp;


                break;

            }
        }
        return false;


    }

    public void addMarkerCircleToMap(final ResultModel resultModel)
    {

        int height = 130;
        int width = 130;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.transparent_circle);
        Bitmap b = bitmapdraw.getBitmap();
        final Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
        try {
                LatLng latLng = new LatLng(resultModel.getLat(),resultModel.getLng());

                if(resultModel.getCovidModel().getCases().getTotal()>=100000){
                    Mmap.addCircle(new CircleOptions().center(latLng).fillColor(0x99B92404)
                            .strokeColor(Color.parseColor("#B92404")).radius(450000).strokeWidth(6)
                    );
                    Mmap.addMarker(new MarkerOptions()
                            .position(latLng).alpha(0.01f)
                            .title(resultModel.getCovidModel().getId())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));


                } else if (resultModel.getCovidModel().getCases().getTotal()>=10000){
                    Mmap.addCircle(new CircleOptions().center(latLng).fillColor(0x33DE3401).strokeColor(Color.parseColor("#B92404"))
                            .strokeWidth(3).radius(300000)
                    );
                    Mmap.addMarker(new MarkerOptions()
                            .position(latLng).alpha(0.01f)
                            .title(resultModel.getCovidModel().getId())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                } else if (resultModel.getCovidModel().getCases().getTotal()>=1000){
                    Mmap.addCircle(new CircleOptions().center(latLng).fillColor(0x66F2670D).strokeColor(Color.parseColor("#F2670D"))
                            .strokeWidth(1).radius(280000)
                    );
                    Mmap.addMarker(new MarkerOptions()
                            .position(latLng).alpha(0.01f)
                            .title(resultModel.getCovidModel().getId())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                }else if (resultModel.getCovidModel().getCases().getTotal()>100){
                    Mmap.addCircle(new CircleOptions().center(latLng).fillColor(0x66FDAE20).strokeColor(Color.parseColor("#FDAE20"))
                            .strokeWidth(0).radius(200000)
                    );
                    Mmap.addMarker(new MarkerOptions()
                            .position(latLng).alpha(0.01f)
                            .title(resultModel.getCovidModel().getId())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                }else if ((resultModel.getCovidModel().getCases().getTotal()>=0) && (resultModel.getCovidModel().getCases().getTotal()<=100)){
                    Mmap.addCircle(new CircleOptions().center(latLng).fillColor(0x66CCDC4E).strokeColor(Color.parseColor("#CCDC4E"))
                            .strokeWidth(0).radius(200000)
                    );
                    Mmap.addMarker(new MarkerOptions()
                            .position(latLng).alpha(0.01f)
                            .title(resultModel.getCovidModel().getId())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                }


            }catch (Exception e){
                System.out.println(e);
            }




                }
            });



    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (Mmap != null) {
                Mmap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        myLongitude=location.getLongitude();
        myLatitude=location.getLatitude();
        LatLng sydney = new LatLng(myLongitude, myLatitude);
        Mmap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
            // [END_EXCLUDE]
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    public void SaveSharedPreferences(List<ResultModel>ResultList){
        SharedPreferences preferences=getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String MyResultListJson = gson.toJson(ResultList);
        editor.putString("myResultList",MyResultListJson);
        editor.apply();

    }

    public void RetreiveSharedPreferences(){
        SharedPreferences preferences=getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        Gson gson =new Gson();
        String MyResultListJson= preferences.getString("myResultList",null );
        Type type =new TypeToken<List<ResultModel>>(){}.getType();
        ResultListArray = gson.fromJson(MyResultListJson,type);
        if (ResultListArray==null){

        } else{
//        for (int i=0;i<ResultListArray.size();i++){
//            System.out.println("SharedPreferences Stored List : "+ResultListArray.get(i).toString());
//        }
        }

    }

    public void RemoveSharedPreferences(){
        SharedPreferences preferences=getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        preferences.edit().clear().commit();

    }


    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    @Override
    public void onBackPressed()
    {
        getCovid_19Api.cancel(true);

       // startActivity(new Intent(MapsMarkerActivity.this, MainActivity.class), ActivityOptions.makeSceneTransitionAnimation(thisAct).toBundle());

      //  finish();
        super.onBackPressed();
    }











}


