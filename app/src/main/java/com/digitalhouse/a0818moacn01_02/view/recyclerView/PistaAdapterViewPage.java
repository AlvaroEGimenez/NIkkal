package com.digitalhouse.a0818moacn01_02.view.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Pista;
import com.digitalhouse.a0818moacn01_02.model.TopChartLocal;

import java.util.List;

public class PistaAdapterViewPage extends PagerAdapter {
    private List<TopChartLocal> pistas;
    private Context context;
    private LayoutInflater layoutInflater;
    PistaViewPageInterface escuchador;

    public PistaAdapterViewPage(List<TopChartLocal> pistas, Context context, PistaViewPageInterface escuchador) {
        this.pistas = pistas;
        this.context = context;
        layoutInflater = ((Activity)context).getLayoutInflater();
        this.escuchador = escuchador;
    }


    @Override
    public int getCount() {
        return pistas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (View)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.pista_view_page, container, false);
        TextView textView = view.findViewById(R.id.tvTituloPistaViewPage);
        final ImageButton btnPistaAnterior = view.findViewById(R.id.ic_play_antrior_pista);
        final ImageButton btnPistaSiguiente = view.findViewById(R.id.ic_play_siguiente_pista);
        final ImageButton btnPlay = view.findViewById(R.id.ic_play_pista);
        final ImageButton btnPause = view.findViewById(R.id.ic_pause_pista);
        final ProgressBar progressBar = view.findViewById(R.id.progrerssBarPistaViewPage);

        container.addView(view);
        final  TopChartLocal pista = pistas.get(position);
        textView.setText(pista.getNombreTrack());

        final  Integer  posicion = position;
        btnPistaAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escuchador.pistaAnterior(posicion-1);
            }
        });

        btnPistaSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escuchador.pistaSiguiente(posicion+1);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlay.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                escuchador.pistaPlayPause( pista, progressBar);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPause.setVisibility(View.GONE);
                btnPlay.setVisibility(View.VISIBLE);
                escuchador.pistaPlayPause( pista, progressBar);
            }
        });


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public interface PistaViewPageInterface {
        void pistaAnterior(Integer position);
        void pistaSiguiente(Integer position);
        void pistaPlayPause( TopChartLocal pista, ProgressBar progressBar);

    }

}
