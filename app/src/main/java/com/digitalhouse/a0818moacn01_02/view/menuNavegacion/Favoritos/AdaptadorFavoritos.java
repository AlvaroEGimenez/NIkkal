package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Favorito;
import com.digitalhouse.a0818moacn01_02.model.RadioDeezer;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
//import com.digitalhouse.a0818moacn01_02.view.categorias.SugerenciasFragment;


import java.util.List;

public class AdaptadorFavoritos extends RecyclerView.Adapter {
    //atributos. no tomamos como Atributo una lista de favoritos ya que aún no manejamos base de datos donde almacenar el favorito del usuario
    private List<Favorito> favoritoList;
    private FavoritosAdapterInterface escuchador; //para pasar datos al fragment

    //CONSTRUCTOR
    public AdaptadorFavoritos(List<Favorito> favoritoList, FavoritosAdapterInterface escuchador) {
        this.favoritoList = favoritoList;
        this.escuchador = escuchador;
    }

    //SETTER
    public void setFavorito(List<Favorito> favoritoList) {
        this.favoritoList = favoritoList;
        notifyDataSetChanged();
    }

    public void setEscuchador(FavoritosAdapterInterface escuchador) {
        this.escuchador = escuchador;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //crea la celda en java
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewCelda = layoutInflater.inflate(R.layout.cardview_celda_favorito, viewGroup, false);
        ViewHolderRadioFavorito viewHolderAlbumFavoritos = new ViewHolderRadioFavorito(viewCelda);
        return viewHolderAlbumFavoritos;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //une la celda con la posicion. busca la posicion y carga a la view.
        Favorito favorito = favoritoList.get(i);
        AdaptadorFavoritos.ViewHolderRadioFavorito viewHolderArtistaFavorito = (AdaptadorFavoritos.ViewHolderRadioFavorito) viewHolder;
        viewHolderArtistaFavorito.cargarTrackFavoritos(favorito);

    }

    @Override
    public int getItemCount() {
        //cantidad de elementos
        return favoritoList.size();
    }


    //VIEWHOLDER
    private class ViewHolderRadioFavorito extends RecyclerView.ViewHolder {
        //ATRIBUTOS
        private TextView textViewViewHolderFavoritosTitulo;
        private ImageView imageViewHolderFavoritos;

        //CONSTRUCTOR
        public ViewHolderRadioFavorito(@NonNull View itemView) {
            super(itemView);
            textViewViewHolderFavoritosTitulo = itemView.findViewById(R.id.tvTituloEscuchadoRecientemente);
            imageViewHolderFavoritos = itemView.findViewById(R.id.ivEscuchadoRecientemente);

            //CLICK
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Favorito favorito = favoritoList.get(getAdapterPosition());
                    escuchador.onClickFavorito(favorito);
                }
            });
        }

        //MÉTODO
        public void cargarTrackFavoritos(Favorito favorito) {
            textViewViewHolderFavoritosTitulo.setText(favorito.getTitulo());
            Glide.with(itemView.getContext()).load(favorito.getUrlImagen()).into(imageViewHolderFavoritos);
        }
    }

    //CLASE ANÓNIMA
    public interface
    FavoritosAdapterInterface {
        void onClickFavorito(Favorito favorito);
    }

}
