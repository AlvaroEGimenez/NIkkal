package com.digitalhouse.a0818moacn01_02.controller;

import android.content.Context;

import com.digitalhouse.a0818moacn01_02.DAO.RadioDAO;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.RadioDeezer;

import java.util.List;

public class RadioController {

    public void getRadios(final ResultListener <List<RadioDeezer>> listenerDelController, Context context){
        if (Util.hayInternet(context)){
            RadioDAO radioDAO = new RadioDAO();
            radioDAO.getRadios(new ResultListener<List<RadioDeezer>>() {
                @Override
                public void finish(List<RadioDeezer> resultado) {
                    listenerDelController.finish(resultado);
                }
            });
        }
    }
}
