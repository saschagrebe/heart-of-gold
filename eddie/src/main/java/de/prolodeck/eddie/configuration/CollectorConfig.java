package de.prolodeck.eddie.configuration;

import com.google.inject.Singleton;

/**
 * Created by grebe on 28.10.2016.
 */
@Singleton
public class CollectorConfig {

    private String serverIp;

    private int serverPort;

    private AdapterConfig upperStatusLightAdapter;

    private AdapterConfig lowerStatusLightAdapter;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public AdapterConfig getUpperStatusLightAdapter() {
        return upperStatusLightAdapter;
    }

    public void setUpperStatusLightAdapter(AdapterConfig upperStatusLightAdapter) {
        this.upperStatusLightAdapter = upperStatusLightAdapter;
    }

    public AdapterConfig getLowerStatusLightAdapter() {
        return lowerStatusLightAdapter;
    }

    public void setLowerStatusLightAdapter(AdapterConfig lowerStatusLightAdapter) {
        this.lowerStatusLightAdapter = lowerStatusLightAdapter;
    }
}
