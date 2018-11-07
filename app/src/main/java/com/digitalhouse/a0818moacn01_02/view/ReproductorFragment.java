package com.digitalhouse.a0818moacn01_02.view;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.ViewTarget;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.jgabrielfreitas.core.BlurImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReproductorFragment extends Fragment {

    public static final String KEY_IMAGEN_TRACK = "imagen";
    public static final String KEY_NOMBRE_TRACK = "nombre track";
    public static final String KEY_NOMBRE_ARTISTA = "nombre artista";
    public static final String KEY_DURACION_TRACK = "duracion";


    private String urlImagen;
    private String nombreTrack;
    private String nombreArtista;
    private Integer duracionTrack;

    public static ReproductorFragment factory(Track track) {
        ReproductorFragment reproductorFragment = new ReproductorFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMAGEN_TRACK, track.getAlbum().getCoverMedium());
        bundle.putString(KEY_NOMBRE_TRACK, track.getTitle());
        bundle.putString(KEY_NOMBRE_ARTISTA, track.getArtist().getName());
        bundle.putInt(KEY_DURACION_TRACK, track.getDuration());
        reproductorFragment.setArguments(bundle);
        return reproductorFragment;
    }


    private void leerBundle(Bundle bundle) {
        if (bundle != null) {
            urlImagen = bundle.getString(KEY_IMAGEN_TRACK);
            nombreTrack = bundle.getString(KEY_NOMBRE_TRACK);
            nombreArtista = bundle.getString(KEY_NOMBRE_ARTISTA);
            duracionTrack = bundle.getInt(KEY_DURACION_TRACK);

        }
    }


    public ReproductorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_reproductor, container, false);

        leerBundle(getArguments());


        ImageView imageViewImagenTrack = view.findViewById(R.id.blurimageview);
        Glide.with(getContext()).load(urlImagen).into(imageViewImagenTrack);

        TextView textViewNombredelTrack = view.findViewById(R.id.tvNombretrackReproductor);
        TextView textViewNombredelArtista = view.findViewById(R.id.tvNombreArtistaReproductor);
        textViewNombredelTrack.setText(nombreTrack);
        textViewNombredelArtista.setText(nombreArtista);
        textViewNombredelTrack.setSelected(true);




        return view;
    }

}
