package de.minimum.hawapp.server.facade.serviceprovider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import de.minimum.hawapp.test.util.ServerStartRule;

public abstract class RestTest {
    public static final String REST_SERVICE_ADDRESS = ServerStartRule.SERVER_ADDRESS + "/rest";

    protected Client client;

    public RestTest(Client client) {
        this.client = client;
    }

    protected void checkResponse(ClientResponse response) {
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
    }
}
