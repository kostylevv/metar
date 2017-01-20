package com.kostylevv.android.metar;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kostylevv.android.metar.parser.AirportFetcher;

public class MainActivity extends AppCompatActivity
        implements AirportAdapter.AirportAdapterOnClickHandler, LoaderManager.LoaderCallbacks<String[]> {

    /**
     * 19/01 COMPLETED add RecyclerView
     * 19/01 COMPLETED add Loader for APTS list with API URL
     * https://v4p4sz5ijk.execute-api.us-east-1.amazonaws.com/anbdata/airports/weather/metar-stations-list?api_key=KEY&airports=&states=&format=json
     * 20/01 TODO add Search
     * 20/01 TODO add DetailFragment
     */

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private AirportAdapter mAirportAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private static final int AIRPORT_LOADER_ID = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_forecast);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        //content size is consistent
        mRecyclerView.setHasFixedSize(true);

        mAirportAdapter = new AirportAdapter(this);

        mRecyclerView.setAdapter(mAirportAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        int loaderId = AIRPORT_LOADER_ID;

        LoaderManager.LoaderCallbacks<String[]> callback = MainActivity.this;

        Bundle bundleForLoader = null;

        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);
    }

    @Override
    public Loader<String[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String[]>(this) {

            String[] mAirportData = null;

            @Override
            protected void onStartLoading() {
                if (mAirportData != null) {
                    deliverResult(mAirportData);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public String[] loadInBackground() {

                try {
                    String[] result = AirportFetcher.fetch();
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            /**
             * Sends the result of the load to the registered listener.
             *
             * @param data The result of the load
             */
            public void deliverResult(String[] data) {
                mAirportData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mAirportAdapter.setAirportData(data);
        if (null == data) {
            showErrorMessage();
        } else {
            showAirportsDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {

    }

    @Override
    public void onClick(String weatherForDay) {

    }

    private void showAirportsDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
