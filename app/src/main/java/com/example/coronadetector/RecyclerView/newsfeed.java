package com.example.coronadetector.RecyclerView;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronadetector.Models.Publication;
import com.example.coronadetector.Models.User;
import com.example.coronadetector.Models.publicationResponse;
import com.example.coronadetector.R;
import com.example.coronadetector.Retro.RetroClient;
import com.example.coronadetector.sharedPreferences.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newsfeed extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    AdapterFeed adapterFeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        User user= SharedPrefManager.getInstance(this).getUser();
//        Publication publication= SharedPrefManager.getInstance(this).getId();
      //  System.out.println(user.getName());
      // findViewById(R.id.temp).setOnClickListener(this);

      //  System.out.println(publication.getIdp());
        //showAllPubs();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapterFeed = new AdapterFeed(this, modelFeedArrayList);
        recyclerView.setAdapter(adapterFeed);

        populateRecyclerView();


    }
    public void populateRecyclerView() {

        //  imgView_proPic = (ImageView) itemView.findViewById(R.id.imgView_proPic);
        // imgView_postPic = (ImageView) itemView.findViewById(R.id.imgView_postPic);
        //   tv_comments = (TextView) itemView.findViewById(R.id.tv_comment);
        //  tv_status = (TextView) itemView.findViewById(R.id.tv_status);
        //   tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        Call<publicationResponse> call= RetroClient.getInstance().getApi().getpubs();
        call.enqueue(new Callback<publicationResponse>() {


            @Override
            public void onResponse(Call<publicationResponse> call, Response<publicationResponse> response) {
                if(!response.isSuccessful()){
                   // txtresult.setText("code"+response.code());
                    return;
                }
                if (response.isSuccessful()){
                    publicationResponse pr=response.body();


                    SharedPrefManager.getInstance(newsfeed.this).savePublication(pr.getPublication());
                    User user=SharedPrefManager.getInstance(newsfeed.this).getUser();

                    List<Publication> px=pr.getPublication();
                    for (Publication publication: px){


                        ModelFeed modelFeed = new ModelFeed(publication.getIdp(), 99, 23,publication.getUsername(),publication.getDescription());
                        modelFeedArrayList.add(modelFeed);
                        adapterFeed.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<publicationResponse> call, Throwable t) {
                //txtresult.setText(t.getMessage());
            }
        });




    }




 /*   void ajoutCommentaire() {

        //String Description=description.getText().toString().trim();
        User user= SharedPrefManager.getInstance(this).getUser();
        Publication publication= SharedPrefManager.getInstance(this).getId();
        System.out.println(publication.getIdp());
       /* if (Description.isEmpty()){
            description.setError("description is required");
            description.requestFocus();
            return;
        }*/


     /*  Call<ResponseBody> call= RetroClient.getInstance().getApi().createCommentaire(commentaire,user.getId(),publication.getIdp());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code()==200){

                        String reponse=response.body().string();
                        Toast.makeText(newsfeed.this,reponse,Toast.LENGTH_SHORT).show();
                        //   Intent intent=new Intent(newsFeed.this,Home.class);

                        // startActivity(intent);


                    }
                    else{
                        String reponse=response.errorBody().string();
                        Toast.makeText(newsfeed.this,reponse,Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(newsfeed.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.temp:
                System.out.println("hi");
               // showAllPubs();
                break;
        }
    }
}