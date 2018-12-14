package com.digitalhouse.a0818moacn01_02.view;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.MediaPlayerNikkal;
import com.digitalhouse.a0818moacn01_02.Utils.ReproducirMp3;
import com.digitalhouse.a0818moacn01_02.model.Track;

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
    public static final String KEY_TRACK_ACTUAL = "track_actual";

    private String urlImagen;
    private String nombreTrack;
    private String nombreArtista;
    private ViewPager viewPager;
    private MediaPlayer mediaPlayer;
    private ReproducirMp3 reproducirMp3;
    ReproductorActivity activity;
    private static Integer posicionAnterior = null;

    public static ReproductorFragment factory(Track track) {
        ReproductorFragment reproductorFragment = new ReproductorFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_TRACK_ACTUAL, track);

        if (track.getAlbum() != null && track.getAlbum().getCoverMedium() != null) {
            bundle.putString(KEY_IMAGEN_TRACK, track.getAlbum().getCoverMedium());
        } else if (track.getArtist() != null && track.getArtist().getPictureMedium() != null) {
            bundle.putString(KEY_IMAGEN_TRACK, track.getArtist().getPictureMedium());
        } else if (track.getImagenAlbum() != null) {
            bundle.putString(KEY_IMAGEN_TRACK, track.getImagenAlbum());
        }

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
        }
    }

    public ReproductorFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reproductor, container, false);

        mediaPlayer = MediaPlayerNikkal.getInstance().getMediaPlayer();
        leerBundle(getArguments());
        this.reproducirMp3 = new ReproducirMp3(false);
        final SeekBar progressBar = getActivity().findViewById(R.id.pbReproductorActivity);
        viewPager = getActivity().findViewById(R.id.viewpageSugerencia);
        ImageView imageViewImagenTrack = view.findViewById(R.id.blurimageview);

        if (urlImagen == null) {
            Glide.with(getContext()).load(getResources().getDrawable(R.drawable.icononikkal)).into(imageViewImagenTrack);
        } else {
            Glide.with(getContext()).load(urlImagen).into(imageViewImagenTrack);
        }
        TextView textViewNombredelTrack = view.findViewById(R.id.tvNombretrackReproductor);
        TextView textViewNombredelArtista = view.findViewById(R.id.tvNombreArtistaReproductor);
        textViewNombredelTrack.setText(nombreTrack);
        textViewNombredelArtista.setText(nombreArtista);
        textViewNombredelTrack.setSelected(true);

        ImageView imageViewAnterior = getActivity().findViewById(R.id.ivAnteriorReproductor);
        final ImageView imageViewPause = getActivity().findViewById(R.id.ivPausa_Reproductor);
        final ImageView imageViewPlay = getActivity().findViewById(R.id.ivPlayReproductor);
        ImageView imageViewProximo = getActivity().findViewById(R.id.ivProximoReproductor);


        activity = (ReproductorActivity) getActivity();
        reproducirMp3.reproducirMp3(activity.getTrackList(), activity.getPosicion(), activity);
        if (posicionAnterior == null) {
            posicionAnterior = activity.getPosicion();
        }

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBar.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        final Handler mSeekbarUpdateHandler = new Handler();
        final Runnable mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(this, 50);

            }
        };

        progressBar.setMax(mediaPlayer.getDuration());
        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
        imageViewPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewPlay.setVisibility(View.VISIBLE);
                imageViewPause.setVisibility(View.INVISIBLE);
                mediaPlayer.pause();
            }
        });

        imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewPlay.setVisibility(View.INVISIBLE);
                imageViewPause.setVisibility(View.VISIBLE);
                mediaPlayer.start();
            }
        });

        imageViewAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer posicion = viewPager.getCurrentItem() - 1;
                posicionAnterior = posicion;
                if (posicion >= 0) {
                    mediaPlayer.stop();
                    reproducirMp3.ReproducirMp3Activity(posicion);
                    viewPager.setCurrentItem(posicion);
                    imageViewPlay.setVisibility(View.INVISIBLE);
                    imageViewPause.setVisibility(View.VISIBLE);
                }

            }
        });

        imageViewProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer posicion = viewPager.getCurrentItem() + 1;
                posicionAnterior = posicion;
                if (posicion < activity.getTrackList().size()) {
                    mediaPlayer.stop();
                    reproducirMp3.ReproducirMp3Activity(posicion);
                    viewPager.setCurrentItem(posicion);
                    imageViewPlay.setVisibility(View.INVISIBLE);
                    imageViewPause.setVisibility(View.VISIBLE);
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (posicionAnterior == viewPager.getCurrentItem()) {
                    posicionAnterior = (viewPager.getCurrentItem() + 1);
                } else {
                    posicionAnterior = viewPager.getCurrentItem();
                }

                if (posicionAnterior < activity.getTrackList().size()) {
                    reproducirMp3.ReproducirMp3Activity(posicionAnterior);
                    viewPager.setCurrentItem(posicionAnterior);
                }
            }
        });

        return view;
    }

}
