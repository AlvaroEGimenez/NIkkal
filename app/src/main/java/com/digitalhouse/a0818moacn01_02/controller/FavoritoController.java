package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.DAO.FavoritoFirebaseDAO;
import com.digitalhouse.a0818moacn01_02.DAO.database.DatabaseHelper;
import com.digitalhouse.a0818moacn01_02.DAO.database.FavoritoRoomDAO;
import com.digitalhouse.a0818moacn01_02.DAO.database.MyDatabaseRoom;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.Favorito;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class FavoritoController {
    public static final String PATH_LIST_FAVORITO = "favorito";
    public static final String KEY_TIPO_PISTA = "pista";
    public static final String KEY_TIPO_ALBUM = "album";
    public static final String KEY_TIPO_ARTISTA = "artista";

    private String tipo;
    private Context context;
    private FavoritoRoomDAO daoRoom;
    private FirebaseUser currentUser;

    public FavoritoController(String tipo, Context context) {
        this.tipo = tipo;
        this.context = context;
        MyDatabaseRoom database = DatabaseHelper.getInstance(context.getApplicationContext());
        daoRoom = database.getDao();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    FavoritoFirebaseDAO daoFavorito = new FavoritoFirebaseDAO();

    public void agregar(Integer id, String urlImagen, String titulo) {
        if (Util.hayInternet(context)) {
            Favorito favorito = daoFavorito.agregar(id, urlImagen, titulo, tipo);
            if (favorito != null) {
                daoRoom.agregar(favorito);
            }
        }
    }

    public void eliminar(final Integer id) {
        if (Util.hayInternet(context)) {
            daoFavorito.eliminar(id, tipo);
            Favorito favorito = daoRoom.getFavoritoPorId(currentUser.getUid(), tipo, id);
            daoRoom.eliminar(favorito);
        }

    }

    public void getLista(final ResultListener<List<Favorito>> resultListener) {
        if (Util.hayInternet(context)) {
            daoFavorito.getLista(new ResultListener<List<Favorito>>() {
                @Override
                public void finish(List<Favorito> resultado) {
                    resultListener.finish(resultado);
                }
            }, tipo);
        } else {
            List<Favorito> resultado = daoRoom.getLista(currentUser.getUid(), tipo);
            resultListener.finish(resultado);
        }
    }

    public void getFavoritoPorId(final ResultListener<Favorito> listener, final Integer id) {
        if (Util.hayInternet(context)) {
            daoFavorito.getFavoritoPorId(new ResultListener<Favorito>() {
                @Override
                public void finish(Favorito resultado) {
                    listener.finish(resultado);
                }
            }, id, tipo);
        } else {
            Favorito resultado = daoRoom.getFavoritoPorId(currentUser.getUid(), tipo, id);
            listener.finish(resultado);
        }
    }
}
