package com.digitalhouse.a0818moacn01_02.view.categorias;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Pista;
import com.digitalhouse.a0818moacn01_02.view.recyclerView.PistaAdapterViewPage;
import com.digitalhouse.a0818moacn01_02.view.recyclerView.PistaAlbumRecyclerView;

import java.util.ArrayList;

import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

public class PistaAlbumFragment extends Fragment implements PistaAlbumRecyclerView.PistaAdapterInterface, PistaAdapterViewPage.PistaViewPageInterface {
    public static final String KEY_IMAGEN_CABECERA_ALBUM_PISTA = "imgCabeceraAlbumPista";
    public static final String KEY_NOMBRE_CABECERA_ALBUM_PISTA = "nombreCabeceraAlbumPista";

    private ImageView imgCabeceraAlbumPista;
    private Toolbar toolbaarNombreCabeceraAlbumPista;

    private ArrayList<Pista> pistas;
    AutoScrollViewPager autoScrollViewPager;
    public PistaAlbumFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pista_album, container, false);

        imgCabeceraAlbumPista = view.findViewById(R.id.imgCabeceraAlbumPista);
        toolbaarNombreCabeceraAlbumPista = view.findViewById(R.id.toolbaarNombreCabeceraAlbumPista);

        Bundle bundle = getArguments();
        String urlImagenCabecera = bundle.getString(KEY_IMAGEN_CABECERA_ALBUM_PISTA);
        String nombreCabeceraPistaAlbum = bundle.getString(KEY_NOMBRE_CABECERA_ALBUM_PISTA);

        cargarImagen(imgCabeceraAlbumPista, urlImagenCabecera);
        toolbaarNombreCabeceraAlbumPista.setTitle(nombreCabeceraPistaAlbum);
        crearAlbumRecyclerView(view, R.id.rvPistaAlbum);

        return view;
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(getContext()).load(url).into(imageView);
    }

    private void crearAlbumRecyclerView(View view, Integer idLayout) {
        RecyclerView recyclerView = view.findViewById(idLayout);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        cargarPista();
        PistaAlbumRecyclerView pistaAlbumRecyclerView = new PistaAlbumRecyclerView(this.pistas, R.layout.cardview_pista_album, getActivity(), this);

        recyclerView.setAdapter(pistaAlbumRecyclerView);
    }

    public void cargarPista() {
        ArrayList<Pista> pistas = new ArrayList<>();
        pistas.add(new Pista("Yalalalala", 132, Boolean.FALSE));
        pistas.add(new Pista("yololo", 132, Boolean.FALSE));
        pistas.add(new Pista("pepepe", 132, Boolean.TRUE));
        pistas.add(new Pista("titititt", 132, Boolean.FALSE));
        pistas.add(new Pista("lelelelel", 132, Boolean.TRUE));
        pistas.add(new Pista("titititt", 132, Boolean.FALSE));
        pistas.add(new Pista("lelelelel", 132, Boolean.TRUE));
        pistas.add(new Pista("titititt", 132, Boolean.FALSE));
        pistas.add(new Pista("lelelelel", 132, Boolean.TRUE));

        this.pistas =  pistas;
    }


    @Override
    public void favoritoListener(Pista pista, ImageView favoritoPista) {
        pista.setFavorito(pista.getFavorito() ? Boolean.FALSE : Boolean.TRUE);
        if (pista.getFavorito()) {
            cargarImagen(favoritoPista, R.drawable.ic_favorite_seleccionado);
        } else {
            cargarImagen(favoritoPista, R.drawable.ic_favorite_no_seleccion);
        }
    }

    @Override
    public void playListListener(Pista pista) {
        Toast.makeText(getContext(), "Se ha agregado al playList: " + pista.getNombre(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void compartirListener(Pista pista) {
        Toast.makeText(getContext(), "Compartir pista", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pistaViewPageListener(Integer posicion) {
        Dialog dialog = new Dialog(getContext(), R.style.pistaViewPage);
        dialog.setContentView(R.layout.pista_view_page_content);

        PistaAdapterViewPage pistaAdapterViewPage = new PistaAdapterViewPage(pistas, getContext(), this);
        autoScrollViewPager = dialog.findViewById(R.id.pistaViewPagerScroll);
        autoScrollViewPager.setAdapter(pistaAdapterViewPage);
        autoScrollViewPager.setCurrentItem(posicion);
        dialog.show();


    }

    private void cargarImagen(ImageView imageView, Integer idDrawable) {
        Glide.with(getContext()).load(idDrawable).into(imageView);
    }

    @Override
    public void pistaAnterior(Integer position) {
        autoScrollViewPager.setCurrentItem(position);
    }

    @Override
    public void pistaSiguiente(Integer position) {
        autoScrollViewPager.setCurrentItem(position);
    }

    @Override
    public void pistaPlayPause(Pista pista, ProgressBar progressBar) {

    }
}
