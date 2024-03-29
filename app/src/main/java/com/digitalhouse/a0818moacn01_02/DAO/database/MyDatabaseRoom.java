package com.digitalhouse.a0818moacn01_02.DAO.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.digitalhouse.a0818moacn01_02.model.Favorito;

@Database(entities = {Favorito.class}, version = 5, exportSchema = false)
public abstract class MyDatabaseRoom extends RoomDatabase {
    public abstract FavoritoRoomDAO getDao();
}
