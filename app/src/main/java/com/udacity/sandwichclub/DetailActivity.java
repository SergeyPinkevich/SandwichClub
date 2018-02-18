package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView ingredientsImage;
    private TextView placeOfOriginText;
    private TextView descriptionText;
    private TextView alsoKnownText;
    private TextView ingredientsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        placeOfOriginText.setText(checkDataIsNotEmpty(sandwich.getPlaceOfOrigin()));
        descriptionText.setText(checkDataIsNotEmpty(sandwich.getDescription()));

        renderList(alsoKnownText, sandwich.getAlsoKnownAs());
        renderList(ingredientsText, sandwich.getIngredients());

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsImage);

        getSupportActionBar().setTitle(sandwich.getMainName());
    }

    private String checkDataIsNotEmpty(String data) {
        return data.equals("") ? getString(R.string.unknown) : data;
    }

    private void renderList(TextView text, List<String> list) {
        if (list.size() == 0) {
            text.setText(getString(R.string.unknown));
        }
        for (int i = 0; i < list.size(); i++) {
            text.append(list.get(i));
            if (i < list.size() - 1) {
                text.append(", ");
            }
        }
    }

    private void initViews() {
        ingredientsImage = findViewById(R.id.image_iv);
        placeOfOriginText = findViewById(R.id.origin_tv);
        descriptionText = findViewById(R.id.description_tv);
        alsoKnownText = findViewById(R.id.also_known_tv);
        ingredientsText = findViewById(R.id.ingredients_tv);
    }
}