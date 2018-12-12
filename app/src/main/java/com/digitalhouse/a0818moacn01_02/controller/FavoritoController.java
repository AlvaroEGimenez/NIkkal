package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.DAO.FavoritoFirebaseDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.Favorito;

import java.util.List;

public class FavoritoController {
    public static final String PATH_LIST_FAVORITO = "favorito";
    public static final String KEY_TIPO_PISTA = "pista";
    public static final String KEY_TIPO_ALBUM = "album";
    public static final String KEY_TIPO_ARTISTA = "artista";

    private String tipo;
    private Context context;

    public FavoritoController(String tipo, Context context) {
        this.tipo = tipo;
        this.context = context;
    }

    FavoritoFirebaseDAO daoFavorito = new FavoritoFirebaseDAO();

    public void agregar(Integer id, String urlImagen, String titulo) {
        if (Util.hayInternet(context)) {
            daoFavorito.agregar(id, urlImagen, titulo, tipo);
            // Room
        }
    }

    public void eliminar(final Integer id) {
        if (Util.hayInternet(context)) {
            daoFavorito.eliminar(id, tipo);
            // room
        }

    }

    public void getLista(final ResultListener<List<Favorito>> resultListener) {
        daoFavorito.getLista(new ResultListener<List<Favorito>>() {
            @Override
            public void finish(List<Favorito> resultado) {
                resultListener.finish(resultado);
            }
        }, tipo);
    }

    public void getFavoritoPorId(final ResultListener<Favorito> listener, final Integer id) {
        daoFavorito.getFavoritoPorId(new ResultListener<Favorito>() {
            @Override
            public void finish(Favorito Resultado) {

            }
        }, id, tipo);

    }
}
