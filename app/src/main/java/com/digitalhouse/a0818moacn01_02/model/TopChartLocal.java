package com.digitalhouse.a0818moacn01_02.model;

public class TopChartLocal {
    private String nombreTrack;
    private String nombreArtista;
    private String urlImagen;
    private Integer posicion;
    private String urlMp3;
    private Boolean favorito;

    public TopChartLocal(String nombreTrack, String nombreArtista, String urlImagen, Integer posicion, String urlMp3) {
        this.nombreTrack = nombreTrack;
        this.nombreArtista = nombreArtista;
        this.urlImagen = urlImagen;
        this.posicion = posicion;
        this.urlMp3 = urlMp3;
        this.favorito = false;
    }

    public String getNombreTrack() {
        return nombreTrack;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public String getUrlMp3() {
        return urlMp3;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }
}
