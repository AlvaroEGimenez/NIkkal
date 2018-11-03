package com.digitalhouse.a0818moacn01_02.model.Container;

import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContenedorArtists {
    @SerializedName("data")
    private List<ArtistDeezer> artistDeezers;

    public List<ArtistDeezer> getArtistDeezers() {
        return artistDeezers;
    }
}
