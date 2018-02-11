package com.skiwithuge.envimove.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skiwithuge.envimove.Fragment.AlertFragment;
import com.skiwithuge.envimove.Fragment.FavFragment;
import com.skiwithuge.envimove.Fragment.LineFragment;
import com.skiwithuge.envimove.Fragment.MapFragment;
import com.skiwithuge.envimove.Fragment.SettingsFragment;
import com.skiwithuge.envimove.R;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_map);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if(savedInstanceState == null)
            replaceMainFragment(new MapFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_alert:
                replaceMainFragment(new AlertFragment());
                return true;
            case R.id.navigation_bus:
                replaceMainFragment(new LineFragment());
                return true;
            case R.id.navigation_map:
                replaceMainFragment(new MapFragment());
                return true;
            case R.id.navigation_favorites:
                replaceMainFragment(new FavFragment());
                return true;
            case R.id.navigation_settings:
                replaceMainFragment(new SettingsFragment());
                return true;
        }
        return true;
    }

    protected void replaceMainFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }


}
