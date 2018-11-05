package com.digitalhouse.a0818moacn01_02.service;

import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorArtists;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceArtistGenre {
    @GET("genre/{genreId}/artists")
    Call<ContenedorArtists> getGenre(@Path(value = "genreId",encoded = true) Integer idGenre);
}

