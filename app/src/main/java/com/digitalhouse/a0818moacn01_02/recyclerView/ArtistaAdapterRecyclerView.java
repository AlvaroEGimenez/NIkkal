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
import com.digitalhouse.a0818moacn01_02.model.Artista;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistaAdapterRecyclerView  extends RecyclerView.Adapter {

    private List<Artista> artistas;
    private Integer resources;
    private Activity activity;
    private ArtistaAdapterInterface escuchador;

    public ArtistaAdapterRecyclerView( List<Artista> artistas, int resources, Activity activity, ArtistaAdapterInterface escuchador) {
        this.artistas = artistas;
        this.resources = resources;
        this.activity = activity;
        this.escuchador = escuchador;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int posicion) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new ArtistaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int posicion) {
        Artista artista = artistas.get(posicion);
        ArtistaViewHolder artistaViewHolder = (ArtistaViewHolder) holder;
        artistaViewHolder.cargar(artista);
    }

    @Override
    public int getItemCount() {
        return artistas.size();
    }

    public interface ArtistaAdapterInterface {
        void cambiarDeActividad(Artista artista);
    }


    public class ArtistaViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagenArtistaCardView;
        private TextView nombreArtistaCardView;

        public ArtistaViewHolder(@NonNull final View itemView) {
            super(itemView);

            imagenArtistaCardView = itemView.findViewById(R.id.imagenArtistaCardView);
            nombreArtistaCardView = itemView.findViewById(R.id.nombreArtistaCardView);

            imagenArtistaCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Artista artista = artistas.get(getAdapterPosition());
                    escuchador.cambiarDeActividad(artista);
                }
            });
        }

        public void cargar(Artista artista) {
            nombreArtistaCardView.setText(artista.getNombre());
            cargarImagen(imagenArtistaCardView, artista.getImagen());
        }
    }

    private void cargarImagen(ImageView imageView, String url) {
        Picasso.Builder picassoBuilder = new Picasso.Builder(activity);
        Picasso picasso = picassoBuilder.build();
        picasso.load(url).error(R.drawable.nikkal).into(imageView);

    }
}
