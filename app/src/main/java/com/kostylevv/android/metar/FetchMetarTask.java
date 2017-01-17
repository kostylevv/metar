package com.kostylevv.android.metar;

/**
 * Created by vkostylev on 17/01/17.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.kostylevv.android.metar.parser.Metar;
import com.kostylevv.android.metar.parser.MetarParseException;
import com.kostylevv.android.metar.parser.MetarParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vkostylev on 17/01/17.
 * This class used to request ICAO weather and parse JSON response
 */
public class FetchMetarTask extends AsyncTask<String, Void, Metar> {
    /**
     *
     * @param - Airport ICAO code
     * @return
     */
    @Override
    protected Metar doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String metarJsonString = null;

        Metar result = new Metar();


        try {
            //Base URL for ICAO API
            final String BASE_URL_PARAM = "https://v4p4sz5ijk.execute-api.us-east-1.amazonaws.com/anbdata/airports/weather/current-conditions-list";

            //see secret.properties
            final String API_KEY_PARAM = "api_key";

            final String AIRPORT = "airports";

            final String FORMAT = "format";

            final String JSON = "json";

            Uri.Builder buildUri = Uri.parse(BASE_URL_PARAM).buildUpon()
                    .appendQueryParameter(AIRPORT, params[0])
                    .appendQueryParameter(FORMAT, JSON)
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.ICAO_API_KEY);

            URL url = new URL(buildUri.toString());
            Log.v("METAR", url.toString());

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

            metarJsonString = buffer.toString();

            result = getMetarDataFromJSON(metarJsonString);

        } catch (IOException e) {
            Log.v("METAR", e.getMessage());
            //e.printStackTrace();
            return null;
        } catch (JSONException e) {
            Log.v("METAR", e.getMessage());
            return null;
        } catch (MetarParseException e) {
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


    @Override
    protected void onPostExecute(Metar metar) {

        //delegate.processFinish(metar);
        Log.v("METAR", metar.getStationID());

    }

    /**
     *
     * @param metarJsonString - JSON which contains METAR data
     * @return - Metar object
     * @throws JSONException
     */
    private Metar getMetarDataFromJSON(String metarJsonString)
            throws JSONException, MetarParseException {

        final String RAW_METAR = "raw_metar";

        JSONArray metarArray = new JSONArray(metarJsonString);
        JSONObject metarJson = metarArray.getJSONObject(0);

        Metar result = new Metar();

        result = MetarParser.parseReport(metarJson.getString(RAW_METAR));

        return result;

    }


}



