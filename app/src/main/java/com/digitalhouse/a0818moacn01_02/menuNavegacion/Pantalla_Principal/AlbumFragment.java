package com.digitalhouse.a0818moacn01_02.menuNavegacion.Pantalla_Principal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Album;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {
    public final static String KEY_GENERO = "Géneros";
    public final static String KEY_SUGERENCIA = "Sugerencias";
    public final static String KEY_MAS_ESCUCHADO = "Lo Más Escuchado";
    public final static String KEY_FAVORITO = "Favoritos";

    private View view;
    private TextView tvGeneros;
    private TextView tvSugerencia;
    private TextView tvMasEscuchado;
    private TextView tvFavorito;
    private AdapterView.OnItemClickListener listener;

    public AlbumFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_album, container, false);
        this.view = view;

        setCategotia();
        crearRecyclerView(R.id.rvGeneroRecyclerView, tvGeneros.getText().toString());
        crearRecyclerView(R.id.rvSugerenciaRecyclerView, tvSugerencia.getText().toString());
        crearRecyclerView(R.id.rvMasEscuchadoRecyclerView, tvMasEscuchado.getText().toString());
        crearRecyclerView(R.id.rvFavoritoRecyclerView, tvFavorito.getText().toString());
        return view;
    }


    private void crearRecyclerView(Integer idLayout, String tvCategoria) {
        RecyclerView albumRecyclerView = view.findViewById(idLayout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        albumRecyclerView.setLayoutManager(linearLayoutManager);

        AlbumAdapterRecyclerView albumAdapterRecyclerView = new AlbumAdapterRecyclerView(cargarAlbunes(tvCategoria), R.layout.carcdview_album, getActivity());

        albumRecyclerView.setAdapter(albumAdapterRecyclerView);
    }


    private void setCategotia() {
        tvGeneros = view.findViewById(R.id.tvGeneroRecyclerView);
        tvGeneros.setText("Géneros");

        tvSugerencia = view.findViewById(R.id.tvSugerenciaRecyclerView);
        tvSugerencia.setText("Sugerencias");

        tvMasEscuchado = view.findViewById(R.id.tvMasEscuchadoRecyclerView);
        tvMasEscuchado.setText("Lo Más Escuchado");

        tvFavorito = view.findViewById(R.id.tvFavoritoRecyclerView);
        tvFavorito.setText("Favoritos");
    }

    public ArrayList<Album> cargarAlbunes(String categoria) {
        ArrayList<Album> albunes = new ArrayList<>();
        if (KEY_GENERO.equals(categoria)) {
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

        if (KEY_SUGERENCIA.equals(categoria)) {
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

        if (KEY_MAS_ESCUCHADO.equals(categoria)) {
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
        }

        if (KEY_FAVORITO.equals(categoria)) {
            albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/7e8314f4280cffde363547a495a260bc/250x250-000000-80-0-0.jpg",
                    "Night Visions", KEY_FAVORITO));

            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/5e61e8290a4d1d64ca58920656c9602d/500x500-000000-80-0-0.jpg",
                    "Killshot", KEY_FAVORITO));

        }

        return albunes;
    }


}
