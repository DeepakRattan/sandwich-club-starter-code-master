package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView ingredientsIv;
    private TextView origin_tv, description_tv, ingredients_tv, also_known_tv, detail_also_known_as_label_tv, detail_ingredients_label_tv, detail_place_of_origin_label_tv, detail_description_label_tv;
    Sandwich sandwich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        origin_tv = findViewById(R.id.origin_tv);
        description_tv = findViewById(R.id.description_tv);
        ingredients_tv = findViewById(R.id.ingredients_tv);
        also_known_tv = findViewById(R.id.also_known_tv);
        detail_also_known_as_label_tv = findViewById(R.id.detail_also_known_as_label_tv);
        detail_ingredients_label_tv = findViewById(R.id.detail_ingredients_label_tv);
        detail_place_of_origin_label_tv = findViewById(R.id.detail_place_of_origin_label_tv);
        detail_description_label_tv = findViewById(R.id.detail_description_label_tv);
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
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    private void populateUI() {
        if (sandwich.getPlaceOfOrigin() != null) {
            origin_tv.append(sandwich.getPlaceOfOrigin());
        } else {
            origin_tv.setVisibility(View.GONE);
            detail_place_of_origin_label_tv.setVisibility(View.GONE);
        }
        if (sandwich.getDescription() != null) {
            description_tv.append(sandwich.getDescription());
        } else {
            description_tv.setVisibility(View.GONE);
            detail_description_label_tv.setVisibility(View.GONE);

        }
        if (sandwich.getIngredients() != null) {
            List<String> ingr = sandwich.getIngredients();
            if (ingr.size() != 0) {
                for (int i = 0; i < ingr.size(); i++) {
                    ingredients_tv.append(ingr.get(i) + "\n");
                }
            }
        } else {
            detail_ingredients_label_tv.setVisibility(View.GONE);
        }
        if (sandwich.getAlsoKnownAs() != null) {
            List<String> alsoKnown = sandwich.getAlsoKnownAs();
            if (alsoKnown.size() != 0) {
                for (int i = 0; i < alsoKnown.size(); i++) {
                    also_known_tv.append(alsoKnown.get(i) + "\n");
                }
            }
        } else {
            detail_also_known_as_label_tv.setVisibility(View.GONE);
        }
    }
}
