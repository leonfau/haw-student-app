package de.minimum.hawapp.server.facade.serviceprovider;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.resource.Singleton;

import de.minimum.hawapp.server.calendar.api.AppointmentBO;
import de.minimum.hawapp.server.calendar.api.CalendarManager;
import de.minimum.hawapp.server.calendar.api.CategoryBO;
import de.minimum.hawapp.server.calendar.api.LectureBO;
import de.minimum.hawapp.server.calendar.parser.CalendarParseMgr;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.calendar.AppointmentPO;
import de.minimum.hawapp.test.util.CleanUpHelper;

@Singleton
@Path("/calendarservice")
public class CalendarService {

    private CalendarManager calMngr = ManagerFactory.getManager(CalendarManager.class);
    
    @GET
    @Path("testContext/start")
    @Produces(MediaType.TEXT_PLAIN)
    public String startTestContext(){
    	String path="";
    	try{
    		CleanUpHelper.CLEANUPHELPER_INSTANCE.startAutoCleanUp();
    		CalendarParseMgr cl=new CalendarParseMgr();
    		path=cl.getClass().getClassLoader().getResource("de/minimum/hawapp/server/facade/serviceprovider/Sem_I.txt").getFile();
    		cl.parseFromFileToDB(path, "Cp1250");
    		
    	}catch(Throwable e){
    		return path +" " +e.getMessage();
    	}
    	return "Test Context ready";
    }
    @GET
    @Path("testContext/stop")
    @Produces(MediaType.TEXT_PLAIN)
    public String stopTestContext(){
    	try{
    		CleanUpHelper.CLEANUPHELPER_INSTANCE.cleanUpAndStop();
    		
    	}catch(Throwable e){
    		return e.getMessage();
    	}
    	return "Test Context stoped";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "calendar rest service";
    
    }
    @GET
    @Path("category/")
    @Produces(MediaType.APPLICATION_JSON)
    public Set< ? extends CategoryBO> getallCategory() {
        return this.calMngr.getAllCategories();
    }
    @GET
    @Path("category/{categoryUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryBO getCategory(@PathParam("categoryUuid") String uuid) {
        return this.calMngr.getCategoryBO(uuid);
    }

    @SuppressWarnings("unchecked")
	@GET
    @Path("lecture/ByCategory/{categoryUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<LectureBO> getLecturesFromCategory(@PathParam("categoryUuid") String uuid) {
        return (Set<LectureBO>) this.calMngr.getLecturesFromCategory(uuid);
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
    @SuppressWarnings("unchecked")
	@GET
    @Path("appointment/ByLecture/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<AppointmentBO> getAppointmentFromLecture(@PathParam("lectureUuid") String
    uuid){
      return (Set<AppointmentBO>) this.calMngr.getAppointmentsFromLecture(uuid);
    }
     @GET
     @Path("appointment/{appointmentUuid}")
     @Produces(MediaType.APPLICATION_JSON)
     public AppointmentBO getAppointment(@PathParam("appointmentUuid") String
     uuid){
       return this.calMngr.getAppointment(uuid);
     }
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
