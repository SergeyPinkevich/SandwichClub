package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            sandwich.setImage(new JSONObject(json).getString("image"));
            sandwich.setDescription(new JSONObject(json).getString("description"));
            sandwich.setPlaceOfOrigin(new JSONObject(json).getString("placeOfOrigin"));
            JSONObject name = new JSONObject(json).getJSONObject("name");
            sandwich.setMainName(name.getString("mainName"));
            sandwich.setAlsoKnownAs(getAlsoKnownNames(name));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return sandwich;
    }

    private static ArrayList<String> getAlsoKnownNames(JSONObject name) throws JSONException {
        JSONArray alsoKnown = name.getJSONArray("alsoKnownAs");
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < alsoKnown.length(); i++) {
            names.add(alsoKnown.get(i).toString());
        }
        return names;
    }
}
