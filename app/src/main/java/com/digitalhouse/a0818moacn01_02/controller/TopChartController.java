package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.DAO.TopChartDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.Track;

import java.util.ArrayList;
import java.util.List;

public class TopChartController {

    public void getTraks (final ResultListener<List<Track>> listenerView, Context context){
        if (Util.hayInternet(context)){
            TopChartDAO topChartDAO = new TopChartDAO();
            topChartDAO.getTracks(new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    listenerView.finish(resultado);
                }
            });
        }else{
            ArrayList<Track> pistas = new ArrayList<>();
            pistas.add(new Track());
            listenerView.finish(pistas);
        }
    }


}
