package de.prolodeck.eddie.configuration;

import static org.testng.Assert.*;

import com.google.inject.Inject;
import de.prolodeck.eddie.Module;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/**
 * Created by grebe on 12.11.2016.
 */
@Test
@Guice(modules = Module.class)
public class CollectorConfigTest {

    @Inject
    private CollectorConfig config;

    @Test
    public void loadConfig() {
        assertNotNull(config);
        assertEquals(config.getServerIp(), "AnyIP");
        assertEquals(config.getServerPort(), 12345);
        assertNotNull(config.getLowerStatusLightAdapter());
        checkLowerConfig();
        assertNotNull(config.getUpperStatusLightAdapter());
        checkUpperConfig();
    }

    private void checkLowerConfig() {
        assertEquals(config.getLowerStatusLightAdapter().getAdapterClassName(), "TestAdapterA");
        assertEquals(config.getLowerStatusLightAdapter().getRefreshRate(), 100);
        assertEquals(config.getLowerStatusLightAdapter().getLightCount(), 15);
        assertEquals(config.getLowerStatusLightAdapter().getAttribute("testAttributeA"), "test");
    }

    private void checkUpperConfig() {
        assertEquals(config.getUpperStatusLightAdapter().getAdapterClassName(), "TestAdapterB");
        assertEquals(config.getUpperStatusLightAdapter().getRefreshRate(), 200);
        assertEquals(config.getUpperStatusLightAdapter().getLightCount(), 30);
        assertEquals(config.getUpperStatusLightAdapter().getAttribute("testAttributeB"), "test");
    }
}
