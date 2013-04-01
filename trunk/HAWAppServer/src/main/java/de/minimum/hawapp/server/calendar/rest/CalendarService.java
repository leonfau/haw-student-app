package de.minimum.hawapp.server.calendar.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.minimum.hawapp.server.calendar.api.Appointment;
import de.minimum.hawapp.server.calendar.api.Category;
import de.minimum.hawapp.server.calendar.api.Lecture;
import de.minimum.hawapp.server.calendar.api.Semester;

@Path("calendar")
public class CalendarService {
    @GET
    @Path("semester")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Semester> getAllSemester() {
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
    public Category getCategory(@PathParam("categoryUuid") String uuid){
    	return null;
    }
    @GET
    @Path("lecture/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Lecture getLecture(@PathParam("lectureUuid") String uuid){
    	return null;
    }
    @GET
    @Path("lecture/lastModified/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Date lectureLastModified(@PathParam("lectureUuid") String uuid){
    	return null;
    }
    @GET
    @Path("appointment/{appointmentUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Appointment getAppointment(@PathParam("appointmentUuid") String uuid){
		return null;
    	
    }
    @GET
    @Path("appointment/lastModified/{appointmentUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Appointment appointmentLastModified(@PathParam("appointmentUuid") String uuid){
		return null;
    	
    }
    @GET
    @Path("appointment/create/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Appointment createAppointment(@PathParam("lectureUuid")String uuid){
    	return null;
    }
    @GET
    @Path("appointment/delete/{appointmentUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteAppointment(@PathParam("appointmentUuid")String uuid){
    	return false;
    }
    @PUT
    @Path("appointment/modify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean modifyAppointment(Appointment appointment){
    	return false;
    }
    
}
