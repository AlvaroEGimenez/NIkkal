package com.digitalhouse.a0818moacn01_02.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaReproduccion implements Serializable {
    private List<Track> pistas = new ArrayList<>();;
    private String nombre;

    public ListaReproduccion() {
    }
    public ListaReproduccion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Track> getPistas() {
        return pistas;
    }

    public void setPistas(List<Track> pistas) {
        this.pistas = pistas;
    }

}
