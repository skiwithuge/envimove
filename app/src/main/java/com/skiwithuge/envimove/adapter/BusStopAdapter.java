/*
 * For filterable:
 * https://stackoverflow.com/questions/17720481/how-could-i-filter-the-listview-using-baseadapter
 */
package com.skiwithuge.envimove.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.skiwithuge.envimove.R;
import com.skiwithuge.envimove.interfaces.OnItemClickListener;
import com.skiwithuge.envimove.model.BusStopList;
import com.skiwithuge.envimove.Util.SharedPreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.BusStopHolder>
        implements Filterable {

    private Context context;
    private BusStopList busAsset;
    private BusStopList busStopsFilter;
    private OnItemClickListener<BusStopList.BusStop> listener;
    private ValueFilter valueFilter;
    private SharedPreference mSharedPreference;


    public BusStopAdapter(BusStopList busStops,
                          OnItemClickListener<BusStopList.BusStop> listener) {
        this.busAsset = busStops;
        this.busStopsFilter = busStops;
        this.listener = listener;
        this.mSharedPreference = new SharedPreference();
    }

    @NonNull
    @Override
    public BusStopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        this.context = parent.getContext();
        View view = layoutInflater.inflate(R.layout.list_item_bus_stop, parent, false);
        return new BusStopHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusStopHolder holder, int position) {
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
        @BindView(R.id.ic_fav)
        ImageView mFav;

        public BusStopHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindBusStop(final BusStopList.BusStop busStop) {
            mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onItemClick(busStop);
                }
            });

            mFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onFavClick(busStop);
                    notifyDataSetChanged();
                }
            });

            mTitle.setText(busStop.name);

            if (BusStopList.checkFavoriteItem(busStop,mSharedPreference,context))
                mFav.setImageResource(R.drawable.ic_favorite_red_a700_24dp);
            else
                mFav.setImageResource(R.drawable.ic_favorite_border_red_a700_24dp);

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
