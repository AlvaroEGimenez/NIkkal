package com.digitalhouse.a0818moacn01_02;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.digitalhouse.a0818moacn01_02.menuNavegacion.RadioOnline.RadioFragment;
import com.digitalhouse.a0818moacn01_02.menuNavegacion.buscar.BuscarFragment;
import com.digitalhouse.a0818moacn01_02.menuNavegacion.favoritos.FavoritoFragment;
import com.digitalhouse.a0818moacn01_02.menuNavegacion.pantallaPrincipal.AlbumFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    private AlbumFragment albumFragment = new AlbumFragment();
    private BuscarFragment buscarFragment = new BuscarFragment();
    private FavoritoFragment favoritoFragment = new FavoritoFragment();
    private RadioFragment radioFragment = new RadioFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomBar bottomBar = findViewById(R.id.bottombar);

        bottomBar.setDefaultTab(R.id.albumFragment);


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.albumFragment:
                        reemplazarFragment(albumFragment);

                        break;
                    case R.id.buscarFragment:
                        reemplazarFragment(buscarFragment);
                        break;

                    case R.id.favoritoFragment:
                        reemplazarFragment(favoritoFragment);
                        break;
                    case R.id.radioFragment:
                        reemplazarFragment(radioFragment);
                        break;
                }
            }
        });

    }

    private void reemplazarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
