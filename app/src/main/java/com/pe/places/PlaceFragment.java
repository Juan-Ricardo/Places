package com.pe.places;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceFragment extends Fragment {

    private MaterialButton showPlacesMaterialButton;
    private RecyclerView placeRecyclerView;
    private FloatingActionButton addPlaceFloatingActionButton;
    private Toolbar toolbar;
    private PlaceAdapterRecyclerView placeAdapterRecyclerView;
    public static final int ADD_PLACE = 200;
    private List<Place> places;

    public PlaceFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        finds(view);
        return view;
    }

    private void finds(View view){
        showPlacesMaterialButton = view.findViewById(R.id.show_places_material_button);
        placeRecyclerView = view.findViewById(R.id.place_recycler_view);
        addPlaceFloatingActionButton = view.findViewById(R.id.add_place_floating_action_button);
        addPlaceFloatingActionButton.setOnClickListener(addPlaceOnClickListener);
        setupToolbar(view,"Places", "", false);
        this.places=new LinkedList<>();
        this.places=Place.getPlaces();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPlaceAdapterRecyclerView();
    }

    private void refreshPlaceAdapterRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        placeRecyclerView.setLayoutManager(linearLayoutManager);
        placeAdapterRecyclerView = new PlaceAdapterRecyclerView(this.places, R.layout.item_place, getActivity());
        placeRecyclerView.setAdapter(placeAdapterRecyclerView);
        placeAdapterRecyclerView.notifyDataSetChanged();
    }

    View.OnClickListener addPlaceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), PlaceDetailActivity.class);
            startActivity(intent);
        }
    };

    private void setupToolbar(View view,String title, String subTitle, boolean arrow) {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(subTitle);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(arrow);
    }

}
