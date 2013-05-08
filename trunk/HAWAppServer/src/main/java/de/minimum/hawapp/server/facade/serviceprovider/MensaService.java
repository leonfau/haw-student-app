package de.minimum.hawapp.server.facade.serviceprovider;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.spi.resource.Singleton;

import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.facade.util.ServiceProviderLogger;
import de.minimum.hawapp.server.facade.wrapper.mensa.RatingWrapper;
import de.minimum.hawapp.server.mensa.Day;
import de.minimum.hawapp.server.mensa.DayPlan;
import de.minimum.hawapp.server.mensa.MensaManager;
import de.minimum.hawapp.server.mensa.Rating;

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

    @PUT
    @Path("/positiverate/{mealId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Rating ratePositive(@PathParam("mealId") UUID id, @Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Positive rating of meal: " + id, MensaService.LOGGER, req);
        return new RatingWrapper(this.mensaMngr.rateMealPositive(id));
    }

    @PUT
    @Path("/negativerate/{mealId}")
    public Rating rateNegative(@PathParam("mealId") UUID id, @Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Negative rating of meal: " + id, MensaService.LOGGER, req);
        return new RatingWrapper(this.mensaMngr.rateMealNegative(id));
    }
}
