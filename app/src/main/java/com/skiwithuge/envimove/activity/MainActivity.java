package com.skiwithuge.envimove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.skiwithuge.envimove.fragment.AlertFragment;
import com.skiwithuge.envimove.fragment.FavFragment;
import com.skiwithuge.envimove.fragment.LineFragment;
import com.skiwithuge.envimove.fragment.MapFragment;
import com.skiwithuge.envimove.fragment.SettingsFragment;
import com.skiwithuge.envimove.R;
import com.skiwithuge.envimove.interfaces.OnBusStopClickListener;
import com.skiwithuge.envimove.model.BusStopList;
import com.skiwithuge.envimove.Util.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, OnBusStopClickListener{
    @BindView(R.id.navigation) BottomNavigationView bottomNavigationView;

    public final static String ENVID = "envID";
    SharedPreference mSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if(savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_bus);
            replaceMainFragment(new LineFragment());
        }
        mSharedPreference = new SharedPreference();
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


    @Override
    public void onBusStopSelected(BusStopList.BusStop b) {
        Intent myIntent = new Intent(this, BusStopActivity.class);
        myIntent.putExtra(ENVID, b.env_id);
        this.startActivity(myIntent);
    }

    @Override
    public void onFavSelected(BusStopList.BusStop b) {
        if( !BusStopList.checkFavoriteItem(b, mSharedPreference,this) )
            mSharedPreference.addFavorite(this, b);
        else
            mSharedPreference.removeFavorite(this, b);

    }

}
