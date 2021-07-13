package com.example.coronadetector.map.Model;

public class CovidModel {

    private String id;
    private Cases cases;
    private Deaths deaths;
    private String day;
    private String time;

    public CovidModel(String id, Cases cases, Deaths deaths, String day, String time) {
        this.id = id;
        this.cases = cases;
        this.deaths = deaths;
        this.day = day;
        this.time = time;
    }

    public CovidModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public Deaths getDeaths() {
        return deaths;
    }

    public void setDeaths(Deaths deaths) {
        this.deaths = deaths;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CovidModel{" +
                "id='" + id + '\'' +
                ", cases=" + cases +
                ", deaths=" + deaths +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
