package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.DAO.SearchDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.Track;

import java.util.ArrayList;
import java.util.List;

public class SearchControlller {

    public void getSearch(final ResultListener<List<Track>> listenerView, Context context, String search) {
        if (Util.hayInternet(context)) {
            SearchDAO searchDAO = new SearchDAO();
            searchDAO.getSearch(new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    listenerView.finish(resultado);
                }
            },search);
        }else{
            listenerView.finish(new ArrayList<Track>());
        }
    }
}
