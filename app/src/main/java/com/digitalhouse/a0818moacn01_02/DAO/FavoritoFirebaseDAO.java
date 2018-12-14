package com.digitalhouse.a0818moacn01_02.DAO;

import android.support.annotation.NonNull;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.FavoritoController;
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

public class FavoritoFirebaseDAO {
    private DatabaseReference mReference;
    private FirebaseUser currentUser;

    public FavoritoFirebaseDAO() {
        mReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public Favorito agregar(Integer id, String urlImagen, String titulo, String tipo, String tipoAlbum) {
        if (currentUser != null) {
            DatabaseReference uid = mReference.child(currentUser.getUid()).child(FavoritoController.PATH_LIST_FAVORITO).child(tipo).push();
            Favorito favorito = new Favorito(id, uid.getKey(), urlImagen, titulo,  currentUser.getUid(), tipo, tipoAlbum);
            uid.setValue(favorito,
                    FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName()
            );
            return favorito;
        }
        return null;
    }

    public void eliminar(final Integer id, final String tipo) {
        getFavoritoPorId(new ResultListener<Favorito>() {
            @Override
            public void finish(Favorito favorito) {
                mReference.child(currentUser.getUid()).child(FavoritoController.PATH_LIST_FAVORITO)
                        .child(tipo).child(favorito.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }, id, tipo);
    }

    public void getLista(final ResultListener<List<Favorito>> resultListener, final String tipo) {
        if (currentUser == null) {
            return;
        }

        final List<Favorito> favoritosList = new ArrayList<>();
        mReference.child(currentUser.getUid()).child(FavoritoController.PATH_LIST_FAVORITO).child(tipo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favoritosList.clear();
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    Favorito favorito = childSnapShot.getValue(Favorito.class);
                    favoritosList.add(favorito);
                }
                resultListener.finish(favoritosList);
                mReference.child(currentUser.getUid()).child(FavoritoController.PATH_LIST_FAVORITO).child(tipo).removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getFavoritoPorId(final ResultListener<Favorito> listener, final Integer id, String tipo) {
        getLista(new ResultListener<List<Favorito>>() {
            @Override
            public void finish(List<Favorito> favoritos) {
                for (Favorito favorito : favoritos) {
                    if (favorito.getId().equals(id)) {
                        listener.finish(favorito);
                    }
                }
            }
        }, tipo);

    }

}
