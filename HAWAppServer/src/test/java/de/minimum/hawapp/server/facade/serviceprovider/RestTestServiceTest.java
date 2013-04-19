package de.minimum.hawapp.server.facade.serviceprovider;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestTestServiceTest extends RestTest {

    public RestTestServiceTest(Client client) {
        super(client);
    }

    public void test() {
        WebResource webResource = this.client.resource(RestTest.REST_SERVICE_ADDRESS + "/testservice");

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String output = response.getEntity(String.class);
        Assert.assertEquals(
                        "{ \"parent\": { \"firstObject\": { \"text\": \"Hello World\", \"year\": 2013 }, \"secondOobject\": { \"count\": 3 } } }",
                        output);
    }

}
