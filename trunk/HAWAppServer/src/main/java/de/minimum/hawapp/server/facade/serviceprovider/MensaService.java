package de.minimum.hawapp.server.facade.serviceprovider;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.spi.resource.Singleton;

import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.facade.util.ServiceProviderLogger;
import de.minimum.hawapp.server.mensa.Day;
import de.minimum.hawapp.server.mensa.DayPlan;
import de.minimum.hawapp.server.mensa.MensaManager;

@Singleton
@Path("/mensaservice")
public class MensaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MensaService.class);
    private MensaManager mensaMngr = ManagerFactory.getManager(MensaManager.class);

    /**
     * Ermittelt den Speiseplan für einen bestimmten Tag
     * 
     * @param day
     *            Tag für den der Speiseplan ermittelt werden soll
     *            ("Montag","Dienstag","Mittwoch","Donnerstag","Freitag")
     * @return DayPlan Objekt
     */
    @Path("/dayplan/{day}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DayPlan getDayPlan(@PathParam("day") Day day, @Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Request for dayplan of " + day, MensaService.LOGGER, req);
        return this.mensaMngr.getDayPlan(day);
    }

    /**
     * Ermittelt den Speiseplan für die aktuelle Woche
     * 
     * @return Eine Liste von DayPlan
     */
    @Path("/weekplan")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DayPlan> getWeekPlan(@Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Request for weekplan", MensaService.LOGGER, req);
        return this.mensaMngr.getWeekPlan();
    }

    @POST
    @Path("/rate/{mealId}/stars/{stars}")
    public Response rate(@PathParam("mealId") UUID id, @PathParam("stars") int stars, @Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Rating of meal: " + id + " Rating: " + stars + " Stars", MensaService.LOGGER,
                        req);
        this.mensaMngr.rate(id, stars);
        return Response.ok().build();
    }
}
