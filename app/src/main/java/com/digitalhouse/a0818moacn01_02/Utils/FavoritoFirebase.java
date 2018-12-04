package com.digitalhouse.a0818moacn01_02.Utils;

import android.support.annotation.NonNull;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.Favorito;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritoFirebase {
    public static final String PATH_LIST_FAVORITO = "favorito";
    public static final String KEY_TIPO_PISTA="pista";
    public static final String KEY_TIPO_ALBUM="album";
    public static final String KEY_TIPO_ARTISTA="artista";

    private DatabaseReference mReference;
    private FirebaseUser currentUser;
    private String tipo;
    private List<Favorito> favoritoList = new ArrayList<>();

    public FavoritoFirebase(String tipo) {
        mReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance()
                .getCurrentUser();
        this.tipo = tipo;
        getLista(new ResultListener<List<Favorito>>() {
            @Override
            public void finish(List<Favorito> Resultado) {
                favoritoList.addAll(Resultado);
            }
        });
    }

    public void agregar(Integer id, String urlImagen) {
        if (currentUser != null) {
            DatabaseReference uid = mReference.child(currentUser.getUid()).child(PATH_LIST_FAVORITO).child(tipo).push();
            Favorito favorito = new Favorito(id, uid.getKey(), urlImagen);
            uid.setValue(favorito,
                    FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName()
            );
        }

    }

    public void eliminar(final Integer id) {
        getFavoritoPorId(new ResultListener<Favorito>() {
            @Override
            public void finish(Favorito favorito) {
                mReference.child(currentUser.getUid()).child(PATH_LIST_FAVORITO).child(tipo).child(favorito.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }, id);

    }

    public void getLista(final ResultListener<List<Favorito>> resultListener) {
        if (currentUser == null) {
            return;
        }
        final List<Favorito> favoritosList = new ArrayList<>();
        mReference.child(currentUser.getUid()).child(PATH_LIST_FAVORITO).child(tipo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favoritosList.clear();
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    Favorito favorito = childSnapShot.getValue(Favorito.class);
                    favoritosList.add(favorito);
                }
                resultListener.finish(favoritosList);
                mReference.child(currentUser.getUid()).child(PATH_LIST_FAVORITO).child(tipo).removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void getFavoritoPorId(final ResultListener<Favorito> listener,final Integer id) {
        getLista(new ResultListener<List<Favorito>>() {
            @Override
            public void finish(List<Favorito> Resultado) {
                for (Favorito favorito : favoritoList) {
                    if (favorito.getId().equals(id)) {
                        listener.finish(favorito);
                    }
                }
            }
        });

    }
}
