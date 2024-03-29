package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Buscar;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.github.abdularis.civ.CircleImageView;

import java.util.List;


public class AdaptadorBusquedaArtistas extends RecyclerView.Adapter {

    private List<ArtistDeezer> listaBusqueda;
    private Context context;


    public AdaptadorBusquedaArtistas(List<ArtistDeezer> listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.modelo_busqueda_artista, viewGroup, false);
        AdaptadorBusquedaArtistas.ViewHolderBusquedaArtista viewHolderBusqueda = new AdaptadorBusquedaArtistas.ViewHolderBusquedaArtista(view);
        return viewHolderBusqueda;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int posicion) {
        AdaptadorBusquedaArtistas.ViewHolderBusquedaArtista viewHolderBusqueda = (AdaptadorBusquedaArtistas.ViewHolderBusquedaArtista) viewHolder;
        ArtistDeezer trackBusqueda = listaBusqueda.get(posicion);
        viewHolderBusqueda.bind(trackBusqueda);



    }

    @Override
    public int getItemCount() {
        return listaBusqueda.size();
    }


    public class ViewHolderBusquedaArtista extends RecyclerView.ViewHolder {
        private TextView textViewBusqueda;
        private CircleImageView imageViewArtista;


        public ViewHolderBusquedaArtista(@NonNull View itemView) {
            super(itemView);
            textViewBusqueda = itemView.findViewById(R.id.nombreArtistaCardViewBusqueda);
            imageViewArtista = itemView.findViewById(R.id.imagenArtistaCardViewBusqueda);


        }

        public void bind(ArtistDeezer track) {
            textViewBusqueda.setText(track.getName());
            Glide.with(context).load(track.getPictureMedium()).into(imageViewArtista);

        }
    }
}
