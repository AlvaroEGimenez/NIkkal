package com.digitalhouse.a0818moacn01_02.model;

import java.util.List;

public class Favoritos  {

    private String favoritoTexto;
    private Integer favoritoImagen;
    private String favoritoTextoSubtitulo;

    /*private String favoritoAlbum;
    private String favoritoArtista;
    private String favoritoPista;
    private Integer favoritoImagen;
    */
    /*private List<Album> albumesFavoritos;
    private List<Artista> artistasFavoritos;
    private List<Pista> pistasFavoritas;*/

    public Favoritos(String favoritoTexto, Integer favoritoImagen, String favoritoTextoSubtitulo) {
        this.favoritoTexto = favoritoTexto;
        this.favoritoImagen = favoritoImagen;
        this.favoritoTextoSubtitulo = favoritoTextoSubtitulo;
    }

    public String getFavoritoTexto() {
        return favoritoTexto;
    }

    public Integer getFavoritoImagen() {
        return favoritoImagen;
    }

    public String getFavoritoTextoSubtitulo() {
        return favoritoTextoSubtitulo;
    }
}
