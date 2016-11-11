package de.prolodeck.eddie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import de.prolodeck.eddie.configuration.CollectorConfig;
import de.prolodeck.eddie.heartofgold.EddieConnection;
import de.prolodeck.eddie.heartofgold.EddieService;
import de.prolodeck.eddie.heartofgold.StatusLight;
import de.prolodeck.eddie.heartofgold.StatusLightProvider;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by grebe on 11.11.2016.
 */
public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(StatusLight.class)
                .annotatedWith(Names.named("Upper"))
                .toProvider(StatusLightProvider.Upper.class);

        bind(StatusLight.class)
                .annotatedWith(Names.named("Lower"))
                .toProvider(StatusLightProvider.Lower.class);
    }

    @Provides
    CollectorConfig loadConfig() {
        final String configName = "/config.json";
        try (final InputStream inputStream = this.getClass().getResourceAsStream(configName)) {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, CollectorConfig.class);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

}
