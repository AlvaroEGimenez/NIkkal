package com.digitalhouse.a0818moacn01_02.model;


public class Favorito {
    private Integer id;
    private String uid;
    private String urlImagen;

    public  Favorito(){}

    public Favorito(Integer id, String uid, String urlImagen) {
        this.id = id;
        this.uid = uid;
        this.urlImagen = urlImagen;
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

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
