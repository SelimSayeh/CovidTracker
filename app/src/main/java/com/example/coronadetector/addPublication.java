package com.example.coronadetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronadetector.Models.User;
import com.example.coronadetector.RecyclerView.newsfeed;
import com.example.coronadetector.Retro.RetroClient;
import com.example.coronadetector.sharedPreferences.SharedPrefManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addPublication extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener{

    EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);


        findViewById(R.id.settings).setOnClickListener(this);
        findViewById(R.id.ajouter).setOnClickListener(this);

        description=findViewById(R.id.description);

    }



    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.newsfeed:
                Intent intent4=new Intent(this, newsfeed.class);

                startActivity(intent4);

                return true;

            case R.id.addpublication:
                Intent intent=new Intent(this,addPublication.class);

                startActivity(intent);
                return true;
            case R.id.profile:
                if(SharedPrefManager.getInstance(this).isLoggedIn()) {
                    Intent intent3 = new Intent(this, Profile.class);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent3);
                    return true;
                }

            case R.id.logoutitem:
               userLogOut();

            default:
                return false;
        }
    }

    private void userLogOut() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



        void ajoutPublication() {

           String Description=description.getText().toString().trim();
            User user= SharedPrefManager.getInstance(this).getUser();
            System.out.println(user.getName());
            System.out.println(user.getEmail());
            System.out.println(user.getId());
            if (Description.isEmpty()){
                description.setError("description is required");
                description.requestFocus();
                return;
            }


              Call<ResponseBody> call= RetroClient.getInstance().getApi().createPublication(Description,user.getEmail(),user.getId(),user.getName());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code()==200){

                        String reponse=response.body().string();
                        Toast.makeText(addPublication.this,reponse, Toast.LENGTH_SHORT).show();
                        System.out.println(reponse);


                    }
                    else{
                        String reponse=response.errorBody().string();
                        Toast.makeText(addPublication.this,reponse, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(addPublication.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:

                userLogOut();
                break;

            case R.id.settings:

                PopupMenu popup= new PopupMenu(this,v);
                popup.setOnMenuItemClickListener(this);
                popup.inflate(R.menu.menu);
                popup.show();
                break;
            case R.id.ajouter:

                ajoutPublication();
                break;
        }
    }

        }


