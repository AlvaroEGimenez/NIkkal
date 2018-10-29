package com.digitalhouse.a0818moacn01_02.view.adapter.pista;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.TopChartLocal;

import java.util.Collections;
import java.util.List;

public class PistaAlbumRecyclerView extends RecyclerView.Adapter implements  RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    private List<TopChartLocal> pistas;
    private Integer resources;
    private Activity activity;

    private PistaAdapterInterface escuchador;

    public PistaAlbumRecyclerView(List<TopChartLocal> pistas, int resources, Activity activity, PistaAdapterInterface escuchador) {
        this.pistas = pistas;
        this.resources = resources;
        this.activity = activity;
        this.escuchador = escuchador;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new PistaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int posicion) {
        TopChartLocal topChartLocal = pistas.get(posicion);
        PistaViewHolder pistaViewHolder = (PistaViewHolder) holder;
        pistaViewHolder.cargar(topChartLocal);
    }

    @Override
    public int getItemCount() {
        return pistas.size();
    }

    @Override
    public boolean onItemMove(int desdePosicion, int hastaPosicion) {
        Collections.swap(pistas, desdePosicion, hastaPosicion);
        notifyItemMoved(desdePosicion, hastaPosicion);
        return true;
    }

    @Override
    public void onItemDismiss(int posicion) {
        pistas.remove(posicion);
        notifyItemRemoved(posicion);
        notifyItemInserted(posicion);
    }

    public interface PistaAdapterInterface {
        void favoritoListener(TopChartLocal pista, ImageView favoritoPista);
        void playListListener(TopChartLocal pista);
        void compartirListener(TopChartLocal pista);
        void pistaViewPageListener(Integer posicion, View itemView);
    }

    public class PistaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombreAlbumTemaPista;
        private TextView tvNombreArtistaTemaPista;
        private ImageView favoritoPista;
        private ImageView compartirPista;
        public LinearLayout linearLayout;


        public PistaViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvNombreAlbumTemaPista = itemView.findViewById(R.id.tvNombreAlbumTemaPista);
            tvNombreArtistaTemaPista = itemView.findViewById(R.id.tvNombreArtistaTemaPista);
            favoritoPista = itemView.findViewById(R.id.favoritoPista);
            compartirPista = itemView.findViewById(R.id.masOpciones);
            linearLayout = itemView.findViewById(R.id.linealForeground);

            favoritoPista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TopChartLocal pista = pistas.get(getAdapterPosition());
                    escuchador.favoritoListener(pista, favoritoPista);
                }
            });

            compartirPista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TopChartLocal topChartLocal = pistas.get(getAdapterPosition());
                    escuchador.compartirListener(topChartLocal);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    escuchador.pistaViewPageListener(getAdapterPosition(), itemView);
                }
            });

        }

        public void cargar(TopChartLocal pista) {
            tvNombreAlbumTemaPista.setText(pista.getNombreTrack());
            tvNombreArtistaTemaPista.setText(pista.getNombreArtista()); //pista.getAlbum().getNombre()
            favoritoPista.setImageResource(pista.getFavorito() ? R.drawable.ic_favorite_seleccionado : R.drawable.ic_favorite_no_seleccion);

        }
    }

}
