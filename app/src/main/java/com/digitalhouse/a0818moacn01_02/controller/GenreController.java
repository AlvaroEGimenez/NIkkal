package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.DAO.GenreDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.Genre;

import java.util.List;

public class GenreController {

    public void getGenre(final ResultListener<List<Genre>> listenerView, Context context) {
        if (Util.hayInternet(context)) {
            GenreDAO genreDAO = new GenreDAO();
            genreDAO.getGenre(new ResultListener<List<Genre>>() {
                @Override
                public void finish(List<Genre> resultado) {
                    listenerView.finish(resultado);
                }
            });
        }
    }
}
