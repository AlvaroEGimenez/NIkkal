package com.digitalhouse.a0818moacn01_02.model;

public class Album {
    private String imagen;
    private String nombre;

    public Album(String imagen, String nombre, String cantidadCanciones) {
        this.imagen = imagen;
        this.nombre = nombre;
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

}
