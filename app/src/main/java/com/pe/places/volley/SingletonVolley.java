package com.pe.places.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonVolley {
    private static SingletonVolley INSTANCE;
    private RequestQueue requestQueue;
    private static Context context;

    private SingletonVolley(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized SingletonVolley getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SingletonVolley(context);
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(this.context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
