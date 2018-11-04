package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.DAO.GenreDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.model.Genre;

import java.util.List;

public class GenreController {
    private GenreDAO genreDAO = new GenreDAO();

    public void getGenre(final ResultListener<List<Genre>> listenerView, Context context) {
        if (Util.hayInternet(context)) {
            genreDAO.getGenre(new ResultListener<List<Genre>>() {
                @Override
                public void finish(List<Genre> resultado) {
                    listenerView.finish(resultado);
                }
            });

        }
    }

    public void getArtistAlbum(final ResultListener<List<AlbumDeezer>> listenerView, Context context, Integer artistId) {
        if (Util.hayInternet(context)) {
            genreDAO.getArtistAlbum(new ResultListener<List<AlbumDeezer>>() {
                @Override
                public void finish(List<AlbumDeezer> resultado) {
                    listenerView.finish(resultado);

                }
            }, artistId);

        }
    }

    public void getArtist(final ResultListener<List<ArtistDeezer>> listenerView, Context context, Integer genreId) {
        if (Util.hayInternet(context)) {
            genreDAO.getArtist(new ResultListener<List<ArtistDeezer>>() {
                @Override
                public void finish(List<ArtistDeezer> resultado) {
                    listenerView.finish(resultado);
                }
            }, genreId);

        }
    }
}
