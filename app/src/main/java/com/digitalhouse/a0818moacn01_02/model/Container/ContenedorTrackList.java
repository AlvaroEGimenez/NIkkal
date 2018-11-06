package com.digitalhouse.a0818moacn01_02.model.Container;

import com.digitalhouse.a0818moacn01_02.model.Track;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContenedorTrackList {
    @SerializedName("data")
    public List<Track> trackList;

    public List<Track> getTrackList() {
        return trackList;
    }
}
