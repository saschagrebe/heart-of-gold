package de.prolodeck.eddie.adapter;

import de.prolodeck.eddie.configuration.AdapterConfig;

import java.util.List;

/**
 * Created by grebe on 28.10.2016.
 */
public interface SystemAdapter {

    void init(AdapterConfig config);

    List<CurrentState> getStates();

}
