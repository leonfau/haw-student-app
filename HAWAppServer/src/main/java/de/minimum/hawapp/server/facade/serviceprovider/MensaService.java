package de.minimum.hawapp.server.facade.serviceprovider;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;

import com.sun.jersey.spi.resource.Singleton;

import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.mensa.MensaManager;

@Singleton
@Path("/mensaservice")
public class MensaService {

    private MensaManager mensaMngr = ManagerFactory.getManager(MensaManager.class);

    @Path("/dayplan/{day}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getDayPlan(@PathParam("day") String day) {
        return new JSONArray(this.mensaMngr.getDayPlan(day));
    }

}
