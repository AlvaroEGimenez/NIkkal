package com.digitalhouse.a0818moacn01_02.view;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.adapter.SugerenciasViewPager;

import java.util.List;

public class ReproductorActivity extends AppCompatActivity {
    public static final String KEY_OBJETO = "lista Objetos";
    public static final String KEY_POSICION = "posicion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        List<Track> trackList = (List<Track>) bundle.getSerializable(KEY_OBJETO);
        Integer posicion = bundle.getInt(KEY_POSICION);



        ViewPager viewPager = findViewById(R.id.viewpageSugerencia);
        SugerenciasViewPager sugerenciasViewPager = new SugerenciasViewPager(getSupportFragmentManager(),trackList);


        viewPager.setAdapter(sugerenciasViewPager);
        viewPager.setCurrentItem(posicion);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(130,0,130,0);
        viewPager.setPageMargin(40);


    }
}
