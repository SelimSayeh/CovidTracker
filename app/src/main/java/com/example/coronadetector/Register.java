package com.example.coronadetector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coronadetector.Retro.RetroClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity implements View.OnClickListener{
Button create;
ImageView chooseimg;
Button choisir;
EditText username;
EditText password;
EditText email;
EditText phone;

private static final int IMAGE_PICK=1000;
private static final int PERMISSION_CODE=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       findViewById(R.id.register).setOnClickListener(this);
        chooseimg=findViewById(R.id.imgtest);
        choisir=findViewById(R.id.buttonimg);
        username=findViewById(R.id.login);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        create=findViewById(R.id.register);


    choisir.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions,PERMISSION_CODE);
                }
                else{
                     pickImageFromGallery();
                }
            }
            else{

                pickImageFromGallery();
            }

        }
    });


    }

    private void pickImageFromGallery() {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK) {
            chooseimg.setImageURI(data.getData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ){
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(this,"permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void userSignUp() {
        String Email=email.getText().toString().trim();
        String Username=username.getText().toString().trim();
        String Phone=phone.getText().toString().trim();
        String Password=password.getText().toString().trim();

        if (Email.isEmpty()){
            email.setError("email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("enter a valid mail");
            email.requestFocus();
            return;
        }
        if (Password.isEmpty()){
            password.setError("password required");
            password.requestFocus();
            return;
        }
        if (Password.length()<6){
            password.setError("password should be 6 characters or longer");
            password.requestFocus();
            return;
        }
        if (Username.isEmpty()){
            username.setError("username required !");
            username.requestFocus();
            return;
        }
        if (Phone.isEmpty()){
            phone.setError("phone number required !");
            phone.requestFocus();
            return;
        }
        if (!Patterns.PHONE.matcher(Phone).matches()){
            phone.setError("this field must be fill with numbers");
            phone.requestFocus();
            return;
        }

        //API call

        Call<ResponseBody> call= RetroClient.getInstance().getApi().createUser(Username,Password,Email,Phone);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code()==200){

                    String reponse=response.body().string();
                     Toast.makeText(Register.this,reponse, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Register.this,Home.class);

                        startActivity(intent);


                    }
                else{
                        String reponse=response.errorBody().string();
                        Toast.makeText(Register.this,reponse, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Register.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                userSignUp();
                break;

            case R.id.password:
                break;
            
        }
    }


}