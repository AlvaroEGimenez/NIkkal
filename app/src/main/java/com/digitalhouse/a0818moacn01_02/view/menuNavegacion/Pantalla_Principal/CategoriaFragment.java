package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Pantalla_Principal;


import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhouse.a0818moacn01_02.DAO.DAOLocal;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.controller.GenreController;
import com.digitalhouse.a0818moacn01_02.controller.RadioController;
import com.digitalhouse.a0818moacn01_02.controller.TopChartAlbumsController;
import com.digitalhouse.a0818moacn01_02.controller.TopChartController;
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;
import com.digitalhouse.a0818moacn01_02.model.Genre;
import com.digitalhouse.a0818moacn01_02.model.RadioDeezer;
import com.digitalhouse.a0818moacn01_02.model.TopChartLocal;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.AdaptadorLocalTopChart;
import com.digitalhouse.a0818moacn01_02.view.adapter.AdaptadorTopChartDeezer;
import com.digitalhouse.a0818moacn01_02.view.adapter.CategoriaAdapterRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.adapter.MasEscuchadosRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.adapter.RadioAdapterRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.categorias.GeneroFragment;
import com.digitalhouse.a0818moacn01_02.view.categorias.PistaAlbumFragment;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class CategoriaFragment extends Fragment implements CategoriaAdapterRecyclerView.AdapterInterface, AdaptadorTopChartDeezer.onItemClickTopChartDeezer, RadioAdapterRecyclerView.AdapterInterface,
        MasEscuchadosRecyclerView.OnclickMasEscuchados {


    private GeneroFragment generoFragment = new GeneroFragment();
    private FeatureCoverFlow featureCoverFlow;
    private List<RadioDeezer> radioDeezerList = new ArrayList<>();
    private List<Track> topChartDeezerList = new ArrayList<>();
    private List<AlbumDeezer> albumDeezerList = new ArrayList<>();
    private TextSwitcher mTitle;
    private View view;
    private ProgressBar pbCategoria;
    private LinearLayout conatiner;
    private MediaPlayer mediaPlayer;
    private MainActivity parent;
    private List<Genre> genreList = new ArrayList<>();
    private AdaptadorTopChartDeezer adaptadorTopChartDeezer;
    private PistaAlbumFragment pistaAlbumFragment = new PistaAlbumFragment();

    public CategoriaFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.categoria_album, container, false);
        featureCoverFlow = view.findViewById(R.id.coverFlow);
        pbCategoria = view.findViewById(R.id.pbCategoria);
        conatiner = view.findViewById(R.id.categoriaContainer);

        this.view = view;
        parent = (MainActivity) getActivity();
        mediaPlayer = parent.getMediaPlayer();

        adaptadorTopChartDeezer = new AdaptadorTopChartDeezer(new ArrayList<Track>(), getContext(), this);
        DAOLocal daoLocal = new DAOLocal();
        final List<TopChartLocal> topChartLocal = new ArrayList<>();
        daoLocal.ObtenerTopChar(topChartLocal);

        AdaptadorLocalTopChart adaptadorLocalTopChart = new AdaptadorLocalTopChart(topChartLocal, getContext());
       featureCoverFlow.setAdapter(adaptadorLocalTopChart);

        setCategotia();
        cargarGenre();
        cargarRadios();
        cargarTopChartAlbums();


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

        TopChartController topChartController = new TopChartController();
        topChartController.getTraks(new ResultListener<List<Track>>() {
            @Override
            public void finish(final List<Track> resultado) {
                adaptadorTopChartDeezer.setTopChartList(resultado);
                featureCoverFlow.setAdapter(adaptadorTopChartDeezer);
                topChartDeezerList = resultado;

                featureCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
                    @Override
                    public void onScrolledToPosition(int position) {
                        if(resultado.get(position).getTitle() != null && resultado.get(position).getArtist() != null)
                        mTitle.setText(resultado.get(position).getTitle() + " - " + resultado.get(position).getArtist().getName());
                    }

                    @Override
                    public void onScrolling() {

                    }
                });

            }
        }, getContext());

        return view;
    }

    private void cargarTopChartAlbums() {
        TopChartAlbumsController topChartAlbumsController = new TopChartAlbumsController();
        topChartAlbumsController.getTopChartAlbums(new ResultListener<List<AlbumDeezer>>() {
            @Override
            public void finish(List<AlbumDeezer> resultado) {
                albumDeezerList = resultado;
                crearRecyclerViewMasEscuchados(R.id.rvMasEscuchadoRecyclerView);

            }
        }, getContext());
    }

    private void setCategotia() {
        TextView tvGeneros = view.findViewById(R.id.tvGeneroRecyclerView);
        TextView tvSugerencia = view.findViewById(R.id.tvSugerenciaRecyclerView);
        TextView tvMasEscuchado = view.findViewById(R.id.tvMasEscuchadoRecyclerView);
        if(Util.hayInternet(getContext())) {
            tvGeneros.setText(R.string.tv_genero);
            tvSugerencia.setText(R.string.tv_sugerencia);
            tvMasEscuchado.setText(R.string.tv_mas_escuchado);
        }else{
            tvGeneros.setText(getResources().getString(R.string.modo_sin_coneccion));
        }

    }

    private void cargarGenre() {

        GenreController genreController = new GenreController();
        genreController.getGenre(new ResultListener<List<Genre>>() {
            @Override
            public void finish(List<Genre> resultado) {
                genreList = resultado;
                if (!resultado.isEmpty()) {
                    genreList.remove(0);
                }
                crearRecyclerViewGenre(R.id.rvGeneroRecyclerView);
                pbCategoria.setVisibility(View.GONE);
                conatiner.setVisibility(View.VISIBLE);
            }

        }, getContext());

    }

    private void cargarRadios() {
        RadioController radioController = new RadioController();
        radioController.getRadios(new ResultListener<List<RadioDeezer>>() {
            @Override
            public void finish(List<RadioDeezer> resultado) {
                radioDeezerList = resultado;
                crearRecyclerViewRadio(R.id.rvSugerenciaRecyclerView);

            }
        }, getContext());
    }

    private void crearRecyclerViewRadio(Integer idLayout) {
        RecyclerView radioRecyclerView = view.findViewById(idLayout);
        radioRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        radioRecyclerView.setLayoutManager(linearLayoutManager);

        RadioAdapterRecyclerView adapter = new RadioAdapterRecyclerView(radioDeezerList, R.layout.carcdview_categoria, getActivity(), this);

        radioRecyclerView.setAdapter(adapter);

    }

    private void crearRecyclerViewMasEscuchados(Integer idLayout) {
        RecyclerView recyclerViewMasEscuchados = view.findViewById(idLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerViewMasEscuchados.setLayoutManager(linearLayoutManager);
        MasEscuchadosRecyclerView masEscuchadosRecyclerView = new MasEscuchadosRecyclerView(albumDeezerList, this);
        recyclerViewMasEscuchados.setHasFixedSize(true);
        recyclerViewMasEscuchados.setAdapter(masEscuchadosRecyclerView);

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

        Intent intent = null;
        Bundle bundle = new Bundle();
        bundle.putString(GeneroFragment.KEY_IMAGEN_GENERO, genre.getPictureMedium());
        bundle.putString(GeneroFragment.KEY_NOMBRE_GENERO, genre.getName());
        bundle.putInt(GeneroFragment.KEY_ID_GENERO, genre.getId());

        generoFragment.setArguments(bundle);
        parent.reemplazarFragment(generoFragment, R.id.genero_fragment);

        if (intent != null)
            startActivity(intent);
    }

    @Override
    public void onClickTopChartDeezer(Track topChartLocalDeezer, final Integer position) {
        TextView textViewNombrePista = getActivity().findViewById(R.id.tvNombreReproductor);
        textViewNombrePista.setSelected(true);
        mediaPlayer.stop();
        parent.getReproducirMp3().reproducirMp3(topChartDeezerList, position, parent);

        textViewNombrePista.setText(topChartLocalDeezer.getTitle() + " - " + topChartLocalDeezer.getArtist().getName());
        textViewNombrePista.setTextColor(Color.parseColor("#FD9701"));
    }


    @Override
    public void cambiarDeActividadGenero(Genre Genre) {
        llamarActividad(Genre);
    }

    @Override
    public void cambiarDeActividadRadio(RadioDeezer radioDeezer) {
        Bundle bundle = new Bundle();
        bundle.putString(PistaAlbumFragment.KEY_IMAGEN_CABECERA_ALBUM_PISTA, radioDeezer.getPictureBig());
        bundle.putString(PistaAlbumFragment.KEY_NOMBRE_CABECERA_ALBUM_PISTA, radioDeezer.getTitle());
        bundle.putInt(PistaAlbumFragment.KEY_PISTA_ID_ALBUM_PISTA, Integer.parseInt(radioDeezer.getId()));
        bundle.putString(PistaAlbumFragment.KEY_CATEGORIA, "sugerencia");

        pistaAlbumFragment.setArguments(bundle);
        parent.reemplazarFragment(pistaAlbumFragment, R.id.genero_fragment);
    }

    @Override
    public void onClickmasescuchados(AlbumDeezer albumDeezer) {
        cambiarDeActividadMasEscuchados(albumDeezer);


    }

    private void cambiarDeActividadMasEscuchados(AlbumDeezer albumDeezer) {
        Bundle bundle = new Bundle();
        bundle.putString(PistaAlbumFragment.KEY_IMAGEN_CABECERA_ALBUM_PISTA, albumDeezer.getCoverMedium());
        bundle.putString(PistaAlbumFragment.KEY_NOMBRE_CABECERA_ALBUM_PISTA, albumDeezer.getTitle());
        bundle.putInt(PistaAlbumFragment.KEY_PISTA_ID_ALBUM_PISTA, albumDeezer.getId());
        bundle.putString(PistaAlbumFragment.KEY_CATEGORIA, "mas_escuchados");
        pistaAlbumFragment.setArguments(bundle);

        parent.reemplazarFragment(pistaAlbumFragment, R.id.genero_fragment);
    }
}
