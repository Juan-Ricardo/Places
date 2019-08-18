package com.pe.places.notification;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pe.places.R;
import com.pe.places.place.PlaceAdapterRecyclerView;
import com.pe.places.retrofit.client.ApiClient;
import com.pe.places.retrofit.client.ApiService;
import com.pe.places.retrofit.response.UserResponse;
import com.pe.places.volley.SingletonVolley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView userRecyclerView;
    private UserAdapterRecyclerView userAdapterRecyclerView;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        finds(view);
        return view;
    }

    private void finds(View view) {
        setupToolbar(view, "Usuarios", "", false);
        userRecyclerView = view.findViewById(R.id.user_recycler_view);
    }

    private void setupToolbar(View view, String title, String subTitle, boolean arrow) {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(arrow);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllUsers();
        //volleyExample();
        //retrofit2();
    }

    private void refreshPlaceAdapterRecyclerView(List<UserResponse> userResponses) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        userRecyclerView.setLayoutManager(linearLayoutManager);
        userAdapterRecyclerView = new UserAdapterRecyclerView(userResponses, R.layout.row_user, getActivity());
        userRecyclerView.setAdapter(userAdapterRecyclerView);
    }

    private void getAllUsers() {

        Call<List<UserResponse>> allUsers = ApiClient.getInstance(getContext())
                .createService(ApiService.class)
                .getAllUsers();

        allUsers.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call,
                                   retrofit2.Response<List<UserResponse>> response) {
                refreshPlaceAdapterRecyclerView(response.body());
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {

            }
        });
    }

    private void volleyExample() {
        String urlBase = "http://api.stackexchange.com/2.2/answers?page=1&pagesize=50&site=stackoverflow";
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                urlBase, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("users: ", "onResponse: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("users: ", "onErrorResponse: " + error.getMessage());
            }
        });
        SingletonVolley.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }


}
