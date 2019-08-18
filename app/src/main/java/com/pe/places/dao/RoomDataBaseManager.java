package com.pe.places.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Place.class}, version = 1, exportSchema = false)
public abstract class RoomDataBaseManager extends RoomDatabase {
    private static RoomDataBaseManager roomDataBaseManager;

    public abstract PlaceDao placeDao();

    public RoomDataBaseManager() {

    }

    public static RoomDataBaseManager getInstance(Context context) {
        if (roomDataBaseManager == null) {
            roomDataBaseManager = Room.databaseBuilder(context, RoomDataBaseManager.class, "placedb")
                    .allowMainThreadQueries()
                    .build();
        }
        return roomDataBaseManager;
    }
}
