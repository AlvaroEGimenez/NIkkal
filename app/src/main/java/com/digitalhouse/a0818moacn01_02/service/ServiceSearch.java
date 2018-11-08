package com.digitalhouse.a0818moacn01_02.service;

import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ServiceSearch {
    @GET("search")
    Call<ContenedorSearch> getSearch(@Query("q") String search);
}
