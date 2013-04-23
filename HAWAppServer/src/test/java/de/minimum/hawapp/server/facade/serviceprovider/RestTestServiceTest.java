package de.minimum.hawapp.server.facade.serviceprovider;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestTestServiceTest extends RestTest {

    @Test
    public void test() {
        WebResource webResource = this.client.resource(RestTest.REST_SERVICE_ADDRESS + "/testservice");

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        checkResponse(response);

        String output = response.getEntity(String.class);
        Assert.assertEquals(
                        "{ \"parent\": { \"firstObject\": { \"text\": \"Hello World\", \"year\": 2013 }, \"secondOobject\": { \"count\": 3 } } }",
                        output);
    }

}
