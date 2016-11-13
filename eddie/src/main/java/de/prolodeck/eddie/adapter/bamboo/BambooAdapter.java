package de.prolodeck.eddie.adapter.bamboo;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import de.prolodeck.eddie.adapter.CurrentState;
import de.prolodeck.eddie.adapter.SystemAdapter;
import de.prolodeck.eddie.adapter.bamboo.model.BambooResponseDTO;
import de.prolodeck.eddie.adapter.bamboo.model.BambooResultDTO;
import de.prolodeck.eddie.configuration.AdapterConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

/**
 * Use the bamboo rest api https://developer.atlassian.com/bamboodev/rest-apis to collect the needed information.
 *
 * Created by grebe on 28.10.2016.
 */
public class BambooAdapter implements SystemAdapter {

    private static final Logger log = LoggerFactory.getLogger(BambooAdapter.class);

    private BambooConfig config;

    @Override
    public final void init(AdapterConfig config) {
        this.config = new BambooConfig(config);
    }

    @Override
    public final List<CurrentState> getStates() {
        log.info("Loading build states from bamboo");

        final Client client = ClientBuilder.newClient();
        client.register(HttpAuthenticationFeature.basic(config.getUsername(), config.getPassword()));
        client.register(JacksonJsonProvider.class);

        // http://de-adn-4rxtmx1:6990/bamboo/rest/api/latest/result/<prj key>
        final WebTarget target = client.target(config.getUrl())
                .path("bamboo/rest/api/latest/result/" + config.getProject())
                .queryParam("os_authType", "basic");

        final Response response = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        final BambooResponseDTO bambooResponse = response.readEntity(BambooResponseDTO.class);

        final List<CurrentState> result = new LinkedList<>();
        for (BambooResultDTO nextJobResult : bambooResponse.getResults().getResult()) {
            result.add(new CurrentState(nextJobResult.getKey(), BambooStateType.valueOfState(nextJobResult.getState())));
        }

        return result;
    }

}
