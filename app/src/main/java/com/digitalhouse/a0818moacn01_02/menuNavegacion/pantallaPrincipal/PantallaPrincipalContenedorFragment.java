package com.digitalhouse.a0818moacn01_02.menuNavegacion.pantallaPrincipal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moacn01_02.R;

public class PantallaPrincipalContenedorFragment extends Fragment {
    private AlbumFragment albumFragment = new AlbumFragment();
    private AlbumFragment albumFragment2 = new AlbumFragment();
    private AlbumFragment albumFragment3 = new AlbumFragment();
    private AlbumFragment albumFragment4 = new AlbumFragment();


    public PantallaPrincipalContenedorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantalla_principal_contenedor, container, false);

        reemplazarFragment(R.id.albumFragment, albumFragment, "Géneros");
        reemplazarFragment(R.id.albumFragment2, albumFragment2, "Sugerencias");
        reemplazarFragment(R.id.albumFragment3, albumFragment3, "Lo más escuchado");
        reemplazarFragment(R.id.albumFragment4, albumFragment4, "Favoritos");

        return view;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        FragmentManager fragmentManager = getFragmentManager();
        getExitTransition();
    }

    private void reemplazarFragment(Integer idFragment, Fragment fragment, String titulo) {

//       if(!fragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putString(AlbumFragment.KEY_CATEGORIA, titulo);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(idFragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

//        }else{
//
//         fragment.setMenuVisibility(true);
//
//        }
    }


}
