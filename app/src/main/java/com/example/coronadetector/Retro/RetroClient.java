package com.example.coronadetector.Retro;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    public static final String BASE_URL="http://192.168.1.2:3000/api/user/";
    static  RetroClient mInstance ;
    Retrofit retrofit ;

    public RetroClient(){
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    }
    public static synchronized RetroClient getInstance(){
        if (mInstance==null){
            mInstance=new RetroClient();

        }
        return mInstance;
    }
    public Api getApi(){
        return retrofit.create(Api.class);

    }
}
