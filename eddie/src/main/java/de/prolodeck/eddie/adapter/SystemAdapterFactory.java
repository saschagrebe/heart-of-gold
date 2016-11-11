package de.prolodeck.eddie.adapter;

import de.prolodeck.eddie.configuration.AdapterConfig;

/**
 * Created by grebe on 02.11.2016.
 */
public class SystemAdapterFactory {

    public static SystemAdapter getInstance(AdapterConfig config) {
        try {
            final Class<? extends SystemAdapter> adapterClass = (Class<? extends SystemAdapter>) Class.forName(config.getAdapterClassName());
            final SystemAdapter adapter = adapterClass.newInstance();
            adapter.init(config);

            return adapter;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
