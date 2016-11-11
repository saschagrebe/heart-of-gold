package de.prolodeck.eddie.heartofgold;

import com.google.inject.Provider;

/**
 * Created by grebe on 11.11.2016.
 */
public class StatusLightProvider implements Provider<StatusLight> {

    private final StatusLight.StatusLightPosition position;

    private StatusLightProvider(final StatusLight.StatusLightPosition position) {
        this.position = position;
    }

    @Override
    public StatusLight get() {
        return new StatusLight(position);
    }

    public static class Upper extends StatusLightProvider {

        public Upper() {
            super(StatusLight.StatusLightPosition.UPPER);
        }

    }

    public static class Lower extends StatusLightProvider {

        public Lower() {
            super(StatusLight.StatusLightPosition.LOWER);
        }

    }
}
