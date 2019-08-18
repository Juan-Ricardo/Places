package com.pe.places.place;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pe.places.R;
import com.pe.places.dao.Place;
import com.pe.places.dao.RoomDataBaseManager;
import com.pe.places.utilities.Constants;
import com.tapadoo.alerter.Alerter;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PlaceDetailActivity extends AppCompatActivity {

    private MaterialButton savePlaceMaterialButton;
    private TextInputEditText namePlaceTextInputEditText;
    private TextInputEditText descriptionTextInputEditText;
    private Toolbar toolbar;
    private MenuItem deleteMenuItem;
    public static final String COUNTRY = "country";
    public static final String NAME = "name";
    private Place currentPlace;
    private String crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        finds();
    }

    private void finds() {
        savePlaceMaterialButton = findViewById(R.id.save_place_material_button);
        namePlaceTextInputEditText = findViewById(R.id.name_place_text_input_edit_text);
        descriptionTextInputEditText = findViewById(R.id.descriptio_text_input_edit_text);
        savePlaceMaterialButton.setOnClickListener(savePlaceOnClickListener);
        setupToolbar("Nuevo", "", true);
    }

    private void setupToolbar(String title, String subTitle, boolean arrow) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(arrow);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            crud = extras.getString(Constants.CRUD);
            if (crud.equalsIgnoreCase(Constants.UPDATE)) {
                currentPlace = (Place) extras.getSerializable(Constants.PLACE);
                namePlaceTextInputEditText.setText("" + currentPlace.getName());
                descriptionTextInputEditText.setText("" + currentPlace.getDescription());
                savePlaceMaterialButton.setText("Update");
                toolbar.setTitle("Actualizar");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    View.OnClickListener savePlaceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveOrUpdate();
        }
    };

    private void saveOrUpdate() {

        String name = namePlaceTextInputEditText.getText().toString();
        String description = descriptionTextInputEditText.getText().toString();

        //placeValidate(name, description);
        if (name.isEmpty()) {
            showMessage("Nombre",R.drawable.ic_user
                    ,"Por favor ingrese nombre de la ciudad",R.color.dark);
            return;
        }
        if (description.isEmpty()) {
            showMessage("Descripción",R.drawable.ic_place
                    ,"Por favor ingrese una descripción de la ciudad",R.color.primary_dark);
            return;
        }

        if (crud.equalsIgnoreCase(Constants.UPDATE)) {
            currentPlace.setName(name);
            currentPlace.setDescription(description);
            RoomDataBaseManager.getInstance(getBaseContext()).placeDao().update(currentPlace);
        } else {
            Place newPlace = new Place();
            newPlace.setName(name);
            newPlace.setImage(getRandomImage());
            newPlace.setDescription(description);
            RoomDataBaseManager.getInstance(getBaseContext()).placeDao().save(newPlace);
        }
        finish();
    }

    private void showMessage(String name,int icon, String detail, int background) {
        Alerter.create(PlaceDetailActivity.this)
                .setTitle(name)
                .setIcon(icon)
                .setText(detail)
                .setBackgroundColorRes(background)
                .show();
    }

    private void placeDelete() {
        RoomDataBaseManager.getInstance(getBaseContext()).placeDao().delete(currentPlace);
        finish();
    }

    private String getRandomImage() {
        List<String> images = new LinkedList<>();
        images.add("https://img.elcomercio.pe/files/ec_article_multimedia_gallery/uploads/2019/06/07/5cfaadc5147aa.jpeg");
        images.add("https://s.twojahistoria.pl/uploads/2019/05/Machu-Picchu-fot.-Diego-Delso-lic.-CC-BY-SA-4.0.jpg");
        images.add("https://e.an.amtv.pe/actualidad-conoce-distritos-donde-metro-cuadrado-bajo-lima-n310432-624x352-442876.jpg");
        images.add("https://portal.andina.pe/EDPfotografia3/Thumbnail/2019/01/03/000553769W.jpg");
        images.add("http://2.blogs.elcomercio.pe/checklistviajero/wp-content/uploads/sites/292/2018/10/Tarapoto-5-1.jpg");
        images.add("https://img.elcomercio.pe/files/article_content_ec_fotos/uploads/2018/03/30/5abef95f528f5.jpeg");
        images.add("https://www.perurail.com/wp-content/uploads/2017/05/Catedral-Plaza-de-Armas-Arequipa1.jpg");
        images.add("https://img.elcomercio.pe/files/ec_article_multimedia_gallery/uploads/2017/08/11/598e241bc94d6.jpeg");
        images.add("http://atawallpaperutravel.com/wp-content/uploads/2018/12/MONTA%C3%91A-7-COLORES-PERU.jpg");
        images.add("https://arc-anglerfish-arc2-prod-elcomercio.s3.amazonaws.com/public/RBHZZSN3XRGMZATQLAKOCVBHPM.jpg");
        Collections.shuffle(images);
        return images.get(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.place_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_item:
                placeDelete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
