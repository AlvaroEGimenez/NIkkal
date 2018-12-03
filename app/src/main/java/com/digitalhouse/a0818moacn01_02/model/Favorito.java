package com.digitalhouse.a0818moacn01_02.model;


public class Favorito {
    private Integer id;
    private String uid;

    public  Favorito(){}

    public Favorito(Integer id, String uid) {
        this.id = id;
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
