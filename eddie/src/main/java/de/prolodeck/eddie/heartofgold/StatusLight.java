package de.prolodeck.eddie.heartofgold;

import com.google.inject.Inject;
import de.prolodeck.eddie.configuration.AdapterConfig;

import java.text.DecimalFormat;

/**
 * Created by grebe on 28.10.2016.
 */
public class StatusLight {

    private final DecimalFormat INDEX_FORMAT = new DecimalFormat("00");

    @Inject
    private EddieConnection eddie;

    private final StatusLightPosition position;

    public enum StatusLightPosition {
        UPPER("u"),
        LOWER("l");

        private final String key;

        StatusLightPosition(final String key) {
            this.key = key;
        }
    }

    // - Help -
    // w<u|l><whirlSpeed>
    // s<u|l><pixel><color>
    // q
    StatusLight(final StatusLightPosition position) {
        this.position = position;
    }

    public void configure(AdapterConfig config) {
        // set whirl speed
        eddie.send("w" + position.key + config.getWhirlSpeed());
    }

    public void switchLight(int index, int color) {
        eddie.send("s" + position.key + INDEX_FORMAT.format(index) + color);
    }
}
