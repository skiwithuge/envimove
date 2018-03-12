package com.skiwithuge.envimove.fragment;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.skiwithuge.envimove.adapter.BusStopAdapter;
import com.skiwithuge.envimove.interfaces.OnBusStopClickListener;
import com.skiwithuge.envimove.interfaces.OnItemClickListener;
import com.skiwithuge.envimove.model.BusStopList;
import com.skiwithuge.envimove.MyApplication;
import com.skiwithuge.envimove.R;
import com.skiwithuge.envimove.Util.Locator;

/**
 * Created by skiwi on 2/7/18.
 */

public class LineFragment extends Fragment implements OnItemClickListener<BusStopList.BusStop>{
    BusStopList mList;
    LocationManager mLocationManager;
    RecyclerView mBusStopRecyclerView;
    private BusStopAdapter mAdapter;
    private OnBusStopClickListener mOnBusStopClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_line, container, false);
        mBusStopRecyclerView = v.findViewById(R.id.day_recycler_view);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBusStopRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        mList = MyApplication.getBusStopList();
//        WebView webView = (WebView) getView().findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("http://www.envibus.fr/flux.html?page=passages");
        Locator lm = new Locator(this.getContext());
        lm.getLocation(Locator.Method.NETWORK_THEN_GPS, new LineLocator());


        if (mAdapter == null) {
            mAdapter = new BusStopAdapter(mList, this);
            mBusStopRecyclerView.setAdapter(mAdapter);
        } else {
            mBusStopRecyclerView.setAdapter(mAdapter);
            mAdapter.setBusStops(mList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(BusStopList.BusStop item) {
        mOnBusStopClickListener.onBusStopClick(item);
    }

    public class LineLocator implements Locator.Listener{
        @Override
        public void onLocationFound(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            mList.orderFromPosition(new LatLng(latitude, longitude));
        }

        @Override
        public void onLocationNotFound() {

        }
    }
}
