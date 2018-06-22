package com.skiwithuge.envimove.Util;

import android.content.Context;
import android.location.LocationManager;

import com.skiwithuge.envimove.interfaces.Locator;

public class LocatorContext {
    private Locator locator;

    public LocatorContext(Locator locator){
        this.locator = locator;
    }

    public void getLocation(int method, Locator.Listener callback){
        locator.getLocation(method, callback);
    }
}
