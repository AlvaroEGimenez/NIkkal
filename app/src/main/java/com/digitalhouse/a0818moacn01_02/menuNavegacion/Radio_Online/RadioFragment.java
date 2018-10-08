package com.digitalhouse.a0818moacn01_02.menuNavegacion.Radio_Online;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhouse.a0818moacn01_02.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends Fragment {
    private String radioEncendidaUrl = "";



    public RadioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_radio, container, false);
        final MediaPlayer mediaPlayer= new MediaPlayer();
        final List<Radio> radios = new ArrayList<>();
        final LinearLayout linearLayout = view.findViewById(R.id.llPlayStop);
        final ImageButton imageButtonPlay = view.findViewById(R.id.ibPlayStop);
        final TextView textViewRadio = view.findViewById(R.id.tvNombreRadioReprod);




        Radio laTribu = new Radio("La Tribu", "88.7", "http://vivo.fmlatribu.com:8000/latribu.mp3");
        Radio Malena = new Radio("Malena", "89.1", "http://vivo.radioam750.com.ar/vivofm.mp3");
        Radio arpeggio = new Radio("Arpeggio", "89.5", "http://14073.live.streamtheworld.com/ARPEGGIOAAC");
        Radio radioConVos = new Radio("Radio Con Vos", "89.9", "http://server6.stweb.tv:1935/rcvos/live/chunklist.m3u8");
        Radio ESPN = new Radio("ESPN", "107.9", "http://apiradio.espn-la.com/64list.m3u8");
        Radio LaBoca = new Radio("La Boca", "90.1", "http://208.98.41.72:9304");
        Radio Metro = new Radio("Metro", "95.1", "http://mp3.metroaudio1.stream.avstreaming.net:7200/metro");

        radios.add(laTribu);
        radios.add(Malena);
        radios.add(arpeggio);
        radios.add(radioConVos);
        radios.add(ESPN);
        radios.add(LaBoca);
        radios.add(Metro);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerRadio);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AdaptadorRadio adaptadorRadio = new AdaptadorRadio(radios, getContext());


        adaptadorRadio.setRadioClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                try {
                    final String url = radios.get(position).getUrl();
                    linearLayout.setVisibility(View.VISIBLE);
                    final String cargando =  "Cargando..." ;
                    textViewRadio.setText(cargando);

                    if (!mediaPlayer.isPlaying() || !url.equals(radioEncendidaUrl)) {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepareAsync();

                        mediaPlayer.setOnPreparedListener(new MediaPlayer.
                                OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                                radioEncendidaUrl = url;
                                String escuchando =  "Escuchando " + radios.get(position).getNombre() + " "+ radios.get(position).getSintonia();
                                textViewRadio.setText(escuchando);
                            }
                        });


                    } else {
                        mediaPlayer.stop();
                        linearLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Stop", Toast.LENGTH_SHORT).show();
                        textViewRadio.setText(cargando);
                    }
                    imageButtonPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mediaPlayer.stop();
                            linearLayout.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(), "Stop", Toast.LENGTH_SHORT).show();
                            textViewRadio.setText(cargando);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        recyclerView.setAdapter(adaptadorRadio);
        return view;
    }
}


