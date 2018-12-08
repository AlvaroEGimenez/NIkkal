package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos;


import android.content.Context;
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
import com.digitalhouse.a0818moacn01_02.view.categorias.PistaAlbumFragment;

import java.util.ArrayList;
import java.util.List;

public class MisAlbumsFragment extends Fragment {
    private AdaptadorFavoritos adaptadorFavoritos;
    private InterfaceNotificador interfaceNotificador;
    private MainActivity parent;

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


        // buscamos la View
        RecyclerView recyclerView = view.findViewById(R.id.recyclerMisAlbums);

        //DATOS instanciamos un controller
        RadioController radioController = new RadioController();

        //performance
        recyclerView.setHasFixedSize(true);

        //layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adaptadorFavoritos);

        //traigo la actividad que contiene este fragment

        parent = (MainActivity) getActivity();


        return view;
    }

    public interface InterfaceNotificador {
        public void notificar(RadioDeezer radioDezeer);// todo : aca en lugar de pasar radio tiene que pasar la lista de los albums que el usuario guarda como favoritos
    }


}
