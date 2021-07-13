package com.example.coronadetector.map.Model;

public class Cases {

    private String New ;
    private int active;
    private int critical;
    private int recovered;
    private int Total;

    public Cases(String aNew, int active,  int recovered, int total) {
        New = aNew;
        this.active = active;
        this.recovered = recovered;
        Total = total;
    }

    public Cases() {

    }

    public String getNew() {
        return New;
    }

    public void setNew(String aNew) {
        New = aNew;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    @Override
    public String toString() {
        return "Cases{" +
                "New=" + New +
                ", active=" + active +
                ", critical=" + critical +
                ", recovered=" + recovered +
                ", Total=" + Total +
                '}';
    }
}
