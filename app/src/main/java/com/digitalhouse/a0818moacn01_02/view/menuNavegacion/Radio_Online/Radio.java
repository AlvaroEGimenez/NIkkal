package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Radio_Online;


public class Radio {

    private String nombre;
    private String sintonia;
    private String url;


    public Radio(String nombre, String sintonia, String url) {
        this.nombre = nombre;
        this.sintonia = sintonia;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSintonia() {
        return sintonia;
    }

    public String getUrl() {
        return url;
    }

}
