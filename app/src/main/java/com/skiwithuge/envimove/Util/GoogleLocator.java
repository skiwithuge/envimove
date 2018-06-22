package com.skiwithuge.envimove.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.skiwithuge.envimove.interfaces.Locator;

public class GoogleLocator implements Locator, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "GOOGLE_LOCATION";
    private Context context;
    private LocationManager locationManager;
    private GoogleApiClient mGoogleApiClient;
    private GoogleLocator.Listener callback;

    public final static int PLAY_LOCATION = 1223;
    public GoogleLocator(Context context){
        this.context = context;
        buildGoogleApiClient();
        this.mGoogleApiClient.connect();
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v(TAG, "onConnected !!! :D");
    }

    @Override
    public void onConnectionSuspended(int i) {
        this.mGoogleApiClient.connect();
        Log.v(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v(TAG, "onConnectionFailed");
    }

    @Override
    public void getLocation(int method, Listener callback) {
        displayLocation(callback);
    }

    private void displayLocation(Listener callback) throws SecurityException {

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            callback.onLocationFound(mLastLocation);
        } else {
            if (this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                Toast.makeText(this.context,"Can not get the location try again",Toast.LENGTH_SHORT).show();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder((Activity)context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
