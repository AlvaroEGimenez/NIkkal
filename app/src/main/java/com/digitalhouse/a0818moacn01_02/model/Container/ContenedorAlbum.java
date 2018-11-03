package com.digitalhouse.a0818moacn01_02.model.Container;

import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContenedorAlbum {
    @SerializedName("data")
    private List<AlbumDeezer> albumDeezerList;

    public List<AlbumDeezer> getAlbumDeezerList() {
        return albumDeezerList;
    }
}
