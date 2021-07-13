package com.example.coronadetector.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class locateResponse {

    private ArrayList<locateCovid> localisation;
    @Expose
    @SerializedName("message")
    private String message;

    public locateResponse(ArrayList<locateCovid> localisation, String message) {
        this.localisation = localisation;
        this.message = message;
    }

    public ArrayList<locateCovid> getLocalisation() {
        return localisation;
    }

    public void setLocalisation(ArrayList<locateCovid> localisation) {
        this.localisation = localisation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
