package com.digitalhouse.a0818moacn01_02.service;

import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorTracks;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceTopChart {
    @GET("chart")
    Call<ContenedorTracks> getTopChart();
}
