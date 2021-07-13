package com.example.coronadetector.Retro;


import com.example.coronadetector.Models.LoginResponse;
import com.example.coronadetector.Models.locateResponse;
import com.example.coronadetector.Models.publicationResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("store")
    Call<ResponseBody> createUser(
            @Field("name") String name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("phone") String phone


    );

    @FormUrlEncoded
    @POST("log")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("ajout")
    Call<ResponseBody> createPublication(
            @Field("description") String description,
            @Field("usermail") String usermail,
            @Field("userid") String userid,
            @Field("usernamep") String usernamep


    );
  /*  @FormUrlEncoded
    @POST("add")
    Call<ResponseBody> createCommentaire(
            @Field("comment") String comment,
            @Field("userid") int userid,
            @Field("userid") int publicationid

    );*/

    @GET("showpub")
    Call<publicationResponse> getpubs();

    @FormUrlEncoded
    @POST("addloc")
    Call<locateResponse>createLoc(
            @Field("latitude") Double latitude,
            @Field("longitude") Double longitude
    );
    @GET("showlocs")
    Call<locateResponse> getLocs();

}
