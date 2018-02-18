package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String IMAGE = "image";
    private static final String DESCRIPTION = "description";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject data = new JSONObject(json);
            sandwich.setImage(data.getString(IMAGE));
            sandwich.setDescription(data.getString(DESCRIPTION));
            sandwich.setPlaceOfOrigin(data.getString(PLACE_OF_ORIGIN));
            sandwich.setMainName(data.getJSONObject(NAME).getString(MAIN_NAME));
            sandwich.setAlsoKnownAs(getListFromJsonArray(data.getJSONObject(NAME), ALSO_KNOWN_AS));
            sandwich.setIngredients(getListFromJsonArray(data, INGREDIENTS));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return sandwich;
    }

    private static ArrayList<String> getListFromJsonArray(JSONObject name, String jsonKey) throws JSONException {
        JSONArray jsonArray = name.getJSONArray(jsonKey);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.get(i).toString());
        }
        return list;
    }
}
