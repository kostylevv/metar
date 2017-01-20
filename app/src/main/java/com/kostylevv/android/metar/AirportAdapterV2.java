package com.kostylevv.android.metar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kostylevv.android.metar.model.Airport;

import java.util.List;

/**
 * Created by vkostylev on 19/01/17.
 */

public class AirportAdapterV2 extends RecyclerView.Adapter<AirportAdapterV2.AirportAdapterViewHolder> {

    private List<Airport> mAirportData;

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final AirportAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface AirportAdapterOnClickHandler {
        void onClick(Airport airport);
    }

    public AirportAdapterV2(AirportAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class AirportAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mAirportCodeTextView;
        public final TextView mAirportNameTextView;
        public final TextView mAirportCountryCodeTextView;


        public AirportAdapterViewHolder(View view) {
            super(view);
            mAirportCodeTextView = (TextView) view.findViewById(R.id.tv_airport_code);
            mAirportCountryCodeTextView = (TextView) view.findViewById(R.id.tv_airport_country);
            mAirportNameTextView = (TextView) view.findViewById(R.id.tv_airport_name);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Airport airport = mAirportData.get(adapterPosition);
            mClickHandler.onClick(airport);
        }
    }

    @Override
    public AirportAdapterV2.AirportAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.airport_list_item_v2;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new AirportAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AirportAdapterV2.AirportAdapterViewHolder holder, int position) {
        Airport airport = mAirportData.get(position);
        holder.mAirportNameTextView.setText(airport.NAME);
        holder.mAirportCountryCodeTextView.setText(airport.COUNTRY_CODE);
        holder.mAirportCodeTextView.setText(airport.CODE);

    }

    @Override
    public int getItemCount() {
        if (null == mAirportData) return 0;
        return mAirportData.size();
    }

    public void setAirportData(List<Airport> airportData) {
        mAirportData = airportData;
        notifyDataSetChanged();
    }


}
