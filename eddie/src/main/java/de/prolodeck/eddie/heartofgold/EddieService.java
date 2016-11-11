package de.prolodeck.eddie.heartofgold;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import de.prolodeck.eddie.configuration.CollectorConfig;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by grebe on 28.10.2016.
 */
public class EddieService {

    @Inject
    private EddieConnection eddie;

    @Inject
    @Named("Upper")
    private StatusLight upper;

    @Inject
    @Named("Lower")
    private StatusLight lower;

    public StatusLight upper() {
        return upper;
    }

    public StatusLight lower() {
        return lower;
    }

    public void quit() {
        eddie.quit();
    }

}
