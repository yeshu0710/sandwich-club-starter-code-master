package com.udacity.sandwichclub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

    ImageView imageView;
    TextView alsoKnown_tv,ingredients_textview,placeOfOrigin_tv,description_textView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent;
        imageView = findViewById(R.id.image_iv);
        alsoKnown_tv=findViewById(R.id.also_known_tv);
        ingredients_textview=findViewById(R.id.ingredients_tv);
        placeOfOrigin_tv=findViewById(R.id.origin_tv);
        description_textView=findViewById(R.id.description_tv);

        intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = 0;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
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
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageView);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    private void populateUI(Sandwich sandwich) {
        //another names
        String another_name;
        List<String> anotherNameList= sandwich.getAlsoKnownAs();
        if (anotherNameList.isEmpty()){
            another_name=getString(R.string.no_another_name);
        }else {
            StringBuilder anotherNameBuilder= new StringBuilder();
            for (int i=0;i<anotherNameList.size();i++){
                anotherNameBuilder.append("\t\t\u2022").append("\t").append(anotherNameList.get(i));
                if (i != anotherNameList.size()-1)
                    anotherNameBuilder.append("\n");
            }
            another_name=anotherNameBuilder.toString();
        }
        alsoKnown_tv.setText(another_name);

        //ingredients
        String ingredients;
        List<String> IngredientsList=sandwich.getIngredients();
        if (IngredientsList.isEmpty()){
            ingredients=getString(R.string.ingredients_not_mentioned);
        }else {
            StringBuilder ingredientBulider=new StringBuilder();
            for (int i=0;i<IngredientsList.size();i++){
                ingredientBulider.append("\t\t\u2022").append("\t").append(IngredientsList.get(i));
                if (i != IngredientsList.size()-1)
                    ingredientBulider.append("\n");
            }
            ingredients=ingredientBulider.toString();
        }
        ingredients_textview.setText(ingredients);

        //place of origin
        String place_of_origin=sandwich.getPlaceOfOrigin();
        if (place_of_origin.equals("")||place_of_origin.equals(null)){
            place_of_origin=getString(R.string.place_not_available);
        }
        placeOfOrigin_tv.setText("\t\t\t"+place_of_origin);

        //description
        String description=sandwich.getDescription();
        if (description.equals("") || description.equals(null)){
            description=getString(R.string.description_not_available);
        }
        description_textView.setText("\t\t\t"+description);

    }
}
