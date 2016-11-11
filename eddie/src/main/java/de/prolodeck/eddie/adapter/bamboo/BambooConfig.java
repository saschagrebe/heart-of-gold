package de.prolodeck.eddie.adapter.bamboo;

import de.prolodeck.eddie.configuration.AdapterConfig;

/**
 * Created by grebe on 28.10.2016.
 */
public class BambooConfig {

    private final AdapterConfig delegate;

    public BambooConfig(final AdapterConfig delegate) {
        this.delegate = delegate;
    }

    public String getUsername() {
        return delegate.getAttribute("username");
    }

    public String getPassword() {
        return delegate.getAttribute("password");
    }

    public String getUrl() {
        return delegate.getAttribute("url");
    }

    public String getProject() {
        return delegate.getAttribute("project");
    }
}
