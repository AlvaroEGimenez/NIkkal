package com.digitalhouse.a0818moacn01_02.model.PruebasRetrofit2.Controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.DAO.RockDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;

import java.util.List;

public class RockController {

    public void getArtistasRock (final ResultListener<List<ArtistDeezer>> listenerView, Context context){
        if (Util.hayInternet(context)){
            RockDAO rockDAO = new RockDAO();
            rockDAO.getTracks(new ResultListener<List<ArtistDeezer>>() {
                @Override
                public void finish(List<ArtistDeezer> resultado) {
                    listenerView.finish(resultado);
                }
            });

        }
    }
}
