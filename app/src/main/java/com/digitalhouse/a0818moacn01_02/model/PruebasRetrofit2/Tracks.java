package com.digitalhouse.a0818moacn01_02.model.PruebasRetrofit2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class Tracks {
    @SerializedName("data")
    private List<Track> trackList;

    @SerializedName("total")
    private Integer total;

    public List<Track> getTrackList() {
        return trackList;
    }

    public Integer getTotal() {
        return total;
    }
}
