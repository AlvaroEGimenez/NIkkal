package com.digitalhouse.a0818moacn01_02.model.Container;

import com.digitalhouse.a0818moacn01_02.model.Track;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContenedorSearch {
    @SerializedName("data")
    private List<Track> trackList;
    @SerializedName("total")
    private Integer total;
    @SerializedName("next")
    private String urlNext25;

    public List<Track> getTrackList() {
        return trackList;
    }

    public Integer getTotal() {
        return total;
    }

    public String getUrlNext25() {
        return urlNext25;
    }
}
