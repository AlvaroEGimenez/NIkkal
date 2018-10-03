package com.digitalhouse.a0818moacn01_02.menuNavegacion.pantallaPrincipal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Album;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {
    public static final String KEY_CATEGORIA = "categoria";
    private TextView tvCategoria;

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        setCategotia(view);

        RecyclerView albumRecyclerView = view.findViewById(R.id.albumRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        albumRecyclerView.setLayoutManager(linearLayoutManager);

        AlbumAdapterRecyclerView albumAdapterRecyclerView = new AlbumAdapterRecyclerView(cargarAlbunes(tvCategoria.getText().toString()), R.layout.carcdview_album, getActivity());

        albumRecyclerView.setAdapter(albumAdapterRecyclerView);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void setCategotia(View view) {
        tvCategoria = view.findViewById(R.id.categoriaRecyclerView);
        Bundle bundle = getArguments();
        String titulo = bundle.get(KEY_CATEGORIA).toString();
        tvCategoria.setText(titulo);
    }

    public ArrayList<Album> cargarAlbunes(String categoria) {
        ArrayList<Album> albunes = new ArrayList<>();
      if("Géneros".equals(categoria)) {
          albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/b36ca681666d617edd0dcb5ab389a6ac/250x250-000000-80-0-0.jpg",
                  "Rock", "20"));
          albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/f14f9fde9feb38ca6d61960f00681860/250x250-000000-80-0-0.jpg",
                  "Metal", "10"));
          albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/ffd77feba2c8fda79b18183861e4e69f/250x250-000000-80-0-0.jpg",
                  "Cumbia", "28"));

          albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/f53d298e46c4722edc245f3b7232343a/250x250-000000-80-0-0.jpg",
                  "Folklore Argentino", "28"));

          albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/1abb6810098d4015bdc860c91bcfd2b6/250x250-000000-80-0-0.jpg",
                  "Blues", "28"));


          albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/069c9888538799748960781f098b5f4b/250x250-000000-80-0-0.jpg",
                  "Latino", "28"));

          albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/misc/b0b8efcbc3cb688864ce69da0061e525/250x250-000000-80-0-0.jpg",
                  "Niños", "28"));
      }

        if("Sugerencias".equals(categoria)) {
            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/artist/4f4ee0a2edcebdb115910bc39fb57f56/250x250-000000-80-0-0.jpg",
                    "La Máqiona de ser Feliz", "28"));

        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/artist/b4719bc7a0ddb4a5be41277f37856ae6/500x500-000000-80-0-0.jpg",
                "Metallica", "28"));


        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/73be4dccb64e1a53d6d3394436367f21/500x500-000000-80-0-0.jpg",
                "Oktubre", "28"));
        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/3ff8f91bb354b0245ea34d9a7fc3c07d/500x500-000000-80-0-0.jpg",
                "Amanecer", "28"));
        albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/5e61e8290a4d1d64ca58920656c9602d/500x500-000000-80-0-0.jpg",
                "Californication ", "28"));
        }

        if("Lo más escuchado".equals(categoria)) {
            albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/d493314dc2ba6d5bad6d6893913c3a9b/500x500-000000-80-0-0.jpg",
                    "Cumbia Peposa", "28"));

            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/a13de21d1c76b3cc3096391c715304ab/500x500-000000-80-0-0.jpg",
                    "Killshot", "28"));


            albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/9a3364528159f8377d3b1b5310f40dae/500x500-000000-80-0-0.jpg",
                    "X (Remix)", "28"));
            albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/27170198c97ac7e37f8a62cf5cae4299/500x500-000000-80-0-0.jpg",
                    "In My Mind", "28"));
            albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/7e8314f4280cffde363547a495a260bc/250x250-000000-80-0-0.jpg",
                    "Night Visions", "28"));
        }

        if("Favoritos".equals(categoria)) {
            albunes.add(new Album("https://cdns-images.dzcdn.net/images/cover/7e8314f4280cffde363547a495a260bc/250x250-000000-80-0-0.jpg",
                    "Night Visions", "28"));

            albunes.add(new Album("https://e-cdns-images.dzcdn.net/images/cover/5e61e8290a4d1d64ca58920656c9602d/500x500-000000-80-0-0.jpg",
                    "Killshot", "28"));

        }

        return albunes;
    }


}
