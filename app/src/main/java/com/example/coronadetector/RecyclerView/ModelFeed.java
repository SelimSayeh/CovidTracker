package com.example.coronadetector.RecyclerView;

/**
 * Created by karsk on 25/04/2018.
 */

public class ModelFeed {

    String id;
            int likes, comments;
    String name ;
    String description;

    public ModelFeed(String id, int likes, int comments, String name, String description) {
        this.id = id;
        this.likes = likes;
        this.comments = comments;
        this.name = name;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //  imgView_proPic = (ImageView) itemView.findViewById(R.id.imgView_proPic);
    // imgView_postPic = (ImageView) itemView.findViewById(R.id.imgView_postPic);
    //   tv_comments = (TextView) itemView.findViewById(R.id.tv_comment);
    //  tv_status = (TextView) itemView.findViewById(R.id.tv_status);
    //   tv_time = (TextView) itemView.findViewById(R.id.tv_time);


}
