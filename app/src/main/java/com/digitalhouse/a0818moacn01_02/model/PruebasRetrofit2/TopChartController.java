package com.digitalhouse.a0818moacn01_02.model.PruebasRetrofit2;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.Utils.Util;

import java.util.List;

public class TopChartController {

    public void getTraks (final ResultListener<List<Track>> listenerView, Context context){
        if (Util.hayInternet(context)){
            TopChartDAO topChartDAO = new TopChartDAO();
            topChartDAO.getTopChart(new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    listenerView.finish(resultado);
                }
            });

        }
    }
}
