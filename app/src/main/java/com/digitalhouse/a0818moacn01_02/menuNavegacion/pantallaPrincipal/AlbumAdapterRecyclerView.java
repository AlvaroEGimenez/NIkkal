package com.digitalhouse.a0818moacn01_02.menuNavegacion.pantallaPrincipal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhouse.a0818moacn01_02.InicioActivity;
import com.digitalhouse.a0818moacn01_02.MainActivity;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.categorias.GeneroActivity;
import com.digitalhouse.a0818moacn01_02.categorias.MasEscuchado;
import com.digitalhouse.a0818moacn01_02.categorias.SugerenciaActivity;
import com.digitalhouse.a0818moacn01_02.model.Album;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapterRecyclerView extends RecyclerView.Adapter<AlbumAdapterRecyclerView.AlbumViewHolder> {
    private List<Album> albunes;
    private Integer resources;
    private Activity activity;

    public AlbumAdapterRecyclerView(ArrayList<Album> albunes, int resources, Activity activity) {
        this.albunes = albunes;
        this.resources = resources;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resources, viewGroup, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlbumViewHolder albumViewHolder, int posicion) {
        final Album album = albunes.get(posicion);
        albumViewHolder.tituloCardView.setText(album.getNombre());

        cargarImagen(albumViewHolder.imagenAlbumCardView, album.getImagen());
        albumViewHolder.imagenAlbumCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarActividad(album);
                Toast.makeText(activity, album.getNombre().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return albunes.size();
    }


    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagenAlbumCardView;
        private TextView tituloCardView;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);

            imagenAlbumCardView = itemView.findViewById(R.id.imagenAlbum);
            tituloCardView = itemView.findViewById(R.id.titulo);
        }
    }

    private void cargarImagen(ImageView imageView, String url) {
        Picasso.Builder picassoBuilder = new Picasso.Builder(activity);
        Picasso picasso = picassoBuilder.build();
        picasso.load(url).error(R.drawable.nikkal).into(imageView);

    }


    private void llamarActividad( Album album ) {
        String genero = album.getGenero();
        Intent intent = null;

        switch(genero){
            case AlbumFragment.KEY_GENERO:
                intent = new Intent(activity, GeneroActivity.class);
                break;
            case AlbumFragment.KEY_SUGERENCIA:
                intent = new Intent(activity, SugerenciaActivity.class);
                break;
            case AlbumFragment.KEY_MAS_ESCUCHADO:
                intent = new Intent(activity, MasEscuchado.class);
        }

        if(intent != null)
        activity.startActivity(intent);
    }

}
