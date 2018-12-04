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
import com.digitalhouse.a0818moacn01_02.Utils.FavoritoFirebase;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.Favorito;
import com.digitalhouse.a0818moacn01_02.model.Track;

import java.util.Collections;
import java.util.List;

public class PistaAlbumRecyclerView extends RecyclerView.Adapter implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private List<Track> pistas;
    private Integer resources;
    private Activity activity;
    private FavoritoFirebase favoritoFirebase;

    private PistaAdapterInterface escuchador;

    public PistaAlbumRecyclerView(List<Track> pistas, int resources, Activity activity, PistaAdapterInterface escuchador, FavoritoFirebase favoritoFirebase) {
        this.pistas = pistas;
        this.resources = resources;
        this.activity = activity;
        this.escuchador = escuchador;
        this.favoritoFirebase = favoritoFirebase;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new PistaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int posicion) {
        Track pista = pistas.get(posicion);
        PistaViewHolder pistaViewHolder = (PistaViewHolder) holder;
        pistaViewHolder.cargar(pista);
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
    public void onItemDismiss(int posicion, int direction) {
        pistas.remove(posicion);
        notifyItemRemoved(posicion);
        notifyItemInserted(posicion);
    }

    public interface PistaAdapterInterface {
        void favoritoListener(Track pista, ImageView favoritoPista);

        void playListListener(Track pista);

        void compartirListener(Track pista);

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
            compartirPista = itemView.findViewById(R.id.share);
            linearLayout = itemView.findViewById(R.id.linealForeground);

            favoritoPista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Track pista = pistas.get(getAdapterPosition());
                    escuchador.favoritoListener(pista, favoritoPista);
                }
            });

            compartirPista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Track pista = pistas.get(getAdapterPosition());
                    escuchador.compartirListener(pista);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    escuchador.pistaViewPageListener(getAdapterPosition(), itemView);
                }
            });

        }

        public void cargar(Track pista) {
            tvNombreAlbumTemaPista.setText(pista.getTitle());
            tvNombreArtistaTemaPista.setText(pista.getArtist().getName());
            setFavoritoPistaFirebase(pista);
        }


        private void setFavoritoPistaFirebase(final Track pista) {
            favoritoFirebase.getFavoritoPorId(new ResultListener<Favorito>() {
                @Override
                public void finish(Favorito favorito) {
                    if (favorito != null) {
                        favoritoPista.setImageResource(R.drawable.ic_favorite_seleccionado);
                        pista.setFavorito(Boolean.TRUE);
                    } else {
                        favoritoPista.setImageResource(R.drawable.ic_favorite_no_seleccion);
                        pista.setFavorito(Boolean.FALSE);
                    }
                }
            }, pista.getId());

        }
    }
}
