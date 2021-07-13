package com.example.coronadetector.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class publicationResponse {

    private ArrayList<Publication> publication;
    @Expose
    @SerializedName("message")
    private String message;


    public publicationResponse(ArrayList<Publication> publication, String message) {
        this.publication = publication;
        this.message = message;
    }

    public ArrayList<Publication> getPublication() {
        return publication;
    }

    public void setPublication(ArrayList<Publication> publication) {
        this.publication = publication;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
