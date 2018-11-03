package com.digitalhouse.a0818moacn01_02.service;

import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorArtists;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorGenre;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorRock;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ServiceArtistGenre {
    @GET("genre/{genreId}/artists")
    Call<ContenedorArtists> getGenre(@Path(value = "genreId",encoded = true) Integer idGenre);
}

//https://api.deezer.com/artist/27/albums
