package com.digitalhouse.a0818moacn01_02.DAO.database;


import android.content.Context;

import androidx.room.Room;

public class DatabaseHelper {
    private static final String DB_NAME = "database-nikkal.db";
    private static MyDatabaseRoom db;

    public static MyDatabaseRoom getInstance(Context applicationContext) {
        if (db == null) {
            db = Room.databaseBuilder(applicationContext,
                    MyDatabaseRoom.class, DB_NAME).allowMainThreadQueries()
                      .fallbackToDestructiveMigration().build();

        }
        return db;
    }
}
