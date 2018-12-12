package com.digitalhouse.a0818moacn01_02.DAO;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorTrackList;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.service.ServiceRadioTrackList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackListDAO extends DaoHelper {
    ServiceRadioTrackList serviceRadioTrackList;

    public TrackListDAO() {
        super();
        serviceRadioTrackList = retrofit.create(ServiceRadioTrackList.class);
    }

    public void getTrackList(final ResultListener<List<Track>> listenerDelController, Integer tracklistId) {
        Call<ContenedorTrackList> call = serviceRadioTrackList.getTrackListRadio(tracklistId);
        call.enqueue(new Callback<ContenedorTrackList>() {
            @Override
            public void onResponse(Call<ContenedorTrackList> call, Response<ContenedorTrackList> response) {
                ContenedorTrackList contenedorTrackList = response.body();
                List<Track> trackList = contenedorTrackList.getTrackList();
                listenerDelController.finish(trackList);
            }

            @Override
            public void onFailure(Call<ContenedorTrackList> call, Throwable t) {
            }
        });
    }
}
