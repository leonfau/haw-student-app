package de.minimum.hawapp.server.facade.serviceprovider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.minimum.hawapp.server.blackboard.api.BlackboardManager;
import de.minimum.hawapp.server.facade.util.ServiceProviderLogger;

/**
 * Example resource class hosted at the URI path "/testservice"
 */
@Path("/testservice")
public class RestTestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlackboardManager.class);

    /**
     * Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * 
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET
    @Produces("application/json")
    public String getTest(@Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Test-Request", RestTestService.LOGGER, req);
        return "{ \"parent\": { \"firstObject\": { \"text\": \"Hello World\", \"year\": 2013 }, \"secondOobject\": { \"count\": 3 } } }";
    }
}
