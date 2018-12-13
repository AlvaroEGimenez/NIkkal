package com.digitalhouse.a0818moacn01_02.model;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;


@Entity(tableName = "Favoritos", primaryKeys = {"id", "uidUsuario", "tipo"})
public class Favorito {
    @NonNull
    private Integer id;
    private String uid;
    private String urlImagen;
    private String titulo;
    @NonNull
    private String uidUsuario;
    @NonNull
    private String tipo;

    public Favorito() {
    }

    public Favorito(Integer id, String uid, String urlImagen, String titulo, String uidUsuario, String tipo) {
        this.id = id;
        this.uid = uid;
        this.urlImagen = urlImagen;
        this.titulo = titulo;
        this.uidUsuario = uidUsuario;
        this.tipo = tipo;
    }

    public Favorito(Integer id, String uid, String urlImagen, String titulo) {
        this.id = id;
        this.uid = uid;
        this.urlImagen = urlImagen;
        this.titulo = titulo;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
