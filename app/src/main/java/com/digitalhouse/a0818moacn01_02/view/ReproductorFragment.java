package com.digitalhouse.a0818moacn01_02.view;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.ViewTarget;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.player.Player;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.PlayerState;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.jgabrielfreitas.core.BlurImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReproductorFragment extends Fragment {

    String applicationID = "54587f8009fc0747c9f2eacef47d35f4";
    final DeezerConnect deezerConnect = new DeezerConnect(getContext(), applicationID);

    public static final String KEY_IMAGEN_TRACK = "imagen";
    public static final String KEY_NOMBRE_TRACK = "nombre track";
    public static final String KEY_NOMBRE_ARTISTA = "nombre artista";
    public static final String KEY_DURACION_TRACK = "duracion";
    public static final String KEY_ID_TRACK = "idTrack";
    public static final String KEY_POSICION_ACTUAL = "posicion";


    private String urlImagen;
    private String nombreTrack;
    private String nombreArtista;
    private Integer duracionTrack;
    private Integer idTrack;
    private Long posicionActual;


    public static ReproductorFragment factory(Track track) {
        ReproductorFragment reproductorFragment = new ReproductorFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMAGEN_TRACK, track.getAlbum().getCoverMedium());
        bundle.putString(KEY_NOMBRE_TRACK, track.getTitle());
        bundle.putString(KEY_NOMBRE_ARTISTA, track.getArtist().getName());
        bundle.putInt(KEY_DURACION_TRACK, track.getDuration());
        bundle.putInt(KEY_ID_TRACK, track.getId());
        reproductorFragment.setArguments(bundle);
        return reproductorFragment;
    }


    private void leerBundle(Bundle bundle) {
        if (bundle != null) {
            urlImagen = bundle.getString(KEY_IMAGEN_TRACK);
            nombreTrack = bundle.getString(KEY_NOMBRE_TRACK);
            nombreArtista = bundle.getString(KEY_NOMBRE_ARTISTA);
            duracionTrack = bundle.getInt(KEY_DURACION_TRACK);
            posicionActual = bundle.getLong(KEY_POSICION_ACTUAL);
            idTrack = bundle.getInt(KEY_ID_TRACK);

        }
    }


    public ReproductorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reproductor, container, false);

        leerBundle(getArguments());


        ImageView imageViewImagenTrack = view.findViewById(R.id.blurimageview);
        Glide.with(getContext()).load(urlImagen).into(imageViewImagenTrack);

        TextView textViewNombredelTrack = view.findViewById(R.id.tvNombretrackReproductor);
        TextView textViewNombredelArtista = view.findViewById(R.id.tvNombreArtistaReproductor);
        textViewNombredelTrack.setText(nombreTrack);
        textViewNombredelArtista.setText(nombreArtista);
        textViewNombredelTrack.setSelected(true);


        ImageView imageViewAnterior = getActivity().findViewById(R.id.ivAnteriorReproductor);
        final ImageView imageViewPause = getActivity().findViewById(R.id.ivPausa_Reproductor);
        final ImageView imageViewPlay = getActivity().findViewById(R.id.ivPlayReproductor);
        ImageView imageViewProximo = getActivity().findViewById(R.id.ivProximoReproductor);


        imageViewPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewPlay.setVisibility(View.VISIBLE);
                imageViewPause.setVisibility(View.INVISIBLE);
            }
        });

        imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewPlay.setVisibility(View.INVISIBLE);
                imageViewPause.setVisibility(View.VISIBLE);

            }
        });


        imageViewAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imageViewProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}
