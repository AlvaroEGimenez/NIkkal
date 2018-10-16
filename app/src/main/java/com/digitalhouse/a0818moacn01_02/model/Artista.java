package com.digitalhouse.a0818moacn01_02.model;

public class Artista {
    private String imagen;
    private String nombre;
    private String  genero;
    private Integer seguidores;

    public Artista(String imagen, String nombre, String genero, Integer seguidores) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.genero = genero;
        this.seguidores = seguidores;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(Integer seguidores) {
        this.seguidores = seguidores;
    }
}
