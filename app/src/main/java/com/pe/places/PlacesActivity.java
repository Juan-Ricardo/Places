package com.pe.places;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class PlacesActivity extends AppCompatActivity {

    private MaterialButton showPlacesMaterialButton;
    private RecyclerView placeRecyclerView;
    private Toolbar toolbar;
    private PlaceAdapterRecyclerView placeAdapterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        finds();
    }

    private void finds(){
        showPlacesMaterialButton=findViewById(R.id.show_places_material_button);
        placeRecyclerView=findViewById(R.id.place_recycler_view);
        setupToolbar("Places","",false);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupPlacesRecyclerView();
    }

    private void setupPlacesRecyclerView(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        placeRecyclerView.setLayoutManager(linearLayoutManager);
        placeAdapterRecyclerView=new PlaceAdapterRecyclerView(Place.getPlaces(),R.layout.item_place,this);
        placeRecyclerView.setAdapter(placeAdapterRecyclerView);
    }

    private void setupToolbar(String title, String subTitle, boolean arrow){
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(arrow);
    }
}
