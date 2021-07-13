package com.example.coronadetector.Models;


public class Publication {

    String idp;
    String description;
    String usermail;
    String userid;
    String usernamep;

        public Publication(String idp, String description, String usermail) {
            this.idp = idp;
            this.description = description;
            this.usermail = usermail;
        }

    public Publication(String idp, String description, String usermail, String userid) {
        this.idp = idp;
        this.description = description;
        this.usermail = usermail;
        this.userid = userid;
    }

    public Publication(String idp, String description, String usermail, String userid, String username) {
        this.idp = idp;
        this.description = description;
        this.usermail = usermail;
        this.userid = userid;
        this.usernamep = username;
    }

    public String getUsername() {
        return usernamep;
    }

    public void setUsername(String username) {
        this.usernamep = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "idp='" + idp + '\'' +
                ", description='" + description + '\'' +
                ", usermail='" + usermail + '\'' +
                ", userid='" + userid + '\'' +
                ", usernamep='" + usernamep + '\'' +
                '}';
    }

    public String getIdp() {
            return idp;
        }

        public void setIdp(String idp) {
            this.idp = idp;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUsermail() {
            return usermail;
        }

        public void setUsermail(String usermail) {
            this.usermail = usermail;
        }
    }
