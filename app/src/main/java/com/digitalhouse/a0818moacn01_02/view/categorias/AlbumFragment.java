package com.digitalhouse.a0818moacn01_02.view.categorias;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.ArtistAlbumController;
import com.digitalhouse.a0818moacn01_02.controller.FavoritoController;
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;
import com.digitalhouse.a0818moacn01_02.model.Favorito;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.AlbumAdapterRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment implements AlbumAdapterRecyclerView.AlbumAdapterInterface {
    public static final String KEY_IMAGEN_ARTISTA = "imagenArtista";
    public static final String KEY_NOMBRE_ARTISTA = "nombreArtista";
    public static final String KEY_ID_ARTISTA = "idArtista";
    public static final String KEY_FAVORITO_ARTISTA = "idFavoritoArtista";

    private PistaAlbumFragment pistaAlbumFragment = new PistaAlbumFragment();
    private Integer idArtist;
    private List<AlbumDeezer> albumDeezerList = new ArrayList<>();
    private View view;
    private ProgressBar pbAlbum;
    private LinearLayout conatiner;
    private MainActivity parent;
    private Boolean favoritoArtista;
    private ImageView btnFavorito;
    private FavoritoController favoritoControllerArtista;
    private String urlImagenCabecera;
    private String nombreGenero;

    public AlbumFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (MainActivity) getActivity();
        favoritoControllerArtista = new FavoritoController(FavoritoController.KEY_TIPO_ARTISTA, getContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        this.view = view;
        conatiner = view.findViewById(R.id.categoriaAlbumContainer);
        pbAlbum = view.findViewById(R.id.pbAlbum);

        btnFavorito = view.findViewById(R.id.btnFavoritoArtista);
        btnFavorito.setOnClickListener(favoritoListener);
        ImageView imagenArtista = view.findViewById(R.id.imagenArtista);
        Toolbar tvCabeceraArtista = view.findViewById(R.id.tvCabeceraArtista);
        view.findViewById(R.id.rvAlbum);

        Bundle bundle = getArguments();
        urlImagenCabecera = bundle.getString(KEY_IMAGEN_ARTISTA);
        nombreGenero = bundle.getString(KEY_NOMBRE_ARTISTA);
        idArtist = bundle.getInt(KEY_ID_ARTISTA);

        favoritoArtista = bundle.getBoolean(KEY_FAVORITO_ARTISTA);

        cargarArtistAlbum();
        cargarImagen(imagenArtista, urlImagenCabecera);
        tvCabeceraArtista.setTitle(nombreGenero);

        inisializacionFavoritoArtista(btnFavorito);
        return view;
    }

    private void cargarArtistAlbum() {
        ArtistAlbumController artistAlbumController = new ArtistAlbumController();
        artistAlbumController.getArtistAlbum(new ResultListener<List<AlbumDeezer>>() {
            @Override
            public void finish(List<AlbumDeezer> resultado) {
                albumDeezerList = resultado;
                crearAlbumRecyclerView(view, R.id.rvAlbum);
                pbAlbum.setVisibility(View.GONE);
                conatiner.setVisibility(View.VISIBLE);

            }
        }, getContext(), idArtist);
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(getContext()).load(url).into(imageView);
    }

    private void crearAlbumRecyclerView(View view, Integer idLayout) {
        RecyclerView recyclerView = view.findViewById(idLayout);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        AlbumAdapterRecyclerView albumAdapterRecyclerView = new AlbumAdapterRecyclerView(albumDeezerList, R.layout.cardview_album, getActivity(), this);

        recyclerView.setAdapter(albumAdapterRecyclerView);
    }


    @Override
    public void cambiarDeActividad(AlbumDeezer album) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Bundle bundle = new Bundle();
        bundle.putString(PistaAlbumFragment.KEY_IMAGEN_CABECERA_ALBUM_PISTA, album.getCoverMedium());
        bundle.putString(PistaAlbumFragment.KEY_NOMBRE_CABECERA_ALBUM_PISTA, album.getTitle());
        bundle.putInt(PistaAlbumFragment.KEY_PISTA_ID_ALBUM_PISTA, album.getId());
        bundle.putString(PistaAlbumFragment.KEY_CATEGORIA, "pistaAlbum");

        pistaAlbumFragment.setArguments(bundle);
        mainActivity.reemplazarFragment(pistaAlbumFragment, R.id.genero_fragment);
    }

    private void inisializacionFavoritoArtista(final ImageView btnFavorito) {
        favoritoControllerArtista.getFavoritoPorId(new ResultListener<Favorito>() {
            @Override
            public void finish(Favorito favorito) {
                btnFavorito.setImageResource(R.drawable.ic_favorite_black_24dp);
                favoritoArtista = Boolean.TRUE;
            }
        }, idArtist);

    }

    View.OnClickListener favoritoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (parent.estaLogeado(getContext())) {
                setFavoritoPista();
            }

        }
    };

    private void setFavoritoPista() {
        if (favoritoArtista) {
            favoritoArtista = Boolean.FALSE;
            cargarImagen(R.drawable.ic_favorite_no_seleccion);
            favoritoControllerArtista.eliminar(idArtist);
            Animation animation;
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.blink_limited);
            btnFavorito.setAnimation(animation);
        } else {
            cargarImagen(R.drawable.ic_favorite_black_24dp);
            favoritoArtista = Boolean.TRUE;
            Animation animation;
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            btnFavorito.setAnimation(animation);
            favoritoControllerArtista.agregar(idArtist, urlImagenCabecera, nombreGenero, "");

        }
    }

    private void cargarImagen(Integer idDrawable) {
        Glide.with(getContext()).load(idDrawable).into(btnFavorito);
    }
}
