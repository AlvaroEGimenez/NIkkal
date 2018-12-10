package com.digitalhouse.a0818moacn01_02.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.MediaPlayerNikkal;
import com.digitalhouse.a0818moacn01_02.Utils.ReproducirMp3;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.adapter.SugerenciasViewPager;

import java.util.ArrayList;
import java.util.List;

public class ReproductorActivity extends AppCompatActivity {
    public static final String KEY_OBJETO = "lista Objetos";
    public static final String KEY_POSICION = "posicion";
    public static final String KEY_POSICION_REPRODUCTOR = "posicion_reproductor";

    List<Track> trackList = new ArrayList<>();
    ViewPager viewPager;
    Integer posicion;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        mediaPlayer = MediaPlayerNikkal.getInstance().getMediaPlayer();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        if (bundle != null) {
            trackList = (List<Track>) bundle.getSerializable(KEY_OBJETO);
            posicion = bundle.getInt(KEY_POSICION);
            Integer poscionReproductor = bundle.getInt(KEY_POSICION_REPRODUCTOR);
            viewPager = findViewById(R.id.viewpageSugerencia);
            SugerenciasViewPager sugerenciasViewPager = new SugerenciasViewPager(getSupportFragmentManager(), trackList);
            viewPager.setAdapter(sugerenciasViewPager);
            viewPager.setCurrentItem(posicion);
            viewPager.setClipToPadding(false);
            viewPager.setPadding(130, 0, 130, 0);
            viewPager.setPageMargin(40);


        } else {
            Toast.makeText(this, "Sin Datos", Toast.LENGTH_SHORT).show();
        }

        ImageView imageViewAnterior = findViewById(R.id.ivAnteriorReproductor);
        final ImageView imageViewPause = findViewById(R.id.ivPausa_Reproductor);
        final ImageView imageViewPlay = findViewById(R.id.ivPlayReproductor);
        ImageView imageViewProximo = findViewById(R.id.ivProximoReproductor);


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
                viewPager.setCurrentItem(posicion -1);
                Toast.makeText(ReproductorActivity.this, "Anterior", Toast.LENGTH_SHORT).show();


            }
        });

        imageViewProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(posicion +1);
                Toast.makeText(ReproductorActivity.this, "Proximo", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
