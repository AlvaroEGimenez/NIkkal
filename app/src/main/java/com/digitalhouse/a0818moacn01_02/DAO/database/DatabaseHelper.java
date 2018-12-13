package com.digitalhouse.a0818moacn01_02.DAO.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseHelper {
    private static final String DB_NAME = "database-nikkal.db";
    private static MyDatabaseRoom db;

    public static MyDatabaseRoom getInstance(Context applicationContext) {
        if (db == null) {
            db = Room.databaseBuilder(applicationContext,
                    MyDatabaseRoom.class, DB_NAME).allowMainThreadQueries().build();
        }
        return db;
    }
}
