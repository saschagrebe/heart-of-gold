package de.prolodeck.eddie.integration;

import de.prolodeck.eddie.heartofgold.Eddie;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.Test;

/**
 * Created by grebe on 06.11.2016.
 */
public class IntegrationTest {

    private static final String serverIp = "192.168.0.111";

    private static final int port = 5999;

    @Test
    public void test() throws Exception {
        final Eddie eddie = new Eddie(serverIp, port);
        for(int i = 0; i < 15; i++) {
            eddie.lower().switchLight(i, 0);
            eddie.upper().switchLight(i, 0);
        }
        for(int i = 0; i < 15; i++) {
            eddie.lower().switchLight(i, 8000);
            eddie.upper().switchLight(i, 100);
            Thread.sleep(1000);
        }
        eddie.quit();
    }

}
