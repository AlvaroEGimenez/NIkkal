package com.digitalhouse.a0818moacn01_02.service;

import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorAlbum;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceArtistAlbum {


    @GET("artist/{artistId}/albums")
    Call<ContenedorAlbum> getArtistAlbum(@Path(value = "artistId", encoded = true) Integer idArtist);


}
