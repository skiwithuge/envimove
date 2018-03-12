package com.skiwithuge.envimove;

import android.app.Application;

import com.google.gson.Gson;
import com.skiwithuge.envimove.model.BusStopList;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by skiwi on 2/13/18.
 */

public class MyApplication extends Application{
    private static BusStopList mBusStopList;

    public static BusStopList getBusStopList() {
        return mBusStopList;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        fillBusStopList();
    }

    public void fillBusStopList(){
        String myJson = inputStreamToString(this.getResources().openRawResource(R.raw.all_stations_cleaned));
        mBusStopList = new Gson().fromJson(myJson, BusStopList.class);
        mBusStopList.initializeMarkers();
    }

    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            return new String(bytes);
        } catch (IOException e) {
            return null;
        }
    }
}
