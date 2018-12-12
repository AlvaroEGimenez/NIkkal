package com.digitalhouse.a0818moacn01_02.DAO;

import android.util.Log;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorAlbum;
import com.digitalhouse.a0818moacn01_02.service.ServiceTopChartAlbums;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopChartAlbumsDAO extends DaoHelper {
    ServiceTopChartAlbums serviceTopChartAlbums;

    public TopChartAlbumsDAO() {
        super();
        serviceTopChartAlbums = retrofit.create(ServiceTopChartAlbums.class);
    }

    public void getTopChartAlbums(final ResultListener<List<AlbumDeezer>> listenerDelController) {
        Call<ContenedorAlbum> call = serviceTopChartAlbums.getTopCharAlbum();

        call.enqueue(new Callback<ContenedorAlbum>() {
            @Override
            public void onResponse(Call<ContenedorAlbum> call, Response<ContenedorAlbum> response) {
                ContenedorAlbum contenedorTracks = response.body();
                List<AlbumDeezer> albumDeezerList = contenedorTracks.getAlbumDeezerList();
                listenerDelController.finish(albumDeezerList);
            }

            @Override
            public void onFailure(Call<ContenedorAlbum> call, Throwable t) {
                Log.e("ERROR", t.toString());
            }
        });
    }
}
