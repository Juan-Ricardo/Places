package com.pe.places.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pe.places.notification.NotificationFragment;
import com.pe.places.place.PlaceFragment;
import com.pe.places.profile.ProfileFragment;
import com.pe.places.R;

public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView menuBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuBottomNavigationView=findViewById(R.id.menu_bottom_navigation);
        menuBottomNavigationView.setSelectedItemId(R.id.place);
        menuBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        goProfile();
                        break;

                    case R.id.place:
                        goPlace();
                        break;

                    case R.id.notification:
                        goNotification();
                        break;
                }
                return false;
            }
        });
        addFragment(new PlaceFragment());
    }

    private void goProfile(){
        addFragment(new ProfileFragment());
        //Toast.makeText(getBaseContext(),"Profile",Toast.LENGTH_SHORT).show();
    }

    private void goPlace(){
        addFragment(new PlaceFragment());
        //Toast.makeText(getBaseContext(),"Place",Toast.LENGTH_SHORT).show();
    }

    private void goNotification(){
        addFragment(new NotificationFragment());
        //Toast.makeText(getBaseContext(),"Notification",Toast.LENGTH_SHORT).show();
    }

    private void addFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_layout, fragment, fragment.getClass().getSimpleName())
                .commit();
    }
}
