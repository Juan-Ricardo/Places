package com.pe.places.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pe.places.dao.RoomDataBaseManager;
import com.pe.places.notification.NotificationFragment;
import com.pe.places.place.PlaceFragment;
import com.pe.places.place.callback.PlaceBadgeCallBack;
import com.pe.places.profile.ProfileFragment;
import com.pe.places.R;

import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView menuBottomNavigationView;
    private Fragment placeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuBottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        menuBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        goProfile();
                        return true;

                    case R.id.place:
                        goPlace();
                        return true;

                    case R.id.notification:
                        goNotification();
                        return true;
                }
                return false;
            }
        });
        menuBottomNavigationView.setSelectedItemId(R.id.place);
        addFragment(new PlaceFragment(), "PlaceFragment");
        updateSize();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSize();
    }

    private void goProfile() {
        addFragment(new ProfileFragment(), "ProfileFragment");
    }

    private void goPlace() {
        addFragment(new PlaceFragment(), "PlaceFragment");
    }

    private void goNotification() {
        addFragment(new NotificationFragment(), "NotificationFragment");
    }

    private void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_layout, fragment, tag)
                .commit();
    }

    public void updateSize() {
        BadgeDrawable orCreateBadge = menuBottomNavigationView.getOrCreateBadge(R.id.place);
        orCreateBadge.setNumber(RoomDataBaseManager.getInstance(this).placeDao().getPlaces());
    }

    private void getFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.getTag().equalsIgnoreCase("PlaceFragment")) {
                PlaceFragment fragmentByTag = (PlaceFragment) getSupportFragmentManager()
                        .findFragmentByTag(fragment.getTag());
            }
        }
    }

   /* @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof PlaceFragment) {
            placeFragment = (PlaceFragment) getSupportFragmentManager()
                    .findFragmentByTag("PlaceFragment");
            ((PlaceFragment) placeFragment).setPlaceBadgeCallBack(this);
        }
    }*/
}
