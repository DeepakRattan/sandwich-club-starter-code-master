package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static String mainName;
    private static List<String> alsoKnownAs = null;
    private static String placeOfOrigin;
    private static String description;
    private static String image;
    private static List<String> ingredients = null;
    private static Sandwich sandwich;

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");
            mainName = name.getString("mainName");
            JSONArray jsonAr = name.getJSONArray("alsoKnownAs");
            if (jsonAr.length() != 0) {
                alsoKnownAs = new ArrayList<>();
                for (int i = 0; i < jsonAr.length(); i++) {
                    String str = jsonAr.getString(i);
                    alsoKnownAs.add(str);
                }

            }
            placeOfOrigin = jsonObject.getString("placeOfOrigin");
            image = jsonObject.getString("image");
            JSONArray jsonArray = jsonObject.getJSONArray("ingredients");
            //if jsonArray is not empty
            if (jsonArray.length() != 0) {
                ingredients = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String s = jsonArray.getString(i);
                    ingredients.add(s);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        return sandwich;
    }
}
