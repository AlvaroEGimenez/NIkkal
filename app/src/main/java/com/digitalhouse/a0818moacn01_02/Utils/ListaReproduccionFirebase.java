package com.digitalhouse.a0818moacn01_02.Utils;

import android.support.annotation.NonNull;

import com.digitalhouse.a0818moacn01_02.model.ListaReproduccion;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaReproduccionFirebase {
    public static final String PATH_LIST_REPRODUCCION = "lista_reproduccion";
    private DatabaseReference mReference;
    private FirebaseUser currentUser;
    private ListaReproduccion listaReproduccion;

    public ListaReproduccionFirebase(String nombre) {
        mReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance()
                .getCurrentUser();
        listaReproduccion = new ListaReproduccion(nombre);
    }

    public ListaReproduccionFirebase() {
        mReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance()
                .getCurrentUser();
        listaReproduccion = new ListaReproduccion();
    }

    public void agregarPista(Track pista) {
        this.getPistas().add(pista);

        if (currentUser != null) {
            DatabaseReference id = mReference.child(currentUser.getUid()).child(PATH_LIST_REPRODUCCION).child(this.listaReproduccion.getNombre()).push();
            pista.setUid(id.getKey());
            id.setValue(pista,
                    FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName()
            );
        }

    }

    public void eliminarPista(Track pista) {
        mReference.child(currentUser.getUid()).child(PATH_LIST_REPRODUCCION).child(listaReproduccion.getNombre()).child(pista.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getLista(final ResultListener<List<Track>> resultListener) {
        if (currentUser == null) {
            return;
        }
        final List<Track> artistList = new ArrayList<>();
        mReference.child(currentUser.getUid()).child(PATH_LIST_REPRODUCCION).child(listaReproduccion.getNombre()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                artistList.clear();
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    Track pista = childSnapShot.getValue(Track.class);
                    artistList.add(pista);
                }
                resultListener.finish(artistList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void getMisLista(final ResultListener<List<ListaReproduccion>> resultListener) {
        if (currentUser == null) {
            return;
        }
        final List<ListaReproduccion> listaReproduccionList = new ArrayList<>();
        final List<String> nombreLista = new ArrayList<>();
        mReference.child(currentUser.getUid()).child(PATH_LIST_REPRODUCCION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    final String key = childSnapShot.getKey();
                    final List<Track> pistaList = new ArrayList<>();

                    mReference.child(currentUser.getUid()).child(PATH_LIST_REPRODUCCION).child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            pistaList.clear();
                            for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                                Track pista = childSnapShot.getValue(Track.class);
                                pistaList.add(pista);
                            }
                            ListaReproduccion listaReproduccion = new ListaReproduccion(key);
                            listaReproduccion.setPistas(pistaList);
                            listaReproduccionList.add(listaReproduccion);
                            resultListener.finish(listaReproduccionList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public List<Track> getPistas() {
        return listaReproduccion.getPistas();
    }

    public String getNombre() {
        return listaReproduccion.getNombre();
    }

    public void setNombre(String nombre) {
        this.listaReproduccion.setNombre(nombre);
    }

}

