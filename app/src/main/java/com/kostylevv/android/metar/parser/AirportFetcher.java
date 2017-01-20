package com.kostylevv.android.metar.parser;

import android.net.Uri;
import android.util.Log;
import com.kostylevv.android.metar.BuildConfig;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vkostylev on 20/01/17.
 */
public class AirportFetcher {

    public static String[] fetch() {

        HttpURLConnection urlConnection = null;

        BufferedReader reader = null;

        String airportJsonString = null;

        String[] result = null;

        try {
            //Base URL for MEtAR providers ICAO API
            final String BASE_URL_PARAM = "https://v4p4sz5ijk.execute-api.us-east-1.amazonaws.com/anbdata/airports/weather/metar-stations-list";
            //
            //see secret.properties
            final String API_KEY_PARAM = "api_key";

            final String AIRPORT = "airports";

            final String STATE = "states";

            final String FORMAT = "format";

            final String FORMAT_JSON = "json";

            final String AIRPORT_NOTSET = "";

            final String STATE_NOTSET = "";

            Uri.Builder buildUri = Uri.parse(BASE_URL_PARAM).buildUpon()
                    .appendQueryParameter(AIRPORT, AIRPORT_NOTSET)
                    .appendQueryParameter(STATE, STATE_NOTSET)
                    .appendQueryParameter(FORMAT, FORMAT_JSON)
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.ICAO_API_KEY);

            URL url = new URL(buildUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            airportJsonString = buffer.toString();

            result = getSimpleAirportsListFromJson(airportJsonString);

        } catch (IOException e) {
            Log.v("METAR", e.getMessage());
            //e.printStackTrace();
            return null;
        } catch (JSONException e) {
            Log.v("METAR", e.getMessage());
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String[] getSimpleAirportsListFromJson(String airportsJson)
            throws JSONException {

        /**
         * {
         "latitude": 30.218777777777778,
         "longitude": -81.87716666666665,
         "airportCode": "KVQQ",
         "airportName": "CECIL",
         "countryCode": "USA",
         "is_international": false,
         "countryName": "United States of America"
         },
         */


        JSONArray airportArray = new JSONArray(airportsJson);

        String[] result = new String[airportArray.length()];


        for (int i = 0; i < airportArray.length(); i++) {
            String airport = airportArray.getJSONObject(i).getString("airportCode");
            result[i] = airport;
        }


        return result;
    }



}
