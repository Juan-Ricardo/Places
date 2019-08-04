package com.pe.places;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PlaceDetailActivity extends AppCompatActivity {

    private MaterialButton savePlaceMaterialButton;
    private TextInputEditText namePlaceTextInputEditText;
    private TextInputEditText countryTextInputEditText;
    private Toolbar toolbar;
    public static final String COUNTRY="country";
    public static final String NAME="name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        finds();
    }

    private void finds() {
        savePlaceMaterialButton = findViewById(R.id.save_place_material_button);
        namePlaceTextInputEditText = findViewById(R.id.name_place_text_input_edit_text);
        countryTextInputEditText = findViewById(R.id.country_text_input_edit_text);
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
            String country = countryTextInputEditText.getText().toString();

            Bundle bundle=new Bundle();
            bundle.putString(NAME,name);
            bundle.putString(COUNTRY,country);

            Intent intent=new Intent();
            intent.putExtras(bundle);

            setResult(Activity.RESULT_OK,intent);
            finish();
            //Toast.makeText(getBaseContext(), " " + name + " " + country, Toast.LENGTH_LONG).show();
        }
    };
}
