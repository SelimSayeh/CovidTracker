package com.example.coronadetector.stat.Model;

public class TableItem {
  private String country;
  private int totalCases;
  private int newCases;
  private int totalDeath;
  private int newDeath;
  private int totalRecovered;
  private int activeCases;
  private int criticCases;

    public TableItem(String country, int totalCases, int newCases, int totalDeath, int newDeath, int totalRecovered, int activeCases, int criticCases) {
        this.country = country;
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.totalDeath = totalDeath;
        this.newDeath = newDeath;
        this.totalRecovered = totalRecovered;
        this.activeCases = activeCases;
        this.criticCases = criticCases;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    public int getTotalDeath() {
        return totalDeath;
    }

    public void setTotalDeath(int totalDeath) {
        this.totalDeath = totalDeath;
    }

    public int getNewDeath() {
        return newDeath;
    }

    public void setNewDeath(int newDeath) {
        this.newDeath = newDeath;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public int getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(int activeCases) {
        this.activeCases = activeCases;
    }

    public int getCriticCases() {
        return criticCases;
    }

    public void setCriticCases(int criticCases) {
        this.criticCases = criticCases;
    }

    @Override
    public String toString() {
        return "TableItem{" +
                "country='" + country + '\'' +
                ", totalCases=" + totalCases +
                ", newCases=" + newCases +
                ", totalDeath=" + totalDeath +
                ", newDeath=" + newDeath +
                ", totalRecovered=" + totalRecovered +
                ", activeCases=" + activeCases +
                ", criticCases=" + criticCases +
                '}';
    }
}
