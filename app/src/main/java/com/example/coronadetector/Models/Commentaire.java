package com.example.coronadetector.Models;

public class Commentaire {

    int idc;
    String comment;
    int userid;
    int idp;

    public Commentaire(String comment, int userid, int idp) {
        this.comment = comment;
        this.userid = userid;
        this.idp = idp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }
}
