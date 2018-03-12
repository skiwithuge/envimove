package com.skiwithuge.envimove.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        for (BusStop arret: mList) {
            arret.marker = new LatLng(arret.latitude,arret.longitude);
        }
    }

    public void orderFromPosition(LatLng myPos){
        Collections.sort(mList, new BusStopDistanceComparator(myPos));
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


