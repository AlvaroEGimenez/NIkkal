package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.DAO.ArtistGenreDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;

import java.util.List;

public class ArtistController {

    public void getArtist(final ResultListener<List<ArtistDeezer>> listenerView, Context context, Integer genreId) {
        if (Util.hayInternet(context)) {
            ArtistGenreDAO artistGenreDAO = new ArtistGenreDAO();
            artistGenreDAO.getArtist(new ResultListener<List<ArtistDeezer>>() {
                @Override
                public void finish(List<ArtistDeezer> resultado) {
                    listenerView.finish(resultado);
                }
            }, genreId);

        }
    }
}
