package com.pe.places.notification;

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
import com.pe.places.place.PlaceDetailActivity;
import com.pe.places.retrofit.response.UserResponse;
import com.pe.places.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapterRecyclerView extends RecyclerView.Adapter<UserAdapterRecyclerView.PlaceViewHolder> {

    private List<UserResponse> userResponses;
    private int resource;
    private Activity activity;

    public UserAdapterRecyclerView(List<UserResponse> userResponses, int resource, Activity activity) {
        this.userResponses = userResponses;
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
        final UserResponse userResponse = this.userResponses.get(position);
        holder.namePlaceTextView.setText(userResponse.getName());
        holder.emailImageView.setText(userResponse.getEmail());

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
        return this.userResponses.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        private TextView emailImageView;
        private TextView namePlaceTextView;


        public PlaceViewHolder(View view) {
            super(view);
            emailImageView = view.findViewById(R.id.email_text_view);
            namePlaceTextView = view.findViewById(R.id.name_text_view);
        }
    }
}
