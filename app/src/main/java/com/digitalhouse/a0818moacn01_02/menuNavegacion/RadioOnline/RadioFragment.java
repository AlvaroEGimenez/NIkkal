package com.digitalhouse.a0818moacn01_02.menuNavegacion.RadioOnline;



import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.digitalhouse.a0818moacn01_02.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.digitalhouse.a0818moacn01_02.R.drawable.ic_stop;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends Fragment {

    private List<Radio> radios = new ArrayList<>();



    public RadioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_radio, container, false);
        final CoordinatorLayout coordinatorLayout = view.findViewById(R.id.radioCoordinator);

        Radio laTribu = new Radio("La Tribu","88.7","http://vivo.fmlatribu.com:8000/latribu.mp3");
        Radio Malena = new Radio("Malena","89.1","http://vivo.radioam750.com.ar/vivofm.mp3");
        Radio arpeggio = new Radio("Arpeggio","89.5","http://14073.live.streamtheworld.com/ARPEGGIOAAC");
        Radio radioConVos = new Radio("Radio Con Vos","89.9","http://server6.stweb.tv:1935/rcvos/live/chunklist.m3u8");
        Radio ESPN = new Radio("ESPN","107.9","http://apiradio.espn-la.com/64list.m3u8");
        Radio LaBoca = new Radio("La Boca","90.1","http://208.98.41.72:9304");
        Radio Metro = new Radio("Metro","95.1","http://mp3.metroaudio1.stream.avstreaming.net:7200/metro");

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

        AdaptadorRadio adaptadorRadio = new AdaptadorRadio(radios,getContext());

        final MediaPlayer player = new MediaPlayer();



        adaptadorRadio.setRadioClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String url = radios.get(position).getUrl();
                Toast.makeText(getActivity(),url,Toast.LENGTH_SHORT).show();
                MediaPlayer mPlayer = new MediaPlayer();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mPlayer.setDataSource(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mPlayer.prepareAsync(); // might take long! (for buffering, etc)
                if (!mPlayer.isPlaying()){
                    mPlayer.start();
                }else {
                    mPlayer.stop();
                }

            }
        });
        recyclerView.setAdapter(adaptadorRadio);
        return view;
    }



}
