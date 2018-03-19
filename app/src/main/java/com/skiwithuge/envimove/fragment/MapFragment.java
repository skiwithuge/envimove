package com.skiwithuge.envimove.fragment;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skiwithuge.envimove.Util.Locator;
import com.skiwithuge.envimove.model.BusStopList;
import com.skiwithuge.envimove.MyApplication;
import com.skiwithuge.envimove.R;

/**
 * Created by skiwi on 2/7/18.
 * Thanks to https://code.luasoftware.com/tutorials/android/supportmapfragment-in-fragment/
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // don't recreate fragment everytime ensure last map location/state are maintained
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }

        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        BusStopList list = MyApplication.getBusStopList();
        mMap = googleMap;
        Locator lm = new Locator(this.getContext());
        lm.getLocation(Locator.Method.NETWORK_THEN_GPS, new MapFragment.MapLocator());

        for(BusStopList.BusStop b: list.mList){
            mMap.addMarker(new MarkerOptions().position(b.marker).title(b.name));
        }
    }

    private class MapLocator implements Locator.Listener {
        @Override
        public void onLocationFound(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng current = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
        }

        @Override
        public void onLocationNotFound() {

        }
    }
}
