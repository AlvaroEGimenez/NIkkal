package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.FavoritoFirebase;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.Favorito;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisArtistasFragment extends Fragment {
    private FavoritoFirebase favoritoFirebase;
    private List<Favorito> c;

    public MisArtistasFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mis_artistas, container, false);
        favoritoFirebase = new FavoritoFirebase(FavoritoFirebase.KEY_TIPO_ARTISTA);
        getDatosFirebase();

        return view;
    }

    private void getDatosFirebase() {
        favoritoFirebase.getLista(new ResultListener<List<Favorito>>() {
            @Override
            public void finish(List<Favorito> resultado) {

            }
        });
    }

}
