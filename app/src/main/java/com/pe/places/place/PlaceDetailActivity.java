package com.pe.places.place;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pe.places.R;
import com.pe.places.dao.Place;
import com.pe.places.dao.RoomDataBaseManager;
import com.tapadoo.alerter.Alerter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PlaceDetailActivity extends AppCompatActivity {

    private MaterialButton savePlaceMaterialButton;
    private TextInputEditText namePlaceTextInputEditText;
    private TextInputEditText descriptionTextInputEditText;
    private Toolbar toolbar;
    public static final String COUNTRY = "country";
    public static final String NAME = "name";

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
        setupToolbar("Nuevo Lugar", "", false);
    }

    private void setupToolbar(String title, String subTitle, boolean arrow) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(arrow);
    }

    View.OnClickListener savePlaceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = namePlaceTextInputEditText.getText().toString();
            String description = descriptionTextInputEditText.getText().toString();

            Place place = new Place();
            place.setName(name);
            place.setImage(getRandomImage());
            place.setDescription(description);

            if (place.getName().isEmpty()) {
                Alerter.create(PlaceDetailActivity.this)
                        .setTitle("Nombre")
                        .setIcon(R.drawable.ic_user)
                        .setText("Por favor ingrese nombre de la ciudad")
                        .setBackgroundColorRes(R.color.dark)
                        .show();
                return;
            } else if (place.getDescription().isEmpty()) {
                Alerter.create(PlaceDetailActivity.this)
                        .setTitle("Descripción")
                        .setIcon(R.drawable.ic_place)
                        .setText("Por favor ingrese una descripción de la ciudad")
                        .setBackgroundColorRes(R.color.dark)
                        .show();
                return;
            } else {
                RoomDataBaseManager.getInstance(getBaseContext()).placeDao().save(place);
                finish();
            }

            //Toast.makeText(getBaseContext(), " " + name + " " + country, Toast.LENGTH_LONG).show();
        }
    };

    private String getRandomImage() {
        List<String> images = new LinkedList<>();
        images.add("https://img.elcomercio.pe/files/ec_article_multimedia_gallery/uploads/2019/06/07/5cfaadc5147aa.jpeg");
        images.add("https://s.twojahistoria.pl/uploads/2019/05/Machu-Picchu-fot.-Diego-Delso-lic.-CC-BY-SA-4.0.jpg");
        images.add("https://portal.andina.pe/EDPfotografia3/Thumbnail/2019/01/03/000553769W.jpg");
        images.add("http://2.blogs.elcomercio.pe/checklistviajero/wp-content/uploads/sites/292/2018/10/Tarapoto-5-1.jpg");
        images.add("https://img.elcomercio.pe/files/article_content_ec_fotos/uploads/2018/03/30/5abef95f528f5.jpeg");
        images.add("https://www.perurail.com/wp-content/uploads/2017/05/Catedral-Plaza-de-Armas-Arequipa1.jpg");
        Collections.shuffle(images);
        return images.get(0);
    }
}
