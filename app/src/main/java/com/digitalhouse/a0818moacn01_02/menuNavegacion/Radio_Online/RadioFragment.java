package com.digitalhouse.a0818moacn01_02.menuNavegacion.Radio_Online;


import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends Fragment implements AdaptadorRadio.RadioInterface {

    private AdaptadorRadio adaptadorRadio;
    private MediaPlayer mediaPlayer;


    public RadioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_radio, container, false);

        final ArrayList<Radio> radios = new ArrayList<>();


        Radio laTribu = new Radio("La Tribu", "88.7 FM", "http://vivo.fmlatribu.com:8000/latribu.mp3");
        Radio Malena = new Radio("Malena", "89.1 FM", "http://vivo.radioam750.com.ar/vivofm.mp3");
        Radio arpeggio = new Radio("Arpeggio", "89.5 FM", "http://14073.live.streamtheworld.com/ARPEGGIOAAC");
        Radio radioConVos = new Radio("Radio Con Vos", "89.9 FM", "http://server6.stweb.tv:1935/rcvos/live/chunklist.m3u8");
        Radio ESPN = new Radio("ESPN", "107.9 FM", "http://apiradio.espn-la.com/64list.m3u8");
        Radio LaBoca = new Radio("La Boca", "90.1 FM", "http://208.98.41.72:9304");
        Radio Metro = new Radio("Metro", "95.1 FM", "http://mp3.metroaudio1.stream.avstreaming.net:7200/metro");

        radios.add(laTribu);
        radios.add(Malena);
        radios.add(arpeggio);
        radios.add(radioConVos);
        radios.add(ESPN);
        radios.add(LaBoca);
        radios.add(Metro);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerRadio);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adaptadorRadio = new AdaptadorRadio(radios, this);
        recyclerView.setAdapter(adaptadorRadio);

        mediaPlayer = new MediaPlayer();


        return view;
    }

    @Override
    public void OnRadioClick(Radio radio) {
        LinearLayout linearLayout = getActivity().findViewById(R.id.layoutPlayer);
        TextView textViewNombrePista = getActivity().findViewById(R.id.tvNombreReproductor);
        textViewNombrePista.setSelected(true);


        linearLayout.setVisibility(View.VISIBLE);
        String url = radio.getUrl();
        textViewNombrePista.setText(radio.getNombre() + " - " + radio.getSintonia());
        textViewNombrePista.setTextColor(Color.parseColor("#FD9701"));
        reproducirMp3(url, mediaPlayer);



    }


    private void reproducirMp3(final String url, final MediaPlayer mediaPlayer) {
        final ImageView imageViewPlay = getActivity().findViewById(R.id.btnRepoductorPlay);
        final ImageView imageViewPause = getActivity().findViewById(R.id.btnReproductorPause);

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        try {
            if (!mediaPlayer.isPlaying()) {

                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();
                imageViewPause.setVisibility(View.VISIBLE);

            } else {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }

            imageViewPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.pause();
                    imageViewPlay.setVisibility(View.VISIBLE);
                    imageViewPause.setVisibility(View.INVISIBLE);
                }
            });


            imageViewPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {

                        mediaPlayer.start();
                        imageViewPause.setVisibility(View.VISIBLE);
                        imageViewPlay.setVisibility(View.INVISIBLE);
                    }
                }
            });


        } catch (
                IOException e)

        {

            e.printStackTrace();
        } catch (
                IllegalArgumentException e)

        {
            e.printStackTrace();
        } catch (
                SecurityException e)

        {
            e.printStackTrace();
        } catch (
                IllegalStateException e)

        {
            e.printStackTrace();
        }
    }

}



