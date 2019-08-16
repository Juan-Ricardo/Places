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

    @Update
    void update(Place place);

    @Delete
    void delete(Place place);
}
