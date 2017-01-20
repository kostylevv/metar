package com.kostylevv.android.metar.model;

/**
 * Created by vkostylev on 20/01/17.
 */
public class Airport {

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

    public Airport (String latitude, String longitude, String code, String name,
                    String countryName, String countryCode, String isInternational) {

        LAT = Double.valueOf(latitude);
        LON = Double.valueOf(longitude);
        CODE = code;
        NAME = name;
        COUNTRY_NAME = countryName;
        COUNTRY_CODE = countryCode;

        if (isInternational.equalsIgnoreCase("false")) {
            IS_INTERNATIONAL = false;
        } else {
            IS_INTERNATIONAL = true;
        }
    }

    public final double LAT;
    public final double LON;
    public final String CODE;
    public final String NAME;
    public final String COUNTRY_CODE;
    public final boolean IS_INTERNATIONAL;
    public final String COUNTRY_NAME;

}
