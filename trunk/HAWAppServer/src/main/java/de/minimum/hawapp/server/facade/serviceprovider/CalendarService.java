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
import de.minimum.hawapp.server.calendar.api.CalendarParseManager;
import de.minimum.hawapp.server.calendar.api.CategoryBO;
import de.minimum.hawapp.server.calendar.api.ChangeMessageBO;
import de.minimum.hawapp.server.calendar.api.LectureBO;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.test.util.CleanUpHelper;

@Singleton
@Path("/calendarservice")
public class CalendarService {

    private final CalendarManager calMngr = ManagerFactory.getManager(CalendarManager.class);

    public final String TEST_CONTEXT = "testContext";
    public final String CATEGORY = "category";
    public final String CATEGORIES = "categories";
    public final String LECTURE = "lecture";
    public final String LECTURES = "lectures";
    public final String APPOINTMENT = "appointment";
    public final String APPOINTMENTS = "appointments";
    public final String CHANGEMESSAGE = "changeMessage";
    public final String CHANGEMESSAGES = "changeMessages";

    @GET
    @Path(TEST_CONTEXT + "/start")
    @Produces(MediaType.TEXT_PLAIN)
    public String startTestContext() {
        String path = "";
        try {
            calMngr.deleteAllCalendarDataFromDB();
            CleanUpHelper.CLEANUPHELPER_INSTANCE.startAutoCleanUp();
            final CalendarParseManager cl = ManagerFactory.getManager(CalendarParseManager.class);
            path = cl.getClass().getClassLoader()
                            .getResource("de/minimum/hawapp/server/facade/serviceprovider/Sem_I.txt").getFile();
            cl.parseFromFileToDB(path, "Cp1250");

        }
        catch(final Throwable e) {
            return path + " " + e.getMessage();
        }
        return "Test Context ready";
    }

    @GET
    @Path(TEST_CONTEXT + "/stop")
    @Produces(MediaType.TEXT_PLAIN)
    public String stopTestContext() {
        try {
            CleanUpHelper.CLEANUPHELPER_INSTANCE.cleanUpAndStop();

        }
        catch(final Throwable e) {
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
    @Path(CATEGORIES)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<? extends CategoryBO> getallCategory() {
        return calMngr.getAllCategories();
    }

    @GET
    @Path(CATEGORY + "/{categoryUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryBO getCategory(@PathParam("categoryUuid")
    final String uuid) {
        return calMngr.getCategoryBO(uuid);
    }

    @GET
    @Path(LECTURES + "/{categoryUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Set<LectureBO> getLecturesFromCategory(@PathParam("categoryUuid")
    final String uuid) {

        return (Set<LectureBO>)calMngr.getLecturesFromCategory(uuid);
    }

    @GET
    @Path(LECTURE + "/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public LectureBO getLecture(@PathParam("lectureUuid")
    final String uuid) {
        return calMngr.getLectureBO(uuid);
    }

    @GET
    @Path(APPOINTMENTS + "/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Set<AppointmentBO> getAppointmentFromLecture(@PathParam("lectureUuid")
    final String uuid) {
        return (Set<AppointmentBO>)calMngr.getAppointmentsFromLecture(uuid);
    }

    @GET
    @Path(APPOINTMENT + "/{appointmentUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public AppointmentBO getAppointment(@PathParam("appointmentUuid")
    final String uuid) {
        return calMngr.getAppointment(uuid);
    }

    // @PUT
    // @Path(APPOINTMENT + "/new/{lectureUuid}")
    // public Boolean createNewAppointment(final NewAppointment app,
    // @PathParam("lectureUuid")
    // final String uuid) {
    // try {
    // final LectureBO lecture = calMngr.getLectureBO(uuid);
    // final AppointmentPO appointment = app.getNewAppointment();
    // appointment.setLecture((LecturePO)lecture);
    // calMngr.createAppointment(appointment);
    // final ChangeMessagePO changemessage = app.getChangeMessage();
    //
    // changemessage.setLecture((LecturePO)lecture);
    // changemessage.setLecture(appointment.getLecture());
    // calMngr.createChangeMessage(changemessage);
    // }
    // catch(final Exception e) {
    // e.printStackTrace();
    // return false;
    // }
    // return true;
    // }

    @GET
    @Path(CHANGEMESSAGES + "/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Set<ChangeMessageBO> getChangeMessagesFromLecture(@PathParam("lectureUuid")
    final String uuid) {
        return (Set<ChangeMessageBO>)calMngr.getChangeMessageFromLecture(uuid);
    }

    @GET
    @Path(CHANGEMESSAGE + "/{changeMessageUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public ChangeMessageBO getChangeMessage(@PathParam("changeMessageUuid")
    final String uuid) {
        return calMngr.getChangeMessage(uuid);
    }

}