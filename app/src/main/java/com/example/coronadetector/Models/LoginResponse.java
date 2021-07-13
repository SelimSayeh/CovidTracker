package com.example.coronadetector.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse {

        private ArrayList<User> user;

        @Expose
        @SerializedName("message")
        private String message;

        @Expose
        @SerializedName("error")
        private boolean error;

    public LoginResponse() {
    }

    public LoginResponse(ArrayList<User> user, String message, boolean error) {
        this.user = user;
        this.message = message;
        this.error = error;
    }

    public LoginResponse(String message, boolean error) {
        this.message = message;
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ArrayList<User> getUser() {
        return user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
