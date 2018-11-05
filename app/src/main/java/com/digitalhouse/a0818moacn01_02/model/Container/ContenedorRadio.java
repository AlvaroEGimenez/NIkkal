package com.digitalhouse.a0818moacn01_02.model.Container;

import com.digitalhouse.a0818moacn01_02.model.RadioDeezer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContenedorRadio {

    @SerializedName("data")
    private List<RadioDeezer> radioDeezerList;

    public List<RadioDeezer> getRadioDeezerList() {
        return radioDeezerList;
    }
}
