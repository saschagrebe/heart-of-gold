package de.prolodeck.eddie.heartofgold;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import de.prolodeck.eddie.configuration.AdapterConfig;
import de.prolodeck.eddie.configuration.CollectorConfig;

/**
 * Created by grebe on 11.11.2016.
 */
public class StatusLightProvider implements Provider<StatusLight> {

    @Inject
    private Injector injector;

    @Inject
    private CollectorConfig config;

    private final StatusLight.StatusLightPosition position;

    private StatusLightProvider(final StatusLight.StatusLightPosition position) {
        this.position = position;
    }

    @Override
    public StatusLight get() {
        final StatusLight light = new StatusLight(position);
        injector.injectMembers(light);

        // configure after injection to be able to access eddie
        light.configure(getAdapterConfig());

        return light;
    }

    private AdapterConfig getAdapterConfig() {
        final AdapterConfig adapterConfig;
        if (StatusLight.StatusLightPosition.UPPER.equals(position)) {
            adapterConfig = config.getUpperStatusLightAdapter();
        } else if (StatusLight.StatusLightPosition.LOWER.equals(position)) {
            adapterConfig = config.getLowerStatusLightAdapter();
        } else {
            adapterConfig = null;
        }

        return adapterConfig;
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
