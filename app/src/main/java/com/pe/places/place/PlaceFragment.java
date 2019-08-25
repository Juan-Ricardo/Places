package com.pe.places.place;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pe.places.R;
import com.pe.places.dao.Place;
import com.pe.places.dao.RoomDataBaseManager;
import com.pe.places.utilities.CircleTransform;
import com.pe.places.utilities.Constants;
import com.squareup.picasso.Picasso;

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
    private ShimmerFrameLayout placeShimmerFrameLayout;

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

        placeShimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        placeShimmerFrameLayout.startShimmer();

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

            //BottomNavigationView
            if (scrollY < oldScrollY) { // up
                animateNavigation(false);
            }
            if (scrollY > oldScrollY) { // down
                animateNavigation(true);
            }

            //FloatingActionButton
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
        view.animate().translationX(moveY).setDuration(300).start();
    }

    public static void showFab(View view) {
        view.animate().translationX(0).setDuration(300).start();
    }

    private void refreshPlaceAdapterRecyclerView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                placeRecyclerView.setLayoutManager(linearLayoutManager);
                placeAdapterRecyclerView = new PlaceAdapterRecyclerView(
                        places, R.layout.row_place, getActivity());

                placeRecyclerView.setAdapter(placeAdapterRecyclerView);

                placeAdapterRecyclerView.setOnItemClickListener(
                        new PlaceAdapterRecyclerView.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, Place place, int position) {
                                showCustomDialog(place);
                            }
                        });

                placeShimmerFrameLayout.setVisibility(View.GONE);
                placeRecyclerView.setVisibility(View.VISIBLE);

            }
        }, 1000);
    }

    private void showCustomDialog(Place place) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.place_dialog_detail);
        dialog.setCancelable(false);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ImageView viewById = dialog.findViewById(R.id.place_image_view);
        Picasso.get()
                .load(place.getImage())
                .transform(new CircleTransform())
                .into(viewById);
        ((TextView) dialog.findViewById(R.id.name_place_text_view))
                .setText(place.getName());
        ((TextView) dialog.findViewById(R.id.description_place_text_view))
                .setText(place.getDescription());
        dialog.findViewById(R.id.close_material_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        dialog.findViewById(R.id.bt_close)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
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
