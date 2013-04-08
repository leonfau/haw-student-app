package de.minimum.hawapp.server.facade.serviceprovider;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.resource.Singleton;

import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.mensa.Meal;
import de.minimum.hawapp.server.mensa.MensaManager;

@Singleton
@Path("/mensaservice")
public class MensaService {

    private MensaManager mensaMngr = ManagerFactory.getManager(MensaManager.class);

    /**
     * Ermittelt den Speiseplan für einen bestimmten Tag
     * 
     * @param day
     *            Tag für den der Speiseplan ermittelt werden soll
     *            ("Montag","Dienstag","Mittwoch","Donnerstag","Freitag")
     * @return Eine Liste der Speisen des Tages
     */
    @Path("/dayplan/{day}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Meal> getDayPlan(@PathParam("day") String day) {
        return this.mensaMngr.getDayPlan(day);
    }

    /**
     * Ermittelt den Speiseplan für die aktuelle Woche
     * 
     * @return Eine Map ('Wochentag' => 'Liste der Speisen')
     */
    @Path("/weekplan")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<Meal>> getWeekPlan() {
        return this.mensaMngr.getWeekPlan();
    }
}
