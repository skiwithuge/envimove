package com.skiwithuge.envimove.fragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skiwithuge.envimove.Util.Locator;
import com.skiwithuge.envimove.activity.BusStopActivity;
import com.skiwithuge.envimove.model.BusStopList;
import com.skiwithuge.envimove.MyApplication;
import com.skiwithuge.envimove.R;

import static com.skiwithuge.envimove.activity.MainActivity.ENVID;

/**
 * Created by skiwi on 2/7/18.
 * Thanks to https://code.luasoftware.com/tutorials/android/supportmapfragment-in-fragment/
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private BusStopList list;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        list = MyApplication.getBusStopList();
        mMap = googleMap;
        Locator lm = new Locator(this.getContext());
        lm.getLocation(Locator.Method.NETWORK_THEN_GPS, new MapFragment.MapLocator());

        for(BusStopList.BusStop b: list.mList){
            mMap.addMarker(new MarkerOptions().position(b.marker).title(b.name));
        }

        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        for(BusStopList.BusStop b: list.mList) {
            if(b.marker.equals(marker.getPosition())){
                Intent myIntent = new Intent(getActivity(), BusStopActivity.class);
                myIntent.putExtra(ENVID, b.env_id);
                this.startActivity(myIntent);
            }
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
