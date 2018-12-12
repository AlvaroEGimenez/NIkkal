package com.digitalhouse.a0818moacn01_02.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ListaReproduccionFirebase;
import com.digitalhouse.a0818moacn01_02.Utils.MediaPlayerNikkal;
import com.digitalhouse.a0818moacn01_02.Utils.ReproducirMp3;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.adapter.listaReproduccion.ItemTouchHelperCallback;
import com.digitalhouse.a0818moacn01_02.view.adapter.listaReproduccion.PistaListaReproduccionAdapter;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Buscar.BuscarFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos.FavoritoFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos.MisListasFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Pantalla_Principal.CategoriaFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Radio_Online.RadioFragment;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        PistaListaReproduccionAdapter.PistaListaReproduccionAdapterInterface {

    private CategoriaFragment categoriaFragment = new CategoriaFragment();
    private BuscarFragment buscarFragment = new BuscarFragment();
    private FavoritoFragment favoritoFragment = new FavoritoFragment();
    private RadioFragment radioFragment = new RadioFragment();
    private TextView textViewNombrePista;
    private ImageView imageViewPlay;
    private ImageView imageViewPause;
    private LinearLayout linearLayoutReproductor;
    private MediaPlayer mediaPlayer;
    private BottomNavigationView bottomNavigation;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View headerView;
    private ListaReproduccionFirebase listaReproduccion;
    private PistaListaReproduccionAdapter pistaAlbumRecyclerView;
    private FloatingActionButton btnListaReproduccion;
    private Integer posicionActualLista;
    private Menu menuFavoritos;
    private ImageView menuHeaderListaReprod;
    private TextView tvHeaderNombreListaReproduccion;
    private DrawerLayout drawerLayout;
    private Boolean reemplazarFragment = Boolean.TRUE;
    private PopupMenu popup;
    private ReproducirMp3 reproducirMp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Util.printHash(this);

        bottomNavigation = findViewById(R.id.navigationView);
        textViewNombrePista = findViewById(R.id.tvNombreReproductor);
        imageViewPlay = findViewById(R.id.btnRepoductorPlay);
        imageViewPause = findViewById(R.id.btnReproductorPause);
        linearLayoutReproductor = findViewById(R.id.layoutPlayer);

        reemplazarFragment(categoriaFragment, R.id.albumFragment);

        mediaPlayer = MediaPlayerNikkal.getInstance().getMediaPlayer();
        navigationView = findViewById(R.id.navigationMainActivity);
        headerView = navigationView.inflateHeaderView(R.layout.header_navigation_view);
        tvHeaderNombreListaReproduccion = headerView.findViewById(R.id.tvHeaderNombreListaReproduccion);
        menuHeaderListaReprod = headerView.findViewById(R.id.menuHeaderListaReprod);
        menuHeaderListaReprod.setOnClickListener(menuHeaderListaReprodListener);
        btnListaReproduccion = findViewById(R.id.btnListaReproduccion);
        btnListaReproduccion.setOnClickListener(listaReproducctionListener);
        drawerLayout = findViewById(R.id.drawerMainActivity);
        cargarImagenHeaderNavigationView();
        reproducirMp3 = new ReproducirMp3(true);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (!reemplazarFragment) {
                    reemplazarFragment = Boolean.TRUE;
                    return true;
                }
                switch (item.getItemId()) {
                    case R.id.albumFragment:
                        reemplazarFragment(categoriaFragment, R.id.albumFragment);

                        return true;
                    case R.id.buscarFragment:
                        reemplazarFragment(buscarFragment, R.id.buscarFragment);
                        return true;

                    case R.id.favoritoFragment:
                        reemplazarFragment(favoritoFragment, R.id.favoritoFragment);
                        return true;
                    case R.id.radioFragment:
                        reemplazarFragment(radioFragment, R.id.radioFragment);
                        return true;
                }
                return false;
            }
        });

        listaReproduccion = new ListaReproduccionFirebase();
        crearListaReproduccionRecyclerView();
      //  automatizacionMediaPLayer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuFavoritos = menu;
        getMenuInflater().inflate(R.menu.menu_favoritos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void reemplazarFragment(Fragment fragment, Integer idFragemnte) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(idFragemnte.toString()).commit();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void visibilidadReproductor(Boolean esVisible) {
        if (esVisible) {
            linearLayoutReproductor.setVisibility(View.VISIBLE);
            imageViewPlay.setVisibility(View.INVISIBLE);
            imageViewPause.setVisibility(View.VISIBLE);
            textViewNombrePista.setTextColor(Color.parseColor("#FD9701"));
            imageViewPause.setVisibility(View.VISIBLE);
        } else {
            linearLayoutReproductor.setVisibility(View.GONE);
            imageViewPlay.setVisibility(View.GONE);
            imageViewPause.setVisibility(View.GONE);
            imageViewPause.setVisibility(View.GONE);
            linearLayoutReproductor.setVisibility(View.GONE);
        }
    }

    public Boolean estaLogeado(final Context context) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            alertDialogInicionSesion(context);
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    private void alertDialogInicionSesion(final Context context) {
        String mensajeSi = getResources().getString(R.string.si);
        String mensajeNo = getResources().getString(R.string.no);
        String mensajeCabecera = getResources().getString(R.string.cabeceraLogin);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(mensajeCabecera);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                mensajeSi,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                    }
                });

        builder1.setNegativeButton(
                mensajeNo,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public Menu getMenuFavoritos() {
        return menuFavoritos;
    }

    public void cargarImagenHeaderNavigationView() {
        ImageView ivUSuario = headerView.findViewById(R.id.usuarioListaReproducion);

        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            Uri uri = profile.getProfilePictureUri(200, 200);
            Glide.with(this).load(uri).into(ivUSuario);
        } else {
            ivUSuario.setImageResource(R.drawable.ic_person_outline_black_24dp);
        }

    }

    private void cargarListaReproduccion() {
        listaReproduccion.getLista(new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> pistas) {
                listaReproduccion.getPistas().clear();
                listaReproduccion.getPistas().addAll(pistas);
                pistaAlbumRecyclerView.setPistas(pistas);
                drawerLayout.openDrawer(Gravity.START);
            }
        });
    }

    public void cargarListaReproduccion(String nombre) {
        listaReproduccion.setNombre(nombre);
        tvHeaderNombreListaReproduccion.setText(nombre);
        cargarListaReproduccion();
    }

    public void crearListaReproduccionRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvListaReproduccion);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        pistaAlbumRecyclerView = new PistaListaReproduccionAdapter(new ArrayList<Track>(), R.layout.cardview_pista_listado_reproduccion, this, this, listaReproduccion);

        recyclerView.setAdapter(pistaAlbumRecyclerView);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(pistaAlbumRecyclerView);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }


    public BottomNavigationView getBottomNavigation() {
        return bottomNavigation;
    }

    public Boolean nuevaListaReproduccion(String nombre) {
        // todo guardar lista anterior
        listaReproduccion.getPistas().clear();
        listaReproduccion.setNombre(nombre);
        tvHeaderNombreListaReproduccion.setText(nombre);
        cargarListaReproduccion();

        drawerLayout.openDrawer(Gravity.START);
        return true;
    }


    public PistaListaReproduccionAdapter getPistaAlbumRecyclerView() {
        return pistaAlbumRecyclerView;
    }

    View.OnClickListener listaReproducctionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            posicionActualLista = 0;
            drawerLayout.closeDrawers();
            pistaListaReproduccionAdapterInterface(posicionActualLista++);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (posicionActualLista < listaReproduccion.getPistas().size()) {
                        pistaListaReproduccionAdapterInterface(posicionActualLista++);
                    } else {
                        posicionActualLista = 0;
                        return;
                    }
                }
            });
        }
    };

    @Override
    public void pistaListaReproduccionAdapterInterface(Integer posicion) {
        if (listaReproduccion != null && !listaReproduccion.getPistas().isEmpty()) {
            textViewNombrePista.setText(listaReproduccion.getPistas().get(posicion).getTitle() + " - " + listaReproduccion.getPistas().get(posicion).getArtist().getName());
            reproducirMp3.reproducirMp3(listaReproduccion.getPistas(), posicion, this);
        }
    }

    public void agregarPistaReproducción(Track pista) {
        if (listaReproduccion.getNombre() == null) {
            Toast.makeText(this, getResources().getString(R.string.debe_crear_lista_rep), Toast.LENGTH_SHORT).show();
        } else {
            listaReproduccion.agregarPista(pista);
            getPistaAlbumRecyclerView().notifyDataSetChanged();
        }
    }

    public void setTvHeaderNombreListaReproduccion(String tvHeaderNombreListaReproduccion) {
        this.tvHeaderNombreListaReproduccion.setText(tvHeaderNombreListaReproduccion);
    }

    View.OnClickListener menuHeaderListaReprodListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popup = new PopupMenu(getApplicationContext(), v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_header_lista_reprod, popup.getMenu());
            popup.setOnMenuItemClickListener(new MenuItemSeleccionClickListener());
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            if (mAuth.getCurrentUser() == null) {
                popup.getMenu().findItem(R.id.cerrarSesion).setEnabled(false);
                popup.getMenu().findItem(R.id.abrirListaReprod).setEnabled(false);
            } else {
                popup.getMenu().findItem(R.id.cerrarSesion).setVisible(true);
                popup.getMenu().findItem(R.id.abrirListaReprod).setEnabled(true);
            }

            popup.show();
        }
    };

    public class MenuItemSeleccionClickListener implements PopupMenu.OnMenuItemClickListener {

        public MenuItemSeleccionClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.cerrarSesion:
                    cerrarSesion();
                    return true;
                case R.id.abrirListaReprod:
                    abrirListaReproduccion();
                    return true;
                default:
            }
            return false;
        }
    }

    private void cerrarSesion() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        cargarImagenHeaderNavigationView();
        pistaAlbumRecyclerView.setPistas(new ArrayList<Track>());
        this.tvHeaderNombreListaReproduccion.setText("");
        bottomNavigation.setSelectedItemId(R.id.albumFragment);
        drawerLayout.closeDrawers();
    }

    private void abrirListaReproduccion() {
        bottomNavigation.setSelectedItemId(R.id.favoritoFragment);
        drawerLayout.closeDrawers();
        reemplazarFragment(new MisListasFragment(), R.id.favorito_fragemnt);
    }

    private String getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            finish();
            return "-1";
        }
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return fragmentTag;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String fragmentBeforeBackPress = getCurrentFragment();
        int idFragemnt = Integer.parseInt(fragmentBeforeBackPress);

        switch (idFragemnt) {
            case R.id.pista_fragment:
                reemplazarFragment = Boolean.FALSE;
                bottomNavigation.setSelectedItemId(R.id.albumFragment);
                break;
            case R.id.genero_fragment:
                reemplazarFragment = Boolean.FALSE;
                bottomNavigation.setSelectedItemId(R.id.albumFragment);
                break;
            case R.id.favorito_fragemnt:
                reemplazarFragment = Boolean.FALSE;
                bottomNavigation.setSelectedItemId(R.id.favoritoFragment);
                break;

            default:
                bottomNavigation.setSelectedItemId(idFragemnt);
        }
    }

 /*   public void automatizacionMediaPLayer() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Track track = getReproducirMp3().getListaReproduccion().get(getReproducirMp3().getPosicion());
                textViewNombrePista.setText(track.getArtist().getName() + " - " + track.getTitle());

            }
        });
    }*/

    public ReproducirMp3 getReproducirMp3() {
        return reproducirMp3;
    }
}
