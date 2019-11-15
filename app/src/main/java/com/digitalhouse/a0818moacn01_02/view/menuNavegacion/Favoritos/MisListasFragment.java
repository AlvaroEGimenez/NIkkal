package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ListaReproduccionFirebase;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.ListaReproduccion;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.ListaReproduccionAdapter;

import java.util.ArrayList;
import java.util.List;

public class MisListasFragment extends Fragment implements ListaReproduccionAdapter.ListaReproduccionAdapterInterface {
    private ListaReproduccionAdapter listaReproduccionAdapter;
    private MainActivity parent;
    private ListaReproduccionFirebase listaReproduccionFirebase;

    public MisListasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_listas_de_reproduccion, container, false);


        listaReproduccionFirebase = new ListaReproduccionFirebase();
        parent = (MainActivity) getActivity();
        crearRecyclerView(view);
        listaReproduccionFirebase.getMisLista(new ResultListener<List<ListaReproduccion>>() {
            @Override
            public void finish(List<ListaReproduccion> resultado) {
                listaReproduccionAdapter.setListaReproduccionList(resultado);
            }
        });

        return view;
    }


    private void crearRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerMisListas);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        listaReproduccionAdapter = new ListaReproduccionAdapter(new ArrayList<ListaReproduccion>(), R.layout.cardview_lista_reproduccion, this);

        ItemTouchHelper.Callback callback = new FavoritoItemTouchHelperCallback(listaReproduccionAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(listaReproduccionAdapter);
    }

    @Override
    public void seleccionLista(ListaReproduccion listaReproduccion) {
        parent.cargarListaReproduccion(listaReproduccion.getNombre());
    }

    @Override
    public void onItemDismiss(int position) {
        ListaReproduccion listaReproduccion = listaReproduccionAdapter.eliminarPista(position);
        listaReproduccionFirebase.eliminarLista(listaReproduccion.getNombre());
    }
}
