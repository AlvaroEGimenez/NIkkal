package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.DAO.TrackListDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackListController {

    public void getTraksList(final ResultListener<List<Track>> listenerView, Context context, Integer tracklistId) {
        if (Util.hayInternet(context)) {
            TrackListDAO trackListDAO = new TrackListDAO();
            trackListDAO.getTrackList(new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    listenerView.finish(resultado);
                }
            }, tracklistId);

        } else {
            listenerView.finish(new ArrayList<Track>());
        }
    }
}
