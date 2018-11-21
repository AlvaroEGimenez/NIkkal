package com.digitalhouse.a0818moacn01_02.view.adapter.listaReproduccion;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.RecyclerItemTouchHelper;

import java.util.Collections;
import java.util.List;

public class PistaListaReproduccionAdapter extends RecyclerView.Adapter implements OnMoveAndSwipedListener {
    private List<Track> pistas;
    private Integer resources;
    private Activity activity;

    private PistaListaReproduccionAdapterInterface escuchador;

    public PistaListaReproduccionAdapter(List<Track> pistas, int resources, Activity activity, PistaListaReproduccionAdapterInterface escuchador) {
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
    }

    public interface PistaListaReproduccionAdapterInterface {
        void pistaListaReproduccionAdapterInterface(Integer posicion);
    }

    public class PistaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombreAlbumTemaPista;
        private TextView tvNombreArtistaTemaPista;
        public ImageView imgAlbum;


        public PistaViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvNombreAlbumTemaPista = itemView.findViewById(R.id.tvNombreAlbumTemaPistaListaReprod);
            tvNombreArtistaTemaPista = itemView.findViewById(R.id.tvNombreArtistaTemaPistaListaReprod);

            imgAlbum = itemView.findViewById(R.id.imgAlbumListaReproduccion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    escuchador.pistaListaReproduccionAdapterInterface(getAdapterPosition());
                }
            });

        }

        public void cargar(Track pista) {

            tvNombreAlbumTemaPista.setText(pista.getTitle());
            tvNombreArtistaTemaPista.setText(pista.getArtist() != null ? pista.getArtist().getName() : "");
            if (pista.getAlbum() != null && pista.getAlbum().getCoverMedium() != null) {
                Glide.with(itemView.getContext()).load(pista.getAlbum().getCoverMedium()).into(imgAlbum);
            }else if(pista.getArtist() != null && pista.getArtist().getPictureMedium() != null){
                Glide.with(itemView.getContext()).load(pista.getArtist().getPictureMedium()).into(imgAlbum);
            }else {
                Glide.with(itemView.getContext()).load(pista.getImagenAlbum()).into(imgAlbum);
            }

        }
    }

}
