package com.digitalhouse.a0818moacn01_02.model;

public class Pista {
    private String nombre;
    private Integer duracion;
    private Boolean favorito;
    private Album album;

    public Pista(String nombre, Integer duracion, Boolean favorito, Album album) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.favorito = favorito;
        this.album = album;
    }

    public Pista(String nombre, Integer duracion, Boolean favorito) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.favorito = favorito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
