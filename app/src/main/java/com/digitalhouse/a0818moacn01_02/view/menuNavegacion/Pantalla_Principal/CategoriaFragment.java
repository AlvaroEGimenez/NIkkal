package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Pantalla_Principal;


import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.android.volley.RequestQueue;
import com.digitalhouse.a0818moacn01_02.DAO.DAOLocal;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ReproducirMp3;
import com.digitalhouse.a0818moacn01_02.controller.GenreController;
import com.digitalhouse.a0818moacn01_02.model.Album;
import com.digitalhouse.a0818moacn01_02.model.Genre;
import com.digitalhouse.a0818moacn01_02.view.adapter.AdaptadorTopChartDeezer;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.PruebasRetrofit2.Controller.TopChartController;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.model.TopChartLocal;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.AdaptadorTopChart;
import com.digitalhouse.a0818moacn01_02.view.adapter.CategoriaAdapterRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.categorias.GeneroFragment;
import com.digitalhouse.a0818moacn01_02.view.categorias.MasEscuchado;
import com.digitalhouse.a0818moacn01_02.view.categorias.SugerenciaActivity;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class CategoriaFragment extends Fragment implements CategoriaAdapterRecyclerView.AdapterInterface, AdaptadorTopChartDeezer.onItemClickTopChartDeezer, AdaptadorTopChart.onItemClickTopChart {
    public final static String KEY_GENERO = "Géneros";
    public final static String KEY_SUGERENCIA = "Sugerencias";
    public final static String KEY_MAS_ESCUCHADO = "Lo Más Escuchado";
    public final static String KEY_FAVORITO = "Favoritos";

    private RecyclerView recyclerView;

    private GeneroFragment generoFragment = new GeneroFragment();

    private FeatureCoverFlow featureCoverFlow;
    private AdaptadorTopChart adaptadorTopChart;
    private List<Track> topChartListDeezer = new ArrayList<>();
    private List<TopChartLocal> topChartListLocal = new ArrayList<>();

    private TextSwitcher mTitle;

    private View view;
    private TextView tvGeneros;
    private TextView tvSugerencia;
    private TextView tvMasEscuchado;
    private TextView tvFavorito;
    private TextView tvTopChart;

    MediaPlayer mediaPlayer = new MediaPlayer();

    private MainActivity parent;

    private RequestQueue requestQueue;
    private  List<Genre> genreList = new ArrayList<>();

    public CategoriaFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.categoria_album, container, false);
        featureCoverFlow = view.findViewById(R.id.coverFlow);

        this.view = view;
        parent = (MainActivity) getActivity();
        setCategotia();
        cargarGenre();


//        crearRecyclerView(R.id.rvSugerenciaRecyclerView, tvSugerencia.getText().toString());
//        crearRecyclerView(R.id.rvMasEscuchadoRecyclerView, tvMasEscuchado.getText().toString());
//        crearRecyclerView(R.id.rvFavoritoRecyclerView, tvFavorito.getText().toString());

        DAOLocal daoLocal = new DAOLocal();
        daoLocal.ObtenerTopChar(topChartListLocal);

        adaptadorTopChart = new AdaptadorTopChart(topChartListLocal, getContext(), CategoriaFragment.this);
        featureCoverFlow.setAdapter(adaptadorTopChart);

        if (savedInstanceState == null) {
            TopChartController topChartController = new TopChartController();
            topChartController.getTraks(new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    if (resultado.size() > 0) {
                        topChartListDeezer = resultado;
                        AdaptadorTopChartDeezer adaptadorTopChartDeezer = new AdaptadorTopChartDeezer(topChartListDeezer, getContext(), CategoriaFragment.this);

                        featureCoverFlow.setAdapter(adaptadorTopChartDeezer);
                    }

                }
            }, getContext());
        }


        mTitle = view.findViewById(R.id.tituloTopChart);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                TextView txt = (TextView) layoutInflater.inflate(R.layout.layout_title, null);
                return txt;
            }
        });

        Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_bottom);
        mTitle.setAnimation(in);
        mTitle.setOutAnimation(out);


        featureCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                if (topChartListDeezer.isEmpty()) {
                    mTitle.setText(topChartListLocal.get(position).getNombreTrack() + " - " + topChartListLocal.get(position).getNombreArtista());
                } else {
                    mTitle.setText(topChartListDeezer.get(position).getTitle() + " - " + topChartListDeezer.get(position).getArtist().getName());
                }

            }

            @Override
            public void onScrolling() {

            }
        });


        return view;
    }

    private void crearRecyclerViewGenre(Integer idLayout) {
        RecyclerView genreRecyclerView = view.findViewById(idLayout);
        genreRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        genreRecyclerView.setLayoutManager(linearLayoutManager);

        CategoriaAdapterRecyclerView adapter = new CategoriaAdapterRecyclerView(genreList, R.layout.carcdview_categoria, getActivity(), this);

        genreRecyclerView.setAdapter(adapter);
    }


    private void llamarActividad(Genre genre) {
        String genero = genre.getName();
        Intent intent = null;

   /*     switch (genero) {
            case CategoriaFragment.KEY_GENERO:*/
                Bundle bundle = new Bundle();
                bundle.putString(GeneroFragment.KEY_IMAGEN_GENERO, genre.getPictureMedium());
                bundle.putString(GeneroFragment.KEY_NOMBRE_GENERO, genre.getName());
                bundle.putInt(GeneroFragment.KEY_ID_GENERO, genre.getId());

                generoFragment.setArguments(bundle);

                parent.reemplazarFragment(generoFragment);
             /*   break;
            case CategoriaFragment.KEY_SUGERENCIA:
                intent = new Intent(getContext(), SugerenciaActivity.class);
                break;
            case CategoriaFragment.KEY_MAS_ESCUCHADO:
                intent = new Intent(getContext(), MasEscuchado.class);
        }*/

        if (intent != null)
            startActivity(intent);
    }

    private void setCategotia() {

        tvGeneros = view.findViewById(R.id.tvGeneroRecyclerView);
        tvGeneros.setText(R.string.tv_genero);

        tvSugerencia = view.findViewById(R.id.tvSugerenciaRecyclerView);
        tvSugerencia.setText(R.string.tv_sugerencia);

        tvMasEscuchado = view.findViewById(R.id.tvMasEscuchadoRecyclerView);
        tvMasEscuchado.setText(R.string.tv_mas_escuchado);

        tvFavorito = view.findViewById(R.id.tvFavoritoRecyclerView);
        tvFavorito.setText(R.string.tv_favorito);
    }

    private void cargarGenre(){

        GenreController genreController = new GenreController();
        genreController.getGenre(new ResultListener<List<Genre>>() {
            @Override
            public void finish(List<Genre> resultado) {
                genreList =  resultado;
                genreList.remove(0);
                crearRecyclerViewGenre(R.id.rvGeneroRecyclerView);

            }
        }, getContext());

    }



    @Override
    public void onClickTopChartDeezer(Track topChartLocalDeezer) {
        TextView textViewNombrePista = getActivity().findViewById(R.id.tvNombreReproductor);
        textViewNombrePista.setSelected(true);

        ReproducirMp3 reproducirMp3 = new ReproducirMp3();
        reproducirMp3.reproducirMp3(topChartLocalDeezer.getPreview(), mediaPlayer, parent);
        textViewNombrePista.setText(topChartLocalDeezer.getTitle() + " - " + topChartLocalDeezer.getArtist().getName());
        textViewNombrePista.setTextColor(Color.parseColor("#FD9701"));
    }


    @Override
    public void onClickTopChart(TopChartLocal topChartLocal) {
        TextView textViewNombrePista = getActivity().findViewById(R.id.tvNombreReproductor);
        textViewNombrePista.setSelected(true);

        ReproducirMp3 reproducirMp3 = new ReproducirMp3();
        reproducirMp3.reproducirMp3(topChartLocal.getUrlMp3(), mediaPlayer, parent);
        textViewNombrePista.setText(topChartLocal.getNombreTrack() + " - " + topChartLocal.getNombreArtista());
        textViewNombrePista.setTextColor(Color.parseColor("#FD9701"));
    }


    @Override
    public void cambiarDeActividad(Genre Genre) {
        llamarActividad(Genre);
    }
}
