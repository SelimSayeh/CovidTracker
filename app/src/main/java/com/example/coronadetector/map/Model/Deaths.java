package com.example.coronadetector.map.Model;

import androidx.annotation.Nullable;

public class Deaths {

    private String New;
    private int Total;

    public Deaths(@Nullable String aNew, @Nullable int total) {
        New = aNew;
        Total = total;
    }

    public Deaths() {

    }

    public String getNew() {
        return New;
    }

    public void setNew(String aNew) {
        New = aNew;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    @Override
    public String toString() {
        return "Deaths{" +
                "New=" + New +
                ", Total=" + Total +
                '}';
    }
}
