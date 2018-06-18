package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich=null;

        try {
            JSONObject jsonObject=new JSONObject(json);

            //getting Sandwich Name
            JSONObject sandwichName=jsonObject.getJSONObject("name");
            String sandwichmainName=sandwichName.getString("mainName");

            //getting anotherNames
            JSONArray anotherNamesArray=sandwichName.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs= jsontoArray(anotherNamesArray);

            //place of origin
            String placeOfOrigin=jsonObject.getString("placeOfOrigin");

            //descriptionwhy intent is stricking off in android studios
            String description=jsonObject.getString("description");

            //getting Image of the Sandwich
            String imageURL=jsonObject.getString("image");

            //Ingredients
            JSONArray ingredientsofSandwich=jsonObject.getJSONArray("ingredients");
            List<String> ingredients= jsontoArray(ingredientsofSandwich);

            sandwich=new Sandwich(sandwichmainName,alsoKnownAs,placeOfOrigin,description,imageURL,ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich ;
    }

    private static List<String> jsontoArray(JSONArray array) throws JSONException {
        int arrayLength=array.length();
        List<String> output=new ArrayList<>();
        for (int i=0;i<arrayLength;i++){
            output.add(array.getString(i));
        }
        return output;
    }
}
