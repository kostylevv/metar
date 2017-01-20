package com.kostylevv.android.metar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vkostylev on 19/01/17.
 */

public class AirportAdapter extends RecyclerView.Adapter<AirportAdapter.AirportAdapterViewHolder> {

    private String[] mAirportData;

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final AirportAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface AirportAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    public AirportAdapter(AirportAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class AirportAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mAirportTextView;

        public AirportAdapterViewHolder(View view) {
            super(view);
            mAirportTextView = (TextView) view.findViewById(R.id.tv_airport);
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
            String airport = mAirportData[adapterPosition];
            mClickHandler.onClick(airport);
        }
    }

    @Override
    public AirportAdapter.AirportAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.airport_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new AirportAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AirportAdapter.AirportAdapterViewHolder holder, int position) {
        String airport = mAirportData[position];
        holder.mAirportTextView.setText(airport);

    }

    @Override
    public int getItemCount() {
        if (null == mAirportData) return 0;
        return mAirportData.length;
    }

    public void setAirportData(String[] airportData) {
        mAirportData = airportData;
        notifyDataSetChanged();
    }


}
