package com.skiwithuge.envimove.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.skiwithuge.envimove.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by skiwi on 2/13/18.
 */

public class BusStopList {
    @SerializedName("list")
    public ArrayList<BusStop> mList;

    public int size() {
        return mList.size();
    }

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
}
