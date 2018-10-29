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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.digitalhouse.a0818moacn01_02.DAOLocal;
import com.digitalhouse.a0818moacn01_02.ReproducirMp3;
import com.digitalhouse.a0818moacn01_02.model.TopChartLocal;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.view.adapter.AdaptadorTopChart;
import com.digitalhouse.a0818moacn01_02.view.adapter.CategoriaAdapterRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.categorias.MasEscuchado;
import com.digitalhouse.a0818moacn01_02.view.categorias.SugerenciaActivity;
import com.digitalhouse.a0818moacn01_02.view.categorias.GeneroFragment;
import com.digitalhouse.a0818moacn01_02.model.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class CategoriaFragment extends Fragment implements CategoriaAdapterRecyclerView.AdapterInterface, AdaptadorTopChart.onItemClickTopChart {
    public final static String KEY_GENERO = "Géneros";
    public final static String KEY_SUGERENCIA = "Sugerencias";
    public final static String KEY_MAS_ESCUCHADO = "Lo Más Escuchado";
    public final static String KEY_FAVORITO = "Favoritos";

    RecyclerView recyclerView;

    private GeneroFragment generoFragment = new GeneroFragment();

    private FeatureCoverFlow featureCoverFlow;
    private AdaptadorTopChart adaptadorTopChart;
    private List<TopChartLocal> topChartList = new ArrayList<>();
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

    public CategoriaFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.categoria_album, container, false);

        this.view = view;
        parent = (MainActivity) getActivity();
        setCategotia();
        crearRecyclerView(R.id.rvGeneroRecyclerView, tvGeneros.getText().toString());
        crearRecyclerView(R.id.rvSugerenciaRecyclerView, tvSugerencia.getText().toString());
        crearRecyclerView(R.id.rvMasEscuchadoRecyclerView, tvMasEscuchado.getText().toString());
        crearRecyclerView(R.id.rvFavoritoRecyclerView, tvFavorito.getText().toString());


        DAOLocal daoLocal = new DAOLocal();
        daoLocal.ObtenerTopChar(topChartList);

        mTitle = view.findViewById(R.id.tituloTopChart);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                TextView txt = (TextView) layoutInflater.inflate(R.layout.layout_title,null);
                return txt;
            }
        });

        Animation in = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_out_bottom);
        mTitle.setAnimation(in);
        mTitle.setOutAnimation(out);



        adaptadorTopChart = new AdaptadorTopChart(topChartList,getContext(),this);
        featureCoverFlow = view.findViewById(R.id.coverFlow);
        featureCoverFlow.setAdapter(adaptadorTopChart);

        featureCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                mTitle.setText(topChartList.get(position).getNombreTrack()+" - "+ topChartList.get(position).getNombreArtista());
            }

            @Override
            public void onScrolling() {

            }
        });



        return view;
    }

    private void crearRecyclerView(Integer idLayout, String tvCategoria) {
         RecyclerView  albumRecyclerView = view.findViewById(idLayout);
        albumRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        albumRecyclerView.setLayoutManager(linearLayoutManager);

        CategoriaAdapterRecyclerView adapter = new CategoriaAdapterRecyclerView(cargarAlbunes(tvCategoria), R.layout.carcdview_categoria, getActivity(), this);

        albumRecyclerView.setAdapter(adapter);
    }

    @Override
    public void cambiarDeActividad(Album album) {
        llamarActividad(album);
    }

    private void llamarActividad(Album album) {
        String genero = album.getGenero();
        Intent intent = null;

        switch (genero) {
            case CategoriaFragment.KEY_GENERO:
                Bundle bundle = new Bundle();
                bundle.putString(GeneroFragment.KEY_IMAGEN_GENERO, album.getImagen());
                bundle.putString(GeneroFragment.KEY_NOMBRE_GENERO, album.getNombre());

                generoFragment.setArguments(bundle);

                parent.reemplazarFragment(generoFragment);
                break;
            case CategoriaFragment.KEY_SUGERENCIA:
                intent = new Intent(getContext(), SugerenciaActivity.class);
                break;
            case CategoriaFragment.KEY_MAS_ESCUCHADO:
                intent = new Intent(getContext(), MasEscuchado.class);
        }

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

    public ArrayList<Album> cargarAlbunes(String categoria) {
        ArrayList<Album> albunes = new ArrayList<>();
        if (KEY_GENERO.equals(categoria) || "Genders".equals(categoria)) {
            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/b36ca681666d617edd0dcb5ab389a6ac/250x250-000000-80-0-0.jpg",
                    "Rock", KEY_GENERO));
            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/f14f9fde9feb38ca6d61960f00681860/250x250-000000-80-0-0.jpg",
                    "Metal", KEY_GENERO));
            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/ffd77feba2c8fda79b18183861e4e69f/250x250-000000-80-0-0.jpg",
                    "Cumbia", KEY_GENERO));

            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/f53d298e46c4722edc245f3b7232343a/250x250-000000-80-0-0.jpg",
                    "Folklore Argentino", KEY_GENERO));

            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/1abb6810098d4015bdc860c91bcfd2b6/250x250-000000-80-0-0.jpg",
                    "Blues", KEY_GENERO));


            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/069c9888538799748960781f098b5f4b/250x250-000000-80-0-0.jpg",
                    "Latino", KEY_GENERO));

            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/b0b8efcbc3cb688864ce69da0061e525/250x250-000000-80-0-0.jpg",
                    "Niños", KEY_GENERO));
        }

        if (KEY_SUGERENCIA.equals(categoria) || "Suggestions".equals(categoria)) {
            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/artist/4f4ee0a2edcebdb115910bc39fb57f56/250x250-000000-80-0-0.jpg",
                    "La Máqiona de ser Feliz", KEY_SUGERENCIA));

            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/artist/b4719bc7a0ddb4a5be41277f37856ae6/500x500-000000-80-0-0.jpg",
                    "Metallica", KEY_SUGERENCIA));


            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/73be4dccb64e1a53d6d3394436367f21/500x500-000000-80-0-0.jpg",
                    "Oktubre", KEY_SUGERENCIA));
            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/3ff8f91bb354b0245ea34d9a7fc3c07d/500x500-000000-80-0-0.jpg",
                    "Amanecer", KEY_SUGERENCIA));
            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/5e61e8290a4d1d64ca58920656c9602d/500x500-000000-80-0-0.jpg",
                    "Californication ", KEY_SUGERENCIA));
        }

        if (KEY_MAS_ESCUCHADO.equals(categoria) || "The Most Listened".equals(categoria)) {
            albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/d493314dc2ba6d5bad6d6893913c3a9b/500x500-000000-80-0-0.jpg",
                    "Cumbia Peposa", KEY_MAS_ESCUCHADO));

            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/a13de21d1c76b3cc3096391c715304ab/500x500-000000-80-0-0.jpg",
                    "Killshot", KEY_MAS_ESCUCHADO));


            albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/9a3364528159f8377d3b1b5310f40dae/500x500-000000-80-0-0.jpg",
                    "X (Remix)", KEY_MAS_ESCUCHADO));
            albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/27170198c97ac7e37f8a62cf5cae4299/500x500-000000-80-0-0.jpg",
                    "In My Mind", KEY_MAS_ESCUCHADO));
            albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/7e8314f4280cffde363547a495a260bc/250x250-000000-80-0-0.jpg",
                    "Night Visions", KEY_MAS_ESCUCHADO));

            //analizarJSONMasEscuchados(albunes);


        }

        if (KEY_FAVORITO.equals(categoria) || "Favorites".equals(categoria)) {
            albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/7e8314f4280cffde363547a495a260bc/250x250-000000-80-0-0.jpg",
                    "Night Visions", KEY_FAVORITO));

            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/5e61e8290a4d1d64ca58920656c9602d/500x500-000000-80-0-0.jpg",
                    "Killshot", KEY_FAVORITO));

        }
        return albunes;
    }

    private void analizarJSONMasEscuchados(final ArrayList<Album>  album) {

        String url = "https://api.deezer.com/chart";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("tracks");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                Object artista = hit.getJSONObject("artist");
                                Object datos = hit.getJSONObject("data");
                                String imagen = ((JSONObject) artista).getString("picture_big");
                                String titulo = ((JSONObject) datos).getString("name");
                                album.add(new Album(imagen,titulo , "Top Chart"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        requestQueue.add(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClickTopChart(TopChartLocal topChartLocal) {
        TextView textViewNombrePista = getActivity().findViewById(R.id.tvNombreReproductor);
        textViewNombrePista.setSelected(true);

        ReproducirMp3 reproducirMp3 = new ReproducirMp3();
        reproducirMp3.reproducirMp3(topChartLocal.getUrlMp3(),mediaPlayer,parent);
        textViewNombrePista.setText(topChartLocal.getNombreTrack() + " - " + topChartLocal.getNombreArtista());
        textViewNombrePista.setTextColor(Color.parseColor("#FD9701"));

    }
}
