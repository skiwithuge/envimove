/*
 * For filterable:
 * https://stackoverflow.com/questions/17720481/how-could-i-filter-the-listview-using-baseadapter
 */
package com.skiwithuge.envimove.adapter;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.skiwithuge.envimove.R;
import com.skiwithuge.envimove.interfaces.OnItemClickListener;
import com.skiwithuge.envimove.model.BusStopList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.BusStopHolder>
        implements Filterable {

    private BusStopList busAsset;
    private BusStopList busStopsFilter;
    private OnItemClickListener<BusStopList.BusStop> listener;
    private ValueFilter valueFilter;

    public BusStopAdapter(BusStopList busStops,
                          OnItemClickListener<BusStopList.BusStop> listener) {
        this.busAsset = busStops;
        this.busStopsFilter = busStops;
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
        holder.bindBusStop(busStopsFilter.get(position));
    }

    @Override
    public int getItemCount() {
        return busStopsFilter.size();
    }

    public void setBusStops(BusStopList busStops) {
        this.busStopsFilter = busStops;
    }

    class BusStopHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bus_stop_name)
        TextView mTitle;

        public BusStopHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindBusStop(final BusStopList.BusStop busStop) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onItemClick(busStop);
                }
            });

            mTitle.setText(busStop.name);
        }
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null)
            valueFilter=new ValueFilter();

        return valueFilter;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                BusStopList busStopsFiltered = new BusStopList();
                busStopsFiltered.mList = new ArrayList<BusStopList.BusStop>();
                for(int i=0;i<busAsset.size();i++){
                    if( (busAsset.get(i).name.toUpperCase())
                            .contains(constraint.toString().toUpperCase()) ){
                        busStopsFiltered.mList.add(busAsset.get(i));
                    }
                }
                results.count=busStopsFiltered.size();
                results.values=busStopsFiltered;
            }else{
                results.count=busStopsFilter.size();
                results.values=busStopsFilter;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            busStopsFilter = (BusStopList) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
