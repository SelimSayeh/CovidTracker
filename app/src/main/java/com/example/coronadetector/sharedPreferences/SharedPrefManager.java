package com.example.coronadetector.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.coronadetector.Models.Publication;
import com.example.coronadetector.Models.User;

import java.util.ArrayList;
import java.util.Iterator;

public class SharedPrefManager {


    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }


    public void saveUser(ArrayList<User> user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Iterator itr=user.iterator();
        while(itr.hasNext()){
        User us=(User)itr.next();

        editor.putString("_id",us.getId());
        editor.putString("name",us.getName());
        editor.putString("email",us.getEmail());
        editor.putInt("phone", us.getPhone());
        }
        editor.apply();

    }

    public void savePublication(ArrayList<Publication> publication){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Iterator itr=publication.iterator();
        while(itr.hasNext()){
            Publication pb=(Publication) itr.next();
            editor.putString("idp",pb.getIdp());
            editor.putString("description",pb.getDescription());
            editor.putString("usermail",pb.getUsermail());
            editor.putString("userid",pb.getUserid());
        }
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("_id", null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString("_id",null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getInt("phone",0)
        );
    }

    public Publication getEverything(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Publication(
                sharedPreferences.getString("idp1",null),
                sharedPreferences.getString("description1",null),
                sharedPreferences.getString("usermail1",null),
                sharedPreferences.getString("userid1",null),
                sharedPreferences.getString("username1",null)

        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
