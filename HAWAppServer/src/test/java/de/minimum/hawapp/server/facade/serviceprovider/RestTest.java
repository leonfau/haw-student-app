package de.minimum.hawapp.server.facade.serviceprovider;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.junit.runner.RunWith;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import de.minimum.hawapp.test.util.runner.RunningServerRunner;

@RunWith(RunningServerRunner.class)
public abstract class RestTest {
    public static final String REST_SERVICE_ADDRESS = RunningServerRunner.SERVER_ADDRESS + "/rest";

    protected Client client;

    public RestTest() {
        ClientConfig cfg = new DefaultClientConfig();
        cfg.getClasses().add(JacksonJsonProvider.class);// Sonst funktioniert
                                                        // das Mapping auf
                                                        // Listen nicht
        this.client = Client.create(cfg);
    }

    protected void checkResponse(ClientResponse response) {
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
    }
}
