package de.minimum.hawapp.server.facade.serviceprovider;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.hibernate.Session;

import com.sun.jersey.api.json.JSONConfiguration;

import de.minimum.hawapp.server.calendar.api.CategoryBO;
import de.minimum.hawapp.server.calendar.api.LectureBO;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.HibernateSessionMgr;
import de.minimum.hawapp.server.persistence.calendar.Semester;

@Path("/calendarservice")
public class CalendarService {
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public String test(){
		return "calendar rest service";
	}
	
	@GET
    @Path("semester")
    @Consumes(MediaType.APPLICATION_JSON)
    public Set<Semester> getAllSemester() {
		return null;
    }
    @GET
    @Path("semester/{semesterUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Semester getSemester(@PathParam("semesterUuid") String uuid) {
    	return null;
    }
    @GET
    @Path("category/{categoryUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryBO getCategory(@PathParam("categoryUuid") String uuid){
    	return null;
    }
    @GET
    @Path("lecture/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public LectureBO getLecture(@PathParam("lectureUuid") String uuid){
    	return null;
    }
//    @GET
//    @Path("lecture/{lectureUuid}/changelog")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Change> getChangelog(@PathParam("lectureUuid") String uuid){
//    	return null;
//    }
//    @GET
//    @Path("lecture/lastModified/{lectureUuid}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Date lectureLastModified(@PathParam("lectureUuid") String uuid){
//    	return null;
//    }
//    @GET
//    @Path("appointment/{appointmentUuid}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Appointment getAppointment(@PathParam("appointmentUuid") String uuid){
//		return null;
//    	
//    }
//    @GET
//    @Path("appointment/lastModified/{appointmentUuid}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Appointment appointmentLastModified(@PathParam("appointmentUuid") String uuid){
//		return null;
//    	
//    }
//    @GET
//    @Path("appointment/create/{lectureUuid}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Appointment createAppointment(@PathParam("lectureUuid")String uuid){
//    	return null;
//    }
//    @GET
//    @Path("appointment/delete/{appointmentUuid}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public boolean deleteAppointment(@PathParam("appointmentUuid")String uuid){
//    	return false;
//    }
//    @PUT
//    @Path("appointment/modify")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public boolean modifyAppointment(Appointment appointment){
//    	return false;
//    }
    
}
