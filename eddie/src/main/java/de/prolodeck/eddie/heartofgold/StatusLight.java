package de.prolodeck.eddie.heartofgold;

import java.text.DecimalFormat;

/**
 * Created by grebe on 28.10.2016.
 */
public class StatusLight {

    private static final DecimalFormat INDEX_FORMAT = new DecimalFormat("00");

    private final Eddie eddie;

    private final StatusLightPosition position;

    public enum StatusLightPosition {
        UPPER("u"),
        LOWER("l");

        private final String key;

        private StatusLightPosition(final String key) {
            this.key = key;
        }
    }
    // - Help -
    // r<u|l><0|1> : rotate <upper|lower> <0|1>
    // s<u|l><pixel><color>
    // quit
    StatusLight(final Eddie eddie, final StatusLightPosition position) {
        this.eddie = eddie;
        this.position = position;
    }

    public void rotate() {
        eddie.send("r" + position.key + "1");
    }

    public void halt() {
        eddie.send("r" + position.key + "0");
    }

    public void switchLight(int index, int color) {
        eddie.send("s" + position.key + INDEX_FORMAT.format(index) + color);
    }
}