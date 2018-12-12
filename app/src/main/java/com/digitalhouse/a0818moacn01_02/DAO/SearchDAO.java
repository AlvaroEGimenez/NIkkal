package com.digitalhouse.a0818moacn01_02.DAO;

import android.util.Log;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorSearch;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.service.ServiceSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDAO extends DaoHelper {
    private ServiceSearch serviceSearch;

    public SearchDAO() {
        super();
        serviceSearch = retrofit.create(ServiceSearch.class);
    }

    public void getSearch(final ResultListener<List<Track>> listenerDelController, String search) {
        Call<ContenedorSearch> call = serviceSearch.getSearch(search);
        call.enqueue(new Callback<ContenedorSearch>() {
            @Override
            public void onResponse(Call<ContenedorSearch> call, Response<ContenedorSearch> response) {
                ContenedorSearch contenedorTracks = response.body();

                List<Track> trackListSeach = contenedorTracks.getTrackList();

                listenerDelController.finish(trackListSeach);

            }

            @Override
            public void onFailure(Call<ContenedorSearch> call, Throwable t) {
                Log.e("ERROR", t.toString());

            }
        });
    }
}
