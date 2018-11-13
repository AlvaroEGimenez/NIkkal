package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.RadioController;
import com.digitalhouse.a0818moacn01_02.controller.TracksController;
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;
import com.digitalhouse.a0818moacn01_02.model.RadioDeezer;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.categorias.PistaAlbumFragment;
import com.digitalhouse.a0818moacn01_02.view.categorias.SugerenciasFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritoFragment extends Fragment implements AdaptadorFavoritos.RadioFavoritodAdapterInterface { //implements AdaptadorFavoritos.AartistaFavoritodAdapterInterface {
   /* public static final String KEY_IMAGEN_ARTISTA_FAVORITO ="imagentArtistaFavorito";
    public static final String KEY_NOMBRE_ARTISTA_FAVORITO ="nombreArtistaFavorito";
    public static final String KEY_LISTA_ARTISTA_FAVORITO = "listaArtistaFavorito";*/


    private AdaptadorFavoritos adaptadorFavoritos;
    private interfacePasadorDeInformacion notificador;
    private SugerenciasFragment sugerenciasFragment = new SugerenciasFragment();
    private MainActivity parent;
    //private Integer trackId = 3135556;
    //private View view;


    public FavoritoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notificador = (interfacePasadorDeInformacion) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorito, container, false);
        /*this.view = view;
        container = view.findViewById(R.id.rlayoutFavoritosContainer);*/
        //TOOLBAR

        Toolbar toolbar = view.findViewById(R.id.toolbarFavoritos);
        //casteado
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //instancio adaptador
        adaptadorFavoritos = new AdaptadorFavoritos(new ArrayList<RadioDeezer>(), this);

        // buscamos la View
        RecyclerView recyclerView = view.findViewById(R.id.recyclerFavoritos);

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
        //notificador.recibirmensaje(radioDeezer);
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

    /*  @Override
      public void cambiarDeActividad(ArtistDeezer artistDeezer) {
          //casteamos para decirle que es el contexto
          MainActivity mainActivity = (MainActivity) getActivity();
          Bundle bundle = new Bundle();
          bundle.putString();
      }*/

    public interface interfacePasadorDeInformacion {
        public void recibirmensaje(RadioDeezer radioDeezer);


    }


}