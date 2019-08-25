package com.pe.places.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pe.places.dao.RoomDataBaseManager;
import com.pe.places.login.LoginActivity;
import com.pe.places.map.MapFragment;
import com.pe.places.notification.NotificationFragment;
import com.pe.places.place.PlaceFragment;
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
        menuBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        goProfile();
                        return true;

                    case R.id.place:
                        goPlace();
                        return true;

                    case R.id.map:
                        goMap();
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

    private void goMap(){
        Dexter.withActivity(MenuActivity.this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            addFragment(new MapFragment(), "MapFragment");
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("Necesita permisos");
        builder.setMessage("Esta aplicación necesita permiso para usar esta función. Puede otorgarlos en la configuración de la aplicación.");
        builder.setPositiveButton("Ir a la configuración", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
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

        //Para mostrar un Badge sin número.
        BadgeDrawable profileBadgeDrawable = menuBottomNavigationView.getOrCreateBadge(R.id.profile);
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
