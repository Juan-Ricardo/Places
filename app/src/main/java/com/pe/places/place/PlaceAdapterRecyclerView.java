package com.pe.places.place;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.places.R;
import com.pe.places.dao.Place;
import com.pe.places.utilities.Constants;
import com.pe.places.utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaceAdapterRecyclerView extends RecyclerView.Adapter<PlaceAdapterRecyclerView.PlaceViewHolder> {

    private List<Place> places;
    private int resource;
    private Activity activity;
    private OnItemClickListener onItemClickListener;

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
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, final int position) {
        final Place place = this.places.get(position);
        holder.namePlaceTextView.setText(place.getName());
        holder.descriptionPlaceTextView.setText("En " + place.getName() + " hay "
                + place.getTotalPerson() + " Personas. " + place.getDescription());

        Picasso.get().load(place.getImage()).into(holder.placeImageView);
        holder.placeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, places.get(position), position);
                }
            }
        });
        holder.editFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPlaceDetail(place);
            }
        });
    }

    private void sendPlaceDetail(Place place) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PLACE, place);
        bundle.putString(Constants.CRUD, Constants.UPDATE);
        Intent intent = new Intent(activity, PlaceDetailActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return this.places.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout editFrameLayout;
        private ImageView placeImageView;
        private TextView namePlaceTextView;
        private TextView descriptionPlaceTextView;


        public PlaceViewHolder(View view) {
            super(view);
            editFrameLayout = view.findViewById(R.id.edit_frame_layout);
            namePlaceTextView = view.findViewById(R.id.name_place_text_view);
            placeImageView = view.findViewById(R.id.place_image_view);
            descriptionPlaceTextView = view.findViewById(R.id.description_place_text_view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Place place, int position);
    }
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
