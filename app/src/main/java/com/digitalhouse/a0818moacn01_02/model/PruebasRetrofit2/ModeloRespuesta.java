package com.digitalhouse.a0818moacn01_02.model.PruebasRetrofit2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModeloRespuesta {
    @SerializedName("data")
    @Expose
    private List<Track> trackList = null;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<Track> getTrackList() {
        return trackList;
    }


    public Integer getTotal() {
        return total;
    }


}
