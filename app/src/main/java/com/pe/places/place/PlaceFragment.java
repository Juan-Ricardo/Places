package com.pe.places.place;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pe.places.R;
import com.pe.places.dao.Place;
import com.pe.places.dao.RoomDataBaseManager;
import com.pe.places.utilities.Constants;

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
    private List<Place> places;
    private NestedScrollView placeNestedScrollView;
    private BottomNavigationView menuBottomNavigationView;
    private boolean hide = false;
    private boolean isNavigationHide = false;

    public PlaceFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        finds(view);
        return view;
    }

    private void finds(View view) {
        menuBottomNavigationView = getActivity().findViewById(R.id.menu_bottom_navigation);
        placeNestedScrollView = view.findViewById(R.id.place_nested_scroll_view);
        placeNestedScrollView.setOnScrollChangeListener(onScrollChangeListener);
        showPlacesMaterialButton = view.findViewById(R.id.show_places_material_button);
        placeRecyclerView = view.findViewById(R.id.place_recycler_view);
        addPlaceFloatingActionButton = view.findViewById(R.id.add_place_floating_action_button);
        addPlaceFloatingActionButton.setOnClickListener(addPlaceOnClickListener);
        setupToolbar(view, "Places", "", false);
        this.places = new LinkedList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
        this.places = RoomDataBaseManager.getInstance(getContext()).placeDao().getAll();
        refreshPlaceAdapterRecyclerView();
    }

    NestedScrollView.OnScrollChangeListener onScrollChangeListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY < oldScrollY) { // up
                animateNavigation(false);
            }
            if (scrollY > oldScrollY) { // down
                animateNavigation(true);
            }

            if (scrollY >= oldScrollY) { // down
                if (hide) return;
                hideFab(addPlaceFloatingActionButton);
                hide = true;
            } else {
                if (!hide) return;
                showFab(addPlaceFloatingActionButton);
                hide = false;
            }
        }
    };

    private void animateNavigation(boolean hide) {
        if (isNavigationHide && hide || !isNavigationHide && !hide)
            return;
        isNavigationHide = hide;
        int moveY = hide ? (2 * menuBottomNavigationView.getHeight()) : 0;
        menuBottomNavigationView.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }

    public static void hideFab(View view) {
        int moveY = 3 * view.getHeight();
        view.animate().translationY(moveY).setDuration(300).start();
    }

    public static void showFab(View view) {
        view.animate().translationY(0).setDuration(300).start();
    }

    private void refreshPlaceAdapterRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        placeRecyclerView.setLayoutManager(linearLayoutManager);
        placeAdapterRecyclerView = new PlaceAdapterRecyclerView(this.places, R.layout.row_place, getActivity());
        placeRecyclerView.setAdapter(placeAdapterRecyclerView);
        placeAdapterRecyclerView.notifyDataSetChanged();
    }

    View.OnClickListener addPlaceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.CRUD, Constants.CREATE);
            Intent intent = new Intent(getContext(), PlaceDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    private void setupToolbar(View view, String title, String subTitle, boolean arrow) {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(arrow);
    }

}
