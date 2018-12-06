package com.digitalhouse.a0818moacn01_02.view.categorias;


import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.FavoritoFirebase;
import com.digitalhouse.a0818moacn01_02.Utils.ReproducirMp3;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.TrackListController;
import com.digitalhouse.a0818moacn01_02.controller.TracksController;
import com.digitalhouse.a0818moacn01_02.model.Favorito;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.ReproductorActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.PistaAdapterViewPage;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.PistaAlbumRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.RecyclerItemTouchHelper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

public class PistaAlbumFragment extends Fragment implements PistaAlbumRecyclerView.PistaAdapterInterface, PistaAdapterViewPage.PistaViewPageInterface, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    public static final String KEY_IMAGEN_CABECERA_ALBUM_PISTA = "imgCabeceraAlbumPista";
    public static final String KEY_NOMBRE_CABECERA_ALBUM_PISTA = "nombreCabeceraAlbumPista";
    public static final String KEY_PISTA_ID_ALBUM_PISTA = "pistaIdAlbumPista";
    public static final String KEY_FAVORITO_ALBUM = "idFavoritoAlbum";
    public static final String KEY_CATEGORIA = "categoria";

    private ImageView imgCabeceraAlbumPista;
    private Toolbar toolbaarNombreCabeceraAlbumPista;
    private PistaAlbumRecyclerView pistaAlbumRecyclerView;
    private PistaAdapterViewPage pistaAdapterViewPage;
    private AutoScrollViewPager autoScrollViewPager;
    private RecyclerView recyclerView;
    private View view;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Track pistaActual;
    private MainActivity parent;
    private ProgressBar progressBar;
    private List<Track> pistas = new ArrayList<>();
    private FloatingActionButton btnReproducirAlbum;
    private Boolean reprducirAlbum = Boolean.FALSE;
    private String urlImagenCabecera;
    private Integer albumId;
    private Boolean favoritoAlbum;
    private FloatingActionButton btnFavorito;
    private FavoritoFirebase favoritoFirebasePista;
    private FavoritoFirebase favoritoFirebaseAlbum;

    public PistaAlbumFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (MainActivity) getActivity();
        favoritoFirebasePista = new FavoritoFirebase(FavoritoFirebase.KEY_TIPO_PISTA);
        favoritoFirebaseAlbum = new FavoritoFirebase(FavoritoFirebase.KEY_TIPO_ALBUM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pista_album, container, false);

        imgCabeceraAlbumPista = view.findViewById(R.id.imgCabeceraAlbumPista);
        toolbaarNombreCabeceraAlbumPista = view.findViewById(R.id.toolbaarNombreCabeceraAlbumPista);
        btnReproducirAlbum = view.findViewById(R.id.btnReproducirAlbumPista);
        btnReproducirAlbum.setOnClickListener(resproducirAlbumListener);
        btnFavorito = view.findViewById(R.id.btnFavoritoAlbum);
        btnFavorito.setOnClickListener(favoritoAlbumListener);

        Bundle bundle = getArguments();
        urlImagenCabecera = bundle.getString(KEY_IMAGEN_CABECERA_ALBUM_PISTA);
        String nombreCabeceraPistaAlbum = bundle.getString(KEY_NOMBRE_CABECERA_ALBUM_PISTA);
        albumId = bundle.getInt(KEY_PISTA_ID_ALBUM_PISTA);
        favoritoAlbum = bundle.getBoolean(KEY_FAVORITO_ALBUM);
        String categoria = bundle.getString(KEY_CATEGORIA);

        cargarImagen(imgCabeceraAlbumPista, urlImagenCabecera);
        toolbaarNombreCabeceraAlbumPista.setTitle(nombreCabeceraPistaAlbum);
        inisializacionFavoritoALbum(btnFavorito);
        crearAlbumRecyclerView(R.id.rvPistaAlbum);

        if ("sugerencia".equals(categoria)) {
            cargarPistaSugerencia(albumId);
        } else if ("pistaAlbum".equals(categoria)) {
            cargarPista(albumId);
        }

        mediaPlayer = parent.getMediaPlayer();

        return view;
    }


    private void cargarPistaSugerencia(Integer idPista) {

        TrackListController trackListController = new TrackListController();
        trackListController.getTraksList(new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                cargarFavoritosAdapter(resultado);
            }
        }, getContext(), idPista);
    }

    private void cargarPista(Integer idPista) {
        TracksController tracksController = new TracksController();
        tracksController.getPistas(new ResultListener<List<Track>>() {
            @Override
            public void finish(final List<Track> pistas) {
                cargarFavoritosAdapter(pistas);
            }
        }, getContext(), idPista);
    }

    public void cargarFavoritosAdapter(final List<Track> pistas) {
        favoritoFirebasePista.getLista(new ResultListener<List<Favorito>>() {
            @Override
            public void finish(List<Favorito> favoritos) {
                for (Track pista : pistas) {
                    for (Favorito favorito : favoritos) {
                        if (pista.getId().equals(favorito.getId())) {
                            pista.setFavorito(Boolean.TRUE);
                        }
                    }
                }
                pistaAlbumRecyclerView.setPistas(pistas);
            }
        });
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    private void crearAlbumRecyclerView(Integer idLayout) {
        recyclerView = view.findViewById(idLayout);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        pistaAlbumRecyclerView = new PistaAlbumRecyclerView(new ArrayList<Track>(), R.layout.cardview_pista_album, getActivity(), this);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(pistaAlbumRecyclerView);

    }


    private void setFavoritoPista(Track pista, ImageView favoritoPista) {
        if (!pista.getFavorito()) {
            pista.setFavorito(true);
            favoritoFirebasePista.agregar(pista.getId(), urlImagenCabecera);
            cargarImagen(favoritoPista, R.drawable.ic_favorite_seleccionado);
        } else {
            pista.setFavorito(false);
            favoritoFirebasePista.eliminar(pista.getId());
            cargarImagen(favoritoPista, R.drawable.ic_favorite_no_seleccion);
        }
    }

    @Override
    public void favoritoListener(Track pista, ImageView favoritoPista) {
        if (parent.estaLogeado(getContext())) {
            setFavoritoPista(pista, favoritoPista);
        }
    }

    @Override
    public void playListListener(Track pista) {

        Toast.makeText(getContext(), "Se ha agregado al playList: " + pista.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void compartirListener(Track pista) {
        Toast.makeText(getContext(), "Compartir pista", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pistaViewPageListener(final Integer posicion, View itemViewSelected) {
        /*parent.getBottomNavigation().setVisibility(View.GONE);
        final Dialog dialog = new Dialog(getContext(), R.style.pistaViewPage);
        dialog.setContentView(R.layout.pista_view_page_content);
        progressBar = dialog.findViewById(R.id.progrerssBarPistaViewPage);
        pistaAdapterViewPage = new PistaAdapterViewPage(pistas, getContext(), this);
        autoScrollViewPager = dialog.findViewById(R.id.pistaViewPagerScroll);
        autoScrollViewPager.setAdapter(pistaAdapterViewPage);
        autoScrollViewPager.setCurrentItem(posicion);

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    TextView textViewNombrePista = parent.findViewById(R.id.tvNombreReproductor);
                    textViewNombrePista.setSelected(true);
                    textViewNombrePista.setText(pistaActual.getArtist().getName() + " - " + pistaActual.getTitle());
                    parent.visibilidadReproductor(true);
                    parent.getBottomNavigation().setVisibility(View.VISIBLE);
                    reprducirAlbum = Boolean.FALSE;*/
        ReproducirMp3 reproducirMp3 = new ReproducirMp3();
        reproducirMp3.reproducirMp3(pistas.get(posicion).getPreview(), mediaPlayer, parent);
        TextView textViewNombrePista = getActivity().findViewById(R.id.tvNombreReproductor);
        textViewNombrePista.setSelected(true);
        textViewNombrePista.setText(pistas.get(posicion).getArtist().getName() + " - " + pistas.get(posicion).getTitle());
        ImageView imageView = getActivity().findViewById(R.id.btnActivityReproductor);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ReproductorActivity.class);

                Integer posicionReproductor = mediaPlayer.getCurrentPosition();

                Bundle bundle = new Bundle();
                bundle.putSerializable(ReproductorActivity.KEY_OBJETO, (Serializable) pistas);
                bundle.putInt(ReproductorActivity.KEY_POSICION, posicion);
                bundle.putInt(ReproductorActivity.KEY_POSICION_REPRODUCTOR, posicionReproductor);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //return true;
        //}
        /*});


        dialog.show();
        parent.visibilidadReproductor(false);*/
    }


    private void cargarImagen(ImageView imageView, Integer idDrawable) {
        Glide.with(getContext()).load(idDrawable).into(imageView);
    }

    @Override
    public void pistaAnterior(Integer posicion) {
        autoScrollViewPager.setCurrentItem(posicion);
        pistaPlay(posicion < 0 ? pistas.size() + posicion : posicion);
    }

    @Override
    public void pistaSiguiente(Integer posicion) {
        autoScrollViewPager.setCurrentItem(posicion);
        pistaPlay(posicion >= pistas.size() ? posicion - pistas.size() : posicion);
    }

    @Override
    public void pistaPlay(final Integer posicion) {

        // De pause a play
        if (mediaPlayer.getCurrentPosition() > 0 && pistaActual != null && pistaActual.getId().equals(pistas.get(posicion).getId())) {
            mediaPlayer.start();
            return;
        }

        recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, new RecyclerView.State(), posicion);
        recyclerView.getAdapter().notifyDataSetChanged();

        pistaActual = pistas.get(posicion);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        final Handler mSeekbarUpdateHandler = new Handler();

        final Runnable mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(this, 50);

            }
        };

        if (reprducirAlbum) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    if (reprducirAlbum && posicion < pistas.size()) {
                        if (posicion == pistas.size() - 2) {
                            reprducirAlbum = Boolean.FALSE;
                        }
                        pistaSiguiente(posicion + 1);
                    }
                }
            });
        }


        final String url = pistaActual.getPreview();

        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        progressBar.setMax(mediaPlayer.getDuration());
        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
    }

    @Override
    public void pistaPause() {
        mediaPlayer.pause();
    }


    @Override
    public void favoritoListenerPista(Integer posicion, ImageView favoritoPistaReproductor) {
        Track pista = pistas.get(posicion);
        setFavoritoPista(pista, favoritoPistaReproductor);
        pistaAlbumRecyclerView.notifyDataSetChanged();
        pistaAdapterViewPage.notifyDataSetChanged();

    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(pistas, fromPosition, toPosition);
        pistaAlbumRecyclerView.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(final int position, int direction) {
        pistaAlbumRecyclerView.notifyItemRemoved(position);
        pistaAlbumRecyclerView.notifyItemInserted(position);
        gestorPistaReproduccion(position, direction);
    }

    private void gestorPistaReproduccion(final int position, int direction) {
        Track pista = pistas.get(position);
        pista.setImagenAlbum(urlImagenCabecera);

        if (ItemTouchHelper.LEFT == direction) {
            Toast.makeText(getContext(), getResources().getString(R.string.pista_gregada), Toast.LENGTH_SHORT).show();
            parent.agregarPistaReproducción(pistas.get(position));
        } else {
            final Dialog dialog = new Dialog(getContext(), R.style.listaReproduccionDialog);
            dialog.setContentView(R.layout.nueva_lista_reprosuccion);
            dialog.show();

            final EditText nombre = dialog.findViewById(R.id.txtNombreNuevaListaRep);
            final TextInputLayout tilNombreListaRep = dialog.findViewById(R.id.tilNombreListaRep);

            Button btnAceptar = dialog.findViewById(R.id.btnAceptarNuevaListaRep);
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mensajeError = null;
                    if (TextUtils.isEmpty(nombre.getText())) {
                        mensajeError = getString(R.string.campo_obligatorio);
                        toggleTextInputLayoutError(tilNombreListaRep, mensajeError);
                        return;
                    }

                    if (parent.nuevaListaReproduccion(nombre.getText().toString())) {
                        parent.agregarPistaReproducción(pistas.get(position));
                        dialog.dismiss();
                    } else {
                        // mostrar error
                    }


                }
            });

            Button btnCancelar = dialog.findViewById(R.id.btnCancelarNuevaListaRep);

            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }

    }


    View.OnClickListener resproducirAlbumListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reprducirAlbum = Boolean.TRUE;
            pistaViewPageListener(0, null);
            pistaPlay(0);
        }
    };


    private void setFavoritoAlbum() {
        if (favoritoAlbum) {
            favoritoAlbum = Boolean.FALSE;
            cargarImagen(R.drawable.ic_favorite_no_seleccion);
            favoritoFirebaseAlbum.eliminar(albumId);
        } else {
            cargarImagen(R.drawable.ic_favorite_black_24dp);
            favoritoAlbum = Boolean.TRUE;
            favoritoFirebaseAlbum.agregar(albumId, urlImagenCabecera);

        }
    }


    View.OnClickListener favoritoAlbumListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (parent.estaLogeado(getContext())) {
                setFavoritoAlbum();
            }
        }
    };

    private void inisializacionFavoritoALbum(final FloatingActionButton btnFavorito) {
        //   btnFavorito.setImageResource( R.drawable.ic_favorite_no_seleccion);
        favoritoFirebaseAlbum.getFavoritoPorId(new ResultListener<Favorito>() {
            @Override
            public void finish(Favorito favorito) {
                btnFavorito.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
        }, albumId);

    }

    private void cargarImagen(Integer idDrawable) {
        Glide.with(getContext()).load(idDrawable).into(btnFavorito);
    }

    private static void toggleTextInputLayoutError(@NonNull TextInputLayout textInputLayout,
                                                   String msg) {
        textInputLayout.setError(msg);
        if (msg == null) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setErrorEnabled(true);
        }
    }

}