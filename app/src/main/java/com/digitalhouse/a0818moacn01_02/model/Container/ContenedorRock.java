package com.digitalhouse.a0818moacn01_02.model.Container;

import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContenedorRock {
    @SerializedName("data")
    private List<ArtistDeezer> artistRock;

    public List<ArtistDeezer> getArtistRock() {
        return artistRock;
    }
}
