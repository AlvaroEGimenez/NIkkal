package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.DAO.TopChartAlbumsDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;

import java.util.ArrayList;
import java.util.List;

public class TopChartAlbumsController {

    public void getTopChartAlbums(final ResultListener<List<AlbumDeezer>> listenerView, Context context) {
        if (Util.hayInternet(context)) {
            TopChartAlbumsDAO topChartAlbumsDAO = new TopChartAlbumsDAO();
            topChartAlbumsDAO.getTopChartAlbums(new ResultListener<List<AlbumDeezer>>() {
                @Override
                public void finish(List<AlbumDeezer> resultado) {
                    listenerView.finish(resultado);
                }
            });
        } else {
            listenerView.finish(new ArrayList<AlbumDeezer>());
        }
    }
}
