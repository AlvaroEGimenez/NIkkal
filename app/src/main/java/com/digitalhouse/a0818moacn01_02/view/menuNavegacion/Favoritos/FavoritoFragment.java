package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Favoritos;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritoFragment extends Fragment {


    public FavoritoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorito, container, false);

        // Inflate the layout for this fragment
        //todo esto iría en una clase recycler que despues se comunica a traves del adapter con el fragment y el fragment con la activity :S
        List<Favoritos> listaDeFavoritos = new ArrayList<>();
        listaDeFavoritos.add(new Favoritos(getResources().getString(R.string.tv_titulo_escuchadas_recientemente),R.drawable.fondo_pista,getResources().getString(R.string.tv_subtitulo_escuchadas_recientemente)));
        listaDeFavoritos.add(new Favoritos(getResources().getString(R.string.tv_titulo_escuchadas_recientemente),R.drawable.fondo_pista,getResources().getString(R.string.tv_subtitulo_escuchadas_recientemente)));
        listaDeFavoritos.add(new Favoritos(getResources().getString(R.string.tv_titulo_escuchadas_recientemente),R.drawable.fondo_pista,getResources().getString(R.string.tv_subtitulo_escuchadas_recientemente)));
        listaDeFavoritos.add(new Favoritos(getResources().getString(R.string.tv_titulo_escuchadas_recientemente),R.drawable.fondo_pista,getResources().getString(R.string.tv_subtitulo_escuchadas_recientemente)));
        listaDeFavoritos.add(new Favoritos(getResources().getString(R.string.tv_titulo_escuchadas_recientemente),R.drawable.fondo_pista,getResources().getString(R.string.tv_subtitulo_escuchadas_recientemente)));
        listaDeFavoritos.add(new Favoritos(getResources().getString(R.string.tv_titulo_escuchadas_recientemente),R.drawable.fondo_pista,getResources().getString(R.string.tv_subtitulo_escuchadas_recientemente)));
        listaDeFavoritos.add(new Favoritos(getResources().getString(R.string.tv_titulo_escuchadas_recientemente),R.drawable.fondo_pista,getResources().getString(R.string.tv_subtitulo_escuchadas_recientemente)));
        listaDeFavoritos.add(new Favoritos(getResources().getString(R.string.tv_titulo_escuchadas_recientemente),R.drawable.fondo_pista,getResources().getString(R.string.tv_subtitulo_escuchadas_recientemente)));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerFavoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        AdaptadorFavoritos adaptadorFavoritos = new AdaptadorFavoritos(listaDeFavoritos);
        recyclerView.setAdapter(adaptadorFavoritos);


        return view;

    }

}