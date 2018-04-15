package com.skiwithuge.envimove.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.skiwithuge.envimove.model.BusStopList;

public class SharedPreference {

    public static final String PREFS_NAME = "ENVIMOVE";
    public static final String FAVORITES = "BusStop_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<BusStopList.BusStop> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.apply();
    }

    public void addFavorite(Context context, BusStopList.BusStop product) {
        List<BusStopList.BusStop> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<BusStopList.BusStop>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, BusStopList.BusStop product) {
        ArrayList<BusStopList.BusStop> favorites = getFavorites(context);
        if (favorites != null) {
            for(BusStopList.BusStop b : favorites){
                if(b.name.equals(product.name)){
                    favorites.remove(b);
                    break;
                }
            }
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<BusStopList.BusStop> getFavorites(Context context) {
        SharedPreferences settings;
        List<BusStopList.BusStop> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            BusStopList.BusStop[] favoriteItems = gson.fromJson(jsonFavorites,
                    BusStopList.BusStop[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<BusStopList.BusStop>(favorites);
        } else
            return null;

        return (ArrayList<BusStopList.BusStop>) favorites;
    }
}