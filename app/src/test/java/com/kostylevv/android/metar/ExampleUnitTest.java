package com.kostylevv.android.metar;

import com.kostylevv.android.metar.parser.Metar;
import com.kostylevv.android.metar.parser.MetarParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void metar_station_id_isCorrect() throws Exception {
        Metar metar = new Metar();
        metar = MetarParser.parseReport("EFHK 171150Z 20006KT 9999 BKN007 M05/M06 Q1030 NOSIG 17-01-17");
        assertEquals(metar.getStationID(), "EFHK");
    }

    @Test
    public void metar_date_isCorrect() throws Exception {
        Metar metar = new Metar();
        metar = MetarParser.parseReport("ULLI 171130Z 19002MPS 160V230 9999 OVC022 M04/M06 Q1031 R88/550348 NOSIG 17-01-17");
        assertEquals((long)metar.getWindDirection(), (long) 190);
    }




}