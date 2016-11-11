package de.prolodeck.eddie.configuration;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by grebe on 28.10.2016.
 */
public class AdapterConfig {

    private String adapterClassName;

    private int refreshRate = 60;

    private int lightCount = 15;

    private Map<String, String> attributes = new HashMap<>();

    public String getAdapterClassName() {
        return adapterClassName;
    }

    public void setAdapterClassName(String adapterClassName) {
        this.adapterClassName = adapterClassName;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    @JsonAnySetter
    public void addAttribute(final String key, final String value) {
        this.attributes.put(key, value);
    }

    public String getAttribute(final String name) {
        return attributes.get(name);
    }

    public int getLightCount() {
        return lightCount;
    }

    public void setLightCount(int lightCount) {
        this.lightCount = lightCount;
    }

    public boolean isValid() {
        return StringUtils.isNotEmpty(adapterClassName);
    }
}
