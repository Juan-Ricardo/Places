package com.pe.places.dao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.pe.places.R;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Place implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    public Place() {

    }

    public Place(String image, String name, String description) {
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public static List<Place> getPlaces() {
        List<Place> places = new LinkedList<>();
        places.add(new Place(R.drawable.ic_arequipa, "Arequipa"));
        places.add(new Place(R.drawable.ic_tacna, "Tacna"));
        places.add(new Place(R.drawable.ic_tarapoto, "Tarapoto"));
        places.add(new Place(R.drawable.ic_machu_picchu, "Machu Picchu"));

        return places;
    }*/
}
