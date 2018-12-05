package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.RadioController;
import com.digitalhouse.a0818moacn01_02.model.RadioDeezer;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.categorias.SugerenciasFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisAlbumsFragment extends Fragment implements AdaptadorFavoritos.RadioFavoritodAdapterInterface {
    private AdaptadorFavoritos adaptadorFavoritos;
    private InterfaceNotificador interfaceNotificador;
    private MainActivity parent;
    private SugerenciasFragment sugerenciasFragment = new SugerenciasFragment();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interfaceNotificador = (InterfaceNotificador) context;
    }

    public MisAlbumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_albums, container, false);

        //instancio adaptador
        adaptadorFavoritos = new AdaptadorFavoritos(new ArrayList<RadioDeezer>(), this);

        // buscamos la View
        RecyclerView recyclerView = view.findViewById(R.id.recyclerMisAlbums);

        //DATOS instanciamos un controller
        RadioController radioController = new RadioController();




        radioController.getRadios(new ResultListener<List<RadioDeezer>>() {
            @Override
            public void finish(List<RadioDeezer> Resultado) {
                adaptadorFavoritos.setRadioDeezerList(Resultado);

            }
        }, this.getContext());


        //performance
        recyclerView.setHasFixedSize(true);

        //layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adaptadorFavoritos);

        //traigo la actividad que contiene este fragment

        parent = (MainActivity) getActivity();




        return view;
    }

    @Override
    public void cambiarDeActividad(RadioDeezer radioDeezer) {
        Intent intent = null;
        Bundle bundle = new Bundle();
        bundle.putString(SugerenciasFragment.KEY_IMAGEN_SUGERENCIA, radioDeezer.getPictureBig());
        bundle.putString(SugerenciasFragment.KEY_ID_PLAYLIST_SUGERENCIA, radioDeezer.getId());
        bundle.putString(SugerenciasFragment.KEY_NOMBRE_SUGERENCIA, radioDeezer.getTitle());
        sugerenciasFragment.setArguments(bundle);
        parent.reemplazarFragment(sugerenciasFragment);
        if (intent != null) {
            startActivity(intent);
        }

    }


    public interface InterfaceNotificador{
        public void notificar(RadioDeezer radioDezeer);// todo : aca en lugar de pasar radio tiene que pasar la lista de los albums que el usuario guarda como favoritos
    }


}
