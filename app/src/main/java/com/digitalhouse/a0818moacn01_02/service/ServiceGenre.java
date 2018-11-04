package com.digitalhouse.a0818moacn01_02.service;

import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorAlbum;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorArtists;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorGenre;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceGenre {
    @GET("genre")
    Call<ContenedorGenre> getTopChart();

    @GET("genre/{genreId}/artists")
    Call<ContenedorArtists> getGenre(@Path(value = "genreId", encoded = true) Integer idGenre);

    @GET("artist/{artistId}/albums")
    Call<ContenedorAlbum> getArtistAlbum(@Path(value = "artistId", encoded = true) Integer idArtist);
}
