package com.skiwithuge.envimove.model;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.maps.android.SphericalUtil;

/**
 * Created by skiwi on 2/13/18.
 */

public class BusStopList {
    @SerializedName("list")
    public ArrayList<BusStop> mList;

    static public class BusStop {
        @SerializedName("stop_id")
        public int stop_id;
        @SerializedName("name")
        public String name;
        @SerializedName("latitude")
        public float latitude;
        @SerializedName("longitude")
        public float longitude;
        @SerializedName("env_id")
        public int[] env_id;

        public LatLng marker;
    }

    public ArrayList<BusStop> getBusStopList() {
        return mList;
    }

    public int size() {
        return mList.size();
    }

    public BusStop get(int i) {
        return mList.get(i);
    }

    public void initializeMarkers() {
        for (BusStop b: mList) {
            b.marker = new LatLng(b.latitude,b.longitude);
        }
    }

    public void orderFromPosition(LatLng myPos){
        Collections.sort(mList, new BusStopDistanceComparator(myPos));
    }

    public static boolean checkFavoriteItem(BusStopList.BusStop checkProduct, SharedPreference sp,
                                            Context context) {
        boolean check = false;
        List<BusStop> favorites = sp.getFavorites(context);
        if (favorites != null) {
            for (BusStopList.BusStop product : favorites) {
                if (product.name.equals(checkProduct.name)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public class BusStopDistanceComparator implements Comparator<BusStop> {
        LatLng currentLoc;
        public BusStopDistanceComparator(LatLng current){
            currentLoc = current;
        }
        @Override
        public int compare(BusStop Friend1, BusStop Friend2) {
            double distanceBusStop1 = SphericalUtil.computeDistanceBetween(
                    Friend1.marker,currentLoc);
            double distanceBusStop2 = SphericalUtil.computeDistanceBetween(
                    Friend2.marker,currentLoc);

            return (int) (distanceBusStop1- distanceBusStop2);
        }
    }
}


