package com.digitalhouse.a0818moacn01_02.DAO;

import android.util.Log;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.model.Tracks;
import com.digitalhouse.a0818moacn01_02.service.TrackService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackDAO extends DaoHelper {
    private TrackService trackService;

    public TrackDAO() {
        super();
        trackService = retrofit.create(TrackService.class);
    }

    public void getTracks(final ResultListener<List<Track>> listenerDelController, Integer trackId) {

        Call<Tracks> call = trackService.getTracks(trackId);

        call.enqueue(new Callback<Tracks>() {
            @Override
            public void onResponse(Call<Tracks> call, Response<Tracks> response) {

                Tracks contenedorTracks = response.body();

                List<Track> trackList = contenedorTracks.getTrackList();

                listenerDelController.finish(trackList);
            }

            @Override
            public void onFailure(Call<Tracks> call, Throwable t) {
                Log.e("ERROR", t.toString());
            }
        });
    }



    public void getTrack(final ResultListener<Track> listenerDelController, Integer trackId) {

        Call<Track> call = trackService.getTrack(trackId);

        call.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                Track track = response.body();
                listenerDelController.finish(track);
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Log.e("ERROR", t.toString());
            }
        });
    }

}

