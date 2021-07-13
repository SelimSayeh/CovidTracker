package com.example.coronadetector.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    @SerializedName("_id")
    String _id;
    @Expose
    @SerializedName("name")
    String name;

    String password;

    String email;


    int phone;

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }

    public User(String _id, String name, String password, String email, int phone) {
        this._id = _id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public User(String id, String name, String email, int phone) {
        this._id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }



   public User(String name, String email, int phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
