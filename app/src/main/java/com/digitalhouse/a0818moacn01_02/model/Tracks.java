package com.digitalhouse.a0818moacn01_02.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tracks {
    @SerializedName("data")
    public List<Track> trackList;

    @SerializedName("total")
    private Integer total;

    public List<Track> getTrackList() {
        return trackList;
    }

    public Integer getTotal() {
        return total;
    }
}
