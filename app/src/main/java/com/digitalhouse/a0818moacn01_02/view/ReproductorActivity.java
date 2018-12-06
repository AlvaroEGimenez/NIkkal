package com.digitalhouse.a0818moacn01_02.view;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ReproducirMp3;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.adapter.SugerenciasViewPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReproductorActivity extends AppCompatActivity {
    public static final String KEY_OBJETO = "lista Objetos";
    public static final String KEY_POSICION = "posicion";
    public static final String KEY_POSICION_REPRODUCTOR = "posicion_reproductor";

    List<Track> trackList = new ArrayList<>();
    ViewPager viewPager;


    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        if (bundle != null){
            trackList = (List<Track>) bundle.getSerializable(KEY_OBJETO);
            Integer posicion = bundle.getInt(KEY_POSICION);
            Integer poscionReproductor = bundle.getInt(KEY_POSICION_REPRODUCTOR);
            viewPager = findViewById(R.id.viewpageSugerencia);
            SugerenciasViewPager sugerenciasViewPager = new SugerenciasViewPager(getSupportFragmentManager(),trackList);
            viewPager.setAdapter(sugerenciasViewPager);
            viewPager.setCurrentItem(posicion);
            viewPager.setClipToPadding(false);
            viewPager.setPadding(130,0,130,0);
            viewPager.setPageMargin(40);


        }
        else {
            Toast.makeText(this, "Sin Datos", Toast.LENGTH_SHORT).show();
        }



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(ReproductorActivity.this, trackList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                String url = trackList.get(position).getPreview();

               ReproducirMp3 reproducirMp3 = new ReproducirMp3();
               reproducirMp3.ReproducirMp3Activity(url,mediaPlayer);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });

    }

}
