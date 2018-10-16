package com.digitalhouse.a0818moacn01_02.recyclerView;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Pista;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PistaAlbumRecyclerView extends RecyclerView.Adapter {
    private List<Pista> pistas;
    private Integer resources;
    private Activity activity;

    private PistaAdapterInterface escuchador;

    public PistaAlbumRecyclerView(List<Pista> pistas, int resources, Activity activity, PistaAdapterInterface escuchador) {
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
        Pista pista = pistas.get(posicion);
        PistaViewHolder pistaViewHolder = (PistaViewHolder) holder;
        pistaViewHolder.cargar(pista);
    }

    @Override
    public int getItemCount() {
        return pistas.size();
    }

    public interface PistaAdapterInterface {
        void favoritoListener(Pista pista, ImageView favoritoPista);
        void playListListener(Pista pista);
        void compartirListener(Pista pista);
    }

    public class PistaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombreAlbumTemaPista;
        private TextView tvNombreArtistaTemaPista;
        private ImageView favoritoPista;
        private ImageView agregarPistaPlayList;
        private ImageView compartirPista;


        public PistaViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvNombreAlbumTemaPista = itemView.findViewById(R.id.tvNombreAlbumTemaPista);
            tvNombreArtistaTemaPista = itemView.findViewById(R.id.tvNombreArtistaTemaPista);
            favoritoPista = itemView.findViewById(R.id.favoritoPista);
            agregarPistaPlayList = itemView.findViewById(R.id.agregarPistaPlayList);
            compartirPista = itemView.findViewById(R.id.compartirPista);

            favoritoPista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Pista pista = pistas.get(getAdapterPosition());
                    escuchador.favoritoListener(pista, favoritoPista);
                }
            });

            agregarPistaPlayList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Pista pista = pistas.get(getAdapterPosition());
                    escuchador.playListListener(pista);
                }
            });

            compartirPista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Pista pista = pistas.get(getAdapterPosition());
                    escuchador.compartirListener(pista);
                }
            });
        }

        public void cargar(Pista pista) {
            tvNombreAlbumTemaPista.setText(pista.getNombre());
            tvNombreArtistaTemaPista.setText("asdsad"); //pista.getAlbum().getNombre()

            if(pista.getFavorito()){
                cargarImagen(favoritoPista, R.drawable.ic_favorite_seleccionado);
            }else{
                cargarImagen(favoritoPista, R.drawable.ic_favorite_no_seleccion);
            }

            cargarImagen(agregarPistaPlayList, R.drawable.ic_agregar_play_list);
            cargarImagen(compartirPista, R.drawable.ic_compartir);
        }
    }

    private void cargarImagen(ImageView imageView, Integer idDrawable) {
        Picasso.Builder picassoBuilder = new Picasso.Builder(activity);
        Picasso picasso = picassoBuilder.build();
        picasso.load(idDrawable).into(imageView);
    }
}
