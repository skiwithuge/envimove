package com.skiwithuge.envimove.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skiwithuge.envimove.R;
import com.skiwithuge.envimove.interfaces.OnItemClickListener;
import com.skiwithuge.envimove.model.BusStopList;

import java.util.List;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.BusStopHolder> {

    private BusStopList busStops;
    private OnItemClickListener<BusStopList.BusStop> listener;

    public BusStopAdapter(BusStopList busStops,
                          OnItemClickListener<BusStopList.BusStop> listener) {
        this.busStops = busStops;
        this.listener = listener;
    }

    @Override
    public BusStopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_bus_stop, parent, false);
        return new BusStopHolder(view);
    }

    @Override
    public void onBindViewHolder(BusStopHolder holder, int position) {
        holder.bindBusStop(busStops.get(position));
    }

    @Override
    public int getItemCount() {
        return busStops.size();
    }

    public void setBusStops(BusStopList busStops) {
        this.busStops = busStops;
    }

    class BusStopHolder extends RecyclerView.ViewHolder {
        TextView mTitle;

        public BusStopHolder(View itemView) {
            super(itemView);
        }

        void bindBusStop(final BusStopList.BusStop busStop) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onItemClick(busStop);
                }
            });

//            mDate.setText(Long.toString(day.getDate()));
//            mTitle.setText(day.getTitle());
            mTitle = itemView.findViewById(R.id.card_title);
            mTitle.setText(busStop.name);
        }
    }
}
