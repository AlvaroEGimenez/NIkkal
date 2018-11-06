package com.digitalhouse.a0818moacn01_02.service;

import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorArtists;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorRadio;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorTrackList;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorTracks;
import com.digitalhouse.a0818moacn01_02.model.Tracks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceRadioTrackList {
    @GET("radio/{trackListId}/tracks")
    Call<ContenedorTrackList> getTrackListRadio(@Path(value = "trackListId",encoded = true) Integer idGenre);
}
