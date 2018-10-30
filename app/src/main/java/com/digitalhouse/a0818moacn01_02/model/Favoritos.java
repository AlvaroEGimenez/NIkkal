package com.digitalhouse.a0818moacn01_02.model;

import java.util.List;

public class Favoritos  {

    private String favoritoTexto;
    private Integer favoritoImagen;

    /*private String favoritoAlbum;
    private String favoritoArtista;
    private String favoritoPista;
    private Integer favoritoImagen;
    */
    /*private List<Album> albumesFavoritos;
    private List<Artista> artistasFavoritos;
    private List<Pista> pistasFavoritas;*/

    public Favoritos(String favoritoTexto, Integer favoritoImagen)/* List<Album> albumesFavoritos, List<Artista> artistasFavoritos, List<Pista> pistasFavoritas*/ {
        this.favoritoTexto = favoritoTexto;
        this.favoritoImagen = favoritoImagen;
        /*this.albumesFavoritos = albumesFavoritos;
        this.artistasFavoritos = artistasFavoritos;
        this.pistasFavoritas = pistasFavoritas;*/
    }

    public String getFavoritoTexto() {
        return favoritoTexto;
    }

    public Integer getFavoritoImagen() {
        return favoritoImagen;
    }

    /*public List<Album> getAlbumesFavoritos() {
        return albumesFavoritos;
    }

    public List<Artista> getArtistasFavoritos() {
        return artistasFavoritos;
    }

    public List<Pista> getPistasFavoritas() {
        return pistasFavoritas;
    }*/
}
