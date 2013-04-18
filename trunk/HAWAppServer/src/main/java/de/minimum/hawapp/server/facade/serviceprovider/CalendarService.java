package de.minimum.hawapp.server.facade.serviceprovider;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.resource.Singleton;

import de.minimum.hawapp.server.calendar.api.CalendarManager;
import de.minimum.hawapp.server.calendar.api.CategoryBO;
import de.minimum.hawapp.server.calendar.api.LectureBO;
import de.minimum.hawapp.server.calendar.api.SemesterBO;
import de.minimum.hawapp.server.context.ManagerFactory;

@Singleton
@Path("/calendarservice")
public class CalendarService {

    private CalendarManager calMngr = ManagerFactory.getManager(CalendarManager.class);

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public String test() {
        return "calendar rest service";
    }

    @GET
    @Path("semester")
    @Consumes(MediaType.APPLICATION_JSON)
    public Set<SemesterBO> getAllSemester() {
        return this.calMngr.getAllSemesterBO();
    }

    @GET
    @Path("semester/{semesterUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public SemesterBO getSemester(@PathParam("semesterUuid") String uuid) {
        return this.calMngr.getSemesterBO(uuid);
    }

    @GET
    @Path("category/{categoryUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryBO getCategory(@PathParam("categoryUuid") String uuid) {
        return this.calMngr.getCategoryBO(uuid);
    }

    @GET
    @Path("lecture/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public LectureBO getLecture(@PathParam("lectureUuid") String uuid) {
        return this.calMngr.getLectureBO(uuid);
    }
    // @GET
    // @Path("lecture/{lectureUuid}/changelog")
    // @Produces(MediaType.APPLICATION_JSON)
    // public List<Change> getChangelog(@PathParam("lectureUuid") String uuid){
    // return null;
    // }
    // @GET
    // @Path("lecture/lastModified/{lectureUuid}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Date lectureLastModified(@PathParam("lectureUuid") String uuid){
    // return null;
    // }
    // @GET
    // @Path("appointment/{appointmentUuid}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Appointment getAppointment(@PathParam("appointmentUuid") String
    // uuid){
    // return null;
    //
    // }
    // @GET
    // @Path("appointment/lastModified/{appointmentUuid}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Appointment appointmentLastModified(@PathParam("appointmentUuid")
    // String uuid){
    // return null;
    //
    // }
    // @GET
    // @Path("appointment/create/{lectureUuid}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Appointment createAppointment(@PathParam("lectureUuid")String
    // uuid){
    // return null;
    // }
    // @GET
    // @Path("appointment/delete/{appointmentUuid}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public boolean deleteAppointment(@PathParam("appointmentUuid")String
    // uuid){
    // return false;
    // }
    // @PUT
    // @Path("appointment/modify")
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    // public boolean modifyAppointment(Appointment appointment){
    // return false;
    // }

}
