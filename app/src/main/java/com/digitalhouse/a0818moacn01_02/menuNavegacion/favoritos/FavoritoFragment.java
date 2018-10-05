package com.digitalhouse.a0818moacn01_02.menuNavegacion.favoritos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moacn01_02.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritoFragment extends Fragment {


    public FavoritoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorito, container, false);
    }

}