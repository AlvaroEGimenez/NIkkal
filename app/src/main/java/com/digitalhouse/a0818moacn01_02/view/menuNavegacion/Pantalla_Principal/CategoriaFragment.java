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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ReproducirMp3;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.GenreController;
import com.digitalhouse.a0818moacn01_02.controller.RadioController;
import com.digitalhouse.a0818moacn01_02.model.Genre;
import com.digitalhouse.a0818moacn01_02.model.PruebasRetrofit2.Controller.TopChartController;
import com.digitalhouse.a0818moacn01_02.model.RadioDeezer;
import com.digitalhouse.a0818moacn01_02.model.TopChartLocal;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.AdaptadorTopChart;
import com.digitalhouse.a0818moacn01_02.view.adapter.AdaptadorTopChartDeezer;
import com.digitalhouse.a0818moacn01_02.view.adapter.CategoriaAdapterRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.adapter.RadioAdapterRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.categorias.GeneroFragment;
import com.digitalhouse.a0818moacn01_02.view.categorias.SugerenciasFragment;
import java.util.ArrayList;
import java.util.List;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class CategoriaFragment extends Fragment implements CategoriaAdapterRecyclerView.AdapterInterface, AdaptadorTopChartDeezer.onItemClickTopChartDeezer, AdaptadorTopChart.onItemClickTopChart
        , RadioAdapterRecyclerView.AdapterInterface {


    private GeneroFragment generoFragment = new GeneroFragment();
    private SugerenciasFragment sugerenciasFragment = new SugerenciasFragment();
    private FeatureCoverFlow featureCoverFlow;
    private List<RadioDeezer> radioDeezerList = new ArrayList<>();
    private TextSwitcher mTitle;
    private View view;
    private ProgressBar pbCategoria;
    private LinearLayout conatiner;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private MainActivity parent;
    private List<Genre> genreList = new ArrayList<>();
    private AdaptadorTopChartDeezer adaptadorTopChartDeezer;

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

        adaptadorTopChartDeezer = new AdaptadorTopChartDeezer(new ArrayList<Track>(), getContext(), this);


        setCategotia();
        cargarGenre();
        cargarRadios();


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

                featureCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
                    @Override
                    public void onScrolledToPosition(int position) {

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

    private void setCategotia() {

        TextView tvGeneros = view.findViewById(R.id.tvGeneroRecyclerView);
        tvGeneros.setText(R.string.tv_genero);

        TextView tvSugerencia = view.findViewById(R.id.tvSugerenciaRecyclerView);
        tvSugerencia.setText(R.string.tv_sugerencia);

        TextView tvMasEscuchado = view.findViewById(R.id.tvMasEscuchadoRecyclerView);
        tvMasEscuchado.setText(R.string.tv_mas_escuchado);

        TextView tvFavorito = view.findViewById(R.id.tvFavoritoRecyclerView);
        tvFavorito.setText(R.string.tv_favorito);
    }

    private void cargarGenre() {

        GenreController genreController = new GenreController();
        genreController.getGenre(new ResultListener<List<Genre>>() {
            @Override
            public void finish(List<Genre> resultado) {
                genreList = resultado;
                genreList.remove(0);
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
        parent.reemplazarFragment(generoFragment);

        if (intent != null)
            startActivity(intent);
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
    public void cambiarDeActividadGenero(Genre Genre) {
        llamarActividad(Genre);
    }

    @Override
    public void cambiarDeActividadRadio(RadioDeezer radioDeezer) {

        Intent intent = null;
        Bundle bundle = new Bundle();
        bundle.putString(SugerenciasFragment.KEY_IMAGEN_SUGERENCIA, radioDeezer.getPictureMedium());
        bundle.putString(SugerenciasFragment.KEY_URL_PLAYLIST_SUGERENCIA, radioDeezer.getTracklist());
        bundle.putString(SugerenciasFragment.KEY_NOMBRE_SUGERENCIA, radioDeezer.getTitle());

        sugerenciasFragment.setArguments(bundle);
        parent.reemplazarFragment(sugerenciasFragment);
        if (intent != null) {
            startActivity(intent);
        }

    }
}
