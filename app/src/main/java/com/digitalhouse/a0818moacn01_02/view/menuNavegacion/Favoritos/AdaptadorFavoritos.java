package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Favoritos;

import java.util.List;

public class AdaptadorFavoritos extends RecyclerView.Adapter {
    private List<Favoritos> favoritos;

    public AdaptadorFavoritos(List<Favoritos> unaListaDeFavoritos){
       favoritos=unaListaDeFavoritos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //crea la celda en java
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View viewCelda = layoutInflater.inflate(R.layout.cardview_celda_favorito,viewGroup,false);
        ViewHolderFavoritos viewHolderFavoritos = new ViewHolderFavoritos(viewCelda);
        return viewHolderFavoritos;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//une la celda con la posicion. busca la posicion y carga a la view.

        Favoritos unFavorito = favoritos.get(i);
        ViewHolderFavoritos viewHolderFavoritos = (ViewHolderFavoritos) viewHolder;
        ((ViewHolderFavoritos) viewHolder).cargarFavoritos(unFavorito);
    }

    @Override
    public int getItemCount() {
        //cantidad de elementos
        return favoritos.size();
    }

    private class ViewHolderFavoritos extends RecyclerView.ViewHolder{
        private TextView textViewViewHolderFavoritosTitulo;
        private TextView textViewViewHolderFavoritosSubtitulo;
        private ImageView imageViewHolderFavoritos;

        public ViewHolderFavoritos(@NonNull View itemView) {
            super(itemView);
            textViewViewHolderFavoritosTitulo = itemView.findViewById(R.id.tvTituloEscuchadoRecientemente);
            textViewViewHolderFavoritosSubtitulo = itemView.findViewById(R.id.tvSubTituloEscuchadoRecientemente);
            imageViewHolderFavoritos = itemView.findViewById(R.id.ivEscuchadoRecientemente);
        }

        public void cargarFavoritos (Favoritos favorito){
            textViewViewHolderFavoritosTitulo.setText(favorito.getFavoritoTexto());
            textViewViewHolderFavoritosSubtitulo.setText(favorito.getFavoritoTextoSubtitulo());
            imageViewHolderFavoritos.setImageResource(favorito.getFavoritoImagen());
        }
    }
}
