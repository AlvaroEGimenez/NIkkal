package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.DAO.ArtistAlbumDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;

import java.util.List;

public class ArtistAlbumControler {

    public void getArtistAlbum(final ResultListener<List<AlbumDeezer>> listenerView, Context context, Integer artistId) {
        if (Util.hayInternet(context)) {
            ArtistAlbumDAO artistAlbumDAO = new ArtistAlbumDAO();
            artistAlbumDAO.getArtistAlbum(new ResultListener<List<AlbumDeezer>>() {
                @Override
                public void finish(List<AlbumDeezer> resultado) {
                    listenerView.finish(resultado);

                }
            }, artistId);


        }
    }
}
