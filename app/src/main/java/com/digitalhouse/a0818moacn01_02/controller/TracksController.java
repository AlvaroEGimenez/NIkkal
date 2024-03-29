package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.DAO.TrackDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.Track;

import java.util.ArrayList;
import java.util.List;

public class TracksController {
    public void getPistas(final ResultListener<List<Track>> listenerView, Context context, Integer trackId) {
        if (Util.hayInternet(context)) {
            TrackDAO trackDAO = new TrackDAO();
            trackDAO.getTracks(new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    listenerView.finish(resultado);
                }
            }, trackId);
        } else {
            listenerView.finish(new ArrayList<Track>());
        }
    }

    public void getPista(final ResultListener<Track> listenerView, Context context, Integer trackId) {
        if (Util.hayInternet(context)) {
            TrackDAO trackDAO = new TrackDAO();
            trackDAO.getTrack(new ResultListener<Track>() {
                @Override
                public void finish(Track resultado) {
                    listenerView.finish(resultado);
                }
            }, trackId);

        } else {
            listenerView.finish(new Track());
        }
    }
}