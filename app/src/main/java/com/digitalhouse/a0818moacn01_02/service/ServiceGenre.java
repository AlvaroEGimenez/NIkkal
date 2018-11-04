package com.digitalhouse.a0818moacn01_02.service;

import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorGenre;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceGenre {
    @GET("genre")
    Call<ContenedorGenre> getTopChart();
}
