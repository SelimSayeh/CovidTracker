package com.example.coronadetector;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronadetector.Models.LoginResponse;
import com.example.coronadetector.Retro.RetroClient;
import com.example.coronadetector.sharedPreferences.SharedPrefManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity implements View.OnClickListener {

    Button create;
    EditText loginHome;
    EditText passwordHome;
    Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        passwordHome=findViewById(R.id.passwordHome);
        loginHome=findViewById(R.id.loginHome);
        findViewById(R.id.buttonlogin).setOnClickListener(this);
        ////////////////////////////////

        ///////////////////////////////////
        create=findViewById(R.id.register);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,Register.class);

                startActivity(intent);

            }
        });

    }

   @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent=new Intent(this,Profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    void userlogin() {
         String Email = loginHome.getText().toString().trim();
         String Password = passwordHome.getText().toString().trim();

         if (Email.isEmpty()) {
             loginHome.setError("email is required");
             loginHome.requestFocus();
             return;
         }
         if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
             loginHome.setError("enter a valid mail");
             loginHome.requestFocus();
             return;
         }
         if (Password.isEmpty()) {
             passwordHome.setError("password required");
             passwordHome.requestFocus();
             return;
         }
         if (Password.length() < 6) {
             passwordHome.setError("password should be 6 characters or longer");
             passwordHome.requestFocus();
             return;
         }

         Call<LoginResponse> call = RetroClient.getInstance().getApi().userLogin(Email, Password);
         call.enqueue(new Callback<LoginResponse>() {
             @Override
             public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {



                        //System.out.println(loginResponse.getMessage()+"1");
                  if (response.isSuccessful()){
                      LoginResponse loginResponse=response.body();

                      Toast.makeText(Home.this,loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                      SharedPrefManager.getInstance(Home.this).saveUser(loginResponse.getUser());


                      Intent intent=new Intent(Home.this,Profile.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      startActivity(intent);
                  }
                  else{
                      ResponseBody error=response.errorBody();
                      // error.string()
                     
                          Toast.makeText(Home.this,"User not found ", Toast.LENGTH_SHORT).show();


                  }

                 }



             @Override
             public void onFailure(Call<LoginResponse> call, Throwable t) {
                 Toast.makeText(Home.this, t.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });
     }

         @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonlogin:

               userlogin();
                break;
        }

    }
}