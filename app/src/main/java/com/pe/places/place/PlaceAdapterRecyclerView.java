package com.pe.places.place;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.places.R;
import com.pe.places.dao.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaceAdapterRecyclerView extends RecyclerView.Adapter<PlaceAdapterRecyclerView.PlaceViewHolder> {

    private List<Place> places;
    private int resource;
    private Activity activity;

    public PlaceAdapterRecyclerView(List<Place> places, int resource, Activity activity) {
        this.places = places;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PlaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = this.places.get(position);
        holder.namePlaceTextView.setText(place.getName());
        Picasso.get().load(place.getImage()).into(holder.placeImageView);
    }

    @Override
    public int getItemCount() {
        return this.places.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        public TextView namePlaceTextView;
        public ImageView placeImageView;

        public PlaceViewHolder(View view) {
            super(view);
            namePlaceTextView = view.findViewById(R.id.name_place_text_view);
            placeImageView = view.findViewById(R.id.place_image_view);
        }
    }
}
