package com.kostylevv.android.metar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /**
     * 19/01 TODO add Fragment with RecyclerView
     * 19/01 TODO add Loader for APTS list with API URL https://v4p4sz5ijk.execute-api.us-east-1.amazonaws.com/anbdata/airports/weather/metar-stations-list?api_key=aaec15f0-d987-11e6-b3ce-4fe0c5a3f2bb&airports=&states=&format=json
     * 20/01 TODO add Search
     * 20/01 TODO add DetailFragment
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchMetarTask().execute("ULLI");
    }
}
