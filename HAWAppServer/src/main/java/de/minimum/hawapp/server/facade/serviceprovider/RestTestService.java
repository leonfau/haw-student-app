package de.minimum.hawapp.server.facade.serviceprovider;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Example resource class hosted at the URI path "/testservice"
 */
@Path("/testservice")
public class RestTestService {

    /**
     * Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * 
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET
    @Produces("text/plain")
    public String getTest() {
        return "Hello World!";
    }
}
