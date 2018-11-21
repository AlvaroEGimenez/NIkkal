package com.digitalhouse.a0818moacn01_02.model;

import java.util.ArrayList;
import java.util.List;

public class ListaReproduccion {
    private List<Track> pistas;
    private String nombre;

    public void agregarPista(Track pista){
        if(this.pistas == null){
            this.pistas = new ArrayList<>();
        }
        this.pistas.add(pista);
    }

    public List<Track> getPistas() {
        return pistas;
    }

    public void setPistas(List<Track> pistas) {
        this.pistas = pistas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
