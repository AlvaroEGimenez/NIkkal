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
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.model.RadioDeezer;
import com.digitalhouse.a0818moacn01_02.model.Track;

import java.util.List;

public class AdaptadorFavoritos extends RecyclerView.Adapter {
    //atributos. no tomamos como Atributo una lista de favoritos ya que aún no manejamos base de datos donde almacenar el favorito del usuario
    private List<RadioDeezer> radioDeezerList;
    //private AartistaFavoritodAdapterInterface escuchador; COMENTADO MIENTRAS HAGO COMUNICACION ENTRE FRAGMENTS

    //CONSTRUCTOR


    public AdaptadorFavoritos(List<RadioDeezer> radioDeezerList){ //AartistaFavoritodAdapterInterface escuchador) {COMENTADO MIENTRAS HAGO COMUNICACION ENTRE FRAGMENTS
        this.radioDeezerList = radioDeezerList;
        //this.escuchador = escuchador;
    }

    //GETTER
    public List<RadioDeezer> getRadioDeezerList() {
        return radioDeezerList;
    }

    //SETTER
    public void setRadioDeezerList(List<RadioDeezer> radioDeezerList) {
        this.radioDeezerList = radioDeezerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //crea la celda en java
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewCelda = layoutInflater.inflate(R.layout.cardview_celda_favorito,viewGroup,false);
        ViewHolderRadioFavorito viewHolderAlbumFavoritos = new ViewHolderRadioFavorito(viewCelda);
        return viewHolderAlbumFavoritos;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //une la celda con la posicion. busca la posicion y carga a la view.
        RadioDeezer unRadioDezzer = radioDeezerList.get(i);
        AdaptadorFavoritos.ViewHolderRadioFavorito viewHolderArtistaFavorito = (AdaptadorFavoritos.ViewHolderRadioFavorito) viewHolder;
        viewHolderArtistaFavorito.cargarTrackFavoritos(unRadioDezzer);

    }

    @Override
    public int getItemCount() {
        //cantidad de elementos
        return radioDeezerList.size();
    }

    //CLASE ANÓNIMA----------TODAVIA NO USO PQ NO HE HECHO COMUNICACION ENTRE FRAGMENTS
    public interface AartistaFavoritodAdapterInterface {
        void cambiarDeActividad(RadioDeezer radioDeezer);
    }


    //VIEWHOLDER
    private class ViewHolderRadioFavorito extends RecyclerView.ViewHolder{

        //ATRIBUTOS
        private TextView textViewViewHolderFavoritosTitulo;
        private TextView textViewViewHolderFavoritosSubtitulo;
        private ImageView imageViewHolderFavoritos;

        //CONSTRUCTOR
        public ViewHolderRadioFavorito(@NonNull View itemView) {
            super(itemView);
            textViewViewHolderFavoritosTitulo = itemView.findViewById(R.id.tvTituloEscuchadoRecientemente);
            textViewViewHolderFavoritosSubtitulo = itemView.findViewById(R.id.tvSubTituloEscuchadoRecientemente);
            imageViewHolderFavoritos = itemView.findViewById(R.id.ivEscuchadoRecientemente);
        }

        //MÉTODO
        public void cargarTrackFavoritos (RadioDeezer radioDeezer){
            textViewViewHolderFavoritosTitulo.setText(radioDeezer.getTitle());
            textViewViewHolderFavoritosSubtitulo.setText(radioDeezer.getType());
            Glide.with(itemView.getContext()).load(radioDeezer.getPicture()).into(imageViewHolderFavoritos);
            //imageViewHolderFavoritos.setImageResource(favorito.getFavoritoImagen());
        }
    }
}
