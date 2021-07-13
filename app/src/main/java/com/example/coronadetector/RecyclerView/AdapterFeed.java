package com.example.coronadetector.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.coronadetector.R;

import java.util.ArrayList;

/**
 * Created by karsk on 25/04/2018.
 */

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.MyViewHolder> {

    Context context;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    RequestManager glide;

    public AdapterFeed(Context context, ArrayList<ModelFeed> modelFeedArrayList) {

        this.context = context;
        this.modelFeedArrayList = modelFeedArrayList;
        glide = Glide.with(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ModelFeed modelFeed = modelFeedArrayList.get(position);

        holder.tv_name.setText(modelFeed.getName());
        holder.tv_status.setText(modelFeed.getDescription());
        holder.myimage.setVisibility(View.VISIBLE);
        holder.tv_likes.setText(String.valueOf(modelFeed.getLikes()));
        holder.myimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println("hello");
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelFeedArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_time, tv_likes, tv_comments, tv_status;
        ImageView imgView_proPic, imgView_postPic;
        ImageView myimage;

        public MyViewHolder(View itemView) {
            super(itemView);
          //   tv_time = (TextView) itemView.findViewById(R.id.tv_time);
          //  imgView_proPic = (ImageView) itemView.findViewById(R.id.imgView_proPic);
           // imgView_postPic = (ImageView) itemView.findViewById(R.id.imgView_postPic);
            //   tv_comments = (TextView) itemView.findViewById(R.id.tv_comment);
                tv_status = (TextView) itemView.findViewById(R.id.tv_status);

                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                 myimage=(ImageView) itemView.findViewById(R.id.temp);
                tv_likes = (TextView) itemView.findViewById(R.id.tv_like);


            }


    }

}
