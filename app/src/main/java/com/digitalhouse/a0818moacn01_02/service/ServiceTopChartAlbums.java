package com.digitalhouse.a0818moacn01_02.service;

import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorAlbum;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceTopChartAlbums {
    @GET("chart/playlists/albums")
    Call<ContenedorAlbum> getTopCharAlbum();
}
