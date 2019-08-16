package com.pe.places.dao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.pe.places.R;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Place {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "image")
    @Ignore
    private int image;

    @ColumnInfo(name = "name")
    private String name;

    public Place() {

    }

    public Place(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Place> getPlaces(){
        List<Place> places=new LinkedList<>();
        places.add(new Place(R.drawable.ic_arequipa,"Arequipa"));
        places.add(new Place(R.drawable.ic_tacna,"Tacna"));
        places.add(new Place(R.drawable.ic_tarapoto,"Tarapoto"));
        places.add(new Place(R.drawable.ic_machu_picchu,"Machu Picchu"));

        return places;
    }
}
