package com.skiwithuge.envimove.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skiwithuge.envimove.R;
import com.skiwithuge.envimove.Util.SharedPreference;
import com.skiwithuge.envimove.adapter.BusStopAdapter;
import com.skiwithuge.envimove.interfaces.OnBusStopClickListener;
import com.skiwithuge.envimove.interfaces.OnItemClickListener;
import com.skiwithuge.envimove.model.BusStopList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by skiwi on 2/7/18.
 */

public class FavFragment extends Fragment  implements OnItemClickListener<BusStopList.BusStop> {
    BusStopList favList;
    @BindView(R.id.day_recycler_view) RecyclerView mBusStopRecyclerView;
    SharedPreference sharedPreference;
    private BusStopAdapter mAdapter;
    private OnBusStopClickListener mOnBusStopClickListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnBusStopClickListener = (OnBusStopClickListener) context;
        favList = new BusStopList();
        // Get favorite items from SharedPreferences.
        sharedPreference = new SharedPreference();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fav, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBusStopRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        updateUI();

    }

    private void updateUI() {
        favList.mList = sharedPreference.getFavorites(getActivity());

        if (mAdapter == null) {
            mAdapter = new BusStopAdapter(favList, this);
            mBusStopRecyclerView.setAdapter(mAdapter);
        } else {
            mBusStopRecyclerView.setAdapter(mAdapter);
            mAdapter.setBusStops(favList);
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
        updateUI();
    }
}
