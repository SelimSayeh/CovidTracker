package com.example.coronadetector.map.Model;

public class ResultModel  {

    private CovidModel covidModel;
    private Double lat;
    private Double lng;


    public ResultModel(CovidModel covidModel, Double lat, Double lng) {
        this.covidModel = covidModel;
        this.lat = lat;
        this.lng = lng;
    }

    public ResultModel() {
    }

    public CovidModel getCovidModel() {
        return covidModel;
    }

    public void setCovidModel(CovidModel covidModel) {
        this.covidModel = covidModel;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "ResultModel{" +
                "covidModel=" + covidModel +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
