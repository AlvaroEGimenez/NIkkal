package com.digitalhouse.a0818moacn01_02.model;

public class TopChart {

    private String nombreTrack;
    private String nombreArtista;
    private Integer posicion;
    private String urlImagen;
    private String urlMp3;

    public TopChart(String nombreTrack, String nombreArtista, Integer posicion, String urlImagen, String urlMp3) {
        this.nombreTrack = nombreTrack;
        this.nombreArtista = nombreArtista;
        this.posicion = posicion;
        this.urlImagen = urlImagen;
        this.urlMp3 = urlMp3;
    }

    public String getNombreTrack() {
        return nombreTrack;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getUrlMp3() {
        return urlMp3;
    }
}
