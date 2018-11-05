package com.digitalhouse.a0818moacn01_02.DAO;



import android.util.Log;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorRadio;
import com.digitalhouse.a0818moacn01_02.model.RadioDeezer;
import com.digitalhouse.a0818moacn01_02.service.ServiceRadio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RadioDAO extends DaoHelper {
    private ServiceRadio serviceRadio;

    public RadioDAO() {
        super();
        serviceRadio = retrofit.create(ServiceRadio.class);
    }

    public void  getRadios(final ResultListener<List<RadioDeezer>> listenerDelController){
        Call<ContenedorRadio> call = serviceRadio.getRadioList();

        call.enqueue(new Callback<ContenedorRadio>() {
            @Override
            public void onResponse(Call<ContenedorRadio> call, Response<ContenedorRadio> response) {
                ContenedorRadio contenedorRadio = response.body();
                List<RadioDeezer> radioDeezers = contenedorRadio.getRadioDeezerList();
                listenerDelController.finish(radioDeezers);
            }

            @Override
            public void onFailure(Call<ContenedorRadio> call, Throwable t) {
                Log.e("ERROR", t.toString());
            }
        });
    }

}
