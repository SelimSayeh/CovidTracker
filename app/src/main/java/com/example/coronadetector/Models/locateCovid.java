package com.example.coronadetector.Models;

public class locateCovid {
    double latitude;
    double longitude;
    int counter;

    public locateCovid(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public locateCovid(double latitude, double longitude, int counter) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
