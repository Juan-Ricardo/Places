package com.pe.places;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pe.places.dao.Place;

import java.util.LinkedList;
import java.util.List;

//https://www.androidhive.info/2016/01/android-working-with-recycler-view/
//https://developer.android.com/training/basics/intents/result?hl=es-419
public class PlacesActivity extends AppCompatActivity {

    private MaterialButton showPlacesMaterialButton;
    private RecyclerView placeRecyclerView;
    private FloatingActionButton addPlaceFloatingActionButton;
    private Toolbar toolbar;
    private PlaceAdapterRecyclerView placeAdapterRecyclerView;
    public static final int ADD_PLACE = 200;
    private List<Place> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        finds();
    }

    private void finds() {
        showPlacesMaterialButton = findViewById(R.id.show_places_material_button);
        placeRecyclerView = findViewById(R.id.place_recycler_view);
        addPlaceFloatingActionButton = findViewById(R.id.add_place_floating_action_button);
        addPlaceFloatingActionButton.setOnClickListener(addPlaceOnClickListener);
        setupToolbar("Places", "", false);
        this.places=new LinkedList<>();
        this.places=Place.getPlaces();
    }

    View.OnClickListener addPlaceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PlacesActivity.this, PlaceDetailActivity.class);
            startActivityForResult(intent, ADD_PLACE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PLACE && resultCode== Activity.RESULT_OK) {
            String name = data.getExtras().getString(PlaceDetailActivity.NAME);
            String country = data.getExtras().getString(PlaceDetailActivity.COUNTRY);
            this.places.add(new Place(R.drawable.ic_tarapoto,name));
            refreshPlaceAdapterRecyclerView();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPlaceAdapterRecyclerView();
    }

    private void refreshPlaceAdapterRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        placeRecyclerView.setLayoutManager(linearLayoutManager);
        placeAdapterRecyclerView = new PlaceAdapterRecyclerView(this.places, R.layout.item_place, this);
        placeRecyclerView.setAdapter(placeAdapterRecyclerView);
        placeAdapterRecyclerView.notifyDataSetChanged();
    }

    private void setupToolbar(String title, String subTitle, boolean arrow) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(arrow);
    }
}
