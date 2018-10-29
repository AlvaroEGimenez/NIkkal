package com.digitalhouse.a0818moacn01_02.model;

public class Busqueda {
    private String busqueda;
    private String mp3;
    private String artista;

    public Busqueda(String busqueda, String mp3, String artista) {
        this.busqueda = busqueda;
        this.mp3 = mp3;
        this.artista = artista;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public String getMp3() {
        return mp3;
    }

    public String getArtista() {
        return artista;
    }
}
