package com.digitalhouse.a0818moacn01_02.categorias.genero;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Album;
import com.digitalhouse.a0818moacn01_02.recyclerView.AlbumAdapterRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GeneroFragment extends Fragment implements AlbumAdapterRecyclerView.AlbumAdapterInterface{
    public static final String KEY_IMAGEN_GENERO = "imagenGenero";
    public static final String KEY_NOMBRE_GENERO = "nombreGenero";

    private ImageView imgGenero;
    private Toolbar tvCabeceraGenero;

    public GeneroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_genero, container, false);

        imgGenero = view.findViewById(R.id.imgGenero);
        tvCabeceraGenero = view.findViewById(R.id.tvCabeceraGenero);
        view.findViewById(R.id.rvGenero);

        Bundle bundle =  getArguments();
        String urlImagenCabecera = bundle.getString(KEY_IMAGEN_GENERO);
        String nombreGenero = bundle.getString(KEY_NOMBRE_GENERO);

        cargarImagen(imgGenero, urlImagenCabecera);
        tvCabeceraGenero.setTitle(nombreGenero);
        crearRecyclerView(view, R.id.rvGenero, "");

        return view;
    }


    private void cargarImagen(ImageView imageView, String url) {
        Picasso.Builder picassoBuilder = new Picasso.Builder(getContext());
        Picasso picasso = picassoBuilder.build();
        picasso.load(url).error(R.drawable.nikkal).into(imageView);
    }

    private void crearRecyclerView(View view, Integer idLayout, String tvCategoria) {
        RecyclerView generoRecyclerView = view.findViewById(idLayout);
        generoRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        generoRecyclerView.setLayoutManager(linearLayoutManager);

        AlbumAdapterRecyclerView categoriaAdapterRecyclerView = new AlbumAdapterRecyclerView(cargarAlbunes(tvCategoria), R.layout.cardview_album, getActivity(), this);

    generoRecyclerView.setAdapter(categoriaAdapterRecyclerView);
    }

    public ArrayList<Album> cargarAlbunes(String genero) {
        ArrayList<Album> albunes = new ArrayList<>();
        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/b36ca681666d617edd0dcb5ab389a6ac/250x250-000000-80-0-0.jpg",
                "Rock", genero));
        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/f14f9fde9feb38ca6d61960f00681860/250x250-000000-80-0-0.jpg",
                "Metal", genero));
        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/ffd77feba2c8fda79b18183861e4e69f/250x250-000000-80-0-0.jpg",
                "Cumbia", genero));

        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/f53d298e46c4722edc245f3b7232343a/250x250-000000-80-0-0.jpg",
                "Folklore Argentino", genero));

        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/1abb6810098d4015bdc860c91bcfd2b6/250x250-000000-80-0-0.jpg",
                "Blues", genero));


        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/069c9888538799748960781f098b5f4b/250x250-000000-80-0-0.jpg",
                "Latino", genero));

        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/b0b8efcbc3cb688864ce69da0061e525/250x250-000000-80-0-0.jpg",
                "Niños", genero));
        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/artist/4f4ee0a2edcebdb115910bc39fb57f56/250x250-000000-80-0-0.jpg",
                "La Máqiona de ser Feliz", genero));

        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/artist/b4719bc7a0ddb4a5be41277f37856ae6/500x500-000000-80-0-0.jpg",
                "Metallica", genero));


        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/73be4dccb64e1a53d6d3394436367f21/500x500-000000-80-0-0.jpg",
                "Oktubre", genero));
        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/3ff8f91bb354b0245ea34d9a7fc3c07d/500x500-000000-80-0-0.jpg",
                "Amanecer", genero));
        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/5e61e8290a4d1d64ca58920656c9602d/500x500-000000-80-0-0.jpg",
                "Californication ", genero));

        albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/d493314dc2ba6d5bad6d6893913c3a9b/500x500-000000-80-0-0.jpg",
                "Cumbia Peposa", genero));

        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/a13de21d1c76b3cc3096391c715304ab/500x500-000000-80-0-0.jpg",
                "Killshot", genero));


        albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/9a3364528159f8377d3b1b5310f40dae/500x500-000000-80-0-0.jpg",
                "X (Remix)", genero));
        albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/27170198c97ac7e37f8a62cf5cae4299/500x500-000000-80-0-0.jpg",
                "In My Mind", genero));
        albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/7e8314f4280cffde363547a495a260bc/250x250-000000-80-0-0.jpg",
                "Night Visions", genero));

        return albunes;
    }

    @Override
    public void cambiarDeActividad(Album album) {

    }

}
