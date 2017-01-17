package com.kostylevv.android.metar;

import com.kostylevv.android.metar.parser.Metar;

/**
 * Created by vkostylev on 17/01/17.
 * Interface intended to deliver messages from FetchMetarTask.
 */
public interface AsyncResponse {
    void processFinish(Metar response);
}
