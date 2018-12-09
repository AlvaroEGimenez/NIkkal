package com.digitalhouse.a0818moacn01_02.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.ListaReproduccion;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos.OnMoveAndSwipedListenerFavorito;
import com.github.abdularis.civ.CircleImageView;

import java.net.Inet4Address;
import java.util.List;

public class ListaReproduccionAdapter extends RecyclerView.Adapter implements OnMoveAndSwipedListenerFavorito {

    private List<ListaReproduccion> listaReproduccionList;
    private Integer resources;
    private ListaReproduccionAdapterInterface escuchador;

    public ListaReproduccionAdapter(List<ListaReproduccion> listaReproduccionList, int resources, ListaReproduccionAdapterInterface escuchador) {
        this.listaReproduccionList = listaReproduccionList;
        this.resources = resources;
        this.escuchador = escuchador;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int posicion) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new ListaReproduccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int posicion) {
        ListaReproduccion listaReproduccion = listaReproduccionList.get(posicion);
        ListaReproduccionViewHolder listaReproduccionViewHolder = (ListaReproduccionViewHolder) holder;
        listaReproduccionViewHolder.cargar(listaReproduccion);
    }

    @Override
    public int getItemCount() {
        return listaReproduccionList.size();
    }

    public interface ListaReproduccionAdapterInterface {
        void seleccionLista(ListaReproduccion listaReproduccion);

        void onItemDismiss(int position);
    }

    public class ListaReproduccionViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreListaReproduccion;
        private ImageView imgAlbumListaReproduccionFavorito;

        public ListaReproduccionViewHolder(@NonNull final View itemView) {
            super(itemView);
            nombreListaReproduccion = itemView.findViewById(R.id.nombreListaReproduccion);
            imgAlbumListaReproduccionFavorito = itemView.findViewById(R.id.imgAlbumListaReproduccionFavorito);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListaReproduccion listaReproduccion = listaReproduccionList.get(getAdapterPosition());
                    escuchador.seleccionLista(listaReproduccion);
                }
            });
        }

        public void cargar(ListaReproduccion listaReproduccion) {
            nombreListaReproduccion.setText(listaReproduccion.getNombre());
            Integer posicionRandom = getPosicionRandom(listaReproduccion.getPistas().size());

            Track pista = listaReproduccion.getPistas().get(posicionRandom);
            if (pista.getAlbum() != null && pista.getAlbum().getCoverMedium() != null) {
                Glide.with(itemView.getContext()).load(pista.getAlbum().getCoverMedium()).into(imgAlbumListaReproduccionFavorito);
            }else if(pista.getArtist() != null && pista.getArtist().getPictureMedium() != null){
                Glide.with(itemView.getContext()).load(pista.getArtist().getPictureMedium()).into(imgAlbumListaReproduccionFavorito);
            }else {
                Glide.with(itemView.getContext()).load(pista.getImagenAlbum()).into(imgAlbumListaReproduccionFavorito);
            }
        }
    }


    private Integer getPosicionRandom(Integer cantidad){
        return  (int) (Math.random() * cantidad);
    }

    public void setListaReproduccionList(List<ListaReproduccion> listaReproduccionList) {
        this.listaReproduccionList = listaReproduccionList;
        notifyDataSetChanged();
    }

    public ListaReproduccion eliminarPista(Integer posicion) {
        ListaReproduccion listaReproduccion = listaReproduccionList.get(posicion);
        listaReproduccionList.remove(listaReproduccion);
        notifyDataSetChanged();
        return listaReproduccion;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        escuchador.onItemDismiss(position);
    }

}
