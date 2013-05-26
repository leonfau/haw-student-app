package de.minimum.hawapp.server.facade.serviceprovider;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.spi.resource.Singleton;

import de.minimum.hawapp.server.calendar.api.AppointmentBO;
import de.minimum.hawapp.server.calendar.api.CalendarManager;
import de.minimum.hawapp.server.calendar.api.CalendarParseManager;
import de.minimum.hawapp.server.calendar.api.CategoryBO;
import de.minimum.hawapp.server.calendar.api.ChangeMessageBO;
import de.minimum.hawapp.server.calendar.api.LectureBO;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.calendar.AppointmentPO;
import de.minimum.hawapp.server.persistence.calendar.LecturePO;
import de.minimum.hawapp.test.util.CleanUpHelper;

@Singleton
@Path("/calendarservice")
public class CalendarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarService.class);
    private final CalendarManager calMngr = ManagerFactory.getManager(CalendarManager.class);

    public static final String TEST_CONTEXT = "testContext";
    public static final String CATEGORY = "category";
    public static final String CATEGORIES = "categories";
    public static final String LECTURE = "lecture";
    public static final String LECTURES = "lectures";
    public static final String APPOINTMENT = "appointment";
    public static final String APPOINTMENTS = "appointments";
    public static final String CHANGEMESSAGE = "changeMessage";
    public static final String CHANGEMESSAGES = "changeMessages";
    public static final String LASTMODIFIED = "lastModified";
    private static final String NEW = "new";
    private static final String MODIFY = "modify";

    @GET
    @Path(CalendarService.TEST_CONTEXT + "/start")
    @Produces(MediaType.TEXT_PLAIN)
    public String startTestContext() {
        String path = "";
        try {
            calMngr.deleteAllCalendarDataFromDB();
            CleanUpHelper.CLEANUPHELPER_INSTANCE.startAutoCleanUp();
            final CalendarParseManager cl = ManagerFactory.getManager(CalendarParseManager.class);
            path = cl.getClass().getClassLoader()
                            .getResource("de/minimum/hawapp/server/facade/serviceprovider/Sem_I_utf8.txt").getFile();
            cl.parseFromFileToDB(path, "utf8");

        }
        catch(final Throwable e) {
            return path + " " + e.getMessage();
        }
        return "Test Context ready";
    }

    @GET
    @Path(CalendarService.TEST_CONTEXT + "/stop")
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
    @Path(CalendarService.CATEGORIES)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<? extends CategoryBO> getallCategory() {
        return calMngr.getAllCategories();
    }

    @GET
    @Path(CalendarService.CATEGORIES + "/" + LASTMODIFIED)
    @Produces(MediaType.APPLICATION_JSON)
    public Date getallCategoryLastModified() {
        final Set<? extends CategoryBO> categories = calMngr.getAllCategories();
        Date nearestlastModified = null;
        for(final CategoryBO cat : categories) {
            final Date catLastModified = cat.getLastModified();
            if (nearestlastModified == null || nearestlastModified.before(catLastModified)) {
                nearestlastModified = catLastModified;
            }
        }
        return nearestlastModified;
    }

    @GET
    @Path(CalendarService.CATEGORY + "/{categoryUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryBO getCategory(@PathParam("categoryUuid")
    final String uuid) {
        return calMngr.getCategoryBO(uuid);
    }

    @GET
    @Path(CalendarService.CATEGORY + "/" + CalendarService.LASTMODIFIED + "/{categoryUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Date getCategoryLastModified(@PathParam("categoryUuid")
    final String uuid) {
        final CategoryBO category = calMngr.getCategoryBO(uuid);
        if (category == null) {
            return new Date(System.currentTimeMillis());
        }
        else {
            return category.getLastModified();
        }
    }

    @GET
    @Path(CalendarService.LECTURES + "/{categoryUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Set<LectureBO> getLecturesFromCategory(@PathParam("categoryUuid")
    final String uuid) {

        return (Set<LectureBO>)calMngr.getLecturesFromCategory(uuid);
    }

    @GET
    @Path(CalendarService.LECTURE + "/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public LectureBO getLecture(@PathParam("lectureUuid")
    final String uuid) {
        return calMngr.getLectureBO(uuid);
    }

    @GET
    @Path(CalendarService.LECTURE + "/" + CalendarService.LASTMODIFIED + "/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Date getLectureLastModified(@PathParam("lectureUuid")
    final String uuid) {
        final LectureBO lecture = calMngr.getLectureBO(uuid);
        if (lecture == null) {
            return new Date(System.currentTimeMillis());
        }
        else {
            return lecture.getLastModified();
        }
    }

    @GET
    @Path(CalendarService.APPOINTMENTS + "/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Set<AppointmentBO> getAppointmentFromLecture(@PathParam("lectureUuid")
    final String uuid) {
        return (Set<AppointmentBO>)calMngr.getAppointmentsFromLecture(uuid);
    }

    @GET
    @Path(CalendarService.APPOINTMENT + "/{appointmentUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public AppointmentBO getAppointment(@PathParam("appointmentUuid")
    final String uuid) {
        return calMngr.getAppointment(uuid);
    }

    @GET
    @Path(CalendarService.APPOINTMENT + "/" + CalendarService.LASTMODIFIED + "/{appointmentUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Date getAppointmentLastModified(@PathParam("appointmentUuid")
    final String uuid) {
        final AppointmentBO appointment = calMngr.getAppointment(uuid);
        if (appointment == null) {
            return new Date(System.currentTimeMillis());
        }
        else {
            return appointment.getLastModified();
        }
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
    @Path(CalendarService.CHANGEMESSAGES + "/{lectureUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Set<ChangeMessageBO> getChangeMessagesFromLecture(@PathParam("lectureUuid")
    final String uuid) {
        return (Set<ChangeMessageBO>)calMngr.getChangeMessageFromLecture(uuid);
    }

    @GET
    @Path(CalendarService.CHANGEMESSAGE + "/{changeMessageUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public ChangeMessageBO getChangeMessage(@PathParam("changeMessageUuid")
    final String uuid) {
        return calMngr.getChangeMessage(uuid);
    }

    @GET
    @Path(CalendarService.CHANGEMESSAGE + "/" + CalendarService.LASTMODIFIED + "/{appointmentUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Date getChangeMessageLastModified(@PathParam("appointmentUuid")
    final String uuid) {
        final ChangeMessageBO changeMessage = calMngr.getChangeMessage(uuid);
        if (changeMessage == null) {
            return new Date(System.currentTimeMillis());
        }
        else {
            return changeMessage.getLastModified();
        }
    }

    @POST
    @Path(CalendarService.LECTURE + "/{lectureUuid}/" + CalendarService.NEW)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewAppointment(@PathParam("lectureUuid")
    final String uuid, final AppointmentPO appointment) {
        try {
            final LecturePO lecture = (LecturePO)calMngr.getLectureBO(uuid);
            appointment.setLecture(lecture);
            final AppointmentBO newAppointment = calMngr.createAppointment(appointment);
            final String whatwasChanged = String.format("Ein neuer Termin wurde hinzugefügt: %s", newAppointment);
            calMngr.createChangeMessage(lecture, newAppointment.getLastModified(), "", whatwasChanged, "anonymous");
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

        return Response.ok().build();
    }

    @PUT
    @Path(CalendarService.APPOINTMENT + "/" + CalendarService.MODIFY)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean modifyAppointment(final AppointmentPO appointment) {
        try {
            final AppointmentBO oldAppointment = calMngr.getAppointment(appointment.getUuid());

            final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,
                            Locale.GERMANY);
            final StringBuilder whatwasChanged = new StringBuilder();

            whatwasChanged.append(String.format("Beim Termin %s wurde folgendes geändert: ", oldAppointment));
            if (!appointment.getName().equals(oldAppointment.getName())) {
                whatwasChanged.append(String.format("\n Name von %s zu %s", oldAppointment.getName(),
                                appointment.getName()));
            }
            if (!appointment.getBegin().equals(oldAppointment.getBegin())) {
                whatwasChanged.append(String.format(" \n Begin von %s zu %s",
                                dateFormat.format(oldAppointment.getBegin()), dateFormat.format(appointment.getBegin())));
            }
            if (!appointment.getEnd().equals(oldAppointment.getEnd())) {
                whatwasChanged.append(String.format("\n Ende von %s zu %s", dateFormat.format(oldAppointment.getEnd()),
                                dateFormat.format(appointment.getEnd())));
            }
            if (!appointment.getLocation().equals(oldAppointment.getLocation())) {
                whatwasChanged.append(String.format("\n Ort von %s zu %s", oldAppointment.getLocation(),
                                appointment.getLocation()));
            }
            if (!appointment.getDetails().equals(oldAppointment.getDetails())) {
                whatwasChanged.append("\n Die Termindetails wurden geändert");
            }

            calMngr.createChangeMessage(oldAppointment.getLecture(), new Date(System.currentTimeMillis()), "",
                            whatwasChanged.toString(), "anonymous");
            appointment.setLecture((LecturePO)oldAppointment.getLecture());
            calMngr.modify(appointment);
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }
}
