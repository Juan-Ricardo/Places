package com.pe.places.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlaceDao {

    @Insert
    void save(Place place);

    @Query("Select * from Place")
    List<Place> getAll();

    @Query("SELECT * FROM Place WHERE id = :id")
    Place findById(int id);

    @Query("DELETE FROM Place WHERE id = :id")
    int deleteById(int id);

    @Query("Select count(id) from Place")
    int getPlaces();

    @Update
    void update(Place place);

    @Delete
    void delete(Place place);

    //https://developer.android.com/training/data-storage/room/accessing-data
}
