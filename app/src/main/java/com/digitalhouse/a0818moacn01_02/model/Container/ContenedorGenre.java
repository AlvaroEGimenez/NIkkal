package com.digitalhouse.a0818moacn01_02.model.Container;

import com.digitalhouse.a0818moacn01_02.model.Genre;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContenedorGenre {
    @SerializedName("data")
    private List<Genre> listaGeneros;

    public List<Genre> getListaGeneros() {
        return listaGeneros;
    }
}
