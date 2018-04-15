package com.skiwithuge.envimove.interfaces;

import com.skiwithuge.envimove.model.BusStopList.BusStop;

/**
 * Created by skiwi on 3/19/18.
 */

public interface OnBusStopClickListener {
    void onBusStopSelected(BusStop b);
    void onFavSelected(BusStop b);
}
