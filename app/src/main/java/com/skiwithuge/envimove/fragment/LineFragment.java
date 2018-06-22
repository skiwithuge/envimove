package com.skiwithuge.envimove.fragment;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.skiwithuge.envimove.Util.AndroidLocator;
import com.skiwithuge.envimove.Util.GoogleLocator;
import com.skiwithuge.envimove.Util.LocatorContext;
import com.skiwithuge.envimove.activity.MainActivity;
import com.skiwithuge.envimove.adapter.BusStopAdapter;
import com.skiwithuge.envimove.interfaces.OnBusStopClickListener;
import com.skiwithuge.envimove.interfaces.OnItemClickListener;
import com.skiwithuge.envimove.model.BusStopList;
import com.skiwithuge.envimove.MyApplication;
import com.skiwithuge.envimove.R;
import com.skiwithuge.envimove.interfaces.Locator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by skiwi on 2/7/18.
 */

public class LineFragment extends Fragment implements OnItemClickListener<BusStopList.BusStop>{
    BusStopList mList;
    @BindView(R.id.day_recycler_view) RecyclerView mBusStopRecyclerView;
    @BindView(R.id.search_bus) EditText mSearchBus;
    @BindView(R.id.swipeRefresh)SwipeRefreshLayout mySwipeRefreshLayout;
    private BusStopAdapter mAdapter;
    private OnBusStopClickListener mOnBusStopClickListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnBusStopClickListener = (OnBusStopClickListener) context;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_line, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBusStopRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mList = MyApplication.getBusStopList();
        updateUI();

        mSearchBus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence constraint, int i, int i1, int i2) {
                mAdapter.getFilter().filter(constraint);
            }

            @Override
            public void onTextChanged(CharSequence constraint, int i, int i1, int i2) {
                mAdapter.getFilter().filter(constraint);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        updateUI();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }

    private void updateUI() {
        LocatorContext lm;// = new LocatorContext(this.getContext());
        if(MainActivity.isGooglePlayServicesAvailable(getActivity())){
            lm = new LocatorContext(new GoogleLocator(this.getContext()));
            lm.getLocation(GoogleLocator.PLAY_LOCATION, new LineLocator());
        }else{
            lm = new LocatorContext(new AndroidLocator(this.getContext()));
            lm.getLocation(AndroidLocator.NETWORK_THEN_GPS, new LineLocator());
        }


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
        mOnBusStopClickListener.onBusStopSelected(item);
    }

    @Override
    public void onFavClick(BusStopList.BusStop item) {
        mOnBusStopClickListener.onFavSelected(item);
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
