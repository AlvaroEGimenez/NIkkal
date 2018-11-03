package com.digitalhouse.a0818moacn01_02.service;

import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorRock;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceArtistasRock {
    @GET("genre/152/artists")
    Call<ContenedorRock> getTopChart();
}
